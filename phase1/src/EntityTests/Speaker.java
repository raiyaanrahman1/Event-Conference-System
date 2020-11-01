package EntityTests;

import java.util.ArrayList;
import java.util.List;

public class Speaker extends User {
    private List<Event> talks;


    //Speaker constructor
    public Speaker(String uname, String pword) {
        super(uname, pword);
        this.talks = new ArrayList<>();
    }
    @Override
    public boolean hasBroadcastRights() {
        return true;
    }

    @Override
    public boolean hasEventCreatingRights() {
        return false;
    }

    //getter for talks list
    public List<Event> getTalks() {
        return talks;
    }//appending an event to the talk list
    public void addTalk(Event talk){
        talks.add(talk);
    }

}
