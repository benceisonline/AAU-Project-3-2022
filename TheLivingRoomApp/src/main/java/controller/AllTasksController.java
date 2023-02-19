package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.*;

public class AllTasksController implements Initializable, UIMethods, DatabaseMethods{
    @FXML
    private GridPane taskGrid;
    @FXML
    private BorderPane overviewAllTasksBorderPane;
    @FXML
    private ComboBox<String> viewDropdownMenu;
    @FXML
    private Button closeProgramButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateOverviewWithTaskBoxes(taskGrid, null, null, null, 0.0, null, null, null, true, true);
        addCssToButtons(closeProgramButton, "exit-button");
        viewDropdownMenu.getItems().addAll(
                "Employee", "Manager"
        );
    }

    public void refreshPage(ActionEvent event) {
        switchScene(overviewAllTasksBorderPane, "overview-history-page.fxml");
    }

    public void returnToManagerOverview(ActionEvent event){
        switchScene(overviewAllTasksBorderPane, "overview-manager-page.fxml");
    }

    public void changeView(ActionEvent event) {
        changeView(viewDropdownMenu, overviewAllTasksBorderPane);
    }

    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
}
