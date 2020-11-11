import static org.junit.Assert.*;

import Entity.Attendee;
import Entity.Event;
import Entity.Speaker;
import org.junit.*;

public class EventManagementSystemTests {

    @Test(timeout = 50)
    public void eventSignUpSuccessful() {

        Attendee a = new Attendee("attendee1", "2");

    }

//    @Test(timeout = 50)
//    public void eventSignUpUnsuccessful() {
//
//        Attendee a = new Attendee("attendee1", "2");
//        Event event = new Event("", "",  "",  "", new Speaker("",""), 2);
//        String time = "10:00";
//        String date = "02/01/20";
//        String room = "b";
//        Speaker speaker = new Speaker("username","password1");
////        event.setDate(date);
////        event.setTime(time);
//        event.setSpeaker(speaker);
//        event.setRoom(room);
//    }

}
