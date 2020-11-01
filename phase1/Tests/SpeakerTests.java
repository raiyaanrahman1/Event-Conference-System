import Entity.Event;
import Entity.Message;
import Entity.Speaker;
import org.junit.*;
import static org.junit.Assert.*;

public class SpeakerTests {

    // the Speaker constructor
    @Test(timeout = 50)
    public void testSpeaker() {

        Speaker s = new Speaker("speaker1", "2");

    }

    // test Speaker broadcast rights and event creating rights
    @Test(timeout = 50)
    public void testhasBroadcastAndEventCreatingRights() {
        Speaker s1 = new Speaker("speaker1", "1");

        assertTrue("incorrect Broadcast rights\n", s1.hasBroadcastRights());
        assertFalse("incorrect Broadcast rights\n", s1.hasEventCreatingRights());

    }

    @Test(timeout = 50)
    public void testSentMessagesList() {
        Speaker s1 = new Speaker("Speaker1", "1");
        Message m = new Message("Hello", "Speaker2", "Speaker1", "11.01.2020", "13:00");

        assertTrue("incorrect sentMessages list\n", s1.getSentMessages().isEmpty());
        s1.addSentMessage(m);
        assertEquals("incorrect sentMessages list\n", 1, s1.getSentMessages().size());
        assertSame("incorrect sentMessages list\n", s1.getSentMessages().get(0), m);
    }

    @Test(timeout = 50)
    public void testReceivedMessagesList() {
        Speaker s1 = new Speaker("speaker1", "1");
        Message m = new Message("Hello", "speaker1", "speaker2", "11.01.2020", "13:00");

        assertTrue("incorrect receivedMessages list\n", s1.getReceivedMessages().isEmpty());
        s1.addReceivedMessage(m);
        assertEquals("incorrect receivedMessages list\n", 1, s1.getReceivedMessages().size());
        assertSame("incorrect receivedMessages list\n", s1.getReceivedMessages().get(0), m);
    }

    @Test(timeout = 50)
    public void testTalks() {
        Speaker s = new Speaker("Barack Obama", "1");
        Event e = new Event("16:00", "2020.10.29", "100", s,2);

        assertTrue("incorrect eventList list\n", s.getTalks().isEmpty());
        s.addTalk(e);
        assertEquals("incorrect eventList list\n", 1, s.getTalks().size());
        assertSame("incorrect eventList list\n", s.getTalks().get(0), e);
    }

}
