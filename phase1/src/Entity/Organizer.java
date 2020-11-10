package Entity;

import java.util.ArrayList;
import java.util.List;
/**
 * The Organizer class represents an organizer, a user that creates events.
 * Organizers can also take on the role of an Attendee
 */
public class Organizer extends Attendee{
    private List<Event> organizedEvents;

    /**
     * Creates a new organizer object.
     *
     * @param uname  the Organizer's username
     * @param pword  the Organizer's password
     */
    public Organizer(String uname, String pword) {
        super(uname, pword);
        this.organizedEvents = new ArrayList<>();
    }


    public List<Event> getOrganizedEvents() {
        return organizedEvents;
    }

    /**
     * Adds an event to the list of organized events of this organizer
     *
     * @param event  the event that this organizer created
     */
    public void addOrganizedEvent(Event event){ this.organizedEvents.add(event); }

    /**
     * Removes an event from the list of organized events of this organizer
     *
     * @param event  the event that this organizer created
     */
    public void removeOrganizedEvent(Event event){ this.organizedEvents.remove(event); }

}
