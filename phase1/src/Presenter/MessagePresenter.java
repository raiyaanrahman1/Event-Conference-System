package Presenter;

import java.util.List;
import java.util.Scanner;

public class MessagePresenter {
    Scanner myObj = new Scanner(System.in);


    public Integer mainPage(){
        boolean incorrectOption = false;
        int answer = 0;
        do {
            System.out.println("========== MESSAGES MENU ========== \n" +
                    "1. Inbox \n" +
                    "2. Contact List \n" +
                    "3. Add user to Contact List\n" +
                    "4. Go back to Main Menu");
            System.out.println("Input the number of the option you wish to choose:");
            String input = myObj.next();
            if(input.matches("[0-9]")){
                answer = Integer.parseInt(input);
                incorrectOption = false;
                if (answer != 1 && answer != 2 && answer != 3 && answer != 4){
                    System.out.println("You have entered an incorrect input. Please enter a valid input.");
                    incorrectOption = true;
                }
            } else{
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }

        } while (incorrectOption);
        return answer;
    }


    public Integer mainSpeakerPage(){
        boolean incorrectOption = false;
        int answer = 0;
        do {
            System.out.println("========== MESSAGES MENU ========== \n" +
                    "1. Inbox \n" +
                    "2. Go back to Main Menu");
            System.out.println("Input the number of the option you wish to choose:");
            String input = myObj.next();
            if(input.matches("[0-9]")){
                answer = Integer.parseInt(input);
                incorrectOption = false;
                if (answer != 1 && answer != 2){
                    System.out.println("You have entered an incorrect input. Please enter a valid input.");
                    incorrectOption = true;
                }
            } else {
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }
        } while (incorrectOption);
        return answer;
    }


    public void formatMessages(List<String> messageList){
        if (messageList.size() == 0){
            System.out.println("You have no messages yet!");
        }
        int i = 1;
        for(String msg: messageList){
            String[] msgarray = msg.split("\\|");
            System.out.println(i + ". From " + msgarray[0] + " at " + msgarray[1] + " - " + msgarray[2]);
            i++;
        }
        System.out.println("==================================");
    }

    public Integer mainInboxPage(List<String> messageList){
        boolean invalidInput = false;
        int response = 0;
        System.out.println("========== INBOX ==========");
        if (messageList.size() == 0) {
            System.out.println("You have no messages yet!");
            return -1;
        }
        else{
            formatMessages(messageList);
            do {
                System.out.println("Would you like to: \n " +
                        "1. Reply to a Message\n " +
                        "2. Go back to previous menu.");
                String input = myObj.next();
                if(input.matches("[0-9]")){
                    response = Integer.parseInt(input);
                    invalidInput = false;
                    if (response != 1 && response != 2) {
                        System.out.println("You have entered an incorrect input. Please enter a valid input.");
                        invalidInput = true;
                    }
                } else {
                    System.out.println("You have entered an incorrect input. Please enter a valid input.");
                    invalidInput = true;
                }
            } while (invalidInput);
            return response;
        }
    }

    public void successfulMessage() {
        System.out.println("You have successfuly sent your message.");
    }

    public Integer getSelectedMessageNumber(){
        System.out.println("Select the message you want to reply to by entering its corresponding number.");
        return myObj.nextInt();
    }


    public String getContent(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the content of your message.");
        return sc.nextLine();
    }


    public void formatContactList(List<String> contacts){
        // Alphabetical sort
        contacts.sort(null);
        int i = 0;
        for (String s : contacts){
            i++;
            System.out.println(i + ". " + s);
        }
    }

    public Integer mainContactPage(List<String> contacts){
        boolean incorrectOption = false;
        int answer = 0;
        System.out.print("========== CONTACT LIST ========== \n");
        if (contacts.size() == 0){
            System.out.println("You have no contacts yet!");
            return -1;
        }
        else {
            formatContactList(contacts);
            System.out.print("================================== \n");

            do {
                System.out.println("1. Select a Contact \n" +
                        "2. Return to Previous Menu");
                String input = myObj.next();
                if(input.matches("[0-9]")){
                    answer = Integer.parseInt(input);
                    incorrectOption = false;
                    if (answer != 1 && answer != 2) {
                        System.out.println("You have entered an incorrect input. Please enter a valid input.");
                        incorrectOption = true;
                    }
                } else {
                    System.out.println("You have entered an incorrect input. Please enter a valid input.");
                    incorrectOption = true;
                }
            } while (incorrectOption);
            return answer;
        }
    }

    public String selectFromContactList(List<String> contacts){
        boolean validContact = false;
        int contactNumber = 0;
        do {
            System.out.println("Enter the number of the contact you would like to select.");
            String input = myObj.next();
            if(input.matches("[0-9]")){
                contactNumber = Integer.parseInt(input);
                validContact = false;
                if (contactNumber > contacts.size() || contactNumber == 0){
                    System.out.println("Please enter a valid number.");
                    validContact = true;
                }
            } else {
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                validContact = true;
            }

        }while(validContact);
        return contacts.get(contactNumber - 1);
    }


    public Integer selectedContactPage(){
        boolean incorrectOption = false;
        int answer = 0;
        do{
            System.out.println("Would you like to: \n" +
                    "1. Send Message to Selected Contact\n" +
                    "2. Remove User from Contact List \n" +
                    "3. View Messages from a Selected Contact \n" +
                    "4. Return to Previous Menu");
            System.out.println("Input the number of the option you wish to choose:");
            String input = myObj.next();
            if(input.matches("[0-9]")){
                answer = Integer.parseInt(input);
                incorrectOption = false;
                if (answer != 1 && answer != 2 && answer != 3 && answer != 4) {
                    System.out.println("You have entered an incorrect input. Please enter a valid input.");
                    incorrectOption = true;
                }
            } else {
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }

        }while(incorrectOption);
        return answer;
    }

    public Integer mainAddUserPage(){
        boolean incorrectOption = false;
        int answer = 0;
        do {
            System.out.println("========== ADD USER ========== \n" +
                    "1. Add a user \n" +
                    "2. Return to Previous Menu");
            String input = myObj.next();
            if(input.matches("[0-9]")){
                answer = Integer.parseInt(input);
                incorrectOption = false;
                if (answer != 1 && answer != 2) {
                    System.out.println("You have entered an incorrect input. Please enter a valid input.");
                    incorrectOption = true;
                }
            } else {
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }
        } while (incorrectOption);
        return answer;
    }


    public String addUserPage(){
        System.out.println("Please input the username of the user you wish to add:");
        return myObj.next();
    }

    public void addUserSuccessful(){
        System.out.println("You have successfully added this user to your contact list.");
    }

    public void addUserError(){
        System.out.println("Invalid input. Please enter a valid username.");
    }

    public void removeUserSuccessful(){
        System.out.println("You have successfully removed this user from your contact list.");
    }

}
