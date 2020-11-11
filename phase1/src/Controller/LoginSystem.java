package Controller;

import Entity.Attendee;
import Entity.Organizer;
import Entity.User;
import Gateway.FileGateway;
import Gateway.IGateway;
import Presenter.LogInSignUpPresenter;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Class which manages the Logging in and Signing up of a User
 */

public class LoginSystem {
    IGateway g = new FileGateway("phase1/src/Controller/LogInInformation.txt");
    UserManager userManager = new UserManager(g.read());
    EventManager eventMan = new EventManager();
    MessageManager messageMan = new MessageManager();
    MessengerSystem msgSys = new MessengerSystem(userManager, messageMan);
    EventManagementSystem eventSys = new EventManagementSystem(userManager, eventMan, messageMan);
    LogInSignUpPresenter lp = new LogInSignUpPresenter();


    //LoginSystem Constructor
    public LoginSystem() {
        welcome();
    }


    /**
     * Calls the appropriate menus depending on the user input.
     *
     * @param type represents the tpe of user.
     */
    public void MainPage(String type) {
        int answer;
        do {
            answer = lp.menu();
            if (answer == 1) {
                msgSys.run();
            } else if (answer == 2) {
                if (type.equals("A")) {
                    eventSys.eventMenuAttendee();
                } else if (type.equals("O")) {
                    eventSys.eventMenuOrganizer();
                }

            } else if (answer == 3) {
                signOut();
            }
        } while (answer != 3);
    }

    /**
     * Manages the initial page that the user sees/
     */
    public void welcome() {
        int answer = lp.wel();
        if (answer == 1) {
            signUp();
        }
        if (answer == 2) {
            String type = logIn();
            MainPage(type);
        }
    }

    /**
     * Signs out the user
     */
    public void signOut() {
        lp.print("Goodbye.");
        System.exit(0);
    }

    /**
     * Logs in the user
     *
     * @return String representing the type of user.
     */
    public String logIn() {
        boolean incorrect = true;
        do {
            System.out.println("enter a username");
            String username = lp.readLine();
            System.out.println("enter a password");
            String password = lp.readLine();
            if (exists(username, password)) {
                userManager.logInUser(username);
                lp.print("Log in successful. Welcome " + username);
                incorrect = false;
                return userManager.getUserInfoList().get(2);
            }
//        String username = askUser("Enter a username", "Username does not exist",
//                userInput -> exists(userInput, null));
//
//        //check password
//        askUser("Enter a password", "Incorrect password",
//                userInput -> exists(username, userInput));
        } while (incorrect);
        return null;
    }

    //helper method
    private String askUser(String prompt, String errorMessage,
                           Function<String, Boolean> validationFunction) {
        boolean keepAsking = true;
        String userInput;
        do {
            lp.print(prompt);
            userInput = lp.readLine();
            if (!validationFunction.apply(userInput)) {
                lp.print(errorMessage);
            } else {
                keepAsking = false;
            }
        }
        while (keepAsking);
        return userInput;
    }

    /**
     * Signs up a new user
     */
    public void signUp() {
        String response = askUser("Are you an (1) attendee or an (2) organizer?", "Incorrect answer",
                userInput -> userInput.equals("1") || userInput.equals("2"));
        if (response.equals("1")) {
            signUpAttendee("A");
        } else {
            signUpOrganizer("O");
        }
        lp.print("You have successfully signed up.");
        lp.print("Continue to Log In.");
    }

    /**
     * Signs up a user with particular type Organizer
     * @param userType represents the type of user
     */
    private void signUpOrganizer(String userType) {
        askUser("Enter your organizer code.", "Invalid code.",
                userInput -> userInput.equals("f9h2q6"));

        signUpAttendee(userType);
    }

    /**
     * Signs up a user with particular type Attendee
     * @param userType represents the type of user
     */
    private void signUpAttendee(String userType) {
        String username = askUser("Enter a username", "Username already exists",
                userInput -> !exists(userInput, null));

        lp.print("Enter a password.");
        String password = lp.readLine();
        List<String> userInfo = new ArrayList<>();
        userInfo.add(username);
        userInfo.add(password);
        userInfo.add(userType);
        userInfo.add("\n");
        g.append(userInfo);
    }

    //helper method
    private boolean exists(String username, String password) {
        ArrayList<List<String>> textFile = g.read();
        for (List<String> line : textFile) {
            if (line.get(0).equals(username) && line.get(1).equals(password)) {
                return true;
            }
        }
        return false;
    }

    //helper method
    private User getUser(String username) {
        g.read();
        while (g.hasNext()) {
            List<String> userStrings = g.next();
            if (userStrings.get(0).equals(username)) {
                User user;
                if (userStrings.get(2).equals("O"))
                {
                    user = new Organizer(userStrings.get(0), userStrings.get(1));
                }
                else
                {
                    user = new Attendee(userStrings.get(0), userStrings.get(1));
                }
                return user;
            }
        }
        return null;
    }
}