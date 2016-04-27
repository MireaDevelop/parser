package org.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by Дмитрий on 27.04.2016.
 */
public class sendingEndViewController {
    @FXML
    private Button sendingEndButton;

    @FXML
    public void setSendingEndButton(){
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
}
