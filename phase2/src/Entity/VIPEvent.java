package Entity;

import java.time.LocalDateTime;

public class VIPEvent extends Event{

    /**
     * Creates a new vip event object.
     *
     * @param name  the Event's name
     * @param room  the Event's room
     * @param organizer the Event's Organizer's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param startTime the date and time of the event
     * @param endTime when the event ends
     */
    public VIPEvent(String name, String room, String organizer, int roomCap, LocalDateTime startTime,
                    LocalDateTime endTime) {
        super(name, room, organizer, roomCap, startTime, endTime);
    }

    /**
     * Creates a new vip event object using data from the store event info file.
     * @param eventID the id associated with this event
     * @param name  the Event's name
     * @param room  the Event's room
     * @param speaker  the Event's Speaker's username
     * @param organizer the Event's Organizer's username
     * @param roomCap the Event's maximum capacity of attendees
     * @param startTime the date and time of the event
     * @param endTime when the event ends
     */
    public VIPEvent(String eventID, String name, String room, String speaker,
                 String organizer, String roomCap, String startTime, String endTime){
        super(eventID, name, room, speaker, organizer, roomCap, startTime, endTime);
    }

    /**
     * Gets this Event's information in a saveable format (i.e in a String)
     * @return this Event's info as a String
     */
    public String getSaveableInfo(){
        return "V" + super.getSaveableInfo().substring(1);

    }
}
