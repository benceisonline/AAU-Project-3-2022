package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import model.Task;

import java.text.ParseException;
import java.util.ArrayList;

public class TaskManagerController implements DatabaseMethods, UIMethods {
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
    private ImageView overdueTask;
    @FXML
    private Circle urgencyCircle;
    @FXML
    private ImageView imageView;
    private ArrayList<String> assignees = new ArrayList<>();

    public void setTaskBoxToUI(Task task) {
        assignees = task.getAssignees();
        stdUIForTaskBoxes(expandAssigneeButton, task, overdueTask, urgencyCircle, progressBar, taskLabel, dropdownMenuPercent);
        setDropdownMenuPercentValue(task.getProgress() * 100, dropdownMenuPercent);
    }

    public void editTask(ActionEvent event) {
        Node parent = returnParentsParentNode(event);

        EditTaskController controller = new EditTaskController(parent.getId());
        makeModalDialog(controller, "edit-task-page.fxml", 1024, 768);
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

        CommentController commentController = new CommentController(parent.getId(), "Manager");
        makeModalDialog(commentController,"add-comment-page.fxml", 731, 500);
    }

    public void expandToViewAssignees(ActionEvent event) {
        expandToViewAssignees(expandAssigneeButton, hBox, vBoxTheWholeBox, imageView, assignees);
    }
}
