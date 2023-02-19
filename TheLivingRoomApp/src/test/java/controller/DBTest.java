package controller;

import com.mongodb.client.MongoCollection;
import model.Task;
import model.User;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.testng.annotations.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static controller.DatabaseMethods.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DBTest implements DatabaseMethods{
    String collName = "test";
    @BeforeEach
    public void setUp() {
        boolean result = DatabaseMethods.emptyCollection("test");
        if (result) {
            System.out.println("Collection deleted");
        } else {
            System.out.println("Collection not deleted");
        }
    }

    @AfterClass
    public static void deleteCollectionAfterLastTest() {
        boolean result = DatabaseMethods.emptyCollection("test");
        if (result) {
            System.out.println("Collection deleted");
        } else {
            System.out.println("Collection not deleted");
        }
    }

    @Test
    public void checkConnectionTest() {
        // [SCENARIO] Test that the connection to the DB is successful

        // [GIVEN] That we have the expected value
        boolean expected = true;

        // [WHEN] Connection to the DB
        boolean actual = checkConnection();

        // [THEN] The expected should be equal to the progress
        assertEquals(expected, actual);
    }

    @Test
    public void getDocumentByIdTest() {
        // [SCENARIO] Testing we can get a given document by id

        // [GIVEN] That we have the expected values
        ArrayList<Object> expectedValues = expectedValues();

        // [GIVEN] That we have a Task in DB and get the id from the inserted document
        String id = populateDBWithTask(true);

        // [WHEN] Getting the document by id
        Document document = getDocumentById(id, collName);

        // [THEN] Asserting the expected values and the documents values
        assertEquals(id, document.get("_id").toString());
        assertTaskDocument(expectedValues, document, false);
    }

    @Test
    public void getDBCollTest() {
        // [SCENARIO] When getting the collection name from DB, it should be the same name as the CollName

        // [GIVEN] That we have the expected value
        String expected = collName;

        // [WHEN] Getting the DB collection name
        MongoCollection<Document> coll = getDBColl(collName);

        // [THEN] Asserting that the collection is not null and the expected should be equal to the collection's name
        assert coll != null;
        assertEquals(expected, coll.getNamespace().getCollectionName());
    }

    @Test
    public void getTasksFromDBActiveTest() {
        // [SCENARIO] Testing that the function returns all tasks with has active set to true

        // [GIVEN] That we have a list for ids
        ArrayList<String> list = new ArrayList<>();

        // [GIVEN] That we have the expected nr of documents and active status
        int expectedNr = 2;

        // [GIVEN] That we populate the DB with Task
        String expectedId = populateDBWithTask(true);
        String expectedId2 = populateDBWithTask(true);
        populateDBWithTask(false);
        populateDBWithTask(false);

        // [GIVEN] That we store the id's
        list.add(expectedId);
        list.add(expectedId2);

        // [WHEN] Getting the active documents
        Bson filter = and(ne("_id", " "));
        ArrayList<Task> tasks = new ArrayList<>(getTasksFromDB(filter,true, collName));

        // [THEN] Asserting the id's and the expected nr of documents
        assertEquals(expectedNr, tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(list.get(i), tasks.get(i).getId().toString());
        }
    }

    @Test
    public void getTasksFromDBFalseTest() {
        // [SCENARIO] Testing that the function returns all tasks with has active set to false

        // [GIVEN] That we have a list for ids
        ArrayList<String> list = new ArrayList<>();

        // [GIVEN] That we have the expected nr of documents
        int expectedNr = 3;

        // [GIVEN] That we populate the DB with Task
        String expectedId = populateDBWithTask(false);
        String expectedId2 = populateDBWithTask(false);
        String expectedId3 =populateDBWithTask(false);
        populateDBWithTask(true);

        // [GIVEN] That we store the id's
        list.add(expectedId);
        list.add(expectedId2);
        list.add(expectedId3);

        // [WHEN] Getting the non-active documents
        Bson filter = and(ne("_id", " "));
        ArrayList<Task> tasks = new ArrayList<>(getTasksFromDB(filter, false, collName));

        // [THEN] Asserting the id's and the expected nr of documents
        assertEquals(expectedNr, tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(list.get(i), tasks.get(i).getId().toString());
        }
    }

    @Test
    public void getEmployeesFromDBAdminTest() {
        // [SCENARIO] Testing that the function returns all User with has admin set to true

        // [GIVEN] That we have a list for ids
        ArrayList<ObjectId> list = new ArrayList<>();

        // [GIVEN] That we have the expected nr of documents
        int expectedNr = 3;

        // [GIVEN] That we populate the DB with User
        ObjectId expectedId = new ObjectId(populateDBWithUser(true));
        ObjectId expectedId2 = new ObjectId(populateDBWithUser(true));
        ObjectId expectedId3 = new ObjectId(populateDBWithUser(true));
        populateDBWithUser(false);

        // [GIVEN] That we store the id's
        list.add(expectedId);
        list.add(expectedId2);
        list.add(expectedId3);

        // [WHEN] Getting the admin documents
        ArrayList<User> users = new ArrayList<>(getEmployeesFromDB(true, collName));

        // [THEN] Asserting the id's and the expected nr of documents
        assertEquals(expectedNr, users.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(list.get(i), users.get(i).getId());
        }
    }

    @Test
    public void getEmployeesFromDBNotAdminTest() {
        // [SCENARIO] Testing that the function returns all User with has admin set to false

        // [GIVEN] That we have a list for ids
        ArrayList<ObjectId> list = new ArrayList<>();

        // [GIVEN] That we have the expected nr of documents
        int expectedNr = 2;

        // [GIVEN] That we populate the DB with User
        ObjectId expectedId = new ObjectId(populateDBWithUser(false));
        ObjectId expectedId2 = new ObjectId(populateDBWithUser(false));
        populateDBWithUser(true);
        populateDBWithUser(true);

        // [GIVEN] That we store the id's
        list.add(expectedId);
        list.add(expectedId2);

        // [WHEN] Getting the admin documents
        ArrayList<User> users = new ArrayList<>(getEmployeesFromDB(false, collName));

        // [THEN] Asserting the id's and the expected nr of documents
        assertEquals(expectedNr, users.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(list.get(i), users.get(i).getId());
        }
    }

    @Test
    public void exportTaskToDatabaseTest() {
        // [SCENARIO] Testing that we can export documents to the DB

        // [GIVEN] That we create a list for expected values
        ArrayList<String> list = new ArrayList<>();

        // [GIVEN] That we populate the DB with Task
        String expectedId = populateDBWithTask(true);
        String expectedId2 = populateDBWithTask(true);

        // [GIVEN] That save the id's from the documents
        list.add(expectedId);
        list.add(expectedId2);

        // [WHEN] Getting the documents in the DB
        Bson filter = and(ne("_id", " "));
        ArrayList<Task> tasks = getTasksFromDB(filter, true, collName);

        // [THEN] Asserting the ids from the DB with the stored ids
        if (tasks.isEmpty()) {
            throw new AssertionError();
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                assertEquals(list.get(i), tasks.get(i).getId().toString());
            }
        }
    }

    @Test
    public void exportUserToDatabaseTest() {
        // [SCENARIO] Testing that we can export documents to the DB

        // [GIVEN] That we create a list for expected values
        ArrayList<ObjectId> list = new ArrayList<>();

        // [GIVEN] That we populate the DB with User
        ObjectId expectedId = new ObjectId(populateDBWithUser(false));
        ObjectId expectedId2 = new ObjectId(populateDBWithUser(false));

        // [GIVEN] That save the id's from the documents
        list.add(expectedId);
        list.add(expectedId2);

        // [WHEN] Getting the documents in the DB
        ArrayList<User> users = getEmployeesFromDB(false, collName);

        // [THEN] Asserting the ids from the DB with the stored ids
        if (users.isEmpty()) {
            throw new AssertionError();
        } else {
            for (int i = 0; i < users.size(); i++) {
                assertEquals(list.get(i), users.get(i).getId());
            }
        }
    }

    @Test
    public void addCommentToDBTest() {
        // [SCENARIO] Testing that we can add a comment to a given task, and it would be the last comment

        // [GIVEN] That we have the expectedSize and expectedComment
        int expectedSize = 11;
        String expectedComment = "tester";

        // [GIVEN] That the DB is populated with a Task and the expectedId is stored
        String expectedId = populateDBWithTask(true);

        // [WHEN] Adding the comment to the task and getting the document
        addCommentToDB(expectedId, expectedComment, collName);
        Document document = getDocumentById(expectedId, collName);

        // [WHEN] Getting the comments from DB and the size of the list
        ArrayList<String> commentList = (ArrayList<String>) document.get("comments");
        int actualSize = commentList.size();

        // [THEN] The expectedSize should be equal to the actualSize and the last comment should be expectedComment
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedComment, commentList.get(expectedSize - 1));
    }

    @Test
    public void updateTaskToActiveTest() {
        // [SCENARIO] When updating a Task from DB to set active to true then the document should also have active set to true

        // [GIVEN] That we have the expected value
        boolean expected = true;

        // [GIVEN] That we have a Task in DB and get the id from the inserted document
        String id = populateDBWithTask(false);

        // [WHEN] Updating the task to be complete
        completeTask(id, collName, true);

        // [WHEN] Getting the active value from the document in DB
        Document document = getDocumentById(id, collName);
        boolean actual = (boolean) document.get("active");

        // [THEN] The expected should be equal to the actual
        assertEquals(expected, actual);
    }

    @Test
    public void updateTaskToFalseTest() {
        // [SCENARIO] When updating a Task from DB to set active to false then the document should also have active set to false

        // [GIVEN] That we have a Task in DB and get the id from the inserted document
        String id = populateDBWithTask(true);

        // [WHEN] Updating the task to be complete
        completeTask(id, collName, false);

        // [WHEN] Getting the active value from the document in DB
        Document document = getDocumentById(id, collName);
        boolean actual = (boolean) document.get("active");
        System.out.println("Tester");

        // [THEN] The expected should be equal to the actual
        assertFalse(actual);
    }

    @Test
    public void updateUserTest() {
        // [SCENARIO] That we can update a user in the database

        // [GIVEN] That we have a user in database, and we store the document's id
        String id = populateDBWithUser(false);

        // [GIVEN] That we have a new user to update in current user in the database
        User user = new User("test", "user", "test@example.com", "12345678", false, "Barista");

        // [GIVEN] We store the expected values
        ArrayList<Object> expectedValues = new ArrayList<>();
        expectedValues.add("test");
        expectedValues.add("user");
        expectedValues.add("test@example.com");
        expectedValues.add("12345678");
        expectedValues.add(false);
        expectedValues.add("Barista");

        // [WHEN] Updating the user in the database
        updateUser(id, "test", user);

        // [THEN] The document should be updated
        Document userDocument = getDocumentById(id, "test");
        assertUserDocument(expectedValues, userDocument);
    }

    @Test
    public void updateTaskTest() {
        // [SCENARIO] That we can update a task in the database

        // [GIVEN] That we have a task in database, and we store the document's id
        String id = populateDBWithTask(true);

        // [GIVEN] That we have a date
        LocalDate date = LocalDate.now();

        // [GIVEN] That we have today's datetime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        LocalDateTime now = LocalDateTime.now();

        // [GIVEN] That we have a new task to update in current task in the database
        Task task = new Task("test", "task", "Once", "High", "Cleaner", new ArrayList<>(), date);

        // [GIVEN] We store the expected values
        ArrayList<Object> expectedValues = new ArrayList<>();
        expectedValues.add("test");
        expectedValues.add("task");
        expectedValues.add("Once");
        expectedValues.add("High");
        expectedValues.add("Cleaner");
        expectedValues.add(new ArrayList<>());
        expectedValues.add(dateTimeFormatter.format(now));

        // [WHEN] Updating the task in the database
        updateTask(id, "test", task);

        // [THEN] The document should be updated
        Document taskDocument = getDocumentById(id, "test");
        assertTaskDocument(expectedValues, taskDocument, true);
    }

    @Test
    public void updateProgressBarInDBWithZeroAndReturnValueTest() throws ParseException {
        // [SCENARIO] When updating the progress dropdown menu with "0%" then it should be 0.0 in the DB

        // [GIVEN] That we have the expected value
        double expected = 0.0;

        // [GIVEN] That we have a Task in DB and get the id from the inserted document
        String id = populateDBWithTask(true);

        // [WHEN] Updating the Task with "0%"
        double actual = updateProgressBarInDBAndReturnValue(id, "0%", collName);

        // [THEN] The expected should be equal to the actual
        assertEquals(expected, actual);

        // [WHEN] Getting the progress value from the document in DB
        Document document = getDocumentById(id, collName);
        double progress = (double) document.get("progress");

        // [THEN] The expected should be equal to the progress
        assertEquals(expected, progress);
    }

    @Test
    public void updateProgressBarInDBWithNonZeroAndReturnValueTest() throws ParseException {
        // [SCENARIO] When updating the progress dropdown menu with "75%" then it should be 0.75 in the DB

        // [GIVEN] That we have the expected value
        double expected = 0.75;

        // [GIVEN] That we have a Task in DB and get the id from the inserted document
        String id = populateDBWithTask(true);

        // [WHEN] Updating the Task with "75%"
        double actual = updateProgressBarInDBAndReturnValue(id, "75%", collName);

        // [THEN] The expected should be equal to the actual
        assertEquals(expected, actual);

        // [WHEN] Getting the progress value from the document in DB
        Document document = getDocumentById(id, collName);
        double progress = (double) document.get("progress");

        // [THEN] The expected should be equal to the progress
        assertEquals(expected, progress);
    }

    @Test
    public void getCommentsFromDBTest() {
        // [SCENARIO] Testing that we can get comments from a given Task

        // [GIVEN] That we have a list of comments
        ArrayList<String> expectedComments = makeArrayList();

        // [GIVEN] That we populate the DB with a Task
        String id = populateDBWithTask(true);

        // [WHEN] Getting the comments from DB
        ArrayList<String> actualComments = getCommentsFromDB(id, collName);

        // [THEN] The expectedComments should be equal to actualComments
        assertEquals(expectedComments, actualComments);
    }

    @Test
    public void emptyCollectionWhenCollIsEmptyTest() {
        // [SCENARIO] When empty a DB collection that is already empty should return true and do not give an error

        // [GIVEN] That we have the expected outcome
        boolean expected = true;

        // [WHEN] Emptying the empty collection
        boolean actual = emptyCollection(collName);

        // [THEN] The expected should be equal to the actual
        assertEquals(expected, actual);
    }

    @Test
    public void emptyCollectionWhenCollIsNotEmptyTest() {
        // [SCENARIO] When empty a DB collection that is not empty should return true

        // [GIVEN] That we have the expected outcome
        boolean expected = true;

        // [GIVEN] That we populate the DB with a Task
        populateDBWithTask(true);

        // [GIVEN] That we populate the DB with a User
        populateDBWithUser(false);

        // [WHEN] Emptying the empty collection
        boolean actual = emptyCollection(collName);

        // [THEN] The expected should be equal to the actual
        assertEquals(expected, actual);
    }

    private void assertTaskDocument(ArrayList<Object> expectedValues, Document document, boolean updateTask) {
        if (updateTask) {
            assertEquals(expectedValues.get(0), document.get("title"));
            assertEquals(expectedValues.get(1), document.get("description"));
            assertEquals(expectedValues.get(2), document.get("frequency"));
            assertEquals(expectedValues.get(3), document.get("urgency"));
            assertEquals(expectedValues.get(4), document.get("type"));
            assertEquals(expectedValues.get(5), document.get("assignees"));
            assertEquals(expectedValues.get(6), document.get("lastEdit"));
        } else {
            assertEquals(expectedValues.get(0), document.get("title"));
            assertEquals(expectedValues.get(1), document.get("description"));
            assertEquals(expectedValues.get(2), document.get("progress"));
            assertEquals(expectedValues.get(3), document.get("active"));
            assertEquals(expectedValues.get(4), document.get("comments"));
            assertEquals(expectedValues.get(5), document.get("assignees"));
        }
    }

    private void assertUserDocument(ArrayList<Object> expectedValues, Document document) {
        assertEquals(expectedValues.get(0), document.get("firstName"));
        assertEquals(expectedValues.get(1), document.get("lastName"));
        assertEquals(expectedValues.get(2), document.get("emailAddress"));
        assertEquals(expectedValues.get(3), document.get("phoneNumber"));
        assertEquals(expectedValues.get(4), document.get("admin"));
        assertEquals(expectedValues.get(5), document.get("role"));
    }

    private ArrayList<Object> expectedValues() {
        ArrayList<Object> list = new ArrayList<>();
        list.add("test");
        list.add("test task");
        list.add(0.5);
        list.add(true);
        list.add(makeArrayList());
        list.add(makeArrayList());
        return list;
    }

    private String populateDBWithTask(boolean isActive) {
        ArrayList<String> comments = makeArrayList();
        ArrayList<String> assignees = makeArrayList();
        LocalDate localDate = LocalDate.now();

        return exportTaskToDatabase(new Task("test", "test task", "Once", 0.5, isActive, comments, assignees, localDate), collName);
    }

    private String populateDBWithUser(boolean isAdmin) {
        return exportUserToDatabase(new User("firstName", "secondName", "email", "phoneNumber", isAdmin, "cleaner"), collName);
    }

    private ArrayList<String> makeArrayList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("dummy string " + i);
        }
        return list;
    }
}
