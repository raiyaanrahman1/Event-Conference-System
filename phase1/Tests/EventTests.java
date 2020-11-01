import Entity.Event;
import Entity.Speaker;
import org.junit.*;
import static org.junit.Assert.*;


public class EventTests {

    @Test(timeout = 50)
    public void testEvent(){
        String time = "09:00";
        String date = "01/01/20";
        String room = "a";
        Speaker speaker = new Speaker("username1","password");
        Event event = new Event(time, date, room, speaker);

    }

    @Test(timeout = 50)
    public void testEventGetters() {
        String time = "09:00";
        String date = "01/01/20";
        String room = "a";
        Speaker speaker = new Speaker("username1","password");
        Event event = new Event(time, date, room, speaker);
        assertEquals(event.getDate(), date);
        assertEquals(event.getTime(), time);
        assertEquals(event.getRoom(), room);
        assertEquals(event.getSpeaker(), speaker);
    }

    @Test(timeout = 50)
    public void  testEventSetters()   {
        Event event = new Event("",  "",  "", new Speaker("",""));
        String time = "10:00";
        String date = "02/01/20";
        String room = "b";
        Speaker speaker = new Speaker("username","password1");
        event.setDate(date);
        event.setTime(time);
        event.setSpeaker(speaker);
        event.setRoom(room);
        assertEquals(event.getDate(), date);
        assertEquals(event.getTime(), time);
        assertEquals(event.getRoom(), room);
        assertEquals(event.getSpeaker(), speaker);

    }
}
