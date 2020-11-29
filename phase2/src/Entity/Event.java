package Entity;

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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String room;
    private List<String> speakers; // perhaps more than one speaker in phase 2
    private String organizer;
    private int roomCap;
    private int eventID;
    private String name;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Creates a new event object.
     *
     * @param name  the Event's name
     * @param room  the Event's room
     * @param organizer the Event's Organizer's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param startTime the date and time of the event
     * @param endTime when the event ends
     */
    public Event(String name, String room, String organizer, int roomCap, LocalDateTime startTime,
                 LocalDateTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.speakers = new ArrayList<>();
        this.organizer = organizer;
        this.attendees = new ArrayList<>();
        this.roomCap = roomCap;
    }
    /**
     * Creates a new event object.
     *
     * @param name  the Event's name
     * @param room  the Event's room
     * @param speaker the Event's speaker
     * @param organizer the Event's Organizer's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param startTime the date and time of the event
     * @param endTime when the event ends
     */
    public Event(String name, String room, String speaker, String organizer, int roomCap, LocalDateTime startTime,
                 LocalDateTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.speakers = new ArrayList<>();
        this.speakers.add(speaker);
        this.organizer = organizer;
        this.attendees = new ArrayList<>();
        this.roomCap = roomCap;
    }

    /**
     * Creates a new event object using data from the store event info file.
     * @param eventID the id associated with this event
     * @param name  the Event's name
     * @param room  the Event's room
     * @param speaker  the Event's Speaker's username
     * @param organizer the Event's Organizer's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param startTime the date and time of the event
     * @param endTime when the event ends
     */
    public Event(String eventID, String name, String room, String speaker,
                 String organizer, String roomCap, String startTime, String endTime){
        this.eventID = Integer.parseInt(eventID);
        this.name = name;
        this.startTime = LocalDateTime.parse(startTime, this.formatter);
        this.endTime = LocalDateTime.parse(endTime, this.formatter);
        this.room = room;
        this.speakers = new ArrayList<>();
        this.speakers.add(speaker);
        this.organizer = organizer;
        this.roomCap = Integer.parseInt(roomCap);
        this.attendees = new ArrayList<>();
    }
    /**
     * Creates a new event object using data from the store event info file.
     * @param eventID the id associated with this event
     * @param name  the Event's name
     * @param room  the Event's room
     * @param organizer the Event's Organizer's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param startTime the date and time of the event
     * @param endTime when the event ends
     */
    public Event(String eventID, String name, String room,
                 String organizer, String roomCap, String startTime, String endTime){
        this.eventID = Integer.parseInt(eventID);
        this.name = name;
        this.startTime = LocalDateTime.parse(startTime, this.formatter);
        this.endTime = LocalDateTime.parse(endTime, this.formatter);
        this.room = room;
        this.speakers = new ArrayList<>();
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
        String[] formatted = getFormattedDateTime().split("T");
        return  eventID + ". " + name + " at " + formatted[0] + " " + formatted[1] + " " + "in room " + room +
                ". Speaker/s: " + getSavableSpeakers();
    }

    /**
     * Gets this Event's information in a saveable format (i.e in a String)
     * @return this Event's info as a String
     */
    public String getSaveableInfo(){
        return this.eventID + "|" + this.name + "|" +
                this.room + "|" + getSavableSpeakers() + "|" + this.organizer +
                "|" + this.roomCap + "|" + getFormattedDateTime() +
                "|" + getSaveableAttendees();

    }

    /**
     * Converts this Event's attendee list into a one String in this format:
     *      attendee1,attendee2,attendee3...
     * @return a string representing the attendee list
     */
    private String getSaveableAttendees(){
        StringBuilder attendees = new StringBuilder();
        for (String a: this.attendees){
            attendees.append(a).append(',');
        }
        return attendees.toString();
    }
    private String getSavableSpeakers() {
        if (this.speakers.size() > 0) {
            StringBuilder speakers = new StringBuilder();
            for (String s : this.speakers) {
                speakers.append(s).append(',');
            }
            return speakers.toString();
        }
        return "";
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
     * Returns the Event's Speaker's username
     */
    public List<String> getSpeaker() {
        return speakers;
    }

    /**
     * Adds the Speaker's username of the Event
     */
    public void addSpeaker(String speaker) {
        this.speakers.add(speaker);
    }

    /**
     * Removes the Speaker's username of the Event
     */
    public void removeSpeaker(String speaker){ this.speakers.remove(speaker); }

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
     * Gets the LocalDateTime of the event
     *
     * @return the LocalDateTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    // Phase 2 Functionality

    /**
     * Sets the LocalDateTime of the event
     *
     * @param  startTime the date and time of the event
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    /**
     * Gets the LocalDateTime of the end of the event
     *
     * @return the LocalDateTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    // Phase 2 Functionality
    /**
     * Sets the LocalDateTime of the end of the event
     *
     * @param  endTime the date and time of the end of the event
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the datetime of this message formatted
     * DD-MM-YYYYTHH:MM:SS.
     *
     * @return  this message's formatted time
     */
    public String getFormattedDateTime() {
        return getStartTime().format(formatter) + " to " + getEndTime().format(formatter);
    }

    /**
     * Sets the id of the event according to the number of events already created
     *
     * @param id the id of this event
     */
    public void setEventID(int id) {
        this.eventID = id;
    }
}

