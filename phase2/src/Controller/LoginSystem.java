package Controller;

import Entity.User;
import Gateway.LoginFileGateway;
import Gateway.IGateway;
import Gateway.IGateway2;
import Gateway.InfoFileGateway;
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
    IGateway loginFileGateway = new LoginFileGateway("phase2/src/Controller/LogInInformation.txt");
    IGateway2 messageListInformationGateway = new InfoFileGateway("phase2/src/Controller/MessageListInformation.txt");
    IGateway2 contactListGateway = new InfoFileGateway("phase2/src/Controller/contactListInfo.txt");
    IGateway2 eventListGateway = new InfoFileGateway("phase2/src/Controller/eventListInfo.txt");
    UserManager userManager = new UserManager(loginFileGateway, contactListGateway);
    EventManager eventMan = new EventManager(eventListGateway);
    MessageManager messageMan = new MessageManager(messageListInformationGateway);
    MessengerSystem msgSys = new MessengerSystem(userManager, messageMan);
    CreateSpeakerController speakerController = new CreateSpeakerController(userManager, loginFileGateway, contactListGateway);
    EventManagementSystem eventSys = new EventManagementSystem(userManager, eventMan, messageMan, speakerController);
    LogInSignUpPresenter logInSignUpPresenter = new LogInSignUpPresenter();
    EventPresenter eventPresenter = new EventPresenter(eventSys, userManager, eventMan);


    /**
     * Creates a new LoginSystem instance.
     */
    public LoginSystem() {
        welcome();
    }


    /**
     * Calls the appropriate menus depending on the user input.
     *
     */
    public void mainPage() {
        int answer;
        do {
            answer = logInSignUpPresenter.menu();
            if (answer == 1) {
                msgSys.menus();
            } else if (answer == 2) {
                eventPresenter.mainEventPage();
            } else if (answer == 3) {
                signOut();
            }
        } while (answer != 3);
    }

    /**
     * Manages the initial page that the user sees/
     */
    public void welcome() {
        int answer;
        do {
            answer = logInSignUpPresenter.wel();
            if (answer == 1) {
                signUp();
                answer = 2;
            }
            if (answer == 2) {
                 if (logIn()) {
                     mainPage();
                 }
            }
        } while(answer != 3);
    }

    /**
     * Signs out the user
     */
    public void signOut() {
        logInSignUpPresenter.print("Goodbye.");
        messageMan.storeMessages(messageListInformationGateway);
        userManager.storeContacts();
        eventMan.storeEvents(eventListGateway);
        System.exit(0);
    }

    /**
     * Logs in the user
     *
     * @return String representing the type of user.
     */
    public boolean logIn() {
        do {
            logInSignUpPresenter.print("Enter a username.");
            String username = logInSignUpPresenter.readLine();
            logInSignUpPresenter.print("Enter a password.");
            String password = logInSignUpPresenter.readLine();
            if (userManager.logInUser(username, password)) {
                logInSignUpPresenter.print("Log in successful. Welcome " + username);
                return true;
            }
            else {
                logInSignUpPresenter.print("Invalid user name or password. Please try again.");
                logInSignUpPresenter.print("Or type 'm' to go back to main menu or any other key to try again");
                if (logInSignUpPresenter.readLine().equals("m")) {
                    return false;
                }
            }

        } while (true);
    }

    private String askUser(String prompt, String errorMessage,
                           Function<String, Boolean> validationFunction) {
        boolean keepAsking = true;
        String userInput;
        do {
            logInSignUpPresenter.print(prompt);
            userInput = logInSignUpPresenter.readLine();
            if (!validationFunction.apply(userInput)) {
                logInSignUpPresenter.print(errorMessage);
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
        logInSignUpPresenter.print("You have successfully signed up.");
        logInSignUpPresenter.print("Continue to Log In.");
    }


    private void signUpOrganizer(String userType) {
        askUser("Enter your organizer code.", "Invalid code.",
                userInput -> userInput.equals("amongUs"));

        signUpAttendee(userType);
    }


    private void signUpAttendee(String userType) {
        String username = askUser("Enter a username.", "Username already exists",
                userInput -> userManager.getUserByUsername(userInput) == null);

        logInSignUpPresenter.print("Enter a password.");
        String password = logInSignUpPresenter.readLine();
        userManager.CreateUser(username, password, userType);
    }
}