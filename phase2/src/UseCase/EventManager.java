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

    private final EventFilterFactory factory = new EventFilterFactory();
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
            if(e.getSpeaker().contains(username)) eventListIDs.add(e.getEventID());
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

    private boolean eventsOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2){
        return  start1.compareTo(start2) < 0 && end1.compareTo(start2) > 0 ||
                start1.compareTo(start2) > 0 && start1.compareTo(end2) < 0 ||
                start1.equals(start2);
    }

    private boolean hasCommonSpeaker(List<String> speakers1, List<String> speakers2){
        for(String s : speakers2){
            if(speakers1.contains(s)) return true;
        }
        return false;
    }


    /**
     * Adds a new event to event list if the event is valid.
     * @return true iff an event was added.
     */
    public boolean addEvent(String eventName, String room, List<String> speakers, String organizer, int roomCap,
                            LocalDateTime startTime, LocalDateTime endTime){


        for(Event e : events){
            if(eventsOverlap(e.getStartTime(), e.getEndTime(), startTime, endTime) &&
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
     * Remove an event to event list iff this user is an Organiser.
     * @param eventID the event that will be removed
     * @return true iff the event was removed
     */
    public boolean removeEvent(int eventID){
        Event event = this.getEventByID(eventID);

        events.remove(event);
        return true;

    }

    private boolean validEventTimeForUser(List<List<LocalDateTime>> userEventTimes,
                                          LocalDateTime start, LocalDateTime end){
        for(List<LocalDateTime> l : userEventTimes){
            if(eventsOverlap(l.get(0), l.get(1), start, end)) return false;
        }

        return true;
    }

    /**
     * Gets the allowed events that this user may sign up for.
     * @return a list of event IDs corresponding to the allowed events that this user may sign up for.
     */
    public List<Integer> getAllowedEvents(String username){
        List<Integer> allowedEvents = new ArrayList<>();
        List<List<LocalDateTime>> userEventTimes = new ArrayList<>();

        for (Event e : events){
            if (e.getAttendees().contains(username)){
                ArrayList<LocalDateTime> times = new ArrayList<>();
                times.add(e.getStartTime());
                times.add(e.getEndTime());
                userEventTimes.add(times);
            }
        }

        for(Event e : events){

            if (!e.getAttendees().contains(username) &&
                    validEventTimeForUser(userEventTimes, e.getStartTime(), e.getEndTime()) &&
                    e.getAttendees().size() < e.getRoomCap() && e.getStartTime().isAfter(LocalDateTime.now())) {
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
        return null;
    }

    /**
     * Signs up attendee for event.
     * @param eventID The event this Attendee wants to sign up for
     * @return true iff attendee was signed up for event.
     */
    public boolean signUpForEvent(int eventID, String username){
        Event event = this.getEventByID(eventID);
        if (this.getAllowedEvents(username).contains(eventID) &&
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
         if (this.getEventListByAttendee(username).contains(eventID)) {
             assert event != null;
             event.removeAttendee(username);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets all the attendees of an event.
     * @param eventID the ID of the event we want to get the attendees of
     * @return an ArrayList of all the attendees attending this event
     */
    public List<String> getAttendeesInEvent(int eventID) {
        Event event = this.getEventByID(eventID);
        assert event != null;
        return event.getAttendees();
    }

    /**
     * Gets the dateTime of an event by its eventID.
     * @param eventID the ID of the event we want to get the datetime of
     * @return a string representing the formatted dateTime of this event
     */
    public String getStartTimeByEventID(int eventID) {
        return Objects.requireNonNull(this.getEventByID(eventID)).getStartTime().toString();
    }

    /**
     * Gets the dateTime of an event by its eventID.
     * @param eventID the ID of the event we want to get the datetime of
     * @return a string representing the formatted dateTime of this event
     */
    public String getEndTimeByEventID(int eventID) {
        return Objects.requireNonNull(this.getEventByID(eventID)).getEndTime().toString();
    }

    /**
     * Gets the string format of this event.
     * @param eventID the ID of the event we want to get the string format of
     * @return a formatted string representing this event
     */
    public String getEventString(int eventID) {
        return Objects.requireNonNull(getEventByID(eventID)).toString();
    }

    // Phase 2 Functionality
    /**
     * Gets the room of an event by its eventID.
     * @param eventID the ID of the event we want to get the room of
     * @return a string representing the room of this event
     */
    public String getRoomByEventID(int eventID) {
        return Objects.requireNonNull(this.getEventByID(eventID)).getRoom();
    }

    /**
     * Returns the string representations of the event at the same date
     * as the given date.
     *
     * @param date  the date
     * @return  the list of event strings that occur at the date
     */
    public List<String> filterEventsByDate(LocalDate date) {
        List<String> events = new ArrayList<>();
        EventFilter filter;

        try {
            filter = factory.getEventFilter("date");
        } catch (NoSuchFilterException ex) {
            return events;
        }

        for (Event event: filter.filter(this.events, date.format(DateTimeFormatter.ISO_LOCAL_DATE))) {
            events.add(event.toString());
        }

        return events;
    }

    /**
     * Returns a list with the string representations of the events
     * at which the given speaker participates in.
     *
     * @param username  the username of the speaker
     * @return  the list of event strings with that speaker
     */
    public List<String> filterEventsBySpeaker(String username) {
        List<String> events = new ArrayList<>();
        EventFilter filter;

        try {
            filter = factory.getEventFilter("speaker");
        } catch (NoSuchFilterException ex) {
            return events;
        }

        for (Event event: filter.filter(this.events, username)) {
            events.add(event.toString());
        }

        return events;
    }

    /**
     * Returns a list with the string representations of the events
     * organized by the given speaker.
     *
     * @param username  the username of the organizer
     * @return  the list of event strings organized by that speaker
     */
    public List<String> filterEventsByOrganizer(String username) {
        List<String> events = new ArrayList<>();
        EventFilter filter;

        try {
            filter = factory.getEventFilter("organizer");
        } catch (NoSuchFilterException ex) {
            return events;
        }

        for (Event event: filter.filter(this.events, username)) {
            events.add(event.toString());
        }

        return events;
    }

    /**
     * Returns a list with the string representations of the events
     * at which the given attendee participates in.
     *
     * @param username  the username of the attendee
     * @return  the list of event strings with that attendee
     */
    public List<String> filterEventsByDate(String username) {
        List<String> events = new ArrayList<>();
        EventFilter filter;

        try {
            filter = factory.getEventFilter("attendee");
        } catch (NoSuchFilterException ex) {
            return events;
        }

        for (Event event: filter.filter(this.events, username)) {
            events.add(event.toString());
        }

        return events;
    }

    public void setRoom(int eventId, String roomName) throws AssertionError {
        Event e = getEventByID(eventId);
        if (e == null) throw new AssertionError();
        e.setRoom(roomName);
    }

    public String getSpeaker(int eventId) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) throw new NoSuchEventException();
        return String.valueOf(e.getSpeaker());
    }

    public void setSpeakers(int eventId, List<String> speakerList) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) {
            throw new NoSuchEventException();
        }
        List<String> speakers = e.getSpeaker();
        for (String s: speakers){ //remove all the speakers
            e.removeSpeaker(s);
        }
        for (String speaker: speakerList){
            e.addSpeaker(speaker);
        }
    }

    public String getName(int eventId) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) throw new NoSuchEventException();
        return e.getName();
    }

    public void setName(int eventId, String name) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) {
            throw new NoSuchEventException();
        }
        e.setName(name);
    }

    public int getRoomCap(int eventId) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) throw new NoSuchEventException();
        return e.getRoomCap();
    }

    public void setRoomCap(int eventId, int cap) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) {
            throw new NoSuchEventException();
        }
        e.setRoomCap(cap);
    }

    public String getOrganizer(int eventId) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) throw new NoSuchEventException();
        return e.getOrganizer();
    }

    public void setOrganizer(int eventId, String org) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) {
            throw new NoSuchEventException();
        }
        e.setOrganizer(org);
    }

    public void setStartTime(int eventId, LocalDateTime startTime) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) {
            throw new NoSuchEventException();
        }
        e.setStartTime(startTime);
    }

    public void setEndTime(int eventId, LocalDateTime endTime) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) {
            throw new NoSuchEventException();
        }
        e.setEndTime(endTime);
    }
}
