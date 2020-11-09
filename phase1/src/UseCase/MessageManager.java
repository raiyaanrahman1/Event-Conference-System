package UseCase;

import Entity.Message;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * The MessageManager class manages communications between users.
 * TODO: Figure out the initialization of this class. Build a constructor that
 *  initializes the message manager from a gateway and a builder class.
 */
public class MessageManager {

    /**
     * The message key-value map. Each key is an user's username which
     * points to a list of all messages received by that user.
     */
    private final Map<String, List<Message>> messages;

    /**
     * Constructs default map, an empty one.
     */
    private MessageManager() {
        messages = new HashMap<>();
    }

    /**
     * Sends a message between users.
     *
     * @param sender  the user that sent the message
     * @param receiver  the user that is to receive the message
     * @param message  the content of the message
     */
    public void message(String sender, String receiver,
                        String message) {
        Message newMessage = new Message(message, receiver, sender);
        this.addMessage(receiver, newMessage);
    }

    /**
     * Broadcasts a message to a group of users.
     *
     * @param sender  the user that broadcasts the message
     * @param receivers  the users that receive the message
     * @param message  the content of the message
     */
    public void broadcast(String sender, List<String> receivers,
                          String message) {
        for (String receiver: receivers) {
            this.message(sender, receiver, message);
        }
    }

    /**
     * Gets the messages received by the given user. Each message
     * is represented by a string with the following format:
     *      (sender's username)|(date)|(time)|(content)
     * For example,
     *  ken|09/08/1969|11:37:45|Ritchie, check this out, i call it ed.
     *
     * @param receiver  the receiver of the messages
     */
    public List<String> getMessages(String receiver) {
        List<String> messages = new ArrayList<>();

        for (Message message: this.messages.get(receiver)) {
            String dateTime = message.getFormattedDateTime();
            String content = message.getContent();
            String sender = message.getSender();

            String formatted = String.format("%s|%s|%s",
                    sender, dateTime, content);
            messages.add(formatted);
        }

        return messages;
    }

    /**
     * Adds new message the given receiver to his list of messages.
     *
     * @param receiver  the user that receives the message
     * @param message  the message
     */
    private void addMessage(String receiver, Message message) {
        if (!this.hasMessages(receiver)) {
            this.addUser(receiver);
        }

        List<Message> messageList = messages.get(receiver);
        messageList.add(message);
    }

    /**
     * Returns true iff the user has been introduced into the map.
     *
     * @param receiver  the username of the user
     * @return  the boolean flag representing the condition.
     */
    private boolean hasMessages(String receiver) {
        return messages.containsKey(receiver);
    }

    /**
     * Adds username to map and constructs empty list of messages.
     * This is only to be used if the user does not yer exist in the
     * map, i.e has not received any messages.
     *
     * @param receiver  the username of the user.
     */
    private void addUser(String receiver) {
        messages.put(receiver, new ArrayList<>());
    }

}
