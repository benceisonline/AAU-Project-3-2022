package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    User user = new User();

    @Test
    void setFirstNameValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting the firstName as valid
        boolean actual = user.setFirstName("name");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setFirstNameNullTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the firstName as null
        boolean actual = user.setFirstName(null);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setLastNameValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting the lastName as null
        boolean actual = user.setLastName("name");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setLastNameNullTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the lastName as null
        boolean actual = user.setLastName(null);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setEmailValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting the email as null
        boolean actual = user.setEmailAddress("email@gmail.com");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setEmailNotValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the email as not valid
        boolean actual = user.setEmailAddress("email");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setEmailNameNullTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the email as null
        boolean actual = user.setEmailAddress(null);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setPhoneNumberValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting the phone number as valid
        boolean actual = user.setPhoneNumber("12345678");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setPhoneNumberNotValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the phone number not valid
        boolean actual = user.setPhoneNumber("1234");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setPhoneNumberNullTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the phone number as null
        boolean actual = user.setPhoneNumber(null);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setRoleValidTest() {
        // [GIVEN] That we store the expected value
        boolean expected = true;

        // [WHEN] Setting the role as valid
        boolean actual = user.setRole("Bartender");

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }

    @Test
    void setRoleNullTest() {
        // [GIVEN] That we store the expected value
        boolean expected = false;

        // [WHEN] Setting the phone number as null
        boolean actual = user.setRole(null);

        // [THEN] That the expected is equal to actual
        assertEquals(expected, actual);
    }
}
