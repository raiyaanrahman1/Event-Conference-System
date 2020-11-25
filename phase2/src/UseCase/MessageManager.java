package UseCase;

import Entity.Message;
import Gateway.IGateway2;
import Exceptions.NonExistentMessageException;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

/**
 * The MessageManager class manages communications between users.
 */
public class MessageManager {

    /**
     * The message key-value map. Each key is an user's username which
     * points to a list of all messages received by that user.
     */
    private final Map<String, List<Message>> messages;

    /**
     * Constructs a map from the file.
     * The file should be formatted like so:
     *   receiver|sender|dateTime|content
     */
    public MessageManager(IGateway2 gateway) {
        messages = new HashMap<>();

        gateway.openForRead();
        List<String> formattedMessages = this.getStoredMessages(gateway);
        gateway.closeForRead();

        for (String formattedMessage: formattedMessages) {
            String[] tokens = formattedMessage.split("\\|");

            String receiver = tokens[0];
            String sender = tokens[1];
            String dateTime = tokens[2];
            String content = tokens[3];

            Message message = new Message(content, receiver, sender, dateTime);

            this.addMessage(receiver, message);
        }
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
     * Removes the message that matches the given string representation.
     * Returns true iff the message was removed correctly, otherwise
     * @param formattedMessage  the string representation of the message
     *                          to remove
     * @return  true iff the message has been removed correctly
     */
    public boolean delete(String formattedMessage) {
        String[] tokens = formattedMessage.split("\\|");

        if (tokens.length != 4) {
            return false;
        }

        String receiver = tokens[0];
        String sender = tokens[1];
        String content = tokens[3];
        String formattedDateTime = tokens[2];

        Message message;

        try {
            message = new Message(receiver, sender, content, formattedDateTime);
        } catch (DateTimeParseException e) {
            return false;
        }

        return this.removeMessage(message);
    }

    /**
     * Gets the messages received by the given user. Each message is
     * represented by a string with the following format:
     *         (sender's username)|(date)|(time)|(content)
     *
     * For example,
     *  ken|09/08/1969 11:37:45|Ritchie, check this out, i call it ed.
     *
     * @param receiver  the receiver of the messages
     */
    public List<String> getMessages(String receiver) {
        List<String> messages = new ArrayList<>();

        if (this.hasMessages(receiver)) {
            for (Message message: this.messages.get(receiver)) {
                messages.add(this.getFormattedMessage(message));
            }
        }

        return messages;
    }

    /**
     * Stores the messages through a gateway.
     * Precondition: the gateway must not be open for write/read.
     *
     * @param gateway  the gateway through which we save our messages
     */
    public void storeMessages(IGateway2 gateway) {
        if (gateway.openForWrite()) {
            for (List<Message> messages: this.messages.values()) {
                for (Message message: messages) {
                    gateway.write(this.convertToString(message));
                }
            }

            gateway.closeForWrite();
        }
    }

    /*
     * Adds new message the given receiver to his list of messages.
     *
     * @param receiver  the user that receives the message
     * @param message  the message
     */
    private void addMessage(String receiver, Message message) {
        if (!this.hasMessages(receiver)) {
            this.addUser(receiver);
        }

        List<Message> messageList = this.messages.get(receiver);
        messageList.add(message);
    }

    /*
     * Removes a message from the given receiver's list of messages.
     *
     * @param receiver  the user that receives the message
     * @param message  the message
     */
    private boolean removeMessage(Message message) {
        if (!this.hasMessages(message.getReceiver())) {
            this.addUser(message.getReceiver());
            return false;
        } else {
            List<Message> messageList = this.messages.get(message.getReceiver());

            try {
                messageList.remove(this.getStoredEquivalent(message));
            } catch (NonExistentMessageException ex) {
                return false;
            }

            return true;
        }
    }

    /*
     * Returns true iff the user has been any messages.
     *
     * @param receiver  the username of the user
     * @return  the boolean flag representing the condition.
     */
    private boolean hasMessages(String receiver) {
        return this.messages.containsKey(receiver);
    }

    /*
     * Returns the message stored in this manager that is equivalent to
     * the given message.
     *
     * @param message  the given message
     * @return  the equivalent stored message iff it exists
     * @throws  NonExistentMessageException iff no such message exists
     */
    private Message getStoredEquivalent(Message message)
            throws NonExistentMessageException {
        List<Message> messageList = this.messages.get(message.getReceiver());

        Comparator<Message> comparator = new ComparatorByData();

        for (Message other: messageList) {
            if (comparator.compare(message, other) == 0) {
                return other;
            }
        }

        throw new NonExistentMessageException();
    }

    /*
     * Adds username to map and constructs empty list of messages.
     * This is only to be used if the user does not yer exist in the
     * map, i.e has not received any messages.
     *
     * @param receiver  the username of the user.
     */
    private void addUser(String receiver) {
        this.messages.put(receiver, new ArrayList<>());
    }

    /*
     * Gets the strings that represent each message to the manager.
     * Each string should be formatted in the manner:
     *          (receiver)|(sender)|(datetime)|(content)
     *
     * @param gateway2  the interface that holds the message data
     * @return  a list of formatted messages
     */
    private List<String> getStoredMessages(IGateway2 gateway2) {
        List<String> formattedMessageStrings = new ArrayList<>();
        while (gateway2.hasNext()) {
            formattedMessageStrings.add(gateway2.next());
        }
        return formattedMessageStrings;
    }

    /*
     * Returns the formatted form of a given message.
     *
     * For example,
     *  ken|09/08/1969 11:37:45|Ritchie, check this out, i call it ed.
     *
     * @param message  the message
     * @return  the formatted string that represents the message.
     */
    private String getFormattedMessage(Message message) {
        String dateTime = message.getFormattedDateTime();
        String content = message.getContent();
        String sender = message.getSender();

        return String.format("%s|%s|%s", sender, dateTime, content);
    }

    /*
     * Returns the string representation of a given message.
     *
     * @param message  the message
     * @return  the string representation of this message
     */
    private String convertToString(Message message) {
        String dateTime = message.getFormattedDateTime();
        String content = message.getContent();
        String sender = message.getSender();
        String receiver = message.getReceiver();

        return String.format("%s|%s|%s|%s", receiver, sender, dateTime, content);
    }

//    /**
//     * Sorts a list of messages by their
//     * @param messages  the list of messages to be sorted
//     */
//    private void sortMessages(List<Message> messages) {
//        messages.sort(new ComparatorByDateTime());
//    }

    /*
     * A comparator class used to compare messages by their datetime.
     */
    private class ComparatorByDateTime implements Comparator<Message> {

        /*
         * Compares two messages by their datetime.
         *
         * Between two messages, the most recent one is the greater one.
         * The oldest one is the smallest one. If they occurred at the
         * same time, they are the same.
         *
         * This comparator is not consistent with the equals method.
         *
         * @param mess1  the one message being compared
         * @param mess2  the other message being compared
         * @return 1 iff mess1 message is greater than mess2
         *         -1 iff mess1 message is smaller than mess2
         *         0 otherwise
         */
        public int compare(Message mess1, Message mess2) {
            if (mess1.getDateTime().isAfter(mess2.getDateTime())) {
                return 1;
            } else if (mess1.getDateTime().isBefore(mess2.getDateTime())) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /*
     * A comparator class used to compare messages by all their data.
     */
    private class ComparatorByData implements Comparator<Message> {

        /*
         * Compares two messages by their inner data.
         *
         * If two messages have the same content, the same content, sender,
         * receiver, and datetime.
         *
         * This comparator is consistent with the equals method. But it is
         * inconsistent with total ordering (i.e. useless for sorting).
         *
         * @param mess1  the one message being compared
         * @param mess2  the other message being compared
         * @return 0 iff mess1 is equal to mess2
         *         -1 otherwise
         */
        public int compare(Message mess1, Message mess2) {
            if (mess1.getContent().equals(mess2.getContent())
                    && mess1.getReceiver().equals(mess2.getReceiver())
                    && mess1.getSender().equals(mess2.getSender())
                    && mess1.getDateTime().equals(mess2.getDateTime())) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
