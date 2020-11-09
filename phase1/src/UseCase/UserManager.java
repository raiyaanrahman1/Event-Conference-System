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
//    private AttendeeSignUp attendeeSignUp;
//    private EventScheduler eventScheduler;
//    private MessageManager messageManager;
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
        ((Attendee) this.user).addContact(getUserByUsername(user));
    }

    /**
     * Removes a user to the logged-in user's list of contacts.
     *
     * @param user, the username of the user to be removed.
     */
    public void removeUserFromContacts(String user){
        ((Attendee) this.user).removeContact(getUserByUsername(user));
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
     * Gets the events that this user is already signed up for, iff user is an Attendee.
     *
     * @return a list of event IDs corresponding to the events this user is signed up for.
     */
    public List<Integer> getUserEvents(){
        return ((Attendee) user).getEventList();
    }

    /**
     * Gets the events that this user is speaking in, iff user is an Speaker.
     *
     * @return a list of event IDs corresponding to the talks user is giving.
     */
    public List<Integer> getTalks(){
        return ((Speaker) user).getTalks();
    }

    /**
     * Gets the events that this user organised, iff user is an Organizer.
     *
     * @return a list of event IDs corresponding to the events this user organised.
     */
    public List<Integer> getOrganizedEvents(){
        return ((Organizer) user).getOrganizedEvents();
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


//    private void createUserInfoList(){
//        userInfoList = new ArrayList<>();
//        userInfoList.add(this.user.getUsername());
//        userInfoList.add(this.user.getPassword());
//
//        if (this.user instanceof Organizer){
//            userInfoList.add("O");
//        }
//        else if (this.user instanceof Attendee){
//            userInfoList.add("A");
//        }
//        else{
//            userInfoList.add("S");
//        }
//    }
    // ================ AttendeeSignUp Functionality ================
//
//    /**
//     * Signs up user for an event in AttendeeSignUp
//     *
//     * @param eventID the ID of the event to sign up for
//     * @return true iff the user was signed up for the event
//     */
//    public boolean signUpForEvent(int eventID){
//        return attendeeSignUp.signUpForEvent(eventScheduler.getEventByID(eventID));
//    }
//
//    /**
//     * Cancels spot of user in an event in AttendeeSignUp
//     *
//     * @param eventID the ID of the event where we want to cancel the spot of a user
//     * @return  true iff this user's spot in event was cancelled.
//     */
//    public boolean cancelSpot(int eventID){
//        return attendeeSignUp.cancelSpot(eventScheduler.getEventByID(eventID));
//    }



//    /**
//     * Gets the list of contacts of user from AttendeeSignUp
//     *
//     * @return list of users
//     */
//    public List<String> getContactList(){
////        return attendeeSignUp.getContactList();
////    }

    // ================ EventScheduler Functionality ================
//
//    /**
//     * Adds an event to the event list of in EventScheduler
//     *
//     * @param eventID the ID of the event to add
//     * @return  true iff the event is added
//     */
//    public boolean addEvent(int eventID){
//        return eventScheduler.addEvent(eventScheduler.getEventByID(eventID));
//    }
//
//    /**
//     * Removes an event from the event list of EventScheduler
//     *
//     * @param eventID  the ID of the event to remove
//     * @return  true iff the event is removed
//     */
//    public boolean removeEvent(int eventID){
//        return eventScheduler.removeEvent(eventScheduler.getEventByID(eventID));
//    }
//
//    /**
//     * Gets the allowed events that this user may sign up for from EventScheduler
//     *
//     * @return a list of event IDs corresponding to the allowed events that this user may sign up for.
//     */
//    public List<Integer> getAllowedEvents(){
//        return eventScheduler.getAllowedEvents();
//    }
//
//
//    // ================ MessageManager Functionality ================
//
//    /**
//     * Sends a message between users in MessageManager
//     *
//     * @param sender  the username of the user that sent the message
//     * @param receiver  the username of the user that is to receive the message
//     * @param message  the content of the message
//     */
//    public void message(String sender, String receiver,
//                        String message){
//        messageManager.message(getUserByUsername(sender),
//                getUserByUsername(receiver), message);
//    }
//
//    /**
//     * Broadcasts a message to an event in Message Manager
//     *
//     * @param user  the username of the user that broadcasts the message
//     * @param eventID  the eventID of the event where the message is broadcast
//     * @param message  the content of the message
//     * @param date the date of the message
//     * @param time the time that the message is sent
//     */
//    public boolean broadcast(String user, int eventID, String message, String date, String time){
//        return messageManager.broadcast(getUserByUsername(user),
//                eventScheduler.getEventByID(eventID), message);
//    }
//
//    /**
//     * Gets the messages between given users in MessageManager
//     *
//     * @param sender  the username of the sender of the messages
//     * @param receiver   the username of the receiver of the messages
//     */
//    public List<String> getMessages(String sender, String receiver) {
//        return messageManager.getMessages(getUserByUsername(sender),
//                getUserByUsername(receiver));
//    }


    // ================ Getters, getters, getters. ================


//    /**
//     *
//     * @param eventID
//     * @return
//     */
//    public List<String> getUsersInEvent(int eventID) {
//        Event event = eventScheduler.getEventByID(eventID);
//
//        List<String> usernames = new ArrayList<>();
//        for (User user: event.getAttendees()) {
//            usernames.add(user.getUsername());
//        }
//
//        return usernames;
//    }


//    /**
//     * Precondition: the user has to be a speaker.
//     * @param username
//     * @return
//     */
//    public List<Integer> getEventsBySpeaker(String username) {
//        List<Integer> eventIDs = new ArrayList<>();
//        List<Event> talks = eventScheduler.getEventsBySpeaker(getUserByUsername(username));
//        for (Event event: talks) {
//            eventIDs.add(event.getEventID());
//        }
//        return eventIDs;
//    }
//
//    /**
//     *
//     * @param eventID
//     * @return
//     */
//    public String getTimeByEventID(int eventID) {
//        return eventScheduler.getEventByID(eventID).getTime();
//    }
//
//    /**
//     *
//     * @param eventID
//     * @return
//     */
//    public String getDateByEventID(int eventID) {
//        return eventScheduler.getEventByID(eventID).getDate();
//    }
//
//    /**
//     *
//     * @param eventID
//     * @return
//     */
//    public String getRoomByEventID(int eventID) {
//        return eventScheduler.getEventByID(eventID).getRoom();
//    }
//

//    /**
//     * Get a list of the user's received messages sorted by date and time
//     * TODO: test order of saved messages (if it maintains chronological order)
//     * @return List of user's received messages
//     */
//    private List<Message> getSortedReceivedMessages(){
//        List<Message> receivedMessagesList = user.getReceivedMessages();
//        int n = receivedMessagesList.size();
//
//        for (int i = 0; i < n-1; i++)
//        {
//            int minimum = i;
//            for (int j = i+1; j < n; j++)
//                if (receivedMessagesList.get(j).getDateTime().isAfter(receivedMessagesList.get(minimum).getDateTime()))
//                    minimum = j;
//
//            Message tempMsg = receivedMessagesList.get(minimum);
//            receivedMessagesList.set(minimum, receivedMessagesList.get(i));
//            receivedMessagesList.set(i, tempMsg);
//        }
//        return receivedMessagesList;
//    }
//
//    /**
//     * Get a table of the user's received messages, where the first column is the username of the sender and
//     * the second column is the content of the message. This table is sorted by date and time.
//     *
//     * @return Table of strings
//     */
//    public String [][] getReceivedMessageListWithSender() {
//        List<Message> receivedMessagesList = getSortedReceivedMessages();
//        String [][] messageBySenderTable = new String [receivedMessagesList.size()][1];
//        int i = 0;
//        for (Message msg: receivedMessagesList){
//            messageBySenderTable[i][0] = msg.getSender();
//            messageBySenderTable[i][1] = msg.getContent();
//        }
//        return messageBySenderTable;
//    }
//
//    /**
//     * Returns ids of all events.
//     * @return
//     */
//    public List<Integer> getAllEventIDs() {
//        List<Integer> ids = new ArrayList<>();
//
//        for(Event event: eventScheduler.getAllEvents()) {
//            ids.add(event.getEventID());
//        }
//
//        return ids;
//    }

}

