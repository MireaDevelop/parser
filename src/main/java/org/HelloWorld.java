package org;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.viewControllers.settingsViewController;

public class HelloWorld extends Application {

    private Stage stage;
    private AnchorPane rootLayout;

    public static void main(String[] args) {
        Application.launch(HelloWorld.class, args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        this.stage.setTitle("Тестовый парсер");

        initRootLayout();
    }



        public void initRootLayout() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/settingsView.fxml"));
            rootLayout = loader.load();
            stage.setScene(new Scene(rootLayout));

            settingsViewController controller = loader.getController();
            //controller.setHelloWorld(this);

            stage.show();
        }
        catch(Exception e){
            System.out.print(e);
        }
    }

    public Stage getStage() {
        return stage;
    }
}
