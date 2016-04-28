package org;
import javafx.application.Application;
import javafx.stage.Stage;
import org.viewControllers.SettingsViewController;

public class Main extends Application {

    private static Stage mainStage;
    private static SettingsViewController settingsController;

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        settingsController = SettingsViewController.initSettingsScene(mainStage);
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}
