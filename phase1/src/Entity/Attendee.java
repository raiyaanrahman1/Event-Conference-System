package Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * The Attendee class represents an attendee, a user that can attend events.
 */
public class Attendee extends User {

    /**
     * Creates a new attendee object.
     *
     * @param uname  the Attendee's username
     * @param pword  the Attendee's password
     */
    public Attendee(String uname, String pword){
        super(uname, pword);
    }

    @Override
    public String getUserType() {
        return "A";
    }
}
