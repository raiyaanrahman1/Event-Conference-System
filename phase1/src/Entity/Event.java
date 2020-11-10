package Entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private int roomCap;
    private int eventID;
    private static int idCounter = 0;
    private String name;

    /**
     * Creates a new event object.
     *
     * @param name  the Event's name
     * @param room  the Event's room
     * @param speaker  the Event's Speaker's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param dateTime the date and time of the event
     */
    public Event(String name, String room, String speaker, int roomCap, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
        this.room = room;
        this.speaker = speaker;
        this.attendees = new ArrayList<>();
        this.roomCap = roomCap;
        eventID = idCounter;
        idCounter ++;

    }

    /**
     * Returns the toString of the Event
     *
     * Ex: Tech Conference Opening at 10:00 2020-11-02 in room BA100. Speaker: Bill Gates
     */
    @Override
    public String toString() {
        return  name + " at " + getFormattedDateTime() + " " + " in room " + room + ". Speaker: " + speaker;
    }

    /**
     * Returns the Event's list of attendees
     */
    public List<String> getAttendees() {
        return attendees;
    }

    /**
     * Adds an attendee to the Event's list of attendees
     *
     * @param attendee  new attendee of the Event
     */
    public void addAttendee(Attendee attendee){
        this.attendees.add(attendee.getUsername());
    }

    /**
     * Removes an attendee from this Event's list of attendees
     *
     * @param attendee  attendee to be removed
     */
    public void removeAttendee(Attendee attendee){
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
     * dd/MM/yyyy HH:MM:ss.
     *
     * @return  this message's formatted time
     */
    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.
                ofPattern("yyyy-MM-dd HH:mm:ss");
        return getDateTime().format(formatter);
    }
}
