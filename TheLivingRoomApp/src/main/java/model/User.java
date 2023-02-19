package model;

import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class User {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private String phoneNumber;
    private String role;
    private boolean admin;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean setFirstName(String firstName) {
        if (firstName == null) {
            return false;
        } else {
            this.firstName = firstName;
            return true;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public boolean setLastName(String lastName) {
        if (lastName == null) {
            return false;
        } else {
            this.lastName = lastName;
            return true;
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public boolean setEmailAddress(String emailAddress) {
        if (emailAddress == null) {
            return false;
        } else if (emailAddress.contains("@") & emailAddress.contains(".")) {
            this.emailAddress = emailAddress;
            return true;
        }
        return false;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }

        if (phoneNumber.length() >= 8) {

            if (!Pattern.matches("[a-zA-Z]+", phoneNumber)) {
                this.phoneNumber = phoneNumber;
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getRole() {
        return role;
    }

    public boolean setRole(String role) {
        if (role == null) {
            return false;
        } else {
            this.role = role;
            return true;
        }
    }

    public String getFullName() {
        if (firstName == null && lastName == null){
            return ("");
        }
        else {
            return (firstName + " " + lastName);
        }
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User(ObjectId id, String firstName, String lastName, String emailAddress, String phoneNumber, boolean admin, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.admin = admin;
        this.role = role;
    }

    public User(String firstName, String lastName, String emailAddress, String phoneNumber, boolean admin, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.admin = admin;
        this.role = role;
    }

    public User(ObjectId id, String fullName, String role) {
        this.id = id;
        this.fullName = fullName;
        this.role = role;
    }

    public User() {
    }
}
