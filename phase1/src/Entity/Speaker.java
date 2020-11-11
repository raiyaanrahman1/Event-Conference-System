package Entity;

import java.util.ArrayList;
import java.util.List;
/**
 * The Speaker class represents a speaker, a user that speaks at an event.
 */
public class Speaker extends User {

    /**
     * Creates a new speaker object.
     *
     * @param uname  the Speaker's username
     * @param pword  the Speaker's password
     */
    public Speaker(String uname, String pword) {
        super(uname, pword);
    }

    @Override
    public String getUserType() {
        return "S";
    }
}
