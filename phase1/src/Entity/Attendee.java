package Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * The Attendee class represents an attendee, a user that can attend events.
 */
public class Attendee extends User {
    private List<Event> eventList;

    /**
     * Creates a new attendee object.
     *
     * @param uname  the Attendee's username
     * @param pword  the Attendee's password
     */
    public Attendee(String uname, String pword){
        super(uname, pword);
        this.eventList = new ArrayList<>();
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
