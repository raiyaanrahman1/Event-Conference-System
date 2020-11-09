package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import UseCase.UserManager;

public class MessengerSystem {

    private UserManager user;

    public void messageAttendee() {
        Scanner myObj = new Scanner(System.in);
        boolean incorrectOption = false;
        do {
            System.out.println("Enter the username of the person you want to message.");
            System.out.print(user.getContactList());
            String username = myObj.nextLine();
            if (user.getContactList().contains(username)){
                incorrectOption = false;
                System.out.println("Enter the content of the message.");
                String content = myObj.nextLine();
                user.message(user.getUserInfoList().get(0), username, content);
            }
            else {
                incorrectOption = true;
            }
        } while (incorrectOption);

    }

    public void getSpecificMessages(String username) {
        // List<String> msgs = user.getMessages(user.getUserInfo().get(0), username)
        // msgs.reverse()
        // print msgs
    }

    public String[][] viewReceivedMessages() {
        String[][] messageBySenderTable = user.getReceivedMessageListWithSender();
        System.out.println("Inbox \n");
        for(int i = 0; i < messageBySenderTable.length; i++){
            String sender = messageBySenderTable[i][0];
            String content = messageBySenderTable[i][1];
            System.out.println(i+1 + ". From " + sender + ": " + content);
        }
        return messageBySenderTable;
    }

    public void replyMessage(String[][] messageBySenderTable) {
        Scanner myObj = new Scanner(System.in);
        System.out.print("Select the message you want to reply to by entering its corresponding number.");
        int response = myObj.nextInt();
        String sender = messageBySenderTable[response - 1][0];
        System.out.print("Enter the content of your message.");
        String content = myObj.nextLine();
        user.message(user.getUserInfoList().get(0), sender, content);
    }

    public void formatContactList(){
        List<String> contacts = user.getContactList();
        // Alphabetical sort
        contacts.sort(null);
        int i = 0;
        for (String s : contacts){
            i++;
            System.out.println(i + ". " + s + "\n");
        }
    }

    public void addUser(){
        Scanner myObj = new Scanner(System.in);
        System.out.print("Input the username of the user you wish to add:");
        String user2 = myObj.nextLine();
        boolean invalidInput = true;
        do{
            if (!user.getSignedUpUsers().contains(user2)){
                System.out.println("This user doesn't exist! Please enter a valid user:");
                invalidInput = true;
            } else {
                user.addUserToContacts(user2);
                invalidInput = false;
            }
        } while(invalidInput);
    }

    public void removeUser(){
        Scanner myObj = new Scanner(System.in);
        System.out.print("Input the username of the user you wish to remove:");
        String user2 = myObj.nextLine();
        boolean invalidInput = false;
        do{
            if (!user.getSignedUpUsers().contains(user2)){
                invalidInput = true;
                System.out.println("This user doesn't exist! Please enter a valid user:");
            } else if (!user.getContactList().contains(user2)){
                invalidInput = true;
                System.out.println("This user is not in your contacts! Please enter a valid user:");
            } else {
                invalidInput = false;
                user.removeUserFromContacts(user2);
            }
        } while(invalidInput);
    }

    public void viewMessages(){
        Scanner myObj = new Scanner(System.in);
        System.out.print("Please input the user you wish to view messages from:");
        String sender = myObj.nextLine();
        boolean invalidInput = false;
        do{
            if (!user.getSignedUpUsers().contains(sender)){
                invalidInput = true;
                System.out.println("This user doesn't exist! Please enter a valid user:");
            } else if (!user.getContactList().contains(sender)){
                invalidInput = true;
                System.out.println("This user is not in your contacts! Please enter a valid user:");
            } else {
                invalidInput = false;
                formatMessages(sender);
                getSpecificMessages();
            }
        } while(invalidInput);
    }

    public void organizerBroadcast() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of event would you like to broadcast a message to?");
        String eventid = myObj.nextLine();
        boolean invalidInput = false;
        do{
            if (!user.getOrganizedEvents().contains(Integer.parseInt(eventid))) {
                invalidInput = true;
                System.out.println("This event has not yet been organized. Please enter a valid event id!");
            }
            else {
                invalidInput = false;
                System.out.println("What is the content of your message?");
                String content = myObj.nextLine();
                if (user.broadcast(user.getUserInfoList().get(0), Integer.parseInt(eventid), content) {
                    System.out.println("You have successfully broadcasted your message.");
                }
            }
        }
        while (invalidInput);
    }

    public void speakerBroadcast() {
        Scanner myObj = new Scanner(System.in);
        List<Integer> listofeventids = new ArrayList<>();
        boolean addevents = true;
        while (addevents) {
            boolean valideventid = false;
            while(!valideventid) {
                System.out.println("What is the id of the event you would like to broadcast your message to?");
                int eventid = myObj.nextInt();
                if(user.getTalks().contains(eventid)) {
                    listofeventids.add(eventid);
                    valideventid = true;
                }
                else {
                    System.out.println("Please input a valid event id.");
                }
            }
            boolean validinput = false;
            while(!validinput) {
                System.out.println("Would you like to broadcast your message to another event? Enter 0 for No or 1 for Yes");
                int response = myObj.nextInt();
                if (response == 0) {
                    addevents = false;
                    validinput = true;
                }
                else if (response != 1) {
                    System.out.println("Please enter a valid number.");
                }
                else {
                    validinput = true;
                }
            }
        }
        System.out.println("What is the content of the message you would like to broadcast?");
        String content = myObj.nextLine();
        for(int eventid : listofeventids) {
            user.broadcast(user.getUserInfoList().get(0), eventid, content);
        }
    }

    public void organizerToSpeakersBroadcast() {
        // get a list of all the speakers in the program
        // user.specialBroadcast()
    }

    private void formatMessages(String sender){
        List <String> messages = user.getMessages(user.getUserInfoList().get(0), sender);
        List <String> sorted = getSortedMessages(messages);
        for (String msg : sorted){
            System.out.println(msg);
        }
    }

    private void getSortedMessages() {

    }

}
