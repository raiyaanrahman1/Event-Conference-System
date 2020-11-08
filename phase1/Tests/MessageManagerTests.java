import Entity.*;
import static org.junit.Assert.*;
import org.junit.*;
import UseCase.MessageManager;
import java.util.List;

public class MessageManagerTests {

    @Test(timeout = 50)
    public void testMessage() {
        MessageManager messageManager = new MessageManager();
        String message = "Hi bro, wanna hang";
        Attendee uDavid = new Attendee("david", "1234");
        Attendee uMark = new Attendee("david", "1234");

        messageManager.message(uDavid, uMark, message);

        List<String> messagesByDavid = messageManager.getMessages(uDavid, uMark);

        assertTrue("message method not working", messagesByDavid.contains(message));
    }

    @Test(timeout = 50)
    public void testBroadcast() {
        MessageManager messageManager = new MessageManager();
        String message = "Hello everyone, welcome!";

        Speaker uJill = new Speaker("jill", "MEGA_password_78_$$");
        Attendee uDavid = new Attendee("david", "1234");
        Attendee uMark = new Attendee("david", "1234");

        Event event = new Event("EventA", "", "", "", uJill, 2);

        messageManager.broadcast(uJill, event, message);

        List<String> messagesDavid = messageManager.getMessages(uJill, uDavid);
        List<String> messagesMark = messageManager.getMessages(uJill, uMark);

        assertFalse("message method not working", messagesDavid.contains(message));
        assertFalse("message method not working", messagesMark.contains(message));

        event.addAttendee(uDavid);
        event.addAttendee(uMark);

        messageManager.broadcast(uJill, event, message);

        messagesDavid = messageManager.getMessages(uJill, uDavid);
        messagesMark = messageManager.getMessages(uJill, uMark);

        assertTrue("message method not working", messagesDavid.contains(message));
        assertTrue("message method not working", messagesMark.contains(message));
    }
}
