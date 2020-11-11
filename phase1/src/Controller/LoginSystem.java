package Controller;

import Entity.Attendee;
import Entity.Organizer;
import Entity.User;
import Gateway.FileGateway;
import Gateway.IGateway;
import Presenter.LogInSignUpPresenter;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LoginSystem {
    MessengerSystem msgSys = new MessengerSystem();
    EventManagementSystem eventSys = new EventManagementSystem();
    LogInSignUpPresenter lp = new LogInSignUpPresenter();
    //EventPresenter ep = new EventPresenter();
    //MessagePresenter mp = new MessagePresenter(user, msgSys);
    IGateway g = new FileGateway("phase1/src/Controller/LogInInformation.txt");
    UserManager userManager = new UserManager(g.read());

    //LoginSystem Constructor
    public LoginSystem() {
        welcome();
    }

//    public void run(){
//        if()
//    }

    /**
     * Display of the main page (for now)
     */
    public void MainPage() {
        int answer;
        do {
            answer = lp.menu(); //Shouldn't this menu be the options: Message, event, logout instead of events?
            if (answer == 1) {
                //call message system
                //msgSys.messageMenu();
            } else if (answer == 2) {
                eventSys.eventMenu();
            } else if (answer == 3) {
                signOut();
            }
        } while (answer != 3);
    }

    /**
     * The initial page that the user sees
     */
    public void welcome() {
        int answer = lp.wel();
        if (answer == 1) {
            logIn();
            MainPage();
        }
        if (answer == 2) {
            signUp();
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
     */
    public void logIn() {
        String username = askUser("Enter a username", "Username does not exist",
                userInput -> exists(userInput, null));

        //check password
        askUser("Enter a password", "Incorrect password",
                userInput -> exists(username, userInput));

        //user = new UserManager(username);
        //also need to save user info and get user type
        //userM.getUserInfoList().get(2);
        userManager.logInUser(username);
        lp.print("Log in successful. Welcome " + username);
    }

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
        String response = askUser("Are you an attendee or an organizer?", "Incorrect answer",
                userInput -> userInput.equals("attendee") || userInput.equals("organizer"));
        if (response.equals("attendee")) {
            signUpAttendee("A");
        } else {
            signUpOrganizer("O");
        }
        lp.print("You have successfully signed up.");
        lp.print("Continue to Log In.");
        logIn();
    }

    /**
     * Signs up a user with particular type Organizer
     */
    private void signUpOrganizer(String userType) {
        askUser("Enter your organizer code.", "Invalid code.",
                userInput -> userInput.equals("f9h2q6"));

        signUpAttendee(userType);
    }

    /**
     * Signs up a user with particular type Attendee
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
        g.append(userInfo);
    }

    /**
     * Helper method that checks if username exists in file
     *
     * @param username the users username
     * @return if username is in the gateway.
     */
    private boolean exists(String username, String password) {
        User user = getUser(username);
        g.read();
        while (g.hasNext()) {
            List<String> users = g.next();
            if (users.get(0).equals(username)) {
                if (password != null ) {
                    return users.get(1).equals(password);
                }
                return true;
            }
        }
        return false;
    }

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