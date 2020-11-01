import Entity.*;
import UseCase.MessageManager;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;

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

        Event event = new Event("", "", "", uJill);

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
