package Controller;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import UseCase.UserManager;
import Controller.LoginSystem;

// Allows current user to message some other given user.
// Returns a list of message contents from a specific user and sends it to MessagePresenter
// Gives option to user to reply to some message.

// TODO
// How to reply to msgs: DESIGN CHOICE: HOW ARE WE GOING TO SEE THE MESSAGES THAT WERE SENT TO US? (INBOX?)
// Select a message you want to reply to:
// Send a message to the person that sent you that message.
// You can reply to anybody that messaged you.
// How to add friends (mutual or not) DESIGN CHOICE: MUTUAL
// Add a method that allows Speakers to DM any Attendee attending their event

public class MessengerSystem {

    private UserManager user;

    public void MessageUser() {

        // ask them who they want to send it to
        // check to see if this user is in their contact list
        // ask them what they want to send (the content of the message)
        // access the current date and time
        // call user.message();

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter the username you want to send a message to:");
        for (String ppl: user.getContactList()){ //want to show list of ppl the user can message
            System.out.println(ppl);
        }

        // add in the ability to send messages to speakers

        String receiver = myObj.nextLine();
        if (!user.getContactList().contains(receiver)) { //still need to keep this in case of user error
            System.out.println("You do not have this user in your contact list.");
            return;
        }
        System.out.println("Enter the content of your message:");
        String content = myObj.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        user.message(user.username, receiver, content, dateFormat, timeFormat);
        System.out.println("Message successfully sent.");
    }

    public void getSpecificMessages() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter a username to see the messages you have had with them.");
        for (String ppl: user.getContactList()){ //want to show list of ppl the user can message
            System.out.println(ppl);
        }
        String receiver = myObj.nextLine();
        if (!user.getContactList().contains(receiver)) {
            System.out.println("You do not have this user in your contact list.");
            return;
        }
        // if there is no messages with that user print "no messages with user"
        if (user.getMessages(user.username(), receiver) == null){
            System.out.println("You have no messages with this user.");
        }
        for (String msg : user.getMessages(user.username(), receiver)) {
            System.out.println(msg);
        }

    }

    public void broadcast() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of event would you like to broadcast a message to?");
        String eventid = myObj.nextLine();
        user.broadcast(user.username, eventid, );


    }

    public void speakerMessage() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the event id?");
        for (Integer eventid : System.out.println(user.getEventsBySpeaker())) {
            System.out.println(eventid);
        }
        String eventid = myObj.nextLine();
        if (!user.getEventsBySpeaker().contains(eventid)) {
            System.out.println("Please enter an event id that you will be speaking at.");
        }
        System.out.println("What is the username of the person you want to message?");
        for (String username : user.getUsersInEvent(Integer.parseInt(eventid))) {
            System.out.println(username);
        }
        String username = myObj.nextLine();
        if (!user.getUsersInEvent(Integer.parseInt(eventid)).contains(username)) {
            System.out.println("Please enter the username of someone attending your event.");
        }
        System.out.println("Enter the content of the message you want to send.");
        String content =


    }


}
