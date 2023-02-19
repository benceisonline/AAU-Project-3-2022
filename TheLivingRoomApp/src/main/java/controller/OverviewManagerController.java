package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class OverviewManagerController implements Initializable, UIMethods, DatabaseMethods {
    @FXML
    private GridPane taskGrid;
    @FXML
    private BorderPane overviewManagerBorderPane;
    @FXML
    private Button refreshFilter;
    @FXML
    private Label dateForShownDay;
    @FXML
    private DatePicker datePickerFilter;
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
    private final LocalDate localDate = LocalDate.now();
    private Date date;
    private final int oneDayMS = 86_400_000;
    @FXML
    private Button closeProgramButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.date = new Date();

        datePickerFilter.setValue(localDate);

        stdUIForPages(frequencyDropdownMenu, urgencyDropdownMenu, typeDropdownMenu, progressDropdownMenu,
                assigneeDropdownMenu, refreshFilter, dateForShownDay, closeProgramButton, true);

        viewDropdownMenu.getItems().addAll(
                "History", "Employee"
        );

        populateOverviewPageWithTaskBoxes();
    }

    public void openTaskFormPage(ActionEvent event) {
        switchScene(overviewManagerBorderPane, "task-form-page.fxml");
    }

    public void openTeamManagerPage(ActionEvent event) {
        switchScene(overviewManagerBorderPane, "manage-team-page.fxml");
    }

    public void openAllTasksPage(ActionEvent event) {
        switchScene(overviewManagerBorderPane, "overview-all-tasks-page.fxml");
    }


    public void populateOverviewPageWithTaskBoxes() {
        Date dateToFormat = Date.from(datePickerFilter.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String dateToParse = df.format(dateToFormat);

        populateOverviewWithTaskBoxes(taskGrid, frequency, urgency, type, progress, progressValue, employee, dateToParse, true, false);
    }

    public void refreshPage(ActionEvent event) {
        switchScene(overviewManagerBorderPane, "overview-manager-page.fxml");
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
        resetFilters(frequencyDropdownMenu, urgencyDropdownMenu, typeDropdownMenu, progressDropdownMenu, assigneeDropdownMenu,
                datePickerFilter, localDate);

        String dateToParse = df.format(new Date());
        dateForShownDay.setText(dateToParse);

        populateOverviewWithTaskBoxes(taskGrid, null, null, null,  0.0, null, null, dateToParse, true, false);
    }

    public void dateFilter(ActionEvent event) {
        Date dateToFormat = Date.from(datePickerFilter.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String dateToShow = df.format(dateToFormat);
        dateForShownDay.setText(dateToShow);

        if (dateToShow.equals(df.format(new Date()))) {
            dateForShownDay.setText("Today");
        } else {
            dateForShownDay.setText(dateToShow);
        }

        populateOverviewWithTaskBoxes(taskGrid, frequency, urgency, type, progress, progressValue, employee, dateToShow, true, false);
    }

    public void previousDay(ActionEvent event) {
        this.date.setTime(this.date.getTime() - oneDayMS);
        String previousDayDate = df.format(this.date);

        setTextForDate(previousDayDate, dateForShownDay, this.date);

        populateOverviewWithTaskBoxes(taskGrid, frequency, urgency, type, progress, progressValue, employee, previousDayDate, true, false);
    }

    public void nextDay(ActionEvent event) {
        this.date.setTime(this.date.getTime() + oneDayMS);
        String nextDayDate = df.format(this.date);

        setTextForDate(nextDayDate, dateForShownDay, this.date);

        populateOverviewWithTaskBoxes(taskGrid, frequency, urgency, type, progress, progressValue, employee, nextDayDate, true, false);
    }

    public void changeView(ActionEvent event) {
        changeView(viewDropdownMenu, overviewManagerBorderPane);
    }

    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
}

