package launcher;

import controller.UIMethods;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application implements UIMethods {

    @Override
    public void start(Stage stage) {createNewStage("login-page.fxml", 1024, 768);}
}