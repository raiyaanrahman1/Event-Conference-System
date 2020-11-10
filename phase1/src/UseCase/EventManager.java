package UseCase;
import Entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Part of the Scheduling System and is responsible for the scheduling of events.
 * Operates by requests through UserManager.
 */
public class EventManager {
    private Attendee user;
    private ArrayList<Event> events;

    /**
     * Creates an EventScheduler instance with user 'logged-in'.
     * @param user The current Attendee that is logged-in.
     */
    public EventManager(Attendee user) {
        this.user = user;
        this.events = new ArrayList<>();
    }

    /**
     * Adds a new event to event list iff this user is an Organiser.
     * @return true iff an event was added.
     */
    public boolean addEvent(String name, String room, String speaker, int roomCap, LocalDateTime dateTime){

        for(Event e : events){
            if(e.getDateTime().equals(dateTime) &&
                    (e.getRoom().equals(room) || e.getSpeaker().equals(speaker))) return false;

        }
        Event event = new Event(name, room, speaker, roomCap, dateTime);
        events.add(event);
        ((Organizer) user).addOrganizedEvent(event);
        return true;
    }

    /**
     * Remove an event to event list iff this user is an Organiser.
     * @param eventID the event that will be removed
     * @return true iff the event was removed
     */
    public boolean removeEvent(int eventID){
        Event event = this.getEventByID(eventID);

        events.remove(event);
        ((Organizer) user).removeOrganizedEvent(event);
        return true;

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
     * Get the event object by its ID.
     * @param ID the ID of the event we want to get
     * @return the Event object corresponding to ID iff the ID corresponds to an existing Event.
     */
    private Event getEventByID(int ID){
        for (Event e: events){
            if (e.getEventID() == ID){
                return e;
            }
        }
        // throws an exception that we haven't coded yet
        return null;
    }


    public List<Event> getAllEvents() {
        return events;
    }


    private boolean conflictingTime(Event event, List<Event> eventList) {
        for (Event e : eventList){
            if (e.getDateTime().equals(event.getDateTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Signs up attendee for event.
     * @param eventID The event this Attendee wants to sign up for
     * @return true iff attendee was signed up for event.
     */
    public boolean signUpForEvent(int eventID){
        Event event = this.getEventByID(eventID);
        // check if Attendee is attending a talk scheduled at the same time but in a different room
        if (!conflictingTime(event, user.getEventList()) &&
                event.getAttendees().size() + 1 <= event.getRoomCap() &&
                !user.getEventList().contains(event)){
            user.addEvent(event);
            event.addAttendee(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cancels attendee's spot in event.
     * @param eventID The event this Attendee wants to cancel
     * @return true iff this attendee's spot in event was cancelled.
     */
    public boolean cancelSpot(int eventID){
        Event event = this.getEventByID(eventID);
         if (user.getEventList().contains(event)) {
            user.cancelEvent(event);
            event.removeAttendee(user);
            return true;
        } else {
            return false;
        }
    }

    public List<String> getUsersInEvent(int eventID) {
        Event event = this.getEventByID(eventID);

        List<String> usernames = new ArrayList<>();
        for (String user: event.getAttendees()) {
            usernames.add(user);
        }

        return usernames;
    }

    public List<Integer> getEventsBySpeaker(String username) {
        List<Integer> eventIDs = new ArrayList<>();
        for(Event e : this.events){
            if(e.getSpeaker().equals(username)) {
                eventIDs.add(e.getEventID());
            }
        }
        return eventIDs;
    }

    public String getDateTimeByEventID(int eventID) {
        return this.getEventByID(eventID).getDateTime().toString();
    }


    public String getRoomByEventID(int eventID) {
        return this.getEventByID(eventID).getRoom();
    }


    public User getCurrentUser() {
        return user;
    }

}
