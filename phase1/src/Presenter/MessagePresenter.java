package Presenter;

import Gateway.FileGateway;
import Gateway.IGateway;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MessagePresenter {
    private UserManager user;
    IGateway g = new FileGateway("phase1/src/Controller/LogInInformation.txt");

    public MessagePresenter(UserManager user){
        this.user = user;
    }
    public void mainPage(){

    }

    public void mainContactPage(){
        Scanner myObj = new Scanner(System.in);
        boolean incorrectOption = false;
        do {
            System.out.println("========== CONTACT LIST ========== \n" +
                    "1. View Contact List \n" +
                    "2. Add User to Contact List \n" +
                    "3. Remove User from Contact List \n" +
                    "4. View Messages from a Specific Sender");
            System.out.println("Input the number of the option you wish to choose:");
            String answer = myObj.nextLine();
            if (answer.equals("1")){
                incorrectOption = false;
                formatContactList();
            } else if (answer.equals("2")){
                incorrectOption = false;
                addUser();
            } else if (answer.equals("3")){
                incorrectOption = false;
                removeUser();
            } else if (answer.equals("4")){
                incorrectOption = false;
                viewMessages();
            } else {
                incorrectOption = true;
            }


        } while (incorrectOption);
    }

    private List<String> sortByMostRecent(List<String> messages){

    }

    public void formatContactList(){
        List<String> contacts = user.getContactList();
        // List<String> sortedContacts = new ArrayList<>();
        // Alphabetical sort
        int i = 0;
        for (String s : contacts){
            i++;
            System.out.println(i + ". " + s + "\n");
        }
    }

    public void addUser(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Input the username of the user you wish to add:");
        String user2 = myObj.nextLine();
        boolean invalidInput = false;
        do{
            if (!user.getSignedUpUsers().contains(user2)){
                invalidInput = true;
            } else {
                invalidInput = false;
                user.addUserToContacts(user2);
            }

            System.out.println("This user doesn't exist! Please enter a valid user:");
        } while(invalidInput);
    }

    public void removeUser(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Input the username of the user you wish to remove:");
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

    private void formatMessages(String sender){
        // Q: How do we get the username of the person logged in? from UserManager or LoginSystem?
        List <String> messages = user.getMessages(USERNAME, sender);
        List <String> sorted = sortByMostRecent(messages);
        for (String msg : sorted){
            System.out.println(msg);
        }

    }
    public void viewMessages(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Please input the user you wish to view messages from:");
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
            }

        } while(invalidInput);
    }
}
