package UseCase;
import Entity.*;

import java.util.List;

public class UserManager{
    private User user;
    private List<User> userList;
    private AttendeeSignUp attendeeSignUp;
    private EventScheduler eventScheduler;
    private MessageManager messageManager;

    public UserManager(User user){
        this.user = user;
    }


    // AttendeeSignUp Functionality
    public boolean signUpForEvent(int eventID){
        return attendeeSignUp.signUpForEvent(eventScheduler.getEventByID(eventID));
    }

    public boolean cancelSpot(int eventID){
        return attendeeSignUp.signUpForEvent(eventScheduler.getEventByID(eventID));
    }

    public Speaker createSpeakerAccount(String uname,
                                        String pword){
        return attendeeSignUp.createSpeakerAccount(uname, pword);
    }

    // EventScheduler Functionality
    public boolean addEvent(int eventID){
        return eventScheduler.addEvent(eventScheduler.getEventByID(eventID));
    }

    public boolean removeEvent(int eventID){
        return eventScheduler.removeEvent(eventScheduler.getEventByID(eventID));
    }

    public List<Integer> getAllowedEvents(){
        return eventScheduler.getAllowedEvents();
    }

    public List<Integer> getUserEvents(){
        return eventScheduler.getUserEvents();
    }

    public User getUserByUsername(String user){
        for (User u : userList){
            if (u.getUsername().equals(user)){
                return u;
            }
        }
        return null;
    }

    // MessageManager Functionality
    public boolean broadcast(String user, int eventID, String message, String date, String time){
        return messageManager.broadcast(getUserByUsername(user),
                eventScheduler.getEventByID(eventID), message, date, time);
    }

    public List<String> getMessages(String sender, String receiver) {
        return messageManager.getMessages(getUserByUsername(sender),
                getUserByUsername(receiver));
    }
}

