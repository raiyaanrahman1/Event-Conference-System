package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class Event {
    private List<Attendee> attendees;
    private String time;
    private String date;
    private String room;
    private Speaker speaker; //perhaps more than one speaker in phase 2

    public Event(String time, String date, String room, Speaker speaker) {
        this.time = time;
        this.date = date;
        this.room = room;
        this.speaker = speaker;
        this.attendees = new ArrayList<>();
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public void addAttendee(Attendee attendee){
        this.attendees.add(attendee);
    }
}
