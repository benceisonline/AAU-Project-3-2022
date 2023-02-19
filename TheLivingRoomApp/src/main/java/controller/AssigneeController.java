package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AssigneeController {
    @FXML
    private Label assigneeName;

    public void setAssigneeName(String name) {
        assigneeName.setText(name);
    }
}
