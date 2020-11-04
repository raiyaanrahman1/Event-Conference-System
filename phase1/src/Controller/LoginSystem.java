package Controller;
import Entity.User;
import Gateway.FileGateway;
import Gateway.IGateway;
import UseCase.UserManager;
import java.util.Scanner;  // Import the Scanner class

public class LoginSystem {

    MessengerSystem msgSys = new MessengerSystem();
    EventManagementSystem eventSys = new EventManagementSystem();
    IGateway g = new FileGateway("");

    //LoginSystem Constructor
    public LoginSystem(){
        welcome();
    }

    public void welcome() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Would you like to sign up or log in?");
        String answer = myObj.nextLine();
        if (answer.equals("log in")) {
            logIn();
        }
        if (answer.equals("sign up")) {
            signUp();
        }
    }

    //public void signOut(){}

    public void logIn(){
        Scanner myObj = new Scanner(System.in);
        boolean invalidUsername = true;
        String username;
        do {
            System.out.println("Enter a username");
            username = myObj.nextLine();
            if (!exists(g, username)) {
                System.out.println("Username does not exists");
            } else {
                invalidUsername = false;
            }
        }
        while (invalidUsername);

        //check password
        boolean correctPassword = true;
        do {
            System.out.println("Enter a password");
            String password = myObj.nextLine();
            if (!exists(g, password)) {
                System.out.println("Incorrect password.");
            } else {
                correctPassword = false;
            }
        }
        while (correctPassword);
        UserManager usermanager = new UserManager(username);
        System.out.println("Log in successful. Welcome " + username);
    }


    public void signUp(){
        System.out.println("Are you an attendee or an organizer?");
        Scanner myObj = new Scanner(System.in);
        String response = myObj.nextLine();
        if (response.equals("attendee")) {
            signUpAttendee();
        }
        else if (response.equals("organizer")) {
            signUpOrganizer();
        }
        System.out.println("You have successfully signed up.");
        System.out.println("Continue to Log In.");
        logIn();
    }

    private void signUpOrganizer(){
        Scanner myObj = new Scanner(System.in);
        String username1 = "";
        boolean correctCode = true;
        do {
            System.out.println("Enter your organizer code.");
            String code = myObj.nextLine();
            if (!code.equals("f9h2q6")) {
                System.out.println("Invalid code.");
            } else {
                correctCode = false;
            }
        }
        while (!correctCode);

        boolean userExists = true;
        do {
            System.out.println("Enter a username");
            username1 = myObj.nextLine();
            if (exists(g, username1)) {
                System.out.println("Username already exists");
            } else {
                userExists = false;
            }
        }
        while (userExists);
        System.out.println("Enter a password.");
        String password = myObj.nextLine();
        g.append(username1 + " " + password + " O");
    }

    private void signUpAttendee() {
        Scanner myObj = new Scanner(System.in);
        String username1 = "";
        boolean userExists = true;
        do {
            System.out.println("Enter a username");
            username1 = myObj.nextLine();
            if (exists(g, username1)) {
                System.out.println("Username already exists");
            } else {
                userExists = false;
            }
        }
        while (userExists);
        System.out.println("Enter a password.");
        String password = myObj.nextLine();
        g.append(username1 + " " + password + " A");
    }

    public boolean exists(IGateway gt, String username){
        g.read();
        while (gt.hasNext()) {
            String actual = gt.next();
            if(actual.contains(username)){
                return true;
            }
        }
        return false;
    }
}