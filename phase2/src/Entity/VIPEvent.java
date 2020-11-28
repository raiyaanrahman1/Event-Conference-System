package Entity;

import java.time.LocalDateTime;

public class VIPEvent extends Event{
    public VIPEvent(String name, String room, String organizer, int roomCap, LocalDateTime dateTime) {
        super(name, room, organizer, roomCap, dateTime);
    }
    public VIPEvent(String name, String room, String speaker, String organizer, int roomCap, LocalDateTime dateTime){
        super(name, room, speaker, organizer, roomCap, dateTime);
    }
}
