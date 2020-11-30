package Entity;


/**
 * The Speaker class represents a speaker, a user that speaks at an event.
 */
public class Speaker extends User {

    public static final String TYPE = "S";
    /**
     * Creates a new speaker object.
     *
     * @param uname  the Speaker's username
     * @param password  the Speaker's password
     */
    public Speaker(String uname, String password) {
        super(uname, password);
    }

    /**
     * Gets the user type of this User
     * @return "S" since this user is a Speaker
     */
    @Override
    public String getUserType() {
        return Speaker.TYPE;
    }
}
