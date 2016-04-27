package org.viewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controllers.settingsController;

import static org.Main.getMainStage;

public class settingsViewController {
    @FXML
    private CheckBox email;
    @FXML
    private ComboBox<String> hostsBox;
    @FXML
    private ComboBox<String> domainBox;
    @FXML
    private TextField emailLoginField;
    @FXML
    private TextField emailPasswordField;
    @FXML
    private CheckBox vk;
    @FXML
    private Button vkAuth;
    @FXML
    private CheckBox sms;
    @FXML
    private TextField smsLoginField;
    @FXML
    private TextField smsPasswordField;
    @FXML
    private Button chooseFile;
    @FXML
    private Button goNext;


    @FXML
    private void initialize() { //ЗАПОЛНИТЬ!!!//
    }

    @FXML
    private void choosingHostsBox() {
        if(choosingHostsBoxListener != null)
            choosingHostsBoxListener.onClick();
    }
    public void setChoosingHostsBoxListener(OnClickedListener choosingHostsBoxListener){
        this.choosingHostsBoxListener = choosingHostsBoxListener;
    }
    private OnClickedListener choosingHostsBoxListener = null;

    @FXML
    private void choosingDomainBox() {
        if (choosingDomainBoxListener != null)
            choosingDomainBoxListener.onClick();
    }
    public void setChoosingDomainBoxListener(OnClickedListener choosingDomainBoxListener){
        this.choosingDomainBoxListener = choosingDomainBoxListener;
    }
    private OnClickedListener choosingDomainBoxListener = null;

    @FXML
    private void choosingVkAuth(){
        if (choosingVkAuthListener != null)
            choosingVkAuthListener.onClick();
    }
    public void setChoosingVkAuthListener(OnClickedListener choosingVkAuthListener){
        this.choosingVkAuthListener = choosingVkAuthListener;
    }
    private OnClickedListener choosingVkAuthListener = null;

    @FXML
    private void choosingFile() {
        if(choosingFileListener !=null)
            choosingFileListener.onClick();
    }
    public void setChoosingFileListener(OnClickedListener choosingFileListener){
        this.choosingFileListener = choosingFileListener;
    }
    private OnClickedListener choosingFileListener = null;


    @FXML
    private void goNext() {
 //       if(goNextListener != null)
 //           goNextListener.onClick();
        messageViewController.initMessageScene();
    }
    public void setGoNextListener(OnClickedListener goNextListener){
        this.goNextListener = goNextListener;
    }
    private OnClickedListener goNextListener = null;

    public interface OnClickedListener{
        void onClick();
    }


    public CheckBox getEmail() {
        return email;
    }
    public ComboBox<String> getDomainBox() {
        return domainBox;
    }
    public TextField getEmailLoginField() {
        return emailLoginField;
    }
    public TextField getEmailPasswordField() {
        return emailPasswordField;
    }
    public CheckBox getVk() {
        return vk;
    }
    public CheckBox getSms() {
        return sms;
    }
    public TextField getSmsPasswordField() {
        return smsPasswordField;
    }
    public TextField getSmsLoginField() {
        return smsLoginField;
    }
    public Button getChooseFile() {
        return chooseFile;
    }

    public void setHostsBox(ComboBox<String> hostsBox) {
        this.hostsBox = hostsBox;
    }
    public void setEmail(CheckBox email) {
        this.email = email;
    }
    public void setVk(CheckBox vk) {
        this.vk = vk;
    }
    public void setSms(CheckBox sms) {
        this.sms = sms;
    }
    public void setEmailLoginField(TextField emailLoginField) {
        this.emailLoginField = emailLoginField;
    }
    public void setSmsLoginField(TextField smsLoginField) {
        this.smsLoginField = smsLoginField;
    }

    public static void initSettingsScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(settingsController.class.getClassLoader().getResource("view/settingsView.fxml"));
            AnchorPane settingsLayout;
            settingsLayout = loader.load();
            getMainStage().setScene(new Scene(settingsLayout));

            getMainStage().show();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
