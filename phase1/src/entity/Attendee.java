package entity;
import java.*;
import java.util.List;

public class Attendee {
    private String username;
    private String password;
    private List<User> contacts;
    private List<Message> receivedMessages;
    private List<Message> sentMessages;
    private List<Event> eventList;

    public Attendee(String uname, String pword){
        super(uname, pword);
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    boolean hasBroadcastRights(){
        return False;
    }

    boolean hasEventCreatingRights(){
        return False;
    }

    public List<string> getContacts() {
        return contacts;
    }

    public void addContact(User user) {
        this.contacts.add(user);
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
}
