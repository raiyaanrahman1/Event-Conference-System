package UseCase;
import Entity.Event;
import Entity.Attendee;
import java.util.List;


import java.util.ArrayList;

public class EventScheduler {
    private Attendee user;
    private ArrayList<Event> events;

    public EventScheduler(Attendee user) {
        this.user = user;
        this.events = new ArrayList<>();
    }

    public void logInUser(Attendee user){
        this.user = user;
    }

    public boolean addEvent(Event event){
        if(!user.hasEventCreatingRights()) return false;

        for(Event e : events){
            if(event.getDate().equals(e.getDate()) && event.getTime().equals(e.getTime()) &&
                    (event.getRoom().equals(e.getRoom()) || event.getSpeaker().equals(e.getSpeaker()))) return false;

        }
        events.add(event);
        return true;
    }

    public boolean removeEvent(Event event){
        if(user.hasEventCreatingRights()){
            events.remove(event);
            return true;
        }
        return false;
    }

    public ArrayList<Event> getAllowedEvents(){
        ArrayList<Event> allowedEvents = new ArrayList<>();
        for(Event e : events){
            if(!e.getAttendees().contains(user)){
                allowedEvents.add(e);
            }
        }
        return allowedEvents;
    }

    public List<Event> getUserEvents(){
        return user.getEventList();
    }




}
