package Entity;
import java.util.ArrayList;
import java.util.List;

public class Attendee extends User{
    private List<User> contacts;
    private List<Message> receivedMessages;
    private List<Message> sentMessages;
    private List<Event> eventList;

    //Attendee Contructor
    public Attendee(String uname, String pword){
        super(uname, pword);
        this.contacts = new ArrayList<>();
        this.receivedMessages = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.eventList = new ArrayList<>();
    }

    @Override
    boolean hasBroadcastRights() {
        return false;
    }

    @Override
    boolean hasEventCreatingRights() {
        return false;
    }


    //contacts Getter
    public List<User> getContacts() {
        return contacts;
    }
    //Method that appends a contact to contacts
    public void addContact(User user) {
        this.contacts.add(user);
    }


    //receivedMessages getter
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }
    //receivedMessages adder
    public void addReceivedMessages(Message receivedMessage) {
        this.receivedMessages.add(receivedMessage);
    }


    //sentMessages getter
    public List<Message> getSentMessages() {
        return sentMessages;
    }

    //sentMessages adder
    public void addSentMessages(Message sentMessage) {
        this.sentMessages.add(sentMessage);
    }


    //eventList getter
    public List<Event> getEventList() {
        return eventList;
    }
    //eventList adder
    public void addEventList(Event event) {
        this.eventList.add(event);
    }


}
