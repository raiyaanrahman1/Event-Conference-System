package UseCase;

import Entity.Message;
import Entity.Event;
import Entity.User;

import java.util.List;
import java.util.ArrayList;

/**
 * The MessageManager class manages communications between users.
 * TODO: Figure out the initialization of this class.
 */
public class MessageManager {

    /**
     * Sends a message between users.
     *
     * @param sender  the user that sent the message
     * @param receiver  the user that is to receive the message
     * @param message  the content of the message
     * @param date the date of the message
     * @param time the time that the message is sent
     */
    public void message(User sender, User receiver, String message, String date, String time) {
        Message newMessage = new Message(message, receiver.getUsername(), sender.getUsername(), date, time);
        receiver.addReceivedMessage(newMessage);
        sender.addSentMessage(newMessage);
    }

    /**
     * Broadcasts a message to an event.
     *
     * @param sender  the user that broadcasts the message
     * @param event  the event where the message is broadcast
     * @param message  the content of the message
     * @param date the date of the message
     * @param time the time that the message is sent
     */
    public boolean broadcast(User sender, Event event, String message, String date, String time) {
        if (sender.hasBroadcastRights()) {
            for (User attendee: event.getAttendees()) {
                this.message(sender, attendee, message, date, time);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the messages between given users.
     *
     * @param sender  the sender of the messages
     * @param receiver   the receiver of the messages
     */
    public List<String> getMessages(User sender, User receiver) {
        List<String> messages = new ArrayList<>();

        for (Message message: receiver.getReceivedMessages()) {
            if (message.getSender().equals(sender.getUsername())) {
                messages.add(message.getContent());
            }
        }

        return messages;
    }

}
