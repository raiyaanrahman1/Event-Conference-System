package Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Message class represents a message between two users.
 */
public class Message {
    private String content;
    private String receiver; //username of receiver
    private String sender;  //username of sender
    private LocalDateTime dateTime;

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
        this.dateTime = LocalDateTime.now();
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

    /**
     * Gets the LocalDateTime of the message
     *
     * @return the LocalDateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the datetime of this message formatted
     * dd/MM/yyyy HH:MM:ss.
     *
     * @return  this message's formatted time
     */
    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.
                ofPattern("yyyy-MM-dd HH:mm:ss");
        return getDateTime().format(formatter);
    }
}
