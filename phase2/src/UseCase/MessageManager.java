package UseCase;

import Entity.Message;
import Exceptions.InvalidMessageFormatException;
import Exceptions.NoSuchMessageException;
import Gateway.IGateway2;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The MessageManager class manages communications between users.
 */
public class MessageManager {

    /*
     * A message collection for all active messages.
     */
    private final MessageCollection messages;

    /*
     * A message collection for all archived messages.
     */
    private final MessageCollection archive;

    /**
     * Constructs a map from the file.
     * The file should be formatted like so:
     *   receiver|sender|dateTime|content
     */
    public MessageManager(IGateway2 gateway) {
        messages = new MessageMap();
        archive = new MessageMap();

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

            this.messages.add(message);
        }
    }

    public MessageManager(MessageCollection messages, MessageCollection archive) {
        this.messages = messages;
        this.archive = archive;
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
        this.messages.add(newMessage);
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
    public boolean delete(String receiver, String formattedMessage) {
        Message message;

        try {
            message = this.parseMessage(receiver, formattedMessage);
        } catch (InvalidMessageFormatException | DateTimeParseException ex) {
            return false;
        }

        return this.messages.remove(message);
    }

    /**
     * Archives the message that matches the given string representation.
     * Returns true iff the message was archived correctly.
     *
     * @param formattedMessage  the string representation of the message
     * @return  true iff the message has been archived correctly
     */
    public boolean archive(String receiver, String formattedMessage) {
        Message message;

        try {
            message = this.parseMessage(receiver, formattedMessage);
        } catch (InvalidMessageFormatException | DateTimeParseException ex) {
            return false;
        }

        try {
            message = this.messages.find(message);
        } catch (NoSuchMessageException ex) {
            return false;
        }

        this.archive.add(message);
        this.messages.remove(message);

        return true;
    }

    public boolean unarchive(String receiver, String formattedMessage) {
        Message message;

        try {
            message = this.parseMessage(receiver, formattedMessage);
        } catch (InvalidMessageFormatException | DateTimeParseException ex) {
            return false;
        }

        try {
            message = this.messages.find(message);
        } catch (NoSuchMessageException ex) {
            return false;
        }

        this.archive.remove(message);
        this.messages.add(message);

        return true;
    }
    /**
     * Marks the message that matches the given string representation
     * as read or unread. Returns true iff the message was marked
     * correctly, otherwise it returns false.
     *
     * @param formattedMessage  the string representation of the message
     * @return  true iff the message has been marks correctly
     */
    public boolean mark(String receiver, String formattedMessage, boolean read) {
        Message message;

        try {
            message = this.parseMessage(receiver, formattedMessage);
        } catch (InvalidMessageFormatException | DateTimeParseException e) {
            return false;
        }

        try {
            this.messages.find(message).setRead(read);
        } catch (NoSuchMessageException e) {
            return false;
        }

        return true;
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
        List<String> userMessages = new ArrayList<>();

        if (this.messages.contains(receiver)) {
            for (Message message: this.messages.get(receiver)) {
                userMessages.add(this.getFormattedMessage(message));
            }
        }

        return userMessages;
    }

    /**
     * Gets the messages archived by the given user in the usual
     * string representation.
     *
     * @param receiver  the receiver of this message
     * @return  the list of archived messages
     */
    public List<String> getArchivedMessages(String receiver) {
        List<String> userMessages = new ArrayList<>();

        if (this.archive.contains(receiver)) {
            for (Message message: this.messages.get(receiver)) {
                userMessages.add(this.getFormattedMessage(message));
            }
        }

        return userMessages;
    }

    /**
     * Stores the messages through a gateway.
     * Precondition: the gateway must not be open for write/read.
     *
     * @param gateway  the gateway through which we save our messages
     */
    public void storeMessages(IGateway2 gateway) {
        if (gateway.openForWrite()) {
            for (Message message: this.messages.getAll()) {
                gateway.write(this.convertToString(message));
            }

            gateway.closeForWrite();
        }
    }

    public boolean isRead(int index, String receiver){
        try {
            return this.messages.find(messages.get(receiver).get(index)).isRead();
        } catch (NoSuchMessageException e) {
            e.printStackTrace();
        }
        return false;
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
     * Splits a formatted message into its necessary string components.
     *
     * @param formattedMessage  the formatted message
     * @return  an array containing the components of the message
     * @throws InvalidMessageFormatException iff formatted message is invalid
     */
    private Message parseMessage(String receiver, String formattedMessage)
            throws InvalidMessageFormatException {
        String[] tokens = formattedMessage.split("\\|");

        if (tokens.length != 4) {
            throw new InvalidMessageFormatException();
        }

        String sender = tokens[0];
        String formattedDateTime = tokens[1];
        String content = tokens[2];
        boolean isRead = Boolean.parseBoolean(tokens[3]);

        return new Message(content, receiver, sender, formattedDateTime, isRead);
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

    /*
     * Sorts a list of messages by their
     * @param messages  the list of messages to be sorted
     */
    private void sortMessages(List<Message> messages) {
        messages.sort(new ComparatorByDateTime());
    }

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
