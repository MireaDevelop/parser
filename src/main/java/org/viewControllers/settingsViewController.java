package org.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;

public class settingsViewController {
    @FXML
    private CheckBox email;
    @FXML
    private ComboBox<String> hostsBox;
    @FXML
    private ComboBox<String> domainBox;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private CheckBox vk;
    @FXML
    private Button vkAuth;
    @FXML
    private CheckBox sms;
    @FXML
    private Button chooseFile;
    @FXML
    private Button goNext;
    @FXML
    private Image image;

//    private Stage settingStage;

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
    private void dndFileChoose(){
        if (dndFileChooseListener !=null)
            dndFileChooseListener.onClick();
    }
    public void setDndFileChooseListener(OnClickedListener dndFileChooseListener){
        this.dndFileChooseListener = dndFileChooseListener;
    }
    private OnClickedListener dndFileChooseListener = null;


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
        if(goNextListener != null)
            goNextListener.onClick();
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
    public TextField getLoginField() {
        return loginField;
    }
    public TextField getPasswordField() {
        return passwordField;
    }
    public CheckBox getVk() {
        return vk;
    }
    public CheckBox getSms() {
        return sms;
    }
    public Button getChooseFile() {
        return chooseFile;
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
    public void setLoginField(TextField loginField) {
        this.loginField = loginField;
    }
}
