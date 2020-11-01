package EntityTests;
import java.util.ArrayList;
import java.util.List;

/**
 * The Attendee class represents an attendee, a user that can attend events.
 */
public class Attendee extends User {
    private List<User> contacts;
    private List<Event> eventList;

    /**
     * Creates a new attendee object.
     *
     * @param uname  the Attendee's username
     * @param pword  the Attendee's password
     */
    public Attendee(String uname, String pword){
        super(uname, pword);
        this.contacts = new ArrayList<>();
        this.eventList = new ArrayList<>();
    }

    /**
     * Returns the broadcast rights flag for this attendee.
     * This flag is always false, since an attendee cannot broadcast to events.
     *
     * @return  the attendee's broadcast rights
     */
    @Override
    public boolean hasBroadcastRights() {
        return false;
    }

    /**
     * Returns the event creation rights flag for this attendee.
     * This flag is always false, since only organizers can create events.
     *
     * @return  the attendee's event creation rights
     */
    @Override
    public boolean hasEventCreatingRights() {
        return false;
    }


    /**
     * Returns this attendee's contacts.
     *
     * @return  the attendee's contact list
     */
    public List<User> getContacts() {
        return contacts;
    }

    /**
     * Adds user to contact list.
     *
     * @param user  the new contact for this attendee
     */
    public void addContact(User user) {
        this.contacts.add(user);
    }

    /**
     * Returns all events this attendee is registered to attend.
     *
     * @return  the list of events where this attendee is signed
     */
    public List<Event> getEventList() {
        return eventList;
    }

    /**
     * Adds new event for which this attendee has signed up.
     *
     * @param event  the event for which this event signed up
     */
    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    /**
     * Cancels this Attendee's spot in event.
     * @param event the event the Attendee wants to cancel.
     */
    public void cancelEvent(Event event) {
        eventList.remove(event);
    }

}
