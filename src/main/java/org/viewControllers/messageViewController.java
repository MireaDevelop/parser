package org.viewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controllers.settingsController;

import static org.Main.getMainStage;

/**
 * Created by Дмитрий on 16.04.2016.
 */
public class messageViewController {

    @FXML
    private String messageThemeLabel;
    @FXML
    private TextField messageTheme;
    @FXML
    private String messageTextLabel;
    @FXML
    private TextArea messageText;
    @FXML
    private Button doIt;

    @FXML
    public void choosingDoIt(){
        //if (choosingDoItListener !=null)
        //    choosingDoItListener.onClick();
        statusViewController.initStatusScene();
    }
    public void setChoosingDoItListener(OnClickedListener choosingDoItListener){
        this.choosingDoItListener = choosingDoItListener;
    }
    private OnClickedListener choosingDoItListener = null;
    public interface OnClickedListener{
        void onClick();
    }

    public TextField getMessageTheme() {
        return messageTheme;
    }

    public TextArea getMessageText() {
        return messageText;
    }

    public static void initMessageScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(settingsController.class.getClassLoader().getResource("view/messageView.fxml"));
            AnchorPane settingsLayout;
            settingsLayout = loader.load();
            getMainStage().setScene(new Scene(settingsLayout));

            getMainStage().show();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
