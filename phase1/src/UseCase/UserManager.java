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
    private AttendeeSignUp attendeeSignUp;
    private EventScheduler eventScheduler;
    private MessageManager messageManager;
    private List<String> userInfoList;

    /**
     * Creates a UserManager instance with user 'logged-in' with the username of the given user.
     *  Adds the user's username, password and type ("A", "O", "S") in the userInfoList.
     * @param username The username of the current User that is logged-in
     */
    public UserManager(String username){
        this.userList = new ArrayList<>();
        userList.add(new Attendee("username1", "p4ssword"));
        this.user = getUserByUsername(username);


        userInfoList = new ArrayList<>();
        userInfoList.add(this.user.getUsername());
        userInfoList.add(this.user.getPassword());

        if (this.user instanceof Organizer){
            userInfoList.add("O");
        }
        else if (this.user instanceof Attendee){
            userInfoList.add("A");
        }
        else{
            userInfoList.add("S");
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
     * Gets the userInfoList
     *
     * @return a list of Strings containing the user's username, password and type.
     */
    public List<String> getUserInfoList() {
        return userInfoList;
    }

    public void addUserToContacts(String user){
        ((Attendee) this.user).addContact(getUserByUsername(user));
    }

    public void removeUserFromContacts(String user){
        ((Attendee) this.user).removeContact(getUserByUsername(user));
    }
    // ================ AttendeeSignUp Functionality ================

    /**
     * Signs up user for an event in AttendeeSignUp
     *
     * @param eventID the ID of the event to sign up for
     * @return true iff the user was signed up for the event
     */
    public boolean signUpForEvent(int eventID){
        return attendeeSignUp.signUpForEvent(eventScheduler.getEventByID(eventID));
    }

    /**
     * Cancels spot of user in an event in AttendeeSignUp
     *
     * @param eventID the ID of the event where we want to cancel the spot of a user
     * @return  true iff this user's spot in event was cancelled.
     */
    public boolean cancelSpot(int eventID){
        return attendeeSignUp.cancelSpot(eventScheduler.getEventByID(eventID));
    }

    /**
     * Creates a Speaker instance in AttendeeSignUp
     *
     * @param uname username of Speaker
     * @param pword password of Speaker
     * @return the Speaker instance
     */
    public Speaker createSpeakerAccount(String uname,
                                        String pword){
        return attendeeSignUp.createSpeakerAccount(uname, pword);
    }

    /**
     * Gets the list of contacts of user from AttendeeSignUp
     *
     * @return list of users
     */
    public List<String> getContactList(){
        return attendeeSignUp.getContactList();
    }

    // ================ EventScheduler Functionality ================

    /**
     * Adds an event to the event list of in EventScheduler
     *
     * @param eventID the ID of the event to add
     * @return  true iff the event is added
     */
    public boolean addEvent(int eventID){
        return eventScheduler.addEvent(eventScheduler.getEventByID(eventID));
    }

    /**
     * Removes an event from the event list of EventScheduler
     *
     * @param eventID  the ID of the event to remove
     * @return  true iff the event is removed
     */
    public boolean removeEvent(int eventID){
        return eventScheduler.removeEvent(eventScheduler.getEventByID(eventID));
    }

    /**
     * Gets the allowed events that this user may sign up for from EventScheduler
     *
     * @return a list of event IDs corresponding to the allowed events that this user may sign up for.
     */
    public List<Integer> getAllowedEvents(){
        return eventScheduler.getAllowedEvents();
    }

    /**
     * Gets the events that this user is already signed up for from EventScheduler
     *
     * @return a list of event IDs corresponding to the events this user is signed up for.
     */
    public List<Integer> getUserEvents(){
        return eventScheduler.getUserEvents();
    }


    // ================ MessageManager Functionality ================

    /**
     * Sends a message between users in MessageManager
     *
     * @param sender  the username of the user that sent the message
     * @param receiver  the username of the user that is to receive the message
     * @param message  the content of the message
     */
    public void message(String sender, String receiver,
                        String message){
        messageManager.message(getUserByUsername(sender),
                getUserByUsername(receiver), message);
    }

    /**
     * Broadcasts a message to an event in Message Manager
     *
     * @param user  the username of the user that broadcasts the message
     * @param eventID  the eventID of the event where the message is broadcast
     * @param message  the content of the message
     * @param date the date of the message
     * @param time the time that the message is sent
     */
    public boolean broadcast(String user, int eventID, String message, String date, String time){
        return messageManager.broadcast(getUserByUsername(user),
                eventScheduler.getEventByID(eventID), message);
    }

    /**
     * Gets the messages between given users in MessageManager
     *
     * @param sender  the username of the sender of the messages
     * @param receiver   the username of the receiver of the messages
     */
    public List<String> getMessages(String sender, String receiver) {
        return messageManager.getMessages(getUserByUsername(sender),
                getUserByUsername(receiver));
    }


    // ================ Getters, getters, getters. ================
    /**
     *
     * @return
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
     *
     * @param eventID
     * @return
     */
    public List<String> getUsersInEvent(int eventID) {
        Event event = eventScheduler.getEventByID(eventID);

        List<String> usernames = new ArrayList<>();
        for (User user: event.getAttendees()) {
            usernames.add(user.getUsername());
        }

        return usernames;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean isPasswordCorrect(String username, String password) {
        return getUserByUsername(username).getPassword().equals(password);
    }

    /**
     * Precondition: the user has to be a speaker.
     * @param username
     * @return
     */
    public List<Integer> getEventsBySpeaker(String username) {
        List<Integer> eventIDs = new ArrayList<>();
        List<Event> talks = eventScheduler.getEventsBySpeaker(getUserByUsername(username));
        for (Event event: talks) {
            eventIDs.add(event.getEventID());
        }
        return eventIDs;
    }

    /**
     *
     * @param eventID
     * @return
     */
    public String getTimeByEventID(int eventID) {
        return eventScheduler.getEventByID(eventID).getTime();
    }

    /**
     *
     * @param eventID
     * @return
     */
    public String getDateByEventID(int eventID) {
        return eventScheduler.getEventByID(eventID).getDate();
    }

    /**
     *
     * @param eventID
     * @return
     */
    public String getRoomByEventID(int eventID) {
        return eventScheduler.getEventByID(eventID).getRoom();
    }

    public List<Integer> getTalks(){
        List<Integer> eventIDs = new ArrayList<>();
        for (Event event: ((Speaker) user).getTalks()) {
            eventIDs.add(event.getEventID());
        }
        return eventIDs;
    }

    public List<Integer> getOrganizedEvents(){
        List<Integer> eventIDs = new ArrayList<>();
        for (Event event: ((Organizer) user).getOrganizedEvents()) {
            eventIDs.add(event.getEventID());
        }
        return eventIDs;
    }

    /**
     * Get a list of the user's received messages sorted by date and time
     *
     * @return List of user's received messages
     */
    private List<Message> getSortedReceivedMessages(){
        List<Message> receivedMessagesList = user.getReceivedMessages();
        int n = receivedMessagesList.size();

        for (int i = 0; i < n-1; i++)
        {
            int minimum = i;
            for (int j = i+1; j < n; j++)
                if (receivedMessagesList.get(j).getDateTime().isAfter(receivedMessagesList.get(minimum).getDateTime()))
                    minimum = j;

            Message tempMsg = receivedMessagesList.get(minimum);
            receivedMessagesList.set(minimum, receivedMessagesList.get(i));
            receivedMessagesList.set(i, tempMsg);
        }
        return receivedMessagesList;
    }

    /**
     * Get a table of the user's received messages, where the first column is the username of the sender and
     * the second column is the content of the message. This table is sorted by date and time.
     *
     * @return Table of strings
     */
    public String [][] getReceivedMessageListWithSender() {
        List<Message> receivedMessagesList = getSortedReceivedMessages();
        String [][] messageBySenderTable = new String [receivedMessagesList.size()][1];
        int i = 0;
        for (Message msg: receivedMessagesList){
            messageBySenderTable[i][0] = msg.getSender();
            messageBySenderTable[i][1] = msg.getContent();
        }
        return messageBySenderTable;
    }

}

