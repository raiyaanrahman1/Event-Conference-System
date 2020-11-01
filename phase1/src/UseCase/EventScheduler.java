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

    public List<Integer> getAllowedEvents(){
        List<Integer> allowedEvents = new ArrayList<>();
        for(Event e : events){
            if(!e.getAttendees().contains(user)){
                allowedEvents.add(e.getEventID());
            }
        }
        return allowedEvents;
    }

    public List<Integer> getUserEvents(){
        List<Event> eventList = user.getEventList();
        List<Integer> eventIDList = new ArrayList<>();
        for (Event e: eventList){
            eventIDList.add(e.getEventID());
        }
        return eventIDList;
    }

    public Event getEventByID(int ID){
        for (Event e: events){
            if (e.getEventID() == ID){
                return e;
            }
        }
        // throws an exception that we haven't coded yet
        return null;
    }



}
