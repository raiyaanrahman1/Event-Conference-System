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

    private ArrayList<Event> events;

    /**
     * Creates an EventManager and initializes its list of events.
     *
     */
    public EventManager() {
        this.events = new ArrayList<>();
    }

    /**
     * Gets the events that this user is already signed up for, iff user is an Attendee.
     *
     * @return a list of event IDs corresponding to the events this user is signed up for.
     */
    public List<Integer> getEventListByAttendee(String username){

        List<Integer> eventListIDs = new ArrayList<>();
        for(Event e : events){
            if(e.getAttendees().contains(username)) eventListIDs.add(e.getEventID());
        }
        return eventListIDs;
    }

    /**
     * Gets the events that this user is speaking in, iff user is an Speaker.
     *
     * @return a list of event IDs corresponding to the talks user is giving.
     */
    public List<Integer> getTalksBySpeaker(String username){
        List<Integer> eventListIDs = new ArrayList<>();
        for(Event e : events){
            if(e.getSpeaker().equals(username)) eventListIDs.add(e.getEventID());
        }
        return eventListIDs;
    }

    /**
     * Gets the events that this user organised, iff user is an Organizer.
     *
     * @return a list of event IDs corresponding to the events this user organised.
     */
    public List<Integer> getOrganizedEventsByOrganizer(String username){
        List<Integer> eventListIDs = new ArrayList<>();
        for(Event e : events){
            if(e.getOrganizer().equals(username)) eventListIDs.add(e.getEventID());
        }
        return eventListIDs;
    }


    /**
     * Adds a new event to event list iff this user is an Organiser.
     * @return true iff an event was added.
     */
    public boolean addEvent(String eventName, String room, String speaker, String organizer, int roomCap, LocalDateTime dateTime){

        for(Event e : events){
            if(e.getDateTime().equals(dateTime) &&
                    (e.getRoom().equals(room) || e.getSpeaker().equals(speaker))) return false;

        }
        Event event = new Event(eventName, room, speaker, organizer, roomCap, dateTime);
        events.add(event);
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
        return true;

    }

    /**
     * Gets the allowed events that this user may sign up for.
     * @return a list of event IDs corresponding to the allowed events that this user may sign up for.
     */
    public List<Integer> getAllowedEvents(String username){
        List<Integer> allowedEvents = new ArrayList<>();
        for(Event e : events){
            if(!e.getAttendees().contains(username)){
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



    private boolean conflictingTime(Event event, List<Integer> eventIDs) {
        List<Event> eventList = new ArrayList<>();
        for(int i : eventIDs){
            eventList.add(this.getEventByID(i));
        }

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
    public boolean signUpForEvent(int eventID, String username){
        Event event = this.getEventByID(eventID);
        // check if Attendee is attending a talk scheduled at the same time but in a different room
        if (!conflictingTime(event, this.getEventListByAttendee(username)) &&
                event.getAttendees().size() + 1 <= event.getRoomCap() &&
                this.getAllowedEvents(username).contains(eventID)){

            event.addAttendee(username);
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
    public boolean cancelSpot(int eventID, String username){
        Event event = this.getEventByID(eventID);
         if (this.getEventListByAttendee(username).contains(eventID)) {
            event.removeAttendee(username);
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

}
