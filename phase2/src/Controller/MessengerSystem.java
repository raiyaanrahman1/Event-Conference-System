package Controller;

import java.util.ArrayList;
import java.util.List;

import UseCase.MessageManager;
import UseCase.UserManager;

/**
 * A messenger system for the logged in user.
 */
public class MessengerSystem {

    private final UserManager userManager;
    private final MessageManager messageManager;

    /**
     * Initializes the MessengerSystem.
     *
     * @param userManager  the manager object for users
     * @param messageManager  the manager object for messages
     */
    public MessengerSystem(UserManager userManager, MessageManager messageManager) {
        this.userManager = userManager;
        this.messageManager = messageManager;
    }

    /**
     * Gets the list of messages the logged on user has received.
     *
     * @return  the list of messages received by the logged in user
     */
    public List<String> viewReceivedMessages() {
        return messageManager.getMessages(getLoggedInUser());
    }

    /**
     * Gets the list of messages that the logged in user has archived.
     *
     * @return  a list of received messages
     */
    public List<String> viewArchivedMessages() {
        return messageManager.getArchivedMessages(getLoggedInUser());
    }

    /**
     * Messages the user inputted with the content inputted.
     *
     * @param username  the username of the User that will be messaged
     * @param content  the content of the message
     */
    public void messageUser(String username, String content) {
        messageManager.message(getLoggedInUser(), username, content);
    }

    /**
     * Replies to the given message.
     *
     * @param index  the index of the message in list of received messages
     * @param content  the content of the message
     */
    public void replyMessage(int index, String content) {
        String message = viewReceivedMessages().get(index);
        String sender = message.split("\\|")[0];
        messageManager.message(userManager.getUserInfoList().get(0), sender, content);
//        msgPres.successfulMessage();
        System.out.println("Success");
    }

    /**
     * Archives the message at the given index in the list of messages.
     *
     * @param index  the index of the message in the list
     */
    public void archiveMessage(int index) {
        messageManager.archive(getLoggedInUser(), viewReceivedMessages().get(index));
    }

    public void unarchiveMessage(int index) {
        messageManager.unarchive(getLoggedInUser(), viewReceivedMessages().get(index));
    }

    /**
     * Deletes the message at the given index in the list of messages for the currently
     * logged in user.
     *
     * @param index  the index of the message in the list
     */
    public void deleteMessage(int index) {
        messageManager.delete(getLoggedInUser(), viewReceivedMessages().get(index));
    }

    public boolean isRead(int index){
        return messageManager.isRead(index, getLoggedInUser());
    }
    /**
     * Marks the message at the given index in the list of messages as read.
     *
     * @param index  the index of the message in the list
     */
    public void markMessageRead(int index) {
        messageManager.mark(getLoggedInUser(), viewReceivedMessages().get(index), true);
    }

    /**
     * Marks the message at the given index in the list of messages as unread.
     *
     * @param index  the index of the message in the list
     */
    public void markMessageUnread(int index) {
        messageManager.mark(getLoggedInUser(), viewReceivedMessages().get(index), false);
    }

    /**
     * Adds the given user to the logged in user's contact list.
     *
     * @param username  the username of the given user
     */
    public boolean addUser(String username) {
        if (!getLoggedInUser().equals(username)
                && !inContacts(username)
                && userManager.getSignedUpUsers().contains(username)) {
            userManager.addUserToContacts(username);
            userManager.storeContacts();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes the given user from the logged in user's contact list.
     *
     * @param username  the username of the given user
     */
    public void removeUser(String username) {
        userManager.removeUserFromContacts(username);
        userManager.storeContacts();
    }

    /**
     * Gets the contact list of the user.
     *
     * @return  the contact list
     */
    public List<String> getContacts() {
        return userManager.getContactList();
    }

    /**
     * Gets the list of messages sent by given the user to the current logged in
     * user.
     *
     * @param  username the username of the sender of the messages
     */
    public List<String> viewMessages(String username) {
        List<String> messages = viewReceivedMessages();
        List<String> messagesBySender = new ArrayList<>();

        for (String message : messages) {
            String[] tokens = message.split("\\|");
            if (tokens[0].equals(username)) {
                messagesBySender.add(message);
            }
        }

        return messagesBySender;
    }

    /*
     * Returns the username of the current logged in user.
     *
     * @return  the username of the logged in user
     */
    private String getLoggedInUser() {
        return userManager.getUserInfoList().get(0);
    }

    /*
     * Returns true iff the given user is in the current logged in user's contact
     * list.
     *
     * @param username  the given user's username
     * @return  true iff the given user is the logged in user's contact list
     */
    private boolean inContacts(String username) {
        return getContacts().contains(username);
    }

}