package Controller;
import Entity.User;
import Gateway.FileGateway;
import Gateway.IGateway;
import Presenter.LogInSignUpPresenter;
import UseCase.UserManager;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class

public class LoginSystem {
    // UserManager user;
    MessengerSystem msgSys = new MessengerSystem();
    EventManagementSystem eventSys = new EventManagementSystem();
    IGateway g = new FileGateway("phase1/src/Controller/LogInInformation.txt");

    //LoginSystem Constructor
    public LoginSystem(){
        welcome();
    }

    public void MainPage() {
        int answer;
        do {
            LogInSignUpPresenter loginPresenter = new LogInSignUpPresenter();

            answer = loginPresenter.menu();
            if (answer == 1) {
                //call message system
                msgSys.messageMenu();
            } else if (answer == 2) {
                eventSys.eventMenu();
            } else if (answer == 3) {
                // log out
                signOut();
            } else {
                answer = loginPresenter.menu();
            }
        }while (answer != 1 && answer != 2 && answer != 3) ;
    }



    public void welcome() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Would you like to sign up or log in?");
        String answer = myObj.nextLine();
        if (answer.equals("log in")) {
            logIn();
            MainPage();
        }
        if (answer.equals("sign up")) {
            signUp();
        }
    }

    public void signOut(){
        System.out.println("Goodbye.");
        System.exit(0);
    }



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
        boolean incorrectPassword = true;
        do {
            System.out.println("Enter a password");
            String password = myObj.nextLine();
            if (!exists(g, password)) {
                System.out.println("Incorrect password.");
            } else {
                incorrectPassword = false;
            }
        }
        while (incorrectPassword);
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
        String username1;
        boolean incorrectCode = true;
        do {
            System.out.println("Enter your organizer code.");
            String code = myObj.nextLine();
            if (!code.equals("f9h2q6")) {
                System.out.println("Invalid code.");
            } else {
                incorrectCode = false;
            }
        }
        while (incorrectCode);

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
        List<String> userInfo = new ArrayList<>();
        userInfo.add(username1);
        userInfo.add(password);
        userInfo.add("O");
        g.append(userInfo);
    }

    private void signUpAttendee() {
        Scanner myObj = new Scanner(System.in);
        String username1;
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
        List<String> userInfo = new ArrayList<>();
        userInfo.add(username1);
        userInfo.add(password);
        userInfo.add("A");
        g.append(userInfo);
    }

    public boolean exists(IGateway gt, String username){
        g.read();
        while (gt.hasNext()) {
            List<String> actual = gt.next();
            if(actual.contains(username)){
                return true;
            }
        }
        return false;
    }
}