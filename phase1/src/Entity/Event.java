package Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The Event class represents an event in the conference.
 */
public class Event {
    private List<Attendee> attendees;
    private String time; // 24 hour clock time between 09:00 and 17:00
    private String date; // mm/dd/yy
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
     * @param time  the Event's time
     * @param date  the Event's date
     * @param room  the Event's room
     * @param speaker  the Event's Speaker
     * @param roomCap the Event's maximum capacity of attendees
     */
    public Event(String name, String time, String date, String room, Speaker speaker, int roomCap) {
        this.name = name;
        this.time = time;
        this.date = date;
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
     * Ex: Tech Conference Opening at 10:00 11.02.2020 in room BA100. Speaker: Bill Gates
     */
    @Override
    public String toString() {
        return  name + " at " + time + " " + date + " in room " + room + ". Speaker: " + speaker;
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
     * Returns the Event's time
     */
    public String getTime() {
        return time;
    }

    /**
     * Changes the Event's time
     *
     * @param time  new time of the Event
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Returns the Event's date
     */
    public String getDate() {
        return date;
    }

    /**
     * Changes the Event's date
     *
     * @param date new date of the Event
     */
    public void setDate(String date) {
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
