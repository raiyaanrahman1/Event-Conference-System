package Controller;

import java.util.ArrayList;
import java.util.List;

import Presenter.MessagePresenter;
import UseCase.MessageManager;
import UseCase.UserManager;

/**
 * Manages the messages and the contact list of the logged on user.
 * Communicates with the presenters.
 */
public class MessengerSystem {

    private UserManager user;
    private MessageManager msgMan;


    /**
     * Creates a MessengerSystem and initializes its UserManager and MessageManager.
     *
     * @param user   the UserManager that the MessengerSystem communicates with
     * @param msgMan the MessageManager that the MessengerSystem communicates with
     */
    public MessengerSystem(UserManager user, MessageManager msgMan) {
        this.user = user;
        this.msgMan = msgMan;
    }

//    /**
//     * Display the appropriate message menu depending on the type of logged-in user
//     */
//    public void menus() {
//        if (user.getUserInfoList().get(2).equals("S")) {
//            runSpeaker();
//        } else {
//            run();
//        }
//    }
//
//    /**
//     * Runs the main message menu.
//     */
//    public void run() {
//        boolean run = true;
//        do {
//            int option = msgPres.mainPage();
//            if (option == 1) {
//                int inboxOption = msgPres.mainInboxPage(viewReceivedMessages());
//                if (inboxOption == 1) {
//                    replyMessage(msgPres.getSelectedMessageNumber(), msgPres.getContent());
//                }
//            } else if (option == 2) {
//                mainContactPageRun(msgPres.mainContactPage(getContacts()));
//            } else if (option == 3) {
//                mainAddUserPageRun();
//            } else {
//                run = false;
//            }
//        } while (run);
//    }
//
//    /**
//     * The main run method for a Speaker user
//     */
//    public void runSpeaker() {
//        boolean run = true;
//        do {
//            int option = msgPres.mainSpeakerPage();
//            if (option == 1) {
//                int inboxOption = msgPres.mainInboxPage(viewReceivedMessages());
//                if (inboxOption == 1) {
//                    replyMessage(msgPres.getSelectedMessageNumber(), msgPres.getContent());
//                }
//            } else {
//                run = false;
//            }
//        } while (run);
//    }
//
//    private void mainContactPageRun(Integer contactOption) {
//        boolean run = true;
//        do {
//            if (contactOption == 1) {
//                String selectedUser = msgPres.selectFromContactList(getContacts());
//                mainSelectedContactPageRun(selectedUser, msgPres.selectedContactPage());
//                contactOption = msgPres.mainContactPage(getContacts());
//            } else {
//                run = false;
//            }
//        } while (run);
//    }
//
//    private void mainSelectedContactPageRun(String selectedUser, Integer option) {
//        boolean run = true;
//        do {
//            if (option == 1) {
//                messageUser(selectedUser, msgPres.getContent());
//                msgPres.successfulMessage();
//                option = msgPres.selectedContactPage();
//            } else if (option == 2) {
//                removeUser(selectedUser);
//                msgPres.removeUserSuccessful();
//                run = false;
//            } else if (option == 3) {
//                msgPres.formatMessages(viewMessages(selectedUser));
//                option = msgPres.selectedContactPage();
//            } else {
//                run = false;
//            }
//        } while (run);
//    }
//
//    private void mainAddUserPageRun() {
//        boolean run = true;
//        do {
//            Integer option = msgPres.mainAddUserPage();
//            if (option == 1) {
//                if (!addUser(msgPres.addUserPage())) {
//                    msgPres.addUserError();
//                } else {
//                    msgPres.addUserSuccessful();
//                    run = false;
//                }
//            } else {
//                run = false;
//            }
//        } while (run);
//    }

    /**
     * Gets the list of messages that the logged on user has received.
     *
     * @return a list of received messages.
     */
    public List<String> viewReceivedMessages() {
        return msgMan.getMessages(user.getUserInfoList().get(0));
    }

    /**
     * Gets the list of messages that the logged in user has archived.
     *
     * @return  a list of received messages
     */
    public List<String> viewArchivedMessages() {
        return msgMan.getArchivedMessages(getLoggedInUser());
    }

    /**
     * Messages the user inputted with the content inputted.
     *
     * @param username the username of the User that will be messaged.
     * @param content  the content of the message.
     */
    public void messageUser(String username, String content) {
        msgMan.message(user.getUserInfoList().get(0), username, content);
    }

    /**
     * From the list of received messages, reply to the message with the index inputted with the content inputted.
     *
     * @param number  the index of the message from the list of received messages.
     * @param content the content of the message.
     */
    public void replyMessage(int number, String content) {
        String message = viewReceivedMessages().get(number - 1);
        String sender = message.split("\\|")[0];
        msgMan.message(user.getUserInfoList().get(0), sender, content);
//        msgPres.successfulMessage();
        System.out.println("Success");
    }

    /**
     * Archives the message at the given index in the list of messages.
     *
     * @param index  the index of the message in the list
     */
    public void archiveMessage(int index) {
        msgMan.archive(getLoggedInUser(), viewReceivedMessages().get(index - 1));
    }

    /**
     * Add the user inputted to the logged on user's contact list.
     *
     * @param user2 the username of the user that will be added to the contact list.
     */
    public boolean addUser(String user2) {
        if (!user.getUserInfoList().get(0).equals(user2) && !user.getContactList().contains(user2) && user.getSignedUpUsers().contains(user2)) {
            user.addUserToContacts(user2);

            return true;
        }
        return false;
    }

    /**
     * Remove the user from the logged on user's contact list.
     *
     * @param user2 the username of the user that will be added to the contact list.
     */
    public void removeUser(String user2) {
        user.removeUserFromContacts(user2);
    }

    /**
     * Gets the contact list of the user.
     *
     * @return the contact list
     */
    public List<String> getContacts() {
        return user.getContactList();
    }

    /**
     * Gets the list of messages that the user had with the user inputted.
     *
     * @param username the username of the sender of the messages
     */
    public List<String> viewMessages(String username) {
        List<String> lstofmessages = viewReceivedMessages();
        List<String> msgsfromsender = new ArrayList<>();
        for (String msg : lstofmessages) {
            String[] msgarray = msg.split("\\|");
            if (msgarray[0].equals(username)) {
                msgsfromsender.add(msg);
            }
        }
        return msgsfromsender;
    }

    private String getLoggedInUser() {
        return user.getUserInfoList().get(0);
    }

}