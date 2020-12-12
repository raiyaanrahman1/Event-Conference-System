package UseCase;


import Entity.User;
import Gateway.IGateway;
import Gateway.IGateway2;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates and contains a list of all Attendees, and has one Attendee logged in.
 * Communicates with the controllers.
 */
public class UserManager {

    private User user;
    private final UserStore userStore;
    private List<String> userInfoList;
    private final ArrayList<List<String>> rawUserInfo;
    private final IGateway gateway;

    /**
     * Creates a UserManager instance with the raw user information on every existing user in the system.
     *
     * @param gateway, all the information on every user in the system, obtained from the Gateway
     */
    public UserManager(IGateway gateway, IGateway2 gateway2) {
        userStore = new UserStore(gateway2);
        this.gateway = gateway;
        ArrayList<List<String>> userInfo = gateway.read();
        userStore.createUserList(userInfo);
        rawUserInfo = userInfo;
    }
    /**
     * Gets the list of all the Speakers.
     * @return the list of Speakers
     */
    public List<String> getSpeakers(){
        List<String> listOfSpeakers = new ArrayList<>();
        for(User speaker : userStore.userList) {
            if (speaker.getUserType().equals("S")){
                listOfSpeakers.add(speaker.getUsername());
            }
        }
        return listOfSpeakers;
    }

    /**
     * Gets a User by its username
     *
     * @param user username of the user
     * @return user iff it is in userList
     */
    public User getUserByUsername(String user) {
        for (User u : userStore.userList) {
            if (u.getUsername().equals(user)) {
                return u;
            }
        }
        return null;
    }
    /**
     * Gets the user type (A, O, S or V).
     * @return the letter indiciating the user type
     */
    public String getUserType(String user){
        return this.getUserByUsername(user).getUserType();
    }

    /**
     * Log-in user and updates userInfoList with username, password and type identifier ("A", "O" or "S").
     *
     * @param username, the username of the logged-in User
     */
    public boolean logInUser(String username, String password) {
        this.user = getUserByUsername(username);
        if (this.user != null && this.user.getPassword().equals(password)) {
            userInfoList = new ArrayList<>();
            for (List<String> u : rawUserInfo) {
                if (u.get(0).equals(username)) {
                    userInfoList = u;
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Gets a list containing the username, password and type identifier ("A", "O" or "S") of
     * the current logged-in user.
     *
     * @return a list of Strings containing the user's username, password and type.
     */
    public List<String> getUserInfoList() {
        return userInfoList;
    }

    /**
     * Adds a user to the logged-in user's list of contacts.
     *
     * @param user, the username of the user to be added.
     */
    public void addUserToContacts(String user) {
        this.user.addContact(user);
        User user2 = getUserByUsername(user);
        user2.addContact(this.user.getUsername());
    }

    /**
     * Removes a user to the logged-in user's list of contacts.
     *
     * @param user, the username of the user to be removed.
     */
    public void removeUserFromContacts(String user) {
        this.user.removeContact(user);
        User user2 = getUserByUsername(user);
        user2.removeContact(this.user.getUsername());
    }

    /**
     * Gets a list of the usernames of every existing user in the system.
     *
     * @return a list of strings representing every user's username.
     */
    public List<String> getSignedUpUsers() {
        List<String> usernames = new ArrayList<>();
        for (User user : userStore.userList) {
            usernames.add(user.getUsername());
        }
        usernames.sort(null);
        return usernames;
    }

    /**
     * Gets user's contact list.
     *
     * @return the usernames of the contacts of user
     */
    public List<String> getContactList() {
        return user.getContacts();
    }

    /**
     * Creates the user and adds it to gateway.
     *
     * @param username is a String representation of the users username
     * @param password is a String representation of the users password
     * @param userType is a String representation of the users type
     */
    public void CreateUser(String username, String password, String userType) {
        List<String> newUserInfo = new ArrayList<>();
        newUserInfo.add(username);
        newUserInfo.add(password);
        newUserInfo.add(userType);
        gateway.append(newUserInfo);
        // Add to lists
        userStore.addUserToList(newUserInfo);
        rawUserInfo.add(newUserInfo);
    }

    /**
     * Stores the contacts of each user through a gateway.
     * * Precondition: the gateway must not be open for write/read.
     * *
     * * @param gateway  the gateway through which we save our contacts
     */
    public void storeContacts() {
        userStore.storeContacts();
    }
}





