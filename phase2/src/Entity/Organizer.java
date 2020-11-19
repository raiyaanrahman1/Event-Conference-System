package Entity;


/**
 * The Organizer class represents an organizer, a user that creates events.
 * Organizers can also take on the role of an Attendee
 */
public class Organizer extends Attendee{

    /**
     * Creates a new organizer object.
     *
     * @param uname  the Organizer's username
     * @param password  the Organizer's password
     */
    public Organizer(String uname, String password) {
        super(uname, password);
    }

    /**
     * Gets the user type of this User
     * @return "O" since this user is an Organizer
     */
    @Override
    public String getUserType() {
        return "O";
    }
}
