package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import model.Task;
import org.bson.types.ObjectId;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OverviewEmployeeController implements Initializable, UIMethods, DatabaseMethods {
    @FXML
    private GridPane taskGrid;
    @FXML
    private BorderPane overviewEmployeeBorderPane;
    @FXML
    private Button refreshFilter;
    @FXML
    private Label dateForShownDay;
    @FXML
    private HBox filterOptionsHBox;
    @FXML
    private ComboBox<String> frequencyDropdownMenu;
    @FXML
    private ComboBox<String> urgencyDropdownMenu;
    @FXML
    private ComboBox<String> typeDropdownMenu;
    @FXML
    private ComboBox<String> progressDropdownMenu;
    @FXML
    private ComboBox<String> assigneeDropdownMenu;
    @FXML
    private ComboBox<String> viewDropdownMenu;
    private String frequency;
    private String urgency;
    private String type;
    private double progress;
    private String progressValue;
    private String employee;
    private final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    private Date date;
    private final int oneDayMS = 86_400_000;
    @FXML
    private Button closeProgramButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date = new Date();

        stdUIForPages(frequencyDropdownMenu, urgencyDropdownMenu, typeDropdownMenu, progressDropdownMenu,
                assigneeDropdownMenu, refreshFilter, dateForShownDay, closeProgramButton, true);

        viewDropdownMenu.getItems().addAll(
                "History", "Manager"
        );

        populateOverviewPageWithTaskBoxes();
    }

    public void populateOverviewPageWithTaskBoxes() {
        String dateToParse = df.format(this.date);
        populateOverviewWithTaskBoxes(taskGrid, frequency, urgency, type, progress, progressValue, employee, dateToParse, false, false);
    }

    static Task createTaskToDisplay(ArrayList<Object> values) {
        ObjectId id = new ObjectId(values.get(0).toString());
        return new Task(id, (String) values.get(1), (String) values.get(2), (String) values.get(3), (String) values.get(4), (String) values.get(5), (Double) values.get(6), (Boolean) values.get(7), (ArrayList<String>) values.get(8), (ArrayList<String>) values.get(9), (Date) values.get(10), (String) values.get(11));
    }

    public void refreshPage(ActionEvent event) {
        switchScene(overviewEmployeeBorderPane, "overview-employee-page.fxml");
    }
    public void filterTasks(ActionEvent event) {
        filterSectionLogic(filterOptionsHBox, refreshFilter);
    }

    public void frequencyFilter(ActionEvent event) {
        if (!Objects.equals(frequency, frequencyDropdownMenu.getValue())) {
            frequency = frequencyDropdownMenu.getValue();
            populateOverviewPageWithTaskBoxes();
        }
    }

    public void urgencyFilter(ActionEvent event) {
        if (!Objects.equals(urgency, urgencyDropdownMenu.getValue())) {
            urgency = urgencyDropdownMenu.getValue();
            populateOverviewPageWithTaskBoxes();
        }
    }

    public void typeFilter(ActionEvent event) {
        if (!Objects.equals(type, typeDropdownMenu.getValue())) {
            type = typeDropdownMenu.getValue();
            populateOverviewPageWithTaskBoxes();
        }
    }

    public void progressFilter(ActionEvent event) throws ParseException {
        progress = progress(progressValue, progressDropdownMenu);
        progressValue = progressDropdownMenu.getValue();
        populateOverviewPageWithTaskBoxes();
    }

    public void assigneesFilter(ActionEvent event) {
        if (!Objects.equals(employee, assigneeDropdownMenu.getValue())) {
            employee = assigneeDropdownMenu.getValue();
            populateOverviewPageWithTaskBoxes();
        }
    }

    public void refreshFilters(ActionEvent event) {
        ListCell<String> frequencyText = new ListCell<>();
        frequencyText.setText("Frequency");
        ListCell<String> urgencyText = new ListCell<>();
        urgencyText.setText("Urgency");
        ListCell<String> typeText = new ListCell<>();
        typeText.setText("Type");
        ListCell<String> progressText = new ListCell<>();
        progressText.setText("Progress");
        ListCell<String> assigneeText = new ListCell<>();
        assigneeText.setText("Assignee");

        frequencyDropdownMenu.setButtonCell(frequencyText);
        urgencyDropdownMenu.setButtonCell(urgencyText);
        typeDropdownMenu.setButtonCell(typeText);
        progressDropdownMenu.setButtonCell(progressText);
        assigneeDropdownMenu.setButtonCell(assigneeText);

        String dateToParse = df.format(this.date);

        populateOverviewWithTaskBoxes(taskGrid, null, null, null,  0.0, null, null, dateToParse, false, false);
    }

    public void previousDay(ActionEvent event) {
        this.date.setTime(this.date.getTime() - oneDayMS);
        String previousDayDate = df.format(this.date);

        setTextForDate(previousDayDate, dateForShownDay, this.date);

        populateOverviewWithTaskBoxes(taskGrid, frequency, urgency, type, progress, progressValue, employee, previousDayDate, false, false);
    }

    public void nextDay(ActionEvent event) {
        this.date.setTime(this.date.getTime() + oneDayMS);
        String nextDayDate = df.format(this.date);

        setTextForDate(nextDayDate, dateForShownDay, this.date);

        populateOverviewWithTaskBoxes(taskGrid, frequency, urgency, type, progress, progressValue, employee, nextDayDate, false, false);
    }

    public void changeView(ActionEvent event) {
        changeView(viewDropdownMenu, overviewEmployeeBorderPane);
    }

    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
}

