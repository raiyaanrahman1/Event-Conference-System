package Presenter;

import Controller.MessengerSystem;
import Gateway.FileGateway;
import Gateway.IGateway;
import UseCase.UserManager;
import Controller.LoginSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MessagePresenter {
    private UserManager user;
    private MessengerSystem messenger = new MessengerSystem();
  //  private LoginSystem loginSystem = new LoginSystem();
    IGateway g = new FileGateway("phase1/src/Controller/LogInInformation.txt");

    public MessagePresenter(UserManager user, MessengerSystem messenger){
        this.user = user;
        this.messenger = messenger;
    }
    public void mainPage(){
        Scanner myObj = new Scanner(System.in);
        boolean incorrectOption = false;
        do {
            System.out.println("========== Messages Menu ========== \n" +
                    "1. Inbox \n" +
                    "2. Contact List \n");
            System.out.print("Input the number of the option you wish to choose:");
            String answer = myObj.nextLine();
            if (answer.equals("1")){
                incorrectOption = false;
                mainInboxPage();
            } else if (answer.equals("2")){
                incorrectOption = false;
                mainContactPage();
            } else {
                incorrectOption = true;
            }
        } while (incorrectOption);
    }

    public void mainInboxPage(){
        Scanner myObj = new Scanner(System.in);
        boolean incorrectOption = false;
        do {
            System.out.println("========== Inbox Menu ========== \n" +
                    "1. View Received Messages \n" +
                    "2. Send Message to a Specific User \n" +
                    "3. Back to Previous Menu");
            System.out.print("Input the number of the option you wish to choose:");
            String answer = myObj.nextLine();
            if (answer.equals("1")){
                incorrectOption = false;
                String[][] messageBySenderTable = messenger.viewReceivedMessages();
                System.out.println("Would you like to: \n 1. Reply \n 2. Go back to previous menu.");
                String response = myObj.nextLine();
                boolean invalidInput = false;
                do {
                    if (response.equals("1")){
                        messenger.replyMessage(messageBySenderTable);
                    } else if (response.equals("2")){
                        messenger.messageAttendee();
                    } else {
                        invalidInput = true;
                    }
                }
                while (invalidInput);
            } else if (answer.equals("2")){
                incorrectOption = false;
                messenger.messageAttendee();
            } else if (answer.equals("3")){
                incorrectOption = false;
                mainPage();
            } else {
                incorrectOption = true;
            }
        } while (incorrectOption);
    }

    public void mainContactPage(){
        Scanner myObj = new Scanner(System.in);
        boolean incorrectOption = false;
        do {
            System.out.println("========== CONTACT LIST ========== \n" +
                    "1. View Contact List \n" +
                    "2. Add User to Contact List \n" +
                    "3. Remove User from Contact List \n" +
                    "4. View Messages from a Specific Sender \n");
            System.out.print("Input the number of the option you wish to choose:");
            String answer = myObj.nextLine();
            if (answer.equals("1")){
                incorrectOption = false;
                messenger.formatContactList();
            } else if (answer.equals("2")){
                incorrectOption = false;
                messenger.addUser();
            } else if (answer.equals("3")){
                incorrectOption = false;
                messenger.removeUser();
            } else if (answer.equals("4")){
                incorrectOption = false;
                messenger.viewMessages();
            } else {
                incorrectOption = true;
            }

        } while (incorrectOption);
    }

}
