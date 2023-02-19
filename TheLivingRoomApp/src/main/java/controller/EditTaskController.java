package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.User;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class EditTaskController implements Initializable, UIMethods, DatabaseMethods {
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> frequencyDropdownMenu;
    @FXML
    private ComboBox<String> urgencyDropdownMenu;
    @FXML
    private ComboBox<String> typeDropdownMenu;
    @FXML
    private GridPane selectedEmployeeGridPane;
    @FXML
    private BorderPane taskEditBorderPane;
    @FXML
    private GridPane commentGridPane;
    @FXML
    private Label lastEditedLabel;
    private final String id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCssToButtons(cancelButton, "cancel-button");
        addCssToButtons(submitButton, "submit-button");

        makeButtonsCancelAndDefault(cancelButton, submitButton);

        stdUIForPages(frequencyDropdownMenu, urgencyDropdownMenu, typeDropdownMenu, null,
                null, null, null, null,false);

        descriptionTextArea.setWrapText(true);
        setValuesInFields();
        displayComments();
    }

    public void displayComments() {
        ArrayList<String> comments = new ArrayList<>(DatabaseMethods.getCommentsFromDB(id, "tasks"));

        int columns = 1;
        int rows = 1;

        try {
            for (String comment : comments) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("comment-box-page.fxml"));

                HBox hBox = loader.load();
                hBox.setPrefWidth(210);

                CommentBoxController commentBoxController = loader.getController();
                commentBoxController.setCommentToUI(comment, 205);

                commentGridPane.add(hBox, columns, rows);

                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelButtonLogic(ActionEvent event) {
        closeStage(event);
    }

    public void submitAndUpdateTask(ActionEvent event) {
        boolean exportedTask = isTaskValidForSubmit(null, datePicker, typeDropdownMenu, titleTextField, frequencyDropdownMenu,
                urgencyDropdownMenu, descriptionTextArea, selectedEmployeeGridPane, true, id);

        if (exportedTask) {
            switchScene(taskEditBorderPane, "overview-manager-page.fxml");
        } else {
            errorDialog("Empty Fields", "The following fields cannot be empty: Title, Frequency, Urgency, or Date");
        }
    }

    private void setValuesInFields() {
        Document doc = DatabaseMethods.getDocumentById(id, "tasks");

        titleTextField.setText(doc.get("title").toString());
        descriptionTextArea.setText(doc.get("description").toString());
        datePicker.setValue(convertToLocalDateViaInstant((Date) doc.get("date")));
        frequencyDropdownMenu.setValue(doc.get("frequency").toString());
        urgencyDropdownMenu.setValue(doc.get("urgency").toString());
        typeDropdownMenu.setValue(doc.get("type").toString());
        lastEditedLabel.setText(doc.get("lastEdit").toString());

        displaySelectedAssignees(doc);
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void displaySelectedAssignees(Document doc) {
        ArrayList<String> assignees = new ArrayList<>((Collection<String>) doc.get("assignees"));
        ArrayList<User> users = createUserList();

        int columns = 1;
        int rows = 1;

        for (User user : users) {
            HBox hBox;
            if (assignees.contains(user.getFullName())) {
                hBox = assigneeBox(user, true);
            } else {
                hBox = assigneeBox(user, false);
            }

            selectedEmployeeGridPane.add(hBox, columns, rows);

            rows++;
        }
    }

    private ArrayList<User> createUserList() {
        User generalUser = new User();
        generalUser.setFirstName("General");
        generalUser.setLastName("");
        generalUser.setRole("All");

        ArrayList<User> users = new ArrayList<>();
        users.add(generalUser);
        users.addAll(DatabaseMethods.getEmployeesFromDB(false, "users"));
        return users;
    }

    public EditTaskController(String id) {
        this.id = id;
    }
}
