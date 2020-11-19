package UseCase;
import Entity.*;
import Gateway.IGateway;
import Gateway.IGateway2;

import java.util.*;

/**
 * Creates and contains a list of all Attendees, and has one Attendee logged in.
 * Communicates with the controllers.
 */
public class UserManager{

    private User user;
    private List<User> userList;
    private List<String> userInfoList;
    private ArrayList<List<String>> rawUserInfo;
    private IGateway gateway;
    private List<String> formattedContacts;


    /**
     * Creates a UserManager instance with the raw user information on every existing user in the system.
     * @param gateway, all the information on every user in the system, obtained from the Gateway
     */
    public UserManager(IGateway gateway, IGateway2 gateway2){
        this.gateway = gateway;
        ArrayList<List<String>> userInfo = gateway.read();
        gateway2.openForRead();
        formattedContacts = this.getStoredContacts(gateway2);
        gateway2.closeForRead();
        createUserList(userInfo);
        rawUserInfo = userInfo;
    }

    /**
     * Gets a User by its username
     *
     * @param user  username of the user
     * @return  user iff it is in userList
     */
    public User getUserByUsername(String user){
        for (User u : userList){
            if (u.getUsername().equals(user)){
                return u;
            }
        }
        return null;
    }

    private void createUserList(ArrayList<List<String>> userInfo){
        userList = new ArrayList<>();
        for (List<String> u : userInfo){
            addUserToList(u);
        }
    }

    private void addUserToList(List<String> userInfo) {
        String type = userInfo.get(2);
        if (type.equals("A")){
            User u = new Attendee(userInfo.get(0), userInfo.get(1));
            userList.add(u);
            readContacts(u.getUsername());
        } else if (type.equals("O")){
            User u = new Organizer(userInfo.get(0), userInfo.get(1));
            userList.add(u);
            readContacts(u.getUsername());
        } else {
            User u = new Speaker(userInfo.get(0), userInfo.get(1));
            userList.add(u);
            readContacts(u.getUsername());        }
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
        readContacts(username);
    }

    private void readContacts(String username){
        List<String> contactsList = new ArrayList<>();
        for(String contacts: formattedContacts){
            String[] tokens = contacts.split("\\|");

            if(username.equals(tokens[0])){
                contactsList.addAll(Arrays.asList(tokens).subList(1, tokens.length));
            }
        }
        getUserByUsername(username).setContacts(contactsList);
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
        this.user.addContact(user);
        User user2 = getUserByUsername(user);
        user2.addContact(this.user.getUsername());
    }

    /**
     * Removes a user to the logged-in user's list of contacts.
     *
     * @param user, the username of the user to be removed.
     */
    public void removeUserFromContacts(String user){
        this.user.removeContact(user);
        User user2 = getUserByUsername(user);
        user2.removeContact(this.user.getUsername());
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
        addUserToList(newUserInfo);
        rawUserInfo.add(newUserInfo);
    }

    /**
     * Stores the contacts of each user through a gateway.
     *      * Precondition: the gateway must not be open for write/read.
     *      *
     *      * @param gateway  the gateway through which we save our contacts
     */
    public void storeContacts(IGateway2 gateway2) {
        if (gateway2.openForWrite()) {

            for (User u: userList){
                StringBuilder contactList = new StringBuilder(u.getUsername());
                for (String contact: u.getContacts()) {
                    contactList.append("|").append(contact);
                }
                gateway2.write(contactList.toString());
            }

            gateway2.closeForWrite();
        }
    }


    // Each string should be formatted in the manner:
    // (Logged in user's username)|(contact1's username)|(contact2's username)|...
    private List<String> getStoredContacts(IGateway2 gateway2) {
        List<String> formattedContacts = new ArrayList<>();
        while (gateway2.hasNext()) {
            formattedContacts.add(gateway2.next());
        }
        return formattedContacts;
    }



}





