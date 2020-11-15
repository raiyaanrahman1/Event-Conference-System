package Entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

/**
 * The Event class represents an event in the conference.
 */
public class Event {
    private List<String> attendees;
    private LocalDateTime dateTime;
    private String room;
    private String speaker; //perhaps more than one speaker in phase 2
    private String organizer;
    private int roomCap;
    private int eventID;
    private static int idCounter = 0;
    private String name;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Creates a new event object.
     *
     * @param name  the Event's name
     * @param room  the Event's room
     * @param speaker  the Event's Speaker's username
     * @param organizer the Event's Organizer's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param dateTime the date and time of the event
     */
    public Event(String name, String room, String speaker, String organizer, int roomCap, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
        this.room = room;
        this.speaker = speaker;
        this.organizer = organizer;
        this.attendees = new ArrayList<>();
        this.roomCap = roomCap;
        eventID = idCounter;
        idCounter ++;

    }

    /**
     * Creates a new event object using data from the store event info file.
     * @param eventID the id associated with this event
     * @param name  the Event's name
     * @param room  the Event's room
     * @param speaker  the Event's Speaker's username
     * @param organizer the Event's Organizer's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param dateTime the date and time of the event
     */
    public Event(String eventID, String name, String room, String speaker,
                 String organizer, String roomCap, String dateTime){
        this.eventID = Integer.parseInt(eventID);
        this.name = name;
        this.dateTime = LocalDateTime.parse(dateTime, this.formatter);
        this.room = room;
        this.speaker = speaker;
        this.organizer = organizer;
        this.roomCap = Integer.parseInt(roomCap);
        this.attendees = new ArrayList<>();
    }

    /**
     * Returns the toString of the Event
     *
     * Ex: Tech Conference Opening at 10:00 2020-11-02 in room BA100. Speaker: Bill Gates
     */
    @Override
    public String toString() {
        return  "(" + eventID + ")" + name + " at " + getFormattedDateTime() + " " + " in room " + room +
                ". Speaker: " + speaker;
    }

    /**
     * Gets this Event's information in a saveable format (i.e in a String)
     * @return this Event's info as a String
     */
    public String getSaveableInfo(){
        return this.eventID + "|" + this.name + "|" +
                this.room + "|" + this.speaker + "|" + this.organizer +
                "|" + this.roomCap + "|" + getFormattedDateTime() +
                "|" + getSaveableAttendees();

    }

    /**
     * Converts this Event's attendee list into a one String in this format:
     *      attendee1,attendee2,attendee3...
     * @return a string representing the attendee list
     */
    private String getSaveableAttendees(){
        String attendees = "";
        for (String a: this.attendees){
            attendees += a +',';
        }
        return attendees;
    }

    /**
     * Returns the Event's list of attendees
     */
    public List<String> getAttendees() {
        return attendees;
    }

    /**
     * Sets this Event's attendee lit using information saved in the text file
     * @param attendees a string representing the attendee list
     */
    public void setAttendees(String attendees){
        String[] attendeeList = attendees.split(",");
        this.attendees.addAll(Arrays.asList(attendeeList));
    }
    /**
     * Adds an attendee's username to the Event's list of attendees
     *
     * @param attendee  new attendee's usename of the Event
     */
    public void addAttendee(String attendee){
        this.attendees.add(attendee);
    }

    /**
     * Removes an attendee's username from this Event's list of attendees
     *
     * @param attendee  attendee's username to be removed
     */
    public void removeAttendee(String attendee){
        this.attendees.remove(attendee);
    }


    /**
     * Returns the Event's room
     */
    public String getRoom() {
        return room;
    }

    /**
     * Changes the Event's room
     *
     * @param room new room of the Event
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Returns the Event's Speaker's username
     */
    public String getSpeaker() {
        return speaker;
    }

    /**
     * Changes the Speaker's username of the Event
     */
    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    /**
     * Returns the Event's Organizer's username
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * Changes the Organizer's username of the Event
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * Returns the Event's eventID
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * Returns the roomCap of the Event
     */
    public int getRoomCap() {
        return roomCap;
    }

    /**
     * Gets the LocalDateTime of the message
     *
     * @return the LocalDateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the LocalDateTime of the message
     *
     * @param  dateTime the date and time of the event
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns the datetime of this message formatted
     * dd-MM-yyyy HH:MM:ss.
     *
     * @return  this message's formatted time
     */
    public String getFormattedDateTime() {
        return getDateTime().format(formatter);
    }
}
