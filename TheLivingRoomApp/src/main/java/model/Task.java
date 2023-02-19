package model;

import javafx.scene.control.Label;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Task {
    private ObjectId id;
    private String title;
    private String description;
    private String frequency;
    private String urgency;
    private String type;
    private double progress;
    private boolean active;
    private ArrayList<String> comments = new ArrayList<>(50);
    private ArrayList<String> assignees;
    private LocalDate date;
    private Date dbDate;
    private String lastEdit;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public boolean setTitle(String title) {
        if (title == null) {
            return false;
        }

        for (int i = 0; i < title.length(); i++) {
            if (!(title.charAt(i) == ' ')) {
                this.title = title;
                return true;
            }
        }
        return false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNullElse(description, "");
    }

    public String getFrequency() {
        return frequency;
    }

    public boolean setFrequency(String frequency) {
        if (frequency == null) {
            return false;
        } else {
            this.frequency = frequency;
            return true;
        }
    }

    public String getUrgency() {
        return urgency;
    }

    public boolean setUrgency(String urgency) {
        if (urgency == null) {
            return false;
        } else {
            this.urgency = urgency;
            return true;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = Objects.requireNonNullElse(type, "All");
    }

    public double getProgress() {
        return progress;
    }

    public boolean setProgress(double progress) {
        if (progress < 0) {
            return false;
        } else {
            this.progress = progress;
            return true;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getAssignees() {
        return assignees;
    }

    public void setAssignees(ArrayList<String> assignees) {
        this.assignees = assignees;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = Objects.requireNonNullElseGet(date, LocalDate::now);
    }

    public Date getDbDate() {
        return dbDate;
    }

    public void setDbDate(Date dbDate) {
        this.dbDate = dbDate;
    }

    public Task(ObjectId id, String title, String description, double progress, ArrayList<String> assignees) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.progress = progress;
        this.assignees = assignees;
    }
    public Task(ObjectId id, String title, String urgency, String description, double progress, ArrayList<String> assignees) {
        this.id = id;
        this.title = title;
        this.urgency = urgency;
        this.description = description;
        this.progress = progress;
        this.assignees = assignees;
    }

    public Task(String title, String description, String frequency, String urgency, String type, double progress, boolean active, ArrayList<String> comments, ArrayList<String> assignees, LocalDate date) {
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.urgency = urgency;
        this.type = type;
        this.progress = progress;
        this.active = active;
        this.comments = comments;
        this.assignees = assignees;
        this.date = date;
    }

    public Task(String title, String description, String frequency, String urgency, String type, int progress, boolean active, ArrayList<String> assignees, LocalDate date) {
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.urgency = urgency;
        this.type = type;
        this.progress = progress;
        this.active = active;
        this.assignees = assignees;
        this.date = date;
    }
    public Task(String title, String description, double progress, boolean active, ArrayList<String> comments, ArrayList<String> assignees) {
        this.title = title;
        this.description = description;
        this.progress = progress;
        this.active = active;
        this.comments = comments;
        this.assignees = assignees;
    }
    public Task(String title, String description, String frequency, double progress, boolean active, ArrayList<String> comments, ArrayList<String> assignees, LocalDate date) {
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.progress = progress;
        this.active = active;
        this.comments = comments;
        this.assignees = assignees;
        this.date = date;
    }

    public Task(ObjectId id, String title, String description, String frequency, String urgency, String type, double progress, boolean active, ArrayList<String> comments, ArrayList<String> assignees, Date dbDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.urgency = urgency;
        this.type = type;
        this.progress = progress;
        this.active = active;
        this.comments = comments;
        this.assignees = assignees;
        this.dbDate = dbDate;
    }

    public Task(ObjectId id, String title, String description, String frequency, String urgency, String type, double progress, boolean active, ArrayList<String> comments, ArrayList<String> assignees, Date dbDate, String lastEdit) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.urgency = urgency;
        this.type = type;
        this.progress = progress;
        this.active = active;
        this.comments = comments;
        this.assignees = assignees;
        this.dbDate = dbDate;
        this.lastEdit = lastEdit;
    }

    public Task(String title, String description, String frequency, String urgency, String type, ArrayList<String> assignees, LocalDate date) {
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.urgency = urgency;
        this.type = type;
        this.assignees = assignees;
        this.date = date;
    }

    public Task(double progress, boolean active) {
        this.progress = progress;
        this.active = active;
    }

    public String makeDateLabel() {
        String date = this.getDbDate().toString();
        String month = date.substring(date.indexOf(" ") + 1, (date.indexOf(" ") + 4));
        String year = date.substring(date.lastIndexOf(" ") + 1, date.lastIndexOf(" ") + 5);
        String dateToPrint = (month + " " + year);
        return dateToPrint;
    }
}
