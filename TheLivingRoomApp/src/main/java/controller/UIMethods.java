package controller;

import com.mongodb.client.model.Filters;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Task;
import model.User;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.ne;

public interface UIMethods {
    default void switchScene(BorderPane pane, String path) {
        try {
            BorderPane borderPane = FXMLLoader.load(getClass().getResource(path));
            pane.getChildren().setAll(borderPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void createNewStage(String path, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));

            Scene scene = new Scene(fxmlLoader.load(), width, height);

            Stage stage = new Stage();

            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            stage.setTitle("The Living Room");
            scene.getStylesheets().add("stylesheet.css");
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void makeModalDialog(Object controller, String fxmlFile, int width, int height) {
        Stage stage = makeModalStage();
        FXMLLoader loader = makeModalLoader(fxmlFile);
        loader.setController(controller);
        showDialog(loader, stage, width, height);
    }

    default Stage makeModalStage() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.TRANSPARENT);
        return dialog;
    }

    default FXMLLoader makeModalLoader(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        return loader;
    }

    default void showDialog(FXMLLoader loader, Stage dialog, int width, int height) {
        try {
            BorderPane dialogBox = loader.load();
            Scene dialogScene = new Scene(dialogBox, width, height);
            dialog.setScene(dialogScene);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void makeButtonsCancelAndDefault(Button cancel,Button ok) {
        cancel.setCancelButton(true);
        ok.setDefaultButton(true);
    }

    default void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    default void informationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.show();
    }

    default void errorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        setCssToDialogs(alert, "error-dialog");
        alert.setGraphic(null);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    default void setCssToDialogs(Alert alert, String cssId) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add(cssId);
    }

    default void descriptionInformation(String title, String description) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
    }

    default Node returnParentsParentNode(ActionEvent event) {
        Node triggerActionNode = (Node) event.getSource();
        return triggerActionNode.getParent().getParent();
    }

    default void addCssToButtons(Button button, String cssClass) {
        button.getStyleClass().add(cssClass);
    }

    default Bson getFilters(String field, Object value) {
        Bson filter;
        if (!(Objects.equals(value, "")) & value != null) {
            filter = Filters.eq(field, value);
        } else {
            filter = Filters.ne(field, null);
        }
        return filter;
    }

    default void populateOverviewWithTaskBoxes(GridPane taskGrid, String frequency, String urgency, String type, double progress, String progressValue, String employee, String date, boolean isManagerView, boolean isAllTask) {
        if (isAllTask) {
            Bson filter = and(ne("_id", " "));
            ArrayList<Task> tasks = new ArrayList<>(DatabaseMethods.getTasksFromDB(filter, true, "tasks"));

            // Sort tasks based on date
            //tasks.sort(Comparator.comparing(Task::getDbDate).reversed());

            int columns = 1;
            int rows = 1;
            String previousDate = (" ");

            try {
                for (Task task : tasks) {
                    String newDate = task.makeDateLabel();

                    // Print new month and year if needed
                    if (!previousDate.equals(newDate)){
                        Separator separator = new Separator(Orientation.HORIZONTAL);
                        Label label = new Label(newDate);
                        label.setPadding(new Insets(20));
                        label.setStyle("-fx-font-size: 18;");

                        VBox vBox = new VBox(separator, label);

                        taskGrid.add(vBox, columns, rows);
                        previousDate = newDate;
                        rows++;
                    }

                    // Print the tasks
                    FXMLLoader loader = new FXMLLoader();

                    if (isManagerView) {
                        loader.setLocation(getClass().getResource("task-box-manager-page.fxml"));
                    } else {
                        loader.setLocation(getClass().getResource("history-task-box-page.fxml"));
                    }

                    VBox vBox = loader.load();
                    vBox.setId(task.getId().toString()); // Store task id as hBox id

                    if (isManagerView) {
                        TaskManagerController taskManagerController = loader.getController();
                        taskManagerController.setTaskBoxToUI(task);
                    } else {
                        HistoryTaskController historyTaskController = loader.getController();
                        historyTaskController.setTaskBoxToUI(task);
                    }

                    taskGrid.add(vBox, columns, rows);
                    rows++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            taskGrid.getChildren().clear();

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date parsedDate;

            try {
                parsedDate = df.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Bson frequencyFilter = getFilters("frequency", frequency);
            Bson urgencyFilter = getFilters("urgency", urgency);
            Bson typeFilter = getFilters("type", type);

            Bson progressFilter;
            if (progressValue == null || progressValue.equals("")) {
                progressFilter = getFilters("progress", null);
            } else {
                progressFilter = getFilters("progress", progress);
            }

            boolean filterEmployee = !(Objects.equals(employee, "")) & employee != null;

            Bson filter = Filters.and(frequencyFilter, urgencyFilter, typeFilter, progressFilter);
            ArrayList<Task> tasks = new ArrayList<>(DatabaseMethods.getTasksFromDB(filter, true, "tasks"));
            //.thenComparing(Task::getUrgency): change the urgency to low: 1, medium: 2, high: 3 to sort on it

            int columns = 1;
            int rows = 1;

            try {
                for (Task task : tasks) {
                    FXMLLoader loader = new FXMLLoader();
                    if (isManagerView) {
                        loader.setLocation(getClass().getResource("task-box-manager-page.fxml"));
                    } else {
                        loader.setLocation(getClass().getResource("task-box-employee.page.fxml"));
                    }

                    VBox vBox = loader.load();
                    vBox.setId(task.getId().toString()); // Store task id as hBox id

                    if (isManagerView) {
                        TaskManagerController taskController = loader.getController();
                        taskController.setTaskBoxToUI(task);
                    } else {
                        TaskEmployeeController taskController = loader.getController();
                        taskController.setTaskBoxToUI(task);
                    }

                    if (isManagerView) {
                        if (filterEmployee && (date.equals(df.format(task.getDbDate())) || parsedDate.after(task.getDbDate()))) {
                            for (String assignee : task.getAssignees()) {
                                if (employee.equals(assignee)) {
                                    taskGrid.add(vBox, columns, rows);
                                }
                            }
                        } else if (date.equals(df.format(task.getDbDate())) || parsedDate.after(task.getDbDate())){
                            taskGrid.add(vBox, columns, rows);
                        }
                    } else {
                        if (filterEmployee && (date.equals(df.format(task.getDbDate())) || parsedDate.after(task.getDbDate()))) {
                            for (String assignee : task.getAssignees()) {
                                if (employee.equals(assignee)) {
                                    taskGrid.add(vBox, columns, rows);
                                }
                            }
                        } else if (date.equals(df.format(task.getDbDate())) || parsedDate.after(task.getDbDate())){
                            taskGrid.add(vBox, columns, rows);
                        }
                    }

                    rows++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    default void stdUIForPages(ComboBox<String> frequencyDropdownMenu, ComboBox<String> urgencyDropdownMenu,
        ComboBox<String> typeDropdownMenu, ComboBox<String> progressDropdownMenu, ComboBox<String> assigneeDropdownMenu,
                                          Button refreshFilter, Label dateForShownDay, Button closeProgramButton, boolean isOverViewPage) {
        frequencyDropdownMenu.getItems().addAll(
                "", "Once", "Every Day", "Every Other Day", "Every Week", "Every Month"
        );

        urgencyDropdownMenu.getItems().addAll(
                "", "Low", "Medium", "High"
        );

        typeDropdownMenu.getItems().addAll(
                "", "Cleaner", "Bartender", "All"
        );

        if (isOverViewPage) {
            ArrayList<User> users = DatabaseMethods.getEmployeesFromDB(false, "users");

            addCssToButtons(closeProgramButton, "exit-button");

            progressDropdownMenu.getItems().addAll(
                    "", "0%", "25%", "50%", "75%"
            );

            refreshFilter.setVisible(false);

            assigneeDropdownMenu.getItems().addAll("", "General");
            for (User user : users) {
                assigneeDropdownMenu.getItems().add(user.getFullName());
            }

            dateForShownDay.setText("Today");
        }
    }

    default void changeView(ComboBox<String> viewDropdownMenu, BorderPane borderPaneToSwitch) {
        if (viewDropdownMenu.getValue().equals("History")) {
            switchScene(borderPaneToSwitch, "overview-history-page.fxml");
        } else if (viewDropdownMenu.getValue().equals("Manager")){
            PinCodeController controller = new PinCodeController();
            makeModalDialog(controller, "manager-pin-code-page.fxml", 300, 400);

            if (controller.isValidPinCode()) {
                switchScene(borderPaneToSwitch, "overview-manager-page.fxml");
            }
        } else {
            switchScene(borderPaneToSwitch, "overview-employee-page.fxml");
        }
    }

    default void resetFilters(ComboBox<String> frequencyDropdownMenu, ComboBox<String> urgencyDropdownMenu, ComboBox<String> typeDropdownMenu,
        ComboBox<String> progressDropdownMenu, ComboBox<String> assigneeDropdownMenu, DatePicker datePickerFilter, LocalDate localDate) {
        frequencyDropdownMenu.setValue("");
        urgencyDropdownMenu.setValue("");
        typeDropdownMenu.setValue("");
        progressDropdownMenu.setValue("");
        assigneeDropdownMenu.setValue("");
        datePickerFilter.setValue(localDate);
    }

    default void filterSectionLogic(HBox filterOptionsHBox, Button refreshFilter) {
        if (filterOptionsHBox.isVisible()) {
            filterOptionsHBox.setVisible(false);
            filterOptionsHBox.setPrefHeight(0);
            refreshFilter.setVisible(false);
        } else {
            filterOptionsHBox.setVisible(true);
            filterOptionsHBox.setPrefHeight(75);
            refreshFilter.setVisible(true);
        }
    }

    default double progress(String progressValue, ComboBox<String> progressDropdownMenu) throws ParseException {
        double progress = 0;
        if (!Objects.equals(progressValue, progressDropdownMenu.getValue()) & !progressDropdownMenu.getValue().equals("")) {
            if (progressDropdownMenu.getValue().equals("0%")) {
                progress = 0.0;
            } else if (!progressDropdownMenu.getValue().equals("Progress")) {
                progress = (double)(new DecimalFormat("0.0#%").parse(progressDropdownMenu.getValue()));
            }
        }
        return progress;
    }

    default HBox assigneeBox(User user, boolean isChecked) {
        Label fullName = new Label(user.getFullName());
        HBox hBoxName = new HBox(fullName);
        hBoxName.setAlignment(Pos.CENTER);
        hBoxName.setPrefWidth(120);
        hBoxName.setPrefHeight(50);

        Label role = new Label(user.getRole());
        HBox hBoxRole = new HBox(role);
        hBoxRole.setAlignment(Pos.CENTER);
        hBoxRole.setPrefWidth(55);
        hBoxRole.setPrefHeight(50);

        CheckBox checkBox = new CheckBox();
        HBox hBoxCheckBox = new HBox(checkBox);
        hBoxCheckBox.setAlignment(Pos.CENTER);
        hBoxCheckBox.setPrefWidth(50);
        hBoxCheckBox.setPrefHeight(50);
        if (isChecked) {
            checkBox.setSelected(true);
        }

        HBox hBox = new HBox(hBoxName, hBoxRole, hBoxCheckBox);

        hBox.setPrefWidth(225);
        hBox.setPrefHeight(50);
        hBox.setMinWidth(Region.USE_PREF_SIZE);
        hBox.setMinHeight(Region.USE_PREF_SIZE);
        hBox.setMaxWidth(Region.USE_PREF_SIZE);
        hBox.setMaxHeight(Region.USE_PREF_SIZE);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    default void setDropdownMenuPercentValue(double dropdownMenuPercentValue, ComboBox<String> dropdownMenuPercent) {
        if (dropdownMenuPercentValue != 0.0) {
            int progress = (int) dropdownMenuPercentValue;
            String progressToString = Integer.toString(progress) + '%';
            dropdownMenuPercent.setValue(progressToString);
        }
    }

    default void expandToViewAssignees(Button expandAssigneeButton, HBox hBox, VBox vBoxTheWholeBox, ImageView imageView, ArrayList<String> assignees) {
        if (Objects.equals(expandAssigneeButton.getId(), "down")) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/up-chevron.png")));
            imageView.setImage(image);
            expandAssigneeButton.setId("up");

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER_LEFT);
            vBox.setSpacing(10);
            vBox.setPrefWidth(167);
            double prefHeight = vBox.getPrefHeight();
            for (String assignee : assignees) {
                Label name = new Label(assignee);

                vBox.getChildren().add(name);
                prefHeight += 20;
            }
            vBoxTheWholeBox.setPrefHeight(75 + prefHeight);
            vBox.setPrefHeight(prefHeight);

            hBox.getChildren().add(vBox);
        } else {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/down-chevron.png")));
            imageView.setImage(image);
            expandAssigneeButton.setId("down");

            hBox.getChildren().remove(0);
            hBox.setPrefHeight(0);
            vBoxTheWholeBox.setPrefHeight(75);
        }
    }

    default void stdUIForTaskBoxes(Button expandAssigneeButton, Task task, ImageView overdueTask, Circle urgencyCircle,
                                                ProgressBar progressBar, Label taskLabel, ComboBox<String> dropdownMenuPercent) {
        expandAssigneeButton.setId("down");
        Date today = new Date();
        int oneDayMS = 86400000;
        today.setTime(new Date().getTime() - oneDayMS);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if (task.getDbDate().before(today)) {
            overdueTask.setVisible(true);
            Tooltip.install(overdueTask, new Tooltip("This task was due " + df.format(task.getDbDate())));
        }

        Tooltip.install(urgencyCircle, new Tooltip("Urgency of task:\n Red = high \n Yellow = medium \n Green = low"));

        switch (task.getUrgency().toLowerCase()) {
            case "low" -> urgencyCircle.setFill(Color.rgb(71, 209, 178));
            case "medium" -> urgencyCircle.setFill(Color.rgb(255, 228, 3));
            case "high" -> urgencyCircle.setFill(Color.rgb(240, 127, 121));
        }

        progressBar.setProgress(task.getProgress());
        taskLabel.setText(task.getTitle());
        dropdownMenuPercent.getItems().addAll("0%", "25%","50%","75%");
    }

    default void setTextForDate(String dateToCheck, Label dateForShownDay, Date date) {
        final int oneDayMS = 86_400_000;
        Date todayDate = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date tomorrow = new Date();
        tomorrow.setTime(tomorrow.getTime() + oneDayMS);

        Date yesteday = new Date();
        yesteday.setTime(yesteday.getTime() - oneDayMS);

        if (dateToCheck.equals(df.format(todayDate))) {
            dateForShownDay.setText("Today");
        } else if (dateToCheck.equals(df.format(tomorrow))) {
            dateForShownDay.setText("Tomorrow");
        } else if (dateToCheck.equals(df.format(yesteday))) {
            dateForShownDay.setText("Yesterday");
        } else {
            dateForShownDay.setText(df.format(date));
        }
    }
}