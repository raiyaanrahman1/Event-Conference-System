package UseCase;
import Entity.*;
import Gateway.IGateway2;

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
    public EventManager(IGateway2 gateway) {
        this.events = new ArrayList<>();
        gateway.openForRead();
        List<String> formattedEvents = getStoredEvents(gateway);
        gateway.closeForRead();

        for (String e: formattedEvents) {
            String[] tokens = e.split("\\|");

            String eventID = tokens[0];
            String name = tokens[1];
            String room = tokens[2];
            String speaker = tokens[3];
            String organizer = tokens[4];
            String roomCap = tokens[5];
            String dateTime = tokens[6];

            Event event = new Event(eventID, name, room, speaker,
                    organizer, roomCap, dateTime);

            if (tokens.length > 7) {
                String attendees = tokens[7];
                event.setAttendees(attendees);
            }

            this.events.add(event);
        }
    }

    /**
     *
     * Stores each event through a gateway.
     *      * Precondition: the gateway must not be open for write/read.
     *      *
     *      * @param gateway  the gateway through which we save our events
     */
    public void storeEvents(IGateway2 gateway) {
        if (gateway.openForWrite()) {
            for (Event e: this.events){
                gateway.write(e.getSaveableInfo());
            }
            gateway.closeForWrite();
        }
    }

    /**
     *
     * Gets the strings that represent the information needed to instantiate an existing
     * event. Each string should be formatted in the manner:
     *      (eventID)|(name)|(room)|(speaker)|(organizer)|(roomCap)|(datetime)|(attendees)
     *
     * Each variable will be parsed back to its original form in Event (if needed).
     * @param gateway2 the interface needed to read the data
     * @return a list of strings represented each stored event in the text file
     */
    private List<String> getStoredEvents(IGateway2 gateway2) {
        List<String> formattedEvents = new ArrayList<>();
        while (gateway2.hasNext()) {
            formattedEvents.add(gateway2.next());
        }
        return formattedEvents;
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
     * Gets the toString of events that this user organised, iff user is an Organizer.
     *
     * @return a list of event IDs corresponding to the events this user organised.
     */
    public List<String> getOrganizedEventsString(String username){
        List<String> eventsString = new ArrayList<>();
        for(Event e : events){
            if(e.getOrganizer().equals(username)) eventsString.add(e.toString());

        }
        return eventsString;
    }

    /**
     * Adds a new event to event list iff this user is an Organiser.
     * @return true iff an event was added.
     */
    public boolean addEvent(String eventName, String room, String speaker, String organizer, int roomCap, LocalDateTime dateTime){

        for(Event e : events){
            if(e.getDateTime().equals(dateTime) &&
                    (e.getRoom().equals(room) || e.getSpeaker().equals(speaker))) {
                return false;}

        }
        Event event = new Event(eventName, room, speaker, organizer, roomCap, dateTime);
        event.setEventID(events.size());
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
        List<LocalDateTime> userEventTimes = new ArrayList<>();


        for(Event e : events){
            if (!e.getAttendees().contains(username) && !userEventTimes.contains(e.getDateTime()) &&
                    e.getAttendees().size() < e.getRoomCap()) {
                allowedEvents.add(e.getEventID());
            }

            if (e.getAttendees().contains(username)) userEventTimes.add(e.getDateTime());
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

    /**
     * Signs up attendee for event.
     * @param eventID The event this Attendee wants to sign up for
     * @return true iff attendee was signed up for event.
     */
    public boolean signUpForEvent(int eventID, String username){
        Event event = this.getEventByID(eventID);
        // check if Attendee is attending a talk scheduled at the same time but in a different room
        if (this.getAllowedEvents(username).contains(eventID) && !event.getAttendees().contains(username)){
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


    public String getDateTimeByEventID(int eventID) {
        return this.getEventByID(eventID).getDateTime().toString();
    }


    public String getRoomByEventID(int eventID) {
        return this.getEventByID(eventID).getRoom();
    }

    public String getEventString(int eventId) {
        return getEventByID(eventId).toString();
    }

}
