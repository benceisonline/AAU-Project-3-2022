package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import model.Task;
import java.text.ParseException;
import java.util.ArrayList;

public class TaskEmployeeController implements DatabaseMethods, UIMethods {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label taskLabel;
    @FXML
    private ComboBox<String> dropdownMenuPercent;
    @FXML
    private Button expandAssigneeButton;
    @FXML
    private HBox hBox;
    @FXML
    private VBox vBoxTheWholeBox;
    @FXML
    private Circle urgencyCircle;
    @FXML
    private ImageView overdueTask;
    @FXML
    private ImageView imageView;
    private ArrayList<String> assignees = new ArrayList<>();

    public void setTaskBoxToUI(Task task) {
        assignees = task.getAssignees();
        stdUIForTaskBoxes(expandAssigneeButton, task, overdueTask, urgencyCircle, progressBar, taskLabel, dropdownMenuPercent);
        setDropdownMenuPercentValue(task.getProgress() * 100, dropdownMenuPercent);
    }

    public void showDescription(ActionEvent event) {
        Node parent = returnParentsParentNode(event);

        DescriptionController controller = new DescriptionController(parent.getId());
        makeModalDialog(controller, "description-page.fxml", 700, 450);
    }

    public void setTaskToInactive(ActionEvent event) {
        Node parent = returnParentsParentNode(event);
        completeTask(parent.getId(), "tasks", false);

        // remove p from parent's child list
        ((GridPane) parent.getParent()).getChildren().remove(parent);
    }

    public void updateProgressBar(ActionEvent event) throws ParseException {
        Node parent = returnParentsParentNode(event);
        double progress = updateProgressBarInDBAndReturnValue(parent.getId(), dropdownMenuPercent.getValue(), "tasks");
        progressBar.setProgress(progress);
    }

    public void addCommentToTask(ActionEvent event) {
        Node parent = returnParentsParentNode(event);

        CommentController commentController = new CommentController(parent.getId(), "Employee");
        makeModalDialog(commentController,"add-comment-page.fxml", 731, 500);
    }

    public void expandToViewAssignees(ActionEvent event) {
        expandToViewAssignees(expandAssigneeButton, hBox, vBoxTheWholeBox, imageView, assignees);
    }
}
