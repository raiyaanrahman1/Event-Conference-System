package UseCase;
import Entity.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Part of the Scheduling System and is responsible for the scheduling of events.
 * Operates by requests through UserManager.
 */
public class EventManager {
    private User user;
    private ArrayList<Event> events;

    /**
     * Creates an EventScheduler instance with user 'logged-in'.
     * @param user The current Attendee that is logged-in.
     */
    public EventManager(Attendee user) {
        this.user = user;
        this.events = new ArrayList<>();
    }

    public void logInUser(User user){
        this.user = user;
    }

    /**
     * Adds a new event to event list iff this user is an Organiser.
     * @param event the event that will be added
     * @return true iff an event was added.
     */
    public boolean addEvent(Event event){
        if(!user.hasEventCreatingRights()) return false;

        for(Event e : events){
            if(event.getDate().equals(e.getDate()) && event.getTime().equals(e.getTime()) &&
                    (event.getRoom().equals(e.getRoom()) || event.getSpeaker().equals(e.getSpeaker()))) return false;

        }
        events.add(event);
        ((Organizer) user).addOrganizedEvent(event);
        return true;
    }

    /**
     * Remove an event to event list iff this user is an Organiser.
     * @param event the event that will be removed
     * @return true iff the event was removed
     */
    public boolean removeEvent(Event event){
        if(user.hasEventCreatingRights()){
            events.remove(event);
            ((Organizer) user).removeOrganizedEvent(event);
            return true;
        }
        return false;
    }

    /**
     * Gets the allowed events that this user may sign up for.
     * @return a list of event IDs corresponding to the allowed events that this user may sign up for.
     */
    public List<Integer> getAllowedEvents(){
        List<Integer> allowedEvents = new ArrayList<>();
        for(Event e : events){
            if(!e.getAttendees().contains(user)){
                allowedEvents.add(e.getEventID());
            }
        }
        return allowedEvents;
    }

    /**
     * Gets the events that this user is already signed up for.
     * @return a list of event IDs corresponding to the events this user is signed up for.
     */
    public List<Integer> getUserEvents(){
        List<Event> eventList = user.getEventList();
        List<Integer> eventIDList = new ArrayList<>();
        for (Event e: eventList){
            eventIDList.add(e.getEventID());
        }
        return eventIDList;
    }

    /**
     * Get the event object by its ID.
     * @param ID the ID of the event we want to get
     * @return the Event object corresponding to ID iff the ID corresponds to an existing Event.
     */
    public Event getEventByID(int ID){
        for (Event e: events){
            if (e.getEventID() == ID){
                return e;
            }
        }
        // throws an exception that we haven't coded yet
        return null;
    }

    /**
     *
     * Precondition: the given user is a speaker.
     * @param user
     * @return
     */
    public List<Event> getEventsBySpeaker(User user) {
        List<Event> eventsWithSpeaker = new ArrayList<>();
        for(Event event: events) {
            if (event.getSpeaker().getUsername().equals(user.getUsername())) {
                eventsWithSpeaker.add(event);
            }
        }
        return eventsWithSpeaker;
    }

    public List<Event> getAllEvents() {
        return events;
    }


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
                event.getAttendees().size() + 1 <= event.getRoomCap() &&
                !attendee.getEventList().contains(event)){
            attendee.addEvent(event);
            event.addAttendee(attendee);
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
            event.removeAttendee(attendee);
            return true;
        } else {
            return false;
        }
    }


    public Attendee getCurrentUser() {
        return attendee;
    }

}
