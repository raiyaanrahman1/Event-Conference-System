package Controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Gateway.FileGateway;
import Gateway.IGateway;
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
    IGateway g = new FileGateway("");

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
        String receiver = myObj.nextLine();
        if (!user.getContactList().contains(receiver)) { //still need to keep this in case of user error
            System.out.println("You do not have this user in your contact list.");
            return;
        }
        System.out.println("Enter the content of your message:");
        String content = myObj.nextLine();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        user.message(user.getUserInfoList().get(0), receiver, content, dateFormat.format(currentDate),
                timeFormat.format(currentDate));
        System.out.println("Message successfully sent.");
    }

    public void getSpecificMessages() {

        // ask the user for a username
        // if the user is not a valid user, i.e, no account has this username, print "username does not exist"
        // if the user does not have the receiver on his contact list, then there is still a possibility that the two
        // users had messages with each other, i.e., broadcast messages
        // if the user is valid, then if there are no messages with this user, print "you don't have any messages with this user"
        // if the user is valid and there are messages between them, print the messages in a line by line format

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter a username to see the messages you have had with them.");
        String receiver = myObj.nextLine();

        if (LoginSystem.exists(g, receiver)) { // shouldn't I be able to use this exists method from LoginSystem?
            if (user.getMessages(user.getUserInfoList().get(0), receiver) == null){
                System.out.println("You have no messages with this user.");
            }
            else {
                for (String msg : user.getMessages(user.getUserInfoList().get(0), receiver)) {
                    System.out.println(msg);
                }
            }
        }
        else {
            System.out.println("Username does not exist");
        }
    }

    public void organizerBroadcast() {

        // ask the user for the eventid they want to broadcast their message to
        // if the eventid is not valid, print "This event has not been organized. Please enter a valid event id."
        // if it is valid, ask for the content of the message
        // get the current date and time
        // call the broadcast method, if it returns true, print "You have successfully broadcasted your message."
        // otherwise, print "Broadcast failed. You do not have broadcasting rights."

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of event would you like to broadcast a message to?");
        String eventid = myObj.nextLine();
        if (!user.getOrganizedEvents.contains(eventid)) {
            System.out.println("This event has not been organized. Please enter a valid event id.");
            return;
        }
        System.out.println("What is the content of your message?");
        String content = myObj.nextLine();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        if (user.broadcast(user.getUserInfoList().get(0), Integer.parseInt(eventid), content,
                dateFormat.format(currentDate), timeFormat.format(currentDate))) {
            System.out.println("You have successfully broadcasted your message.");
        }
        System.out.println("Broadcast failed. You do not have broadcasting rights.");
    }

    public void speakerBroadcast() {

        // ask the user for the eventid they want to broadcast their message to
        // if the is NOT in the list of events that this speaker is going to be speaking at, then print "Please enter
        // an event id that you will be speaking at."
        // if it is valid, ask for the content of the message
        // get the current date and time
        // call the broadcast method, if it returns true, print "You have successfully broadcasted your message."
        // otherwise, print "Broadcast failed. You do not have broadcasting rights."

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of event would you like to broadcast a message to?");
        String eventid = myObj.nextLine();
        if (!user.getEventsBySpeaker(user.getUserInfoList().get(0)).contains(Integer.parseInt(eventid))) {
            System.out.println("Please enter an event id that you will be speaking at.");
            return;
        }
        System.out.println("What is the content of your message?");
        String content = myObj.nextLine();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        if (user.broadcast(user.getUserInfoList().get(0), Integer.parseInt(eventid), content,
                dateFormat.format(currentDate), timeFormat.format(currentDate))) {
            System.out.println("You have successfully broadcasted your message.");
        }
        System.out.println("Broadcast failed. You do not have broadcasting rights.");
    }

    public void speakerMessage() {

        // ask the user for the event id
        // show the user the possible event ids they can choose from
        // if the user picks an eventid that is not from these options, print "Please enter an event id that you will
        // be speaking at."
        // if the user picks a valid eventid, then ask the user for the content of the message they want to send
        // get the current date and time
        // if the broadcast method returns true, print "You have successfully broadcasted your message."
        // if the broadcast method returns false, print "Broadcast failed. You do not have broadcasting rights."

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the event id?");
        for (Integer eventid : user.getEventsBySpeaker(user.getUserInfoList().get(0))) {
            System.out.println(eventid);
        }
        String eventid = myObj.nextLine();
        if (!user.getEventsBySpeaker(user.getUserInfoList().get(0)).contains(Integer.parseInt(eventid))) {
            System.out.println("Please enter an event id that you will be speaking at.");
        }
        System.out.println("What is the username of the person you want to message?");
        for (String username : user.getUsersInEvent(Integer.parseInt(eventid))) {
            System.out.println(username);
        }
        String receiver = myObj.nextLine();
        if (!user.getUsersInEvent(Integer.parseInt(eventid)).contains(receiver)) {
            System.out.println("Please enter the username of someone attending your event.");
        }
        System.out.println("Enter the content of the message you want to send.");
        String content = myObj.nextLine();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        if (user.broadcast(user.getUserInfoList().get(0), Integer.parseInt(eventid), content,
                dateFormat.format(currentDate), timeFormat.format(currentDate))) {
            System.out.println("You have successfully broadcasted your message.");
        }
        System.out.println("Broadcast failed. You do not have broadcasting rights.");
    }

    public void messageMenu() {

    }

}
