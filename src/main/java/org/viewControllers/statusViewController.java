package org.viewControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import org.controllers.settingsController;

import static org.Main.getMainStage;

/**
 * Created by Дмитрий on 27.04.2016.
 */
public class statusViewController {

    public static void initStatusScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(settingsController.class.getClassLoader().getResource("view/statusView.fxml"));
            AnchorPane settingsLayout;
            settingsLayout = loader.load();
            getMainStage().setScene(new Scene(settingsLayout));

            getMainStage().show();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
