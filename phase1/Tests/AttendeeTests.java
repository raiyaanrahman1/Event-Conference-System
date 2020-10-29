import Entity.Attendee;
import Entity.Event;
import Entity.Message;
import Entity.User;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class AttendeeTests {

    // the Attendee constructor
    @Test(timeout = 50)
    public void testAttendee() {
        List<User> contacts = new ArrayList<>();
        List<Message> receivedMessages = new ArrayList<>();
        List<Message> sentMessages = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();

        Attendee a = new Attendee("attendee1", "2");

    }

    // test Attendee broadcast rights and event creating rights
    @Test(timeout = 50)
    public void testAttendeeMethods() {
        Attendee a1 = new Attendee("attendee1", "1");
        Attendee a2 = new Attendee("attendee2", "2");

        assertFalse("incorrect Broadcast rights\n", a1.hasBroadcastRights());
        assertFalse("incorrect Broadcast rights\n", a1.hasEventCreatingRights());

        assertTrue("incorrect contacts list\n", a1.getContacts().isEmpty());
        a1.addContact(a2);
        assertEquals("incorrect contacts list\n", 1, a1.getContacts().size());


    }



}
