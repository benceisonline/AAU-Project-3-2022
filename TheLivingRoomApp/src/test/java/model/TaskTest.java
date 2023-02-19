package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    Task task = new Task(0.0, true);

    @Test
    void setTitleEmptyTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN]
        boolean actual = task.setTitle("");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setTitleOnlySpacesTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the title with only spaces
        boolean actual = task.setTitle("     ");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setTitleNullTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the title as null
        boolean actual = task.setTitle(null);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setTitleValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting title is a valid title
        boolean actual = task.setTitle("This is a title");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setDescriptionNullTest() {
        // [GIVEN] That we store the expected value
        String expected = "";

        // [WHEN] Setting description as null
        task.setDescription(null);
        String actual = task.getDescription();

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setDescriptionValidTest() {
        // [GIVEN] That we store the expected value
        String expected = "test";

        // [WHEN] Setting description as valid
        task.setDescription(expected);
        String actual = task.getDescription();

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setFrequencyNullTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting frequency as null
        boolean actual = task.setFrequency(null);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setFrequencyValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting frequency as valid
        boolean actual = task.setFrequency("Once");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setUrgencyNullTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting frequency as null
        boolean actual = task.setUrgency(null);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setUrgencyValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting frequency as valid
        boolean actual = task.setUrgency("Once");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setTypeNullTest() {
        // [GIVEN] That we store the expected value
        String expected = "All";

        // [WHEN] Setting description as null
        task.setType(null);
        String actual = task.getType();

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setTypeValidTest() {
        // [GIVEN] That we store the expected value
        String expected = "Cleaner";

        // [WHEN] Setting description as valid
        task.setType(expected);
        String actual = task.getType();

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setProgressPositiveTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting progress with positive amount
        boolean actual = task.setProgress(50.0);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setProgressNegativeTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting progress with negative
        boolean actual = task.setProgress(-25.0);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setProgressZeroTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting progress with zero
        boolean actual = task.setProgress(0);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setDateNullTest() {
        // [GIVEN] That we store the expected value
        LocalDate expected = LocalDate.now();

        // [WHEN] Setting description as valid
        task.setDate(null);
        LocalDate actual = task.getDate();

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setDateValidTest() {
        // [GIVEN] That we store the expected value
        String expected = "2022-11-11";

        // [WHEN] Setting description as valid
        task.setDate(LocalDate.of(2022, 11, 11));
        String actual = task.getDate().toString();

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }
}
