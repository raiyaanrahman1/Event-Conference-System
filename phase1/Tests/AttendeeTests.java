import Entity.Attendee;
import Entity.Event;
import Entity.Message;
import Entity.Speaker;
import org.junit.*;
import static org.junit.Assert.*;

public class AttendeeTests {

    // the Attendee constructor
    @Test(timeout = 50)
    public void testAttendee() {

        Attendee a = new Attendee("attendee1", "2");

    }

    // test Attendee broadcast rights and event creating rights
    @Test(timeout = 50)
    public void testhasBroadcastAndEventCreatingRights() {
        Attendee a1 = new Attendee("attendee1", "1");

        assertFalse("incorrect Broadcast rights\n", a1.hasBroadcastRights());
        assertFalse("incorrect Broadcast rights\n", a1.hasEventCreatingRights());

    }

    @Test(timeout = 50)
    public void testContactList() {
        Attendee a1 = new Attendee("attendee1", "1");
        Attendee a2 = new Attendee("attendee2", "2");

        assertTrue("incorrect contacts list\n", a1.getContacts().isEmpty());
        a1.addContact(a2);
        assertEquals("incorrect contacts list\n", 1, a1.getContacts().size());
        assertSame("incorrect contacts list\n", a1.getContacts().get(0), a2);

    }

    @Test(timeout = 50)
    public void testSentMessagesList() {
        Attendee a1 = new Attendee("attendee1", "1");
        Message m = new Message("Hello", "attendee2", "attendee1");

        assertTrue("incorrect sentMessages list\n", a1.getSentMessages().isEmpty());
        a1.addSentMessage(m);
        assertEquals("incorrect sentMessages list\n", 1, a1.getSentMessages().size());
        assertSame("incorrect sentMessages list\n", a1.getSentMessages().get(0), m);
    }

    @Test(timeout = 50)
    public void testReceivedMessagesList() {
        Attendee a1 = new Attendee("attendee1", "1");
        Message m = new Message("Hello", "attendee1", "attendee2");

        assertTrue("incorrect receivedMessages list\n", a1.getReceivedMessages().isEmpty());
        a1.addReceivedMessage(m);
        assertEquals("incorrect receivedMessages list\n", 1, a1.getReceivedMessages().size());
        assertSame("incorrect receivedMessages list\n", a1.getReceivedMessages().get(0), m);
    }

    @Test(timeout = 50)
    public void testEventList() {
        Attendee a1 = new Attendee("attendee1", "1");
        Speaker s = new Speaker("Barack Obama", "1");
        Event e = new Event("16:00", "2020.10.29", "100", s);

        assertTrue("incorrect eventList list\n", a1.getEventList().isEmpty());
        a1.addEvent(e);
        assertEquals("incorrect eventList list\n", 1, a1.getEventList().size());
        assertSame("incorrect eventList list\n", a1.getEventList().get(0), e);
    }
}
