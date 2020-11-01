package Entity;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private List<Attendee> attendees;
    private String time; // 24 hour clock time between 09:00 and 17:00
    private String date; // mm/dd/yy
    private String room;
    private Speaker speaker; //perhaps more than one speaker in phase 2


    public Event(String time, String date, String room, Speaker speaker) {
        this.time = time;
        this.date = date;
        this.room = room;
        this.speaker = speaker;
        this.attendees = new ArrayList<>();
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

}
