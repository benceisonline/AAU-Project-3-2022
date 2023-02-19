package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import model.Task;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.ne;

public class OverviewHistoryController implements Initializable, UIMethods, DatabaseMethods{
    @FXML
    private GridPane taskGrid;
    @FXML
    private BorderPane overviewHistoryBorderPane;
    @FXML
    private ComboBox<String> viewDropdownMenu;
    @FXML
    private Button closeProgramButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateOverviewWithTaskBoxes(taskGrid, null, null, null, 0.0, null, null, null, false, true);

        viewDropdownMenu.getItems().addAll(
                "Employee", "Manager"
        );

        addCssToButtons(closeProgramButton, "exit-button");
    }

    public void refreshPage(ActionEvent event) {
        switchScene(overviewHistoryBorderPane, "overview-history-page.fxml");
    }

    public void changeView(ActionEvent event) {
        changeView(viewDropdownMenu, overviewHistoryBorderPane);
    }

    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
}
