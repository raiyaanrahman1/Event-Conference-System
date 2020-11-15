package Controller;

import Entity.User;
import Gateway.FileGateway;
import Gateway.IGateway;
import Gateway.IGateway2;
import Gateway.MessageFileGateway;
import Presenter.EventPresenter;
import Presenter.LogInSignUpPresenter;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.UserManager;

import java.util.function.Function;

/**
 * Class which manages the Logging in and Signing up of a User
 */

public class LoginSystem {
    IGateway g = new FileGateway("phase1/src/Controller/LogInInformation.txt");
    IGateway2 g2 = new MessageFileGateway("phase1/src/Controller/MessageListInformation.txt");
    IGateway2 g3 = new MessageFileGateway("phase1/src/Controller/contactListInfo.txt");
    IGateway2 g4 = new MessageFileGateway("phase1/src/Controller/eventListInfo.txt");
    UserManager userManager = new UserManager(g, g3);
    EventManager eventMan = new EventManager(g4);
    MessageManager messageMan = new MessageManager(g2);
    MessengerSystem msgSys = new MessengerSystem(userManager, messageMan);
    EventManagementSystem eventSys = new EventManagementSystem(userManager, eventMan, messageMan);
    LogInSignUpPresenter lp = new LogInSignUpPresenter();
    EventPresenter ep = new EventPresenter(eventSys, userManager, eventMan);


    //LoginSystem Constructor
    public LoginSystem() {
        welcome();
    }


    /**
     * Calls the appropriate menus depending on the user input.
     *
     * @param userType represents the type of user.
     */
    public void MainPage(String userType) {
        int answer;
        do {
            answer = lp.menu();
            if (answer == 1) {
                msgSys.menus();
            } else if (answer == 2) {
                //this is changed
                ep.mainEventPage();
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
            answer = 2;
        }
        if (answer == 2) {
            MainPage(LogIn());
        }
    }

    /**
     * Signs out the user
     */
    public void signOut() {
        lp.print("Goodbye.");
        messageMan.storeMessages(g2);
        userManager.storeContacts(g3);
        eventMan.storeEvents(g4);
        System.exit(0);
    }

    /**
     * Logs in the user
     *
     * @return String representing the type of user.
     */
    public String LogIn() {
        do {
            System.out.println("Enter a username.");
            String username = lp.readLine();
            System.out.println("Enter a password.");
            String password = lp.readLine();
            User user = userManager.getUserByUsername(username);
            if (user != null && userManager.isPasswordCorrect(username, password)) {
                userManager.logInUser(username);
                lp.print("Log in successful. Welcome " + username);
                return user.getUserType();
            }
            else {
                lp.print("Invalid user name or password. Please try again.");
            }
        } while (true);
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
        String response = askUser("Are you an \n1. Attendee \n2. Organizer", "Incorrect answer",
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
                userInput -> userInput.equals("amongUs"));

        signUpAttendee(userType);
    }

    /**
     * Signs up a user with particular type Attendee
     * @param userType represents the type of user
     */
    private void signUpAttendee(String userType) {
        String username = askUser("Enter a username", "Username already exists",
                userInput -> userManager.getUserByUsername(userInput) == null);

        lp.print("Enter a password.");
        String password = lp.readLine();
        userManager.CreateUser(username, password, userType);
    }
}