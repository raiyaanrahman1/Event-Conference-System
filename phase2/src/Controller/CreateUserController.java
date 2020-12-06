package Controller;

import Entity.Attendee;
import Entity.Speaker;
import Entity.VIP;
import UseCase.UserManager;

import java.util.Scanner;
import java.util.function.Function;

/**
 * Creates Attendee, Speaker, and VIP account by request of Organizer
 */
public class CreateUserController {

    UserManager userManager;

    /**
     * Creates a new CreateUserController instance.
     *
     * @param user the UserManager instance instantiated in LoginSystem
     */
    public CreateUserController(UserManager user) {
        userManager = user;
    }

    /**
     * Creates a Speaker account iff the user is an Organizer
     */
    public void CreateSpeaker() {
        CreateAccount(Speaker.TYPE, "speaker");
    }

    /**
     * Creates an Attendee account iff the user is an Organizer
     */
    public void CreateAttendee() {
        CreateAccount(Attendee.TYPE, "attendee");
    }

    /**
     * Creates a VIP account iff the user is an Organizer
     */
    public void CreateVIP() {
        CreateAccount(VIP.TYPE, "VIP");
    }

    /**
     * Creates an account
     * @param type String representing user type
     * @param prompt String message prompting user
     */
    public void CreateAccount(String type, String prompt) {
        Scanner in = new Scanner(System.in);
        String username = askUser("Enter a username.", "Username already exists.",
                userInput -> userManager.getUserByUsername(userInput) == null);

        System.out.println("Enter a password.");
        String password = in.next();
        System.out.println(String.format("You have successfully created a % account.", prompt));
        userManager.CreateUser(username, password, type);
    }

    /**
     * Generally asks the user for input based on prompt
     * @param prompt String representing a prompt
     * @param errorMessage String error message that appears when error occurs
     * @param validationFunction a function that checks the validity of input
     * @return String representing user input
     */
    public String askUser(String prompt, String errorMessage,
                          Function<String, Boolean> validationFunction) {
        Scanner myObj = new Scanner(System.in);
        boolean keepAsking = true;
        String userInput;
        do {
            System.out.println(prompt);
            userInput = myObj.next();
            if (!validationFunction.apply(userInput)) {
                System.out.println(errorMessage);
            } else {
                keepAsking = false;
            }
        }
        while (keepAsking);
        return userInput;
    }
}
