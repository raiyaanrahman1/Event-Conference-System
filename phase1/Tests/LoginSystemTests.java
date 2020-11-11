import static org.junit.Assert.*;

import Controller.LoginSystem;
import Entity.Attendee;
import UseCase.MessageManager;
import org.junit.*;

import java.util.List;

public class LoginSystemTests {

    @Test(timeout = 50)
    public void testWelcome() {
        LoginSystem logIn = new LoginSystem();
        logIn.welcome();
        String message = "Hi bro, wanna hang";
        Attendee uDavid = new Attendee("david", "1234");
        Attendee uMark = new Attendee("david", "1234");

        messageManager.message(uDavid, uMark, message);

        List<String> messagesByDavid = messageManager.getMessages(uDavid, uMark);

        assertTrue("message method not working", messagesByDavid.contains(message));
    }
}
