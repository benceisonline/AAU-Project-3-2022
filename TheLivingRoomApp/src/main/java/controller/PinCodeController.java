package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class PinCodeController implements Initializable, UIMethods {
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;
    private boolean validPinCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeButtonsCancelAndDefault(cancelButton, okButton);
        addCssToButtons(cancelButton, "cancel-button");
        addCssToButtons(okButton, "submit-button");

        passwordField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (passwordField.getText().length() > 4) {
                String s = passwordField.getText().substring(0, 4);
                passwordField.setText(s);
            }
        });
    }

    public void clearPasswordField(ActionEvent event) {
        passwordField.clear();
    }

    public void pressNumberButton(ActionEvent event) {
        if (!(passwordField.getText().length() > 3)) {
            Button button = (Button) event.getSource();
            String number = button.getText();
            passwordField.appendText(number);
        }
    }

    public void cancelButtonAction(ActionEvent event) {
        closeStage(event);
    }

    public void submitButtonAction(ActionEvent event) {
        if (passwordField.getText().length() < 4) {
            passwordField.clear();
            errorDialog("Invalid Pin Code", "The Pin Code must be 4 characters");
        } else if (!(passwordField.getText().equals("1234"))) {
            passwordField.clear();
            errorDialog("Invalid Pin Code", "The Entered Pin Code is incorrect");
        } else {
            this.validPinCode = true;
            closeStage(event);
        }
    }

    public boolean isValidPinCode() {
        return validPinCode;
    }
}
