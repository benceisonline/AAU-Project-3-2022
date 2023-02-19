package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Task;
import model.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class UserBoxController implements DatabaseMethods, UIMethods, Initializable {
    @FXML
    private Button deleteButton;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label emailLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCssToButtons(deleteButton, "cancel-button");
    }

    public void setUserBoxToUI(User user) {
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        roleLabel.setText(user.getRole());
        phoneNumberLabel.setText(user.getPhoneNumber());
        emailLabel.setText(user.getEmailAddress());
    }

    public void deleteTaskFromDB(ActionEvent event){
        Node parent = returnParentsParentNode(event);
        deleteFromDB(parent.getId(), "users");
        ((GridPane) parent.getParent()).getChildren().remove(parent);
    }

    public void editUser(ActionEvent event) {
        Node parent = returnParentsParentNode(event);

        EditUserController controller = new EditUserController(parent.getId());
        makeModalDialog(controller, "edit-user-page.fxml", 500, 600);
    }
}
