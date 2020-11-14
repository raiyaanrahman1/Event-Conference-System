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
    private MessagePresenter msgPres = new MessagePresenter();

    /**
     * Creates a MessengerSystem and initializes its UserManager and MessageManager.
     *
     * @param user the UserManager that the MessengerSystem communicates with
     * @param msgMan the MessageManager that the MessengerSystem communicates with
     */
    public MessengerSystem(UserManager user, MessageManager msgMan) {
        this.user = user;
        this.msgMan = msgMan;
    }

    public void menus() {
        if(user.getUserInfoList().get(2).equals("S")){
            runSpeaker();
        }
        else{
            run();
        }
    }

    /**
     * The main run method
     */
    public void run() {
        boolean run = true;
        do {
            int option = msgPres.mainPage();
            if (option == 1) {
                int inboxOption = msgPres.mainInboxPage(viewReceivedMessages());
                if (inboxOption == 1) {
                    replyMessage(msgPres.getSelectedMessageNumber(), msgPres.getContent());
                }
            } else if (option == 2) {
                mainContactPageRun(msgPres.mainContactPage(getContacts()));
            } else if (option == 3) {
                mainAddUserPageRun(msgPres.mainAddUserPage());
            } else {
                run = false;
            }
        } while (run);
    }

    /**
     * The main run method for a Speaker user
     */
    public void runSpeaker() {
        boolean run = true;
        do {
            int option = msgPres.mainSpeakerPage();
            if (option == 1) {
                int inboxOption = msgPres.mainInboxPage(viewReceivedMessages());
                if (inboxOption == 1) {
                    replyMessage(msgPres.getSelectedMessageNumber(), msgPres.getContent());
                }
            } else {
                run = false;
            }
        } while (run);
    }

    /**
     * The run method for the mainContactPage.
     */
    private void mainContactPageRun(Integer contactOption){
        boolean run = true;
        do {
            if (contactOption == 1) {
                String selectedUser = msgPres.selectFromContactList(getContacts());
                mainSelectedContactPageRun(selectedUser, msgPres.selectedContactPage());
                contactOption = msgPres.mainContactPage(getContacts());
            } else {
                run = false;
            }
        } while (run);
    }

    /**
     * The run method for the mainSelectedContactPageRun.
     */
    private void mainSelectedContactPageRun(String selectedUser, Integer option) {
        boolean run = true;
        do {
            if (option == 1) {
                messageUser(selectedUser, msgPres.getContent());
                option = msgPres.selectedContactPage();
            } else if (option == 2) {
                removeUser(selectedUser);
                run = false;
            } else if (option == 3) {
                msgPres.formatMessages(viewMessages(selectedUser));
                option = msgPres.selectedContactPage();
            } else {
                run = false;
            }
        } while (run);
    }

    /**
     * The run method for the mainAddUserPageRun.
     *
     * @param option the number entered by the user
     */
    private void mainAddUserPageRun(Integer option){
        boolean run = true;
        do {
            if (option == 1) {
                if (!addUser(msgPres.addUserPage())) {
                    msgPres.addUserError();
                } else {
                    run = false;
                }
            } else {
                run = false;
            }
        } while (run);
    }

    /**
     * Gets the list of messages that the logged on user has received.
     *
     * @return a list of received messages.
     */
    public List<String> viewReceivedMessages() {
        return msgMan.getMessages(user.getUserInfoList().get(0));
    }

    /**
     * Messages the user inputted with the content inputted.
     *
     * @param username the username of the User that will be messaged.
     * @param content the content of the message.
     */
    public void messageUser(String username, String content) {
        msgMan.message(user.getUserInfoList().get(0), username, content);
    }

    /**
     * From the list of received messages, reply to the message with the index inputted with the content inputted.
     *
     * @param number the index of the message from the list of received messages.
     * @param content the content of the message.
     */
    public void replyMessage(int number, String content) {
        String message = viewReceivedMessages().get(number - 1);
        String sender = message.split("\\|")[0];
        msgMan.message(user.getUserInfoList().get(0), sender, content);
    }

    /**
     * Add the user inputted to the logged on user's contact list.
     *
     * @param user2 the username of the user that will be added to the contact list.
     */
    public boolean addUser(String user2){
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
    public void removeUser(String user2){
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
        // filter out the messages that were just sent by the sender
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


//    public void organizerBroadcast() {
//        Scanner myObj = new Scanner(System.in);
//        System.out.println("What is the id of event would you like to broadcast a message to?");
//        String eventid = myObj.nextLine();
//        boolean invalidInput = false;
//        do{
//            if (!user.getOrganizedEvents().contains(Integer.parseInt(eventid))) {
//                invalidInput = true;
//                System.out.println("This event has not yet been organized. Please enter a valid event id!");
//            }
//            else {
//                invalidInput = false;
//                System.out.println("What is the content of your message?");
//                String content = myObj.nextLine();
//                if (msgMan.broadcast(user.getUserInfoList().get(0), Integer.parseInt(eventid), content)) {
//                    System.out.println("You have successfully broadcasted your message.");
//                }
//            }
//        }
//        while (invalidInput);
//    }
//
//    public void speakerBroadcast() {
//        Scanner myObj = new Scanner(System.in);
//        List<Integer> listofeventids = new ArrayList<>();
//        boolean addevents = true;
//        while (addevents) {
//            boolean valideventid = false;
//            while(!valideventid) {
//                System.out.println("What is the id of the event you would like to broadcast your message to?");
//                int eventid = myObj.nextInt();
//                if(user.getTalks().contains(eventid)) {
//                    listofeventids.add(eventid);
//                    valideventid = true;
//                }
//                else {
//                    System.out.println("Please input a valid event id.");
//                }
//            }
//            boolean validinput = false;
//            while(!validinput) {
//                System.out.println("Would you like to broadcast your message to another event? Enter 0 for No or 1 for Yes");
//                int response = myObj.nextInt();
//                if (response == 0) {
//                    addevents = false;
//                    validinput = true;
//                }
//                else if (response != 1) {
//                    System.out.println("Please enter a valid number.");
//                }
//                else {
//                    validinput = true;
//                }
//            }
//        }
//        System.out.println("What is the content of the message you would like to broadcast?");
//        String content = myObj.nextLine();
//        for(int eventid : listofeventids) {
//            msgMan.broadcast(user.getUserInfoList().get(0), eventid, content);
//        }
//    }
//
//    public void organizerToSpeakersBroadcast() {
//        // get a list of all the speakers in the program
//        // user.specialBroadcast()
//    }


//        Scanner myObj = new Scanner(System.in);
//        boolean incorrectOption = false;
//        do {
//            System.out.println("Enter the username of the person you want to message.");
//            System.out.print(user.getContactList());
//            String username = myObj.nextLine();
//            if (user.getContactList().contains(username)){
//                incorrectOption = false;
//                System.out.println("Enter the content of the message.");
//                String content = myObj.nextLine();
//                msgMan.message(user.getUserInfoList().get(0), username, content);
//            }
//            else {
//                incorrectOption = true;
//            }
//        } while (incorrectOption);


//    public void getSpecificMessages(String sender) {
//         List<String> msgs = user.getMessages(sender)
//         msgs.reverse()
//         print msgs
//    }

//        int i = 0;
//        for (String msg : lstofreceivedmsgs) {
//            String[] msgarray = msg.split("|");
//            System.out.println(i + ". From " + msgarray[0] + " on " + msgarray[1] + " at " + msgarray[2] + " - " +
//                    msgarray[3]);

//        Scanner myObj = new Scanner(System.in);
//        System.out.print("Please input the user you wish to view messages from:");
//        String sender = myObj.nextLine();
//        boolean invalidInput;
//        do{
//            if (!user.getSignedUpUsers().contains(sender)){
//                invalidInput = true;
//                System.out.println("This user doesn't exist! Please enter a valid user:");
//            } else if (!user.getContactList().contains(sender)){
//                invalidInput = true;
//                System.out.println("This user is not in your contacts! Please enter a valid user:");
//            } else {
//                invalidInput = false;
//                formatMessages(sender);
//                getSpecificMessages();
//            }
//        } while(invalidInput);

}
