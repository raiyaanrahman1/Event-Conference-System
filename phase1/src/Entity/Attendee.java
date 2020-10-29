package Entity;
import java.util.ArrayList;

public class Attendee extends User{
    private ArrayList<User> contacts;
    private ArrayList<Message> receivedMessages;
    private ArrayList<Message> sentMessages;
    private ArrayList<Event> eventList;

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
    public ArrayList<User> getContacts() {
        return contacts;
    }
    //Method that appends a contact to contacts
    public void addContact(User user) {
        this.contacts.add(user);
    }


    //receivedMessages getter
    public ArrayList<Message> getReceivedMessages() {
        return receivedMessages;
    }
    //receivedMessages adder
    public void addReceivedMessages(Message receivedMessage) {
        this.receivedMessages.add(receivedMessage);
    }


    //sentMessages getter
    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    //sentMessages adder
    public void addSentMessages(Message sentMessage) {
        this.sentMessages.add(sentMessage);
    }


    //eventList getter
    public ArrayList<Event> getEventList() {
        return eventList;
    }
    //eventList adder
    public void addEventList(Event event) {
        this.eventList.add(event);
    }


}
