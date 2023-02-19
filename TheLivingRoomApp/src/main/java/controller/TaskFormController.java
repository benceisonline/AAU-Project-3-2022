package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;
import java.net.URL;
import java.util.*;

public class TaskFormController implements Initializable, UIMethods, DatabaseMethods {
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> frequencyDropdownMenu;
    @FXML
    private ComboBox<String> urgencyDropdownMenu;
    @FXML
    private ComboBox<String> typeDropdownMenu;
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private BorderPane taskFormBorderPane;
    @FXML
    private GridPane selectedEmployeeGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCssToButtons(cancelButton, "cancel-button");
        addCssToButtons(submitButton, "submit-button");

        makeButtonsCancelAndDefault(cancelButton, submitButton);

        stdUIForPages(frequencyDropdownMenu, urgencyDropdownMenu, typeDropdownMenu, null,
                null, null, null, null, false);

        populateTaskFormWithAssigneeBoxes();
    }

    public void cancelAndReturnToOverviewPage(ActionEvent event) {
        switchScene(taskFormBorderPane, "overview-manager-page.fxml");
    }

    public void submitAndReturnToOverviewPage(ActionEvent event) {
         boolean exportedTask = isTaskValidForSubmit(descriptionTextField, datePicker, typeDropdownMenu, titleTextField, frequencyDropdownMenu,
                urgencyDropdownMenu,null,  selectedEmployeeGridPane, false, null);

         if (exportedTask) {
             switchScene(taskFormBorderPane, "overview-manager-page.fxml");
         } else {
             errorDialog("Empty Fields", "The following fields cannot be empty: Title, Frequency, or Urgency");
         }
    }

    public void populateTaskFormWithAssigneeBoxes() {
        ArrayList<User> users = new ArrayList<>(DatabaseMethods.getEmployeesFromDB(false, "users"));

        int columns = 1;
        int rows = 1;

        if (!users.isEmpty()) {
            for (User user : users) {
                HBox hBox = assigneeBox(user, false);

                selectedEmployeeGridPane.add(hBox, columns, rows);

                rows++;
            }
        } else {
            selectedEmployeeGridPane.add(setNoAssigneesLabel(), 1, 1);
        }
    }

    public HBox setNoAssigneesLabel() {
        Label noAssigneesCreatedLabel = new Label("No Assignees Created");
        noAssigneesCreatedLabel.setMinWidth(225);
        noAssigneesCreatedLabel.setMinHeight(50);
        noAssigneesCreatedLabel.setWrapText(true);
        noAssigneesCreatedLabel.setMaxWidth(200);
        noAssigneesCreatedLabel.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(noAssigneesCreatedLabel);
        hBox.setMinWidth(200);

        return hBox;
    }
}
