import Entity.Event;
import Entity.Message;
import Entity.Organizer;
import Entity.Speaker;
import org.junit.*;
import static org.junit.Assert.*;

public class OrganizerTests {
    // the Organizer constructor
    @Test(timeout = 50)
    public void testOrganizer() {

        Organizer o = new Organizer("organizer1", "2");

    }

    // test Organizer broadcast rights and event creating rights
    @Test(timeout = 50)
    public void testHasBroadcastAndEventCreatingRights() {
        Organizer o1 = new Organizer("organizer1", "1");

        assertTrue("incorrect Broadcast rights\n", o1.hasBroadcastRights());
        assertTrue("incorrect Broadcast rights\n", o1.hasEventCreatingRights());

    }

    @Test(timeout = 50)
    public void testContactList() {
        Organizer o1 = new Organizer("organizer1", "1");
        Organizer o2 = new Organizer("organizer2", "2");

        assertTrue("incorrect contacts list\n", o1.getContacts().isEmpty());
        o1.addContact(o2);
        assertEquals("incorrect contacts list\n", 1, o1.getContacts().size());
        assertSame("incorrect contacts list\n", o1.getContacts().get(0), o2);

    }

    @Test(timeout = 50)
    public void testSentMessagesList() {
        Organizer o1 = new Organizer("organizer1", "1");
        Message m = new Message("Hello", "organizer2", "organizer1");

        assertTrue("incorrect sentMessages list\n", o1.getSentMessages().isEmpty());
        o1.addSentMessage(m);
        assertEquals("incorrect sentMessages list\n", 1, o1.getSentMessages().size());
        assertSame("incorrect sentMessages list\n", o1.getSentMessages().get(0), m);
    }

    @Test(timeout = 50)
    public void testReceivedMessagesList() {
        Organizer o1 = new Organizer("organizer1", "1");
        Message m = new Message("Hello", "organizer1", "organizer2");

        assertTrue("incorrect receivedMessages list\n", o1.getReceivedMessages().isEmpty());
        o1.addReceivedMessage(m);
        assertEquals("incorrect receivedMessages list\n", 1, o1.getReceivedMessages().size());
        assertSame("incorrect receivedMessages list\n", o1.getReceivedMessages().get(0), m);
    }

    @Test(timeout = 50)
    public void testEventList() {
        Organizer o1 = new Organizer("organizer1", "1");
        Speaker s = new Speaker("Barack Obama", "1");
        Event e = new Event("16:00", "2020.10.29", "100", s);

        assertTrue("incorrect eventList list\n", o1.getEventList().isEmpty());
        o1.addEvent(e);
        assertEquals("incorrect eventList list\n", 1, o1.getEventList().size());
        assertSame("incorrect eventList list\n", o1.getEventList().get(0), e);
    }

    @Test(timeout = 50)
    public  void testOrganizedEvents() {
        Organizer o1 = new Organizer("organizer1", "1");
        Speaker s = new Speaker("Barack Obama", "1");
        Event e = new Event("16:00", "2020.10.29", "100", s);

        assertTrue("incorrect organizedEvents list\n", o1.getOrganizedEvents().isEmpty());
        o1.addOrganizedEvent(e);
        assertEquals("incorrect organizedEvents list\n", 1, o1.getOrganizedEvents().size());
        assertSame("incorrect eventList list\n", o1.getOrganizedEvents().get(0), e);
    }

}
