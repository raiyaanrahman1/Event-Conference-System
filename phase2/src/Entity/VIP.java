package Entity;
/**
 * The VIP class represents a VIP attendee, an attendee that can attend VIP events.
 */
public class VIP extends Attendee{

    /**
     * Creates a new VIP object.
     *
     * @param uname  the VIP attendee's username
     * @param pword  the VIP attendee's password
     */
    public VIP(String uname, String pword){ super(uname, pword); }

    /**
     * Gets the user type of this User
     * @return "V" since this user is a VIP attendee
     */
    @Override
    public String getUserType() {
        return "V";
    }
}
