package org.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        if (choosingDoItListener !=null)
            choosingDoItListener.onClick();
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
}
