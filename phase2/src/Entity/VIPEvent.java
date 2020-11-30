package Entity;

import java.time.LocalDateTime;

public class VIPEvent extends Event{
    public VIPEvent(String name, String room, String organizer, int roomCap, LocalDateTime startTime,
                    LocalDateTime endTime) {
        super(name, room, organizer, roomCap, startTime, endTime);
    }
    public VIPEvent(String name, String room, String speaker, String organizer, int roomCap, LocalDateTime startTime,
                    LocalDateTime endTime){
        super(name, room, speaker, organizer, roomCap, startTime, endTime);
    }
    public VIPEvent(String eventID, String name, String room, String speaker,
                 String organizer, String roomCap, String startTime, String endTime){
        super(eventID, name, room, speaker, organizer, roomCap, startTime, endTime);
    }
    public VIPEvent(String eventID, String name, String room,
                    String organizer, String roomCap, String startTime, String endTime){
        super(eventID, name, room, organizer, roomCap, startTime, endTime);
    }
    /**
     * Gets this Event's information in a saveable format (i.e in a String)
     * @return this Event's info as a String
     */
    public String getSaveableInfo(){
        return "V" + super.getSaveableInfo().substring(1);

    }
}
