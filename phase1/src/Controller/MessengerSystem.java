package Controller;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class


import Entity.User;
import UseCase.MessageManager;
import UseCase.UserManager;

// Allows current user to message some other given user.
// Returns a list of message contents from a specific user and sends it to MessagePresenter
// Gives option to user to reply to some message.

public class MessengerSystem {

    private String user;

    public MessengerSystem(String username) {
        this.user = username;
    }

    public void MessageUser() {

        // ask them who they want to send it to
        // ask them what they want to send (the content of the message)
        // access the current date and time

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter a username you want to send a message to.");
        String username = myObj.nextLine();

    }

    public void getSpecificMessages() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter a username to see the messages you have had with them.");
        String username = myObj.nextLine();
        User userobject = UserManager.getUserByUsername(username);
        boolean notvaliduser = (userobject == null);
        if (notvaliduser) {
            System.out.println("This is not a valid user.");
            return;
        }
        List<String> listofmsgs = MessageManager.getMessages(userobject, user);
        for (String msg:listofmsgs) {
            System.out.println(msg);
        }

        // ask them who they want to get the messages from
        // if this user doesn't exist, tell them it doesn't exist
        // if this user is not in their contact list, tell them that you do not have this user in your contact list
        // otherwise, show them the messages by calling MessageManager.getMessages(sender, receiver)

    }


}
