package Entity;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;

/**
 * The Event class represents an event in the conference.
 */
public class Event {
    private List<Attendee> attendees;
    private LocalTime time; // 24 hour clock time between 09:00 and 17:00

    private LocalDate date; // mm/dd/yy
    private String room;
    private Speaker speaker; //perhaps more than one speaker in phase 2
    private int roomCap;
    private int eventID;
    private static int idCounter = 0;
    private String name;

    /**
     * Creates a new event object.
     *
     * @param name  the Event's name
     * @param room  the Event's room
     * @param speaker  the Event's Speaker
     * @param roomCap the Event's maximum capacity of attendees
     */
    public Event(String name, String room, Speaker speaker, int roomCap) {
        this.name = name;
        this.time = LocalTime.now();
        this.date = LocalDate.now();
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
        return  name + " at " + time.toString() + " " + date.toString() + " in room " + room + ". Speaker: " + speaker;
    }

    /**
     * Returns the Event's list of attendees
     */
    public List<Attendee> getAttendees() {
        return attendees;
    }

    /**
     * Adds an attendee to the Event's list of attendees
     *
     * @param attendee  new attendee of the Event
     */
    public void addAttendee(Attendee attendee){
        this.attendees.add(attendee);
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
     * Gets the time of this event
     * @return the local time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the time of this event
     *
     * @param time LocalTime.now() of the event
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Gets the date of this event
     *
     * @return the Local date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the event
     *
     * @param date LocalDate.now() of the event
     */
    public void setDate(LocalDate date) {
        this.date = date;
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
     * Returns the Event's Speaker
     */
    public Speaker getSpeaker() {
        return speaker;
    }

    /**
     * Changes the Speaker of the Event
     */
    public void setSpeaker(Speaker speaker) {
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
}
