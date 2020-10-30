package Entity;

import java.util.ArrayList;
import java.util.List;

public class Organizer extends User{
    private List<Event> organizedEvents;
    private List<User> contacts;
    private List<Event> eventList;

    public Organizer(String uname, String pword) {
        super(uname, pword);
        this.organizedEvents = new ArrayList<>();
        this.contacts = new ArrayList<>();
        this.eventList = new ArrayList<>();
    }
    @Override
    public boolean hasBroadcastRights() {
        return true;
    }

    @Override
    public boolean hasEventCreatingRights() {
        return true;
    }


    //contacts Getter
    public List<User> getContacts() {
        return contacts;
    }
    //Method that appends a contact to contacts
    public void addContact(User user) {
        this.contacts.add(user);
    }


    //eventList getter
    public List<Event> getEventList() {
        return eventList;
    }
    //Method that appends an event to eventList
    public void addEvent(Event event) {
        this.eventList.add(event);
    }


    //organizedEvents list getter
    public List<Event> getOrganizedEvents() {
        return organizedEvents;
    }
    //Method that appends an event to organizedEvent
    public void addOrganizedEvent(Event event){ this.organizedEvents.add(event); }

}
