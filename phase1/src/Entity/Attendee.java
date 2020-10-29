package Entity;
import java.util.ArrayList;

public class Attendee extends User{
    private ArrayList<User> contacts;
    private ArrayList<Message> receivedMessages;
    private ArrayList<Message> sentMessages;
    private ArrayList<Event> eventList;

    //Attendee Contructor
    public Attendee(String uname, String pword, ArrayList<User> contacts, ArrayList<Message> receivedMessages,
                    ArrayList<Message> sentMessages, ArrayList<Event> eventList){
        super(uname, pword);
        this.contacts = contacts;
        this.receivedMessages = receivedMessages;
        this.sentMessages = sentMessages;
        this.eventList = eventList;
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
    //receivedMessages setter
    public void setReceivedMessages(Message receivedMessage) {
        this.receivedMessages.add(receivedMessage);
    }


    //sentMessages getter
    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    //sentMessages setter
    public void setSentMessages(Message sentMessage) {
        this.sentMessages.add(sentMessage);
    }


    //eventList getter
    public ArrayList<Event> getEventList() {
        return eventList;
    }
    //eventList setter
    public void setEventList(Event event) {
        this.eventList.add(event);
    }


}
