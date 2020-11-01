package Entity;
import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private String username;
    private String password;
    private List<Message> receivedMessages;
    private List<Message> sentMessages;

    public User(String uname, String pword){
        this.username = uname;
        this.password = pword;
        this.receivedMessages = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
    }
    public abstract boolean hasBroadcastRights();
    public abstract boolean hasEventCreatingRights();

    //getter for username
    public String getUsername() {
        return username;
    }

    //getter for password
    public String getPassword() {
        return password;
    }

    /**
     * Returns the messages received by this user.
     *
     * @return  the list of received messages
     */
    public List<Message> getReceivedMessages() { return receivedMessages; }

    /**
     * Returns all messages sent by this attendee.
     *
     * @return  the list of messages sent by this user
     */
    public List<Message> getSentMessages() { return sentMessages; }

    //setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    //setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Adds a new message to this attendee's received messages.
     *
     * @param receivedMessage  the new message in this user's inbox
     */
    public void addReceivedMessage(Message receivedMessage){  this.receivedMessages.add(receivedMessage); }

    /**
     * Adds new message sent by this attendee.
     *
     * @param sentMessage  the new message sent by this user
     */
    public void addSentMessage(Message sentMessage){  this.sentMessages.add(sentMessage); }
}

