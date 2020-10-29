package Entity;

import java.util.ArrayList;

public class Organizer extends User{
    private ArrayList<Event> organizedEvents;
    private ArrayList<User> contacts;
    private ArrayList<Message> receivedMessages;
    private ArrayList<Message> sentMessages;
    private ArrayList<Event> eventList;

    public Organizer(String uname, String pword) {
        super(uname, pword);
        this.organizedEvents = new ArrayList<>();
        this.contacts = new ArrayList<>();
        this.receivedMessages = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.eventList = new ArrayList<>();
    }
    @Override
    boolean hasBroadcastRights() {
        return true;
    }

    @Override
    boolean hasEventCreatingRights() {
        return true;
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
    public void addReceivedMessage(Message receivedMessage) {
        this.receivedMessages.add(receivedMessage);
    }


    //sentMessages getter
    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    //sentMessages adder
    public void addSentMessage(Message sentMessage) {
        this.sentMessages.add(sentMessage);
    }


    //eventList getter
    public ArrayList<Event> getEventList() {
        return eventList;
    }
    //eventList adder
    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    public ArrayList<Event> getOrganizedEvents() {
        return organizedEvents;
    }
    public void addOrganizedEvent(Event event){ this.organizedEvents.add(event); }

}
