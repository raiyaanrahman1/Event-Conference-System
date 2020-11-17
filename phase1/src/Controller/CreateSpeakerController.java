package Controller;

import Gateway.IGateway;
import Gateway.IGateway2;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class CreateSpeakerController {

    IGateway g;
    IGateway2 g1;
    UserManager userManager;

    /**
     * Creates a new CreateSpeakerController instance.
     * @param user the UserManager instance instantiated in LoginSystem
     * @param g the LoginFileGateway instance instantiated in LoginSystem
     * @param g1 the MessageLoginFileGateway instance instantiated in LoginSystem
     */
    public CreateSpeakerController(UserManager user, IGateway g, IGateway2 g1){
        userManager = user;
        this.g = g;
        this.g1 = g1;
    }
    /**
     * Creates a Speaker account iff the user is an Organizer
     */
    public void CreateSpeaker() {

        Scanner myObj = new Scanner(System.in);
            String username = askUser("Enter a username.", "Username already exists.",
                    userInput -> userManager.getUserByUsername(userInput) == null);

            System.out.println("Enter a password.");
            String password = myObj.next();
            System.out.println("You have successfully created a speaker account.");
            userManager.CreateUser(username, password, "S");
    }

    private String askUser(String prompt, String errorMessage,
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

    /**
     * Checks if username already exists in the database.
     * @param gt the IGateway used to read LogInInformation
     * @param username the username to be checked against LogInInformation
     * @return true iff the database contains username
     */
    public boolean exists(IGateway gt, String username){
        ArrayList<List<String>> list = gt.read();
        for(List<String> actual: list){
            if(actual.contains(username)){
                return true;
        }
        }
        return false;
    }
}
