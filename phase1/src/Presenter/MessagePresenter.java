package Presenter;

import Controller.MessengerSystem;
import Gateway.FileGateway;
import Gateway.IGateway;
import UseCase.UserManager;
import java.util.Scanner;


public class MessagePresenter {
    Scanner myObj = new Scanner(System.in);
    private UserManager user;
    private MessengerSystem messenger;
    IGateway g = new FileGateway("phase1/src/Controller/LogInInformation.txt");

    public MessagePresenter(UserManager user, MessengerSystem messenger){
        this.user = user;
        this.messenger = messenger;
    }

    public void mainPage(){
        boolean incorrectOption = false;
        do {
            System.out.println("========== Messages Menu ========== \n" +
                    "1. Inbox \n" +
                    "2. Contact List \n" +
                    "3. Add user to Contact List\n ");
            System.out.println("Input the number of the option you wish to choose:");
            int answer = myObj.nextInt();
            if (answer == 1){
                mainInboxPage();
            } else if (answer == 2){
                mainContactPage();
            } else if (answer == 3){
                messenger.addUser();
            } else {
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }
        } while (incorrectOption);
    }

    public void mainInboxPage(){
        boolean incorrectOption = false;
        do {
            System.out.println("========== Inbox Menu ========== \n" +
                    "1. View Received Messages \n" +
                    "2. Back to Previous Menu");
            System.out.println("Input the number of the option you wish to choose:");
            int answer = myObj.nextInt();
            if (answer == 1){
                viewInbox();
            }  else if (answer == 2){
                mainPage();
            } else {
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }
        } while (incorrectOption);
    }

    public void viewInbox(){
        String[][] messageBySenderTable = messenger.viewReceivedMessages();
        boolean invalidInput = false;
        do {
            System.out.println("Would you like to: \n 1. Reply \n 2. Go back to previous menu.");
            int response = myObj.nextInt();
            if (response == 1){
                messenger.replyMessage(messageBySenderTable);
            } else if (response == 2){
                mainInboxPage();
            } else{
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                invalidInput = true;
            }
        }
        while (invalidInput);
    }


    public void mainContactPage(){
        boolean incorrectOption = false;
        messenger.formatContactList();
        do {
            System.out.println("========== CONTACT LIST ========== \n" +
                                "1. Select a Contact \n" +
                                "2. Return to Previous Menu");
            int answer = myObj.nextInt();
            if (answer == 1){
                boolean validContact = false;
                String selectedContact;
                int contactNumber;
                do {
                    System.out.println("Enter the number of the contact you would like to select.");
                    contactNumber = myObj.nextInt();

                    if (contactNumber > user.getContactList().size() || contactNumber == 0){
                        System.out.println("Please entre a valid number.");
                        validContact = true;
                    }

                }while(validContact);

                selectedContact = user.getContactList().get(contactNumber - 1);
                selectedContactPage(selectedContact);

            } else if (answer == 2){
                mainPage();
            } else {
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }
        } while (incorrectOption);
    }

    public void selectedContactPage(String selectedContact){
        boolean incorrectOption = false;
        do{
            System.out.println("Would you like to: \n" +
                    "1. Send Message to Selected Contact\n" +
                    "2. Remove User from Contact List \n" +
                    "3. View Messages from a Selected Contact \n" +
                    "4. Return to Previous Menu");
            System.out.println("Input the number of the option you wish to choose:");
            int answer = myObj.nextInt();
            if (answer == 1){
                messenger.messageAttendee();
            } else if (answer == 2){
                messenger.removeUser();
            } else if (answer == 3){
                messenger.getSpecificMessages(selectedContact);
            } else if (answer == 4){
                mainContactPage();
            } else {
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }
        }while(incorrectOption);
    }

}
