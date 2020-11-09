package Entity;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Message class represents a message between two users.
 */
public class Message {
    private String content;
    private String receiver; //username of receiver
    private String sender;  //username of sender
    private LocalDate date;
    private LocalTime time;

    /**
     * Creates a new message object.
     *
     * @param content  the Message's content
     * @param receiver  the username of the user that sends the Message
     * @param sender  the username of the user that receives the Message
     */
    public Message(String content, String receiver, String sender) {
        this.content = content;
        this.receiver = receiver;
        this.sender = sender;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    /**
     * Returns the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Changes the content of the message
     *
     * @param content   new content of the message
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the username of the receiver of the message
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     *Changes the receiver of the message
     *
     * @param receiver  username of the new receiver of the message
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Returns the username of the sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     *Changes the sender of the message
     *
     * @param sender  username of the new sender of the message
     */
    public void setSender(String sender) {
        this.sender = sender;
    }


    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
