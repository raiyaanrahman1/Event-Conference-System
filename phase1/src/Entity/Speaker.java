package Entity;

import java.util.ArrayList;
import java.util.List;
/**
 * The Speaker class represents a speaker, a user that speaks at an event.
 */
public class Speaker extends User {
    private List<Event> talks;

    /**
     * Creates a new speaker object.
     *
     * @param uname  the Speaker's username
     * @param pword  the Speaker's password
     */
    public Speaker(String uname, String pword) {
        super(uname, pword);
        this.talks = new ArrayList<>();
    }

    /**
     * Returns true to allow Speaker to mass message the attendees of their event
     */
    @Override
    public boolean hasBroadcastRights() {
        return true;
    }

    /**
     * Returns false since Speaker cannot create events
     */
    @Override
    public boolean hasEventCreatingRights() {
        return false;
    }

    /**
     * Returns list of events of the speaker
     */
    public List<Event> getTalks() {
        return talks;
    }//appending an event to the talk list

    /**
     * Adds an event the the list of events of the speaker
     *
     * @param talk  the Speaker's event
     */
    public void addTalk(Event talk){
        talks.add(talk);
    }

}
