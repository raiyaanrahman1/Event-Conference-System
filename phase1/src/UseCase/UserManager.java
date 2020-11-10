package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates and contains a list of all Attendees, and has one Attendee logged in.
 * Communicates with the controllers.
 */
public class UserManager{

    private User user;
    private List<User> userList;
    private List<String> userInfoList;
    private ArrayList<List<String>> rawUserInfo;

    /**
     * Creates a UserManager instance with the raw user information on every existing user in the system.
     * @param userInfo, all the information on every user in the system, obtained from the Gateway
     */
    public UserManager(ArrayList<List<String>> userInfo){
        createUserList(userInfo);
        rawUserInfo = userInfo;

    }
    /**
     * Gets a User by its username
     *
     * @param user  username of the user
     * @return  user iff it is in userList
     */
    private User getUserByUsername(String user){
        for (User u : userList){
            if (u.getUsername().equals(user)){
                return u;
            }
        }
        return null;
    }

    /**
     * Instantiates User objects representing the existing users in the system.
     *
     * @param userInfo, the raw user information read from the gateway.
     */
    private void createUserList(ArrayList<List<String>> userInfo){
        userList = new ArrayList<>();
        for (List<String> u : userInfo){
            String type = u.get(2);
            if (type.equals("A")){
                userList.add(new Attendee(u.get(0), u.get(1)));
            } else if (type.equals("O")){
                userList.add(new Organizer(u.get(0), u.get(1)));
            } else {
                userList.add(new Speaker(u.get(0), u.get(1)));
            }
        }
    }

    /**
     * Log-in user and updates userInfoList with username, password and type identifier ("A", "O" or "S").
     *
     * @param username, the username of the logged-in User
     */
    public void logInUser(String username){
        this.user = getUserByUsername(username);
        userInfoList = new ArrayList<>();
        for (List<String> u : rawUserInfo){
            if (u.get(0).equals(username)){
                userInfoList = u;
            }
        }
    }


    /**
     * Gets the user that is currently logged-in
     *
     * @return User that is logged in
     */
    public User getUser() {
        return user;
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
    public void addUserToContacts(String user){
        ((Attendee) this.user).addContact(user);
    }

    /**
     * Removes a user to the logged-in user's list of contacts.
     *
     * @param user, the username of the user to be removed.
     */
    public void removeUserFromContacts(String user){
        ((Attendee) this.user).removeContact(user);
    }

    /**
     * Checks if the inputted password corresponds to the logged-in user's password.
     * @param username, the logged-in user's username.
     * @param password, the logged-in user's password.
     *
     * @return true iff the inputted password matches the logged-in user's password.
     */
    public boolean isPasswordCorrect(String username, String password) {
        return getUserByUsername(username).getPassword().equals(password);
    }

    /**
     * Creates a Speaker account iff the logged-in User is an Organiser.
     *
     * @param uname username of Speaker
     * @param pword password of Speaker
     */
    public void createSpeakerAccount(String uname,
                                     String pword){
        userList.add(new Speaker(uname, pword));
    }

    /**
     * Gets a list of the usernames of every existing user in the system.
     *
     * @return a list of strings representing every user's username.
     */
    public List<String> getSignedUpUsers() {
        List<String> usernames = new ArrayList<>();
        for (User user: userList) {
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
    public List<String> getContactList(){
        return user.getContacts();
    }

}

