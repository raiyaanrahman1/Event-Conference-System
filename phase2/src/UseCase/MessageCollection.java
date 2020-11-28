package UseCase;

import Entity.Message;
import Exceptions.NoSuchMessageException;

import java.util.List;

/**
 * An interface for a collection of messages.
 */
public interface MessageCollection {
    /**
     * Returns the first instance of the equivalent message contained
     * in this collection.
     *
     * @param message  the message
     * @return  the equivalent message stored in the collection
     * @throws  NoSuchMessageException iff no such message is found
     */
    Message find(Message message) throws NoSuchMessageException;

    /**
     * Returns all the messages received by the given user.
     *
     * @param receiver  the receiver's username
     * @return  a list of messages received by user.
     */
    List<Message> get(String receiver);

    /**
     * Returns true iff receiver has any messages.
     *
     * @param receiver  the receiver's username
     * @return  true iff receiver has any messages
     */
    boolean contains(String receiver);

    /**
     * Adds the given message to the collection.
     * Returns true iff the message was added successfully.
     *
     * @param message  the message
     * @return  true iff the message was added successfully
     */
    boolean add(Message message);

    /**
     * Removes the given message from the collection.
     *
     * @param message  the message
     * @return  true iff the message was removed successfully
     */
    boolean remove(Message message);

    /**
     * Returns all messages stored in this collection.
     *
     * @return  all messages in this collection
     */
    List<Message> getAll();
}
