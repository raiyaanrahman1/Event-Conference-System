package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

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

    @Override
    public String toString() {
        return  name + " at " + time + " PM " + date + " in room " + room + ". Speaker: " + speaker;
    }

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
    //getter for attendees list
    public List<Attendee> getAttendees() {
        return attendees;
    }
    //appending an attendee to attendees
    public void addAttendee(Attendee attendee){
        this.attendees.add(attendee);
    }

    //getter for time
    public String getTime() {
        return time;
    }
    //setter for time
    public void setTime(String time) {
        this.time = time;
    }

    //getter for date
    public String getDate() {
        return date;
    }
    //setter for date
    public void setDate(String date) {
        this.date = date;
    }

    //getter for room
    public String getRoom() {
        return room;
    }
    //setter for room
    public void setRoom(String room) {
        this.room = room;
    }

    //getter for speaker
    public Speaker getSpeaker() {
        return speaker;
    }
    //setter for speaker
    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public int getEventID() {
        return eventID;
    }

    public int getRoomCap() {
        return roomCap;
    }
}
