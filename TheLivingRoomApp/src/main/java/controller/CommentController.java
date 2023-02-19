package controller;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentController implements DatabaseMethods, UIMethods, Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button addCommentButton;
    @FXML
    private TextArea addComment;
    @FXML
    private GridPane addCommentGridPane;
    private final String id;

    private final String sender;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
    LocalDateTime now = LocalDateTime.now();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCssToButtons(cancelButton, "cancel-button");
        addCssToButtons(addCommentButton, "submit-button");

        makeButtonsCancelAndDefault(cancelButton, addCommentButton);

        displayComments();
    }

    public void cancelAndReturnToOverviewPage(ActionEvent event) {
        closeStage(event);
    }

    public void addCommentAndReturnToOverviewPage(ActionEvent event) {
        if (addComment.getText().equals("")) {
            informationDialog("The Comment Cannot Be Empty");
        } else {
            addCommentToDB(this.id, this.sender + " - " + dateTimeFormatter.format(now) + ":\n" + addComment.getText(), "tasks");
            closeStage(event);        }
    }

    public void displayComments() {
        ArrayList<String> comments = new ArrayList<>(DatabaseMethods.getCommentsFromDB(id, "tasks"));

        int columns = 1;
        int rows = 1;

        try {
            for (String comment : comments) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("comment-box-page.fxml"));

                HBox hBox = loader.load();
                hBox.setPrefWidth(300);

                CommentBoxController commentBoxController = loader.getController();
                commentBoxController.setCommentToUI(comment, 295);

                addCommentGridPane.add(hBox, columns, rows);

                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommentController(String id, String sender) {
        this.id = id;
        this.sender = sender;
    }
}
