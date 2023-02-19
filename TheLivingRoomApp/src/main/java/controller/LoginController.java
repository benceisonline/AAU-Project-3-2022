package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements UIMethods, Initializable {
    @FXML
    private BorderPane loginBorderPane;
    @FXML
    private Button closeProgramButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCssToButtons(closeProgramButton, "exit-button");
    }

    public void openEmployeeOverviewPage(ActionEvent event) {
        switchScene(loginBorderPane, "overview-employee-page.fxml");
    }

    public void openManagerOverviewPage(ActionEvent event) {
        PinCodeController controller = new PinCodeController();
        makeModalDialog(controller, "manager-pin-code-page.fxml", 300, 400);

        if (controller.isValidPinCode()) {
            switchScene(loginBorderPane, "overview-manager-page.fxml");
        }
    }

    public void openHistoryOverviewPage(ActionEvent event) {
        switchScene(loginBorderPane, "overview-history-page.fxml");
    }

    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
}
