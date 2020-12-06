package Controller;

import Entity.Attendee;
import Entity.Organizer;
import Gateway.IGateway;
import Gateway.IGateway2;
import Gateway.InfoFileGateway;
import Gateway.LoginFileGateway;
import Presenter.EventPresenter;
import Presenter.LogInSignUpPresenter;
import UseCase.EventManager;
import UseCase.MessageManager;
import UseCase.UserManager;

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
    CreateUserController userController = new CreateUserController(userManager);
    EventManagementSystem eventSys = new EventManagementSystem(userManager, eventMan, messageMan, userController, eventListGateway);
    LogInSignUpPresenter logInSignUpPresenter = new LogInSignUpPresenter();
    EventPresenter eventPresenter = new EventPresenter(eventSys, userManager, eventMan);


    /**
     * Creates a new LoginSystem instance.
     */
    public LoginSystem() {
    }

    /**
     * Gets the EventManagementSystem
     *
     * @return event management system
     */
    public EventManagementSystem getEventSys() {
        return this.eventSys;
    }

    /**
     * Gets the MessageSystem
     *
     * @return message management system
     */
    public MessengerSystem getMsgSys() {
        return this.msgSys;
    }

    /**
     * Calls the appropriate menus depending on the user input.
     */
    public void mainPage() {
        int answer;
        do {
            answer = logInSignUpPresenter.menu();
//            if (answer == 1) {
//                //msgSys.menus();
//            } else if (answer == 2) {
//                eventPresenter.mainEventPage();
//            } else if (answer == 3) {
//                signOut();
//            }
        } while (answer != 3);
    }

    /**
     * Manages the initial page that the user sees/
     */
    public void welcome() {
        int answer;
        do {
            answer = logInSignUpPresenter.welcome();
            if (answer == 1) {
                signUp();
                answer = 2;
            }
            if (answer == 2) {
                if (logIn()) {
                    mainPage();
                }
            }
        } while (answer != 3);
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
     * Determines if given username and password can log in
     */
    public boolean canLogin(String username, String password) {
        return userManager.logInUser(username, password);
    }

    /**
     * Determines if given username exists in list of users
     */
    public boolean isUser(String username) {
        return userManager.getSignedUpUsers().contains(username);
    }

    /**
     * Signs in the user
     */
    public void signUpUser(String username, String password, String userType) {
        userManager.CreateUser(username, password, userType);
    }

    /**
     * Logs in the user
     *
     * @return boolean representing whether log in was successful
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
            } else {
                logInSignUpPresenter.print("Invalid user name or password. Please try again.");
                logInSignUpPresenter.print("Or type 'm' to go back to main menu or any other key to try again");
                if (logInSignUpPresenter.readLine().equals("m")) {
                    return false;
                }
            }
        } while (true);
    }

    /**
     * Signs up a new user
     */
    public void signUp() {
        String response = userController.askUser("Are you an \n1. Attendee \n2. Organizer", "Incorrect answer",
                userInput -> userInput.equals("1") || userInput.equals("2"));
        if (response.equals("1")) {
            signUpAttendee(Attendee.TYPE);
        } else {
            signUpOrganizer(Organizer.TYPE);
        }
        logInSignUpPresenter.print("You have successfully signed up.");
        logInSignUpPresenter.print("Continue to Log In.");
    }


    private void signUpOrganizer(String userType) {
        userController.askUser("Enter your organizer code.", "Invalid code.",
                userInput -> userInput.equals("amongUs"));

        signUpAttendee(userType);
    }


    private void signUpAttendee(String userType) {
        String username = userController.askUser("Enter a username.", "Username already exists",
                userInput -> userManager.getUserByUsername(userInput) == null);

        logInSignUpPresenter.print("Enter a password.");
        String password = logInSignUpPresenter.readLine();
//        String strength = checkPassword.scorePassword(password);
//        logInSignUpPresenter.print(strength);
        userManager.CreateUser(username, password, userType);
    }

}