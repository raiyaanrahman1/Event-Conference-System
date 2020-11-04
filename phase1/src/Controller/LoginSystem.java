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

    //LoginSystem Constructor
    public LoginSystem(){
        welcome();
    }

    //public void signOut(){}

    public void logIn(){
        System.out.println("Enter username");
        Scanner myObj = new Scanner(System.in);
        String username = myObj.nextLine();  // Read user input
        while (UserManager.getUserByUsername(username) == null) {
            System.out.println("Username does not exist.");
            logIn();
        }
        System.out.println("Enter a password.");
        String password = myObj.nextLine();
        User userObject = UserManager.getUserByUsername(username);
        //wait for use case
        if (userobject.getPassword().equals(password)) {
            UserManager usermanager = new UserManager(userobject);
        }
    }

    public void signUp(){
        System.out.println("Are you an attendee or an organizer?");
        Scanner myObj = new Scanner(System.in);
        String response = myObj.nextLine();
        if (response.equals("attendee")) {
            System.out.println("Enter a username");
            String username1 = myObj.nextLine();
            while (!g.read().contains(username1)) {
                signUp();
            }
            System.out.println("Enter a password.");
            String password = myObj.nextLine();
            g.write(username1 + " " + password + "A");
        }
        else if (response.equals("organizer")) {
            System.out.println("Enter your organizer code.");
            String code = myObj.nextLine();
            while (!code.equals("f9h2q6")) {
                System.out.println("Invalid code.");
                signUp();
            }
            System.out.println("Enter a username");
            String username = myObj.nextLine();
            while (g.read().contains(username)) {
                System.out.println("Username is already taken.");
                signUp();
            }
            System.out.println("Enter a password.");
            String password = myObj.nextLine();
        }
    }
}