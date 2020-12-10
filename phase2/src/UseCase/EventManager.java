package UseCase;
import Entity.*;
import Exceptions.NoSuchEventException;
import Exceptions.NoSuchFilterException;
import Gateway.IGateway2;
import UseCase.Filter.EventFilter;
import UseCase.Filter.EventFilterFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Part of the Scheduling System and is responsible for the scheduling of events.
 * Operates by requests through UserManager.
 */
public class EventManager {

    private ArrayList<Event> events;
    public EventGetter eventGetter;

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

            String eventType = tokens[0];
            String eventID = tokens[1];
            String name = tokens[2];
            String room = tokens[3];
            String speaker = tokens[4];
            String organizer = tokens[5];
            String roomCap = tokens[6];
            String startTime = tokens[7];
            String endTime = tokens[8];

            Event event;
            if(eventType.equals("V")) {
                event = new VIPEvent(eventID, name, room, speaker, organizer, roomCap, startTime, endTime);
            }
            else{
                event = new Event(eventID, name, room, speaker, organizer, roomCap, startTime, endTime);
            }

            if (tokens.length > 9) {
                String attendees = tokens[9];
                event.setAttendees(attendees);
                String speakers = tokens[4];
                event.setSpeakers(speakers);
            }

            this.events.add(event);
            this.eventGetter = new EventGetter(this.events);
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

    private boolean hasCommonSpeaker(List<String> speakers1, List<String> speakers2){
        for(String s : speakers2){
            if(speakers1.contains(s)) return true;
        }
        return false;
    }

    /**
     * add a new regular event to the system
     * @param eventName the name of the event
     * @param room the room of the event
     * @param speakers the speaker(s) of the event (can be empty for no speakers)
     * @param organizer the organizer of the event
     * @param roomCap the room cap of the event
     * @param startTime the start time of the event
     * @param endTime the end time of the event
     * @return true iff the event was added
     */
    public boolean addEvent(String eventName, String room, List<String> speakers, String organizer, int roomCap,
                            LocalDateTime startTime, LocalDateTime endTime){


        for(Event e : events){
            if(eventGetter.eventsOverlap(e.getStartTime(), e.getEndTime(), startTime, endTime) &&
                    (e.getRoom().equals(room) || hasCommonSpeaker(e.getSpeaker(), speakers))) {
                return false;}

        }
        Event event = new Event(eventName, room, organizer, roomCap, startTime, endTime);
        for(String s : speakers){
            event.addSpeaker(s);
        }
        event.setEventID(events.size()+1);
        events.add(event);
        return true;
    }

    /**
     *
     * @param eventName the name of the event
     * @param room the room of the event
     * @param speakers the speaker(s) of the event (can be empty for no speakers)
     * @param organizer the organizer of the event
     * @param roomCap the room cap of the event
     * @param startTime the start time of the event
     * @param endTime the end time of the event
     * @return true iff the event was added
     */

    public boolean addVIPEvent(String eventName, String room, List<String> speakers, String organizer, int roomCap,
                               LocalDateTime startTime, LocalDateTime endTime){
        for(Event e : events){
            if(eventGetter.eventsOverlap(e.getStartTime(), e.getEndTime(), startTime, endTime) &&
                    (e.getRoom().equals(room) || hasCommonSpeaker(e.getSpeaker(), speakers))) {
                return false;}

        }
        VIPEvent event = new VIPEvent(eventName, room, organizer, roomCap, startTime, endTime);
        for(String s : speakers){
            event.addSpeaker(s);
        }
        event.setEventID(events.size()+1);
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
     *
     * @param eventID the id of this event
     * @param newRoomCapacity the new room capacity
     * @return true iff the room capacity was changed
     */

    public boolean changeRoomCapacity(int eventID, int newRoomCapacity){
        Event e = this.getEventByID(eventID);
        if(e != null && e.getAttendees().size() <= newRoomCapacity){
            e.setRoomCap(newRoomCapacity);
            return true;
        }
        return false;
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
        return null;
    }

    /**
     * Signs up attendee for event.
     * @param eventID The event this Attendee wants to sign up for
     * @return true iff attendee was signed up for event.
     */
    public boolean signUpForEvent(int eventID, String username, String userType){
        Event event = this.getEventByID(eventID);
        if (eventGetter.getAllowedEvents(username, userType).contains(eventID) &&
                !Objects.requireNonNull(event).getAttendees().contains(username)){
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
         if (eventGetter.getEventListByAttendee(username).contains(eventID)) {
             assert event != null;
             event.removeAttendee(username);
            return true;
        } else {
            return false;
        }
    }


}
