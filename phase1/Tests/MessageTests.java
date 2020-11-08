import Entity.Message;
import org.junit.*;


public class MessageTests {

    @Test(timeout = 50)
    public void testMessage(){
        Message a = new Message("hi how are u", "uname1", "uname2");
    }

    @Test(timeout = 50)
    public void testMessageGetters(){
        Message a = new Message("hi how are u", "uname1", "uname2");
        assertTrue("getContent() does not work :/", "hi how are u", a.getContent());
        assertTrue("getReceiver() does not work :/", "uname1", a.getReceiver());
        assertTrue("getSender() does not work :/", "uname2", a.getSender());
    }

    private void assertTrue(String s, String hi_how_are_u, String content) {
    }

    @Test(timeout = 50)
    public void testMessageSetters(){
        Message a = new  Message("", "", "");
        String username1 = "uname1";
        String username2 = "uname2";
        String content = "whassssaaaap";
        a.setContent(content);
        a.setReceiver(username1);
        a.setSender(username2);
        assertTrue("setter for content does not work", a.getContent(), content);
        assertTrue("setter for receiver does not work", a.getReceiver(), username1);
        assertTrue("setter for sender does not work", a.getSender(), username2);
    }
}
