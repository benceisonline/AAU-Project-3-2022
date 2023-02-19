package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.User;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController implements Initializable, UIMethods, DatabaseMethods {
    @FXML
    private BorderPane editEmployeeBorderPane;
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
    private final String id;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCssToButtons(cancelButton, "cancel-button");
        addCssToButtons(submitButton, "submit-button");

        makeButtonsCancelAndDefault(cancelButton, submitButton);

        roleComboBox.getItems().addAll(
                "All", "Bartender", "Cleaner"
        );

        setValuesInFields();
    }

    private void setValuesInFields() {
        Document doc = DatabaseMethods.getDocumentById(id, "users");

        firstNameTextField.setText(doc.get("firstName").toString());
        lastNameTextField.setText(doc.get("lastName").toString());
        roleComboBox.setValue(doc.get("role").toString());
        phoneNumberTextField.setText(doc.get("phoneNumber").toString());
        emailTextField.setText(doc.get("emailAddress").toString());
    }

    public void submitAndUpdateUser(ActionEvent event) {
        User user = new User(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), phoneNumberTextField.getText(), false,  roleComboBox.getValue());
        updateUser(this.id, "users", user);
        closeStage(event);
    }

    public void cancelAction(ActionEvent event) {
        closeStage(event);
    }

    public EditUserController(String id) {
        this.id = id;
    }
}
