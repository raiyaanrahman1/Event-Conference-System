package UseCase;

import Entity.Event;
import Exceptions.NoSuchEventException;
import Exceptions.NoSuchFilterException;
import UseCase.Filter.EventFilter;
import UseCase.Filter.EventFilterFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventGetter {
    private final EventFilterFactory factory = new EventFilterFactory();
    protected List<Event> events;

    /**
     * Creates an EventManager and initializes its list of events.
     *
     */
    public EventGetter(List<Event> events) {
        this.events = events;
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

    /**
     * Gets the allowed events that this user may sign up for.
     * @param username the username of the attendee
     * @param type the type of the attendee
     * @return a list of event IDs corresponding to the allowed events that this user may sign up for.
     */
    public List<Integer> getAllowedEvents(String username, String type){
        if(type.equals("A")) return this.getAllowedEventsForAttendee(username);
        else return this.getAllowedEventsForVIP(username);
    }

    private boolean validEventTimeForUser(List<List<LocalDateTime>> userEventTimes,
                                          LocalDateTime start, LocalDateTime end){
        for(List<LocalDateTime> l : userEventTimes){
            if(eventsOverlap(l.get(0), l.get(1), start, end)) return false;
        }

        return true;
    }

    protected boolean eventsOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2){
        return  start1.compareTo(start2) < 0 && end1.compareTo(start2) > 0 ||
                start1.compareTo(start2) > 0 && start1.compareTo(end2) < 0 ||
                start1.equals(start2);
    }

    private List<Integer> getAllowedEventsForAttendee(String username){
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

            if (e.getSaveableInfo().charAt(0) == 'E' && !e.getAttendees().contains(username) &&
                    validEventTimeForUser(userEventTimes, e.getStartTime(), e.getEndTime()) &&
                    e.getAttendees().size() < e.getRoomCap() && e.getStartTime().isAfter(LocalDateTime.now())) {
                allowedEvents.add(e.getEventID());
            }


        }
        return allowedEvents;
    }

    private List<Integer> getAllowedEventsForVIP(String username){
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
     * Get a list of all Event IDs
     * @return list of all Event IDs
     */
    public List<Integer> getAllEventIDs(){
        ArrayList<Integer> eventIDs = new ArrayList<>();
        for(Event e: events){
            eventIDs.add(e.getEventID());
        }
        return eventIDs;
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
        return filterHelper(username, "speaker");
    }

    /**
     * Returns a list with the string representations of the events
     * organized by the given organizer.
     *
     * @param username  the username of the organizer
     * @return  the list of event strings organized by that organizer
     */
    public List<String> filterEventsByOrganizer(String username) {
        return filterHelper(username, "organizer");
    }

    /**
     * Returns a list with the string representations of the events
     * at which the given attendee participates in.
     *
     * @param username  the username of the attendee
     * @return  the list of event strings with that attendee
     */
    public List<String> filterEventsByAttendee(String username) {
        return filterHelper(username, "attendee");
    }

    private List<String> filterHelper(String username, String filterType){
        List<String> events = new ArrayList<>();
        EventFilter filter;

        try {
            filter = factory.getEventFilter(filterType);
        } catch (NoSuchFilterException ex) {
            return events;
        }

        for (Event event: filter.filter(this.events, username)) {
            events.add(event.toString());
        }

        return events;
    }

    /**
     * Gets the speaker of a specific event
     * @param eventId the id of the event
     * @
     */
    public String getSpeaker(int eventId) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) throw new NoSuchEventException();
        return String.valueOf(e.getSpeaker());
    }

    public String getName(int eventId) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) throw new NoSuchEventException();
        return e.getName();
    }

    public int getRoomCap(int eventId) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) throw new NoSuchEventException();
        return e.getRoomCap();
    }

    public String getOrganizer(int eventId) throws NoSuchEventException {
        Event e = getEventByID(eventId);
        if (e == null) throw new NoSuchEventException();
        return e.getOrganizer();
    }

}
