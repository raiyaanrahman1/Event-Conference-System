package UseCase;
import Entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of the Sign-Up System and is responsible for the sign-up process of different Users.
 * Operates by requests through AttendeeManager.
 */
public class AttendeeSignUp {
    private Attendee attendee;

    /**
     * Creates an AttendeeSignUp instance with attendee 'logged-in'.
     * @param attendee The current Attendee that is logged-in
     */
    public AttendeeSignUp(Attendee attendee){
        this.attendee = attendee;
    }

    // public void createAccount();

    private boolean conflictingTime(Event event, List<Event> eventList) {
        for (Event e : eventList){
            if (e.getTime().equals(event.getTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Signs up attendee for event.
     * @param event The event this Attendee wants to sign up for
     * @return true iff attendee was signed up for event.
     */
    public boolean signUpForEvent(Event event){
        // check if Attendee is attending a talk scheduled at the same time but in a different room
        if (!conflictingTime(event, attendee.getEventList()) &&
                event.getAttendees().size() + 1 <= 2 &&
                !attendee.getEventList().contains(event)){
            attendee.addEvent(event);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cancels attendee's spot in event.
     * @param event The event this Attendee wants to cancel
     * @return true iff this attendee's spot in event was cancelled.
     */
    public boolean cancelSpot(Event event){
        if (attendee.getEventList().contains(event)) {
            attendee.cancelEvent(event);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Create a Speaker account iff attendee is an Organiser.
     * @param uname the Speaker's username
     * @param pword the Speaker's password
     * @return the speaker account that was created, to be stored in AttendeeManager.
     */
    public Speaker createSpeakerAccount(String uname,
                                        String pword){
        if (attendee.hasBroadcastRights() && attendee.hasEventCreatingRights()){
            return new Speaker(uname, pword);
        }
        // should throw an exception that we haven't coded yet
        return null;
    }

    public Attendee getCurrentUser() {
        return attendee;
    }

    public List<String> getContactList(){
        List<User> users = attendee.getContacts();
        List<String> userList = new ArrayList<>();
        for (User u : users){
            userList.add(u.getUsername());
        }
        return userList;
    }
}
