package Entity;

import java.util.ArrayList;
import java.util.List;
/**
 * The Organizer class represents an organizer, a user that creates events.
 * Organizers can also take on the role of an Attendee
 */
public class Organizer extends Attendee{

    /**
     * Creates a new organizer object.
     *
     * @param uname  the Organizer's username
     * @param pword  the Organizer's password
     */
    public Organizer(String uname, String pword) {
        super(uname, pword);
    }

}
