package Entity;

import java.util.ArrayList;
import java.util.List;
/**
 * The User class represents a user, a user can be an Attendee, an Organizer or a Speaker.
 */
public abstract class User {
    private final String username;
    private final String password;
    private List<String> contacts;

    /**
     * Creates a new user object.
     *
     * @param uname  the User's username
     * @param pword  the User's password
     */
    public User(String uname, String pword){
        this.username = uname;
        this.password = pword;
        this.contacts = new ArrayList<>();
    }

    /**
     * Gets the username of this User
     * @return the username of this User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of this User
     * @return the password of this User
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user type of this User
     */
    public abstract String getUserType();

    /**
     * Gets this user's contacts.
     *
     * @return  the user's contact list
     */
    public List<String> getContacts() {
        return contacts;
    }

    /**
     * Sets this user's contacts.
     *
     * @param   contacts list of this user's contacts
     */
    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    /**
     * Adds user to contact list.
     *
     * @param username  the username of the new contact for this user
     */
    public void addContact(String username) {
        this.contacts.add(username);
    }

    /**
     * Remove user to contact list.
     *
     * @param username  the username of the contact we want to remove from this user's contact list
     */
    public void removeContact(String username) {
        this.contacts.remove(username);
    }


}

