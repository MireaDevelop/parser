package org;
import javafx.application.Application;
import javafx.stage.Stage;
import org.controllers.settingsController;

public class Main extends Application {

    private static Stage MainStage;

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        MainStage = stage;
        MainStage.setTitle("Тестовый парсер");

        settingsController.initSettingsScene();
    }

    public static Stage getMainStage() {
        return MainStage;
    }
}
