package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController implements Initializable, UIMethods, DatabaseMethods {
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCssToButtons(cancelButton, "cancel-button");
        addCssToButtons(submitButton, "submit-button");

        makeButtonsCancelAndDefault(cancelButton, submitButton);

        roleComboBox.getItems().addAll(
                "All", "Bartender", "Cleaner"
        );
    }

    public void cancelAction(ActionEvent event) {
        closeStage(event);
    }

    public void submitAction(ActionEvent event) {
        boolean isUserTaskValid = createUserFromUI(firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, roleComboBox);
        if (isUserTaskValid) {
            closeStage(event);
        } else {
            errorDialog("The fields can't be empty", "");
        }
    }
}
