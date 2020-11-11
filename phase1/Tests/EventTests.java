//import Entity.Event;
//import Entity.Speaker;
//import org.junit.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//import static org.junit.Assert.*;
//
//
//public class EventTests {
//
//    @Test(timeout = 50)
//    public void testEvent(){
//        String name = "EventA";
//        LocalTime time = LocalTime.now();
//        LocalDate date = LocalDate.now();
//        String room = "a";
//        Speaker speaker = new Speaker("username1","password");
//        Event event = new Event(name, room, speaker, 2, date, time);
//
//    }
//
//    @Test(timeout = 50)
//    public void testEventGetters() {
//        String name = "EventA";
//        LocalTime time = LocalTime.now();
//        LocalDate date = LocalDate.now();
//        String room = "a";
//        Speaker speaker = new Speaker("username1","password");
//        Event event = new Event(name, room, speaker, 2, date, time);
//        assertEquals(event.getDate(), date);
//        assertEquals(event.getTime(), time);
//        assertEquals(event.getRoom(), room);
//        assertEquals(event.getSpeaker(), speaker);
//    }
//
//    @Test(timeout = 50)
//    public void  testEventSetters()   {
//        Event event = new Event("", "",  new Speaker("",""), 2,  LocalDate.now(), LocalTime.now());
//        LocalTime time = LocalTime.now();
//        LocalDate date = LocalDate.now();
//        String room = "b";
//        Speaker speaker = new Speaker("username","password1");
//        event.setDate(date);
//        event.setTime(time);
//        event.setSpeaker(speaker);
//        event.setRoom(room);
//        assertEquals(event.getDate(), date);
//        assertEquals(event.getTime(), time);
//        assertEquals(event.getRoom(), room);
//        assertEquals(event.getSpeaker(), speaker);
//
//    }
//}
