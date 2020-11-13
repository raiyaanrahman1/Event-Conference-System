package Controller;

import Gateway.FileGateway;
import Gateway.IGateway;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class CreateSpeakerController {

    IGateway g = new FileGateway("");
    UserManager userManager = new UserManager(g);

    /**
     * Creates a Speaker account iff the user is an Organizer
     */
    public void CreateSpeaker() {

        Scanner myObj = new Scanner(System.in);
        askUser("Enter your organizer code.", "Invalid code.",
                userInput -> userInput.equals("f9h2q6"));
            String username = askUser("Enter a username", "Username already exists",
                    userInput -> userManager.getUserByUsername(userInput) == null);

            System.out.println("Enter a password.");
            String password = myObj.next();
            userManager.CreateUser(username, password, "S");
    }

    //helper method
    private String askUser(String prompt, String errorMessage,
                           Function<String, Boolean> validationFunction) {
        Scanner myObj = new Scanner(System.in);
        boolean keepAsking = true;
        String userInput;
        do {
            System.out.println();
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

    public boolean exists(IGateway gt, String username){
        ArrayList<List<String>> list = gt.read();
//        while (!list.isEmpty()) {
//            List<String> actual = gt.next();
//            if(actual.contains(username)){
//                return true;
//            }
        for(List<String> actual: list){
            if(actual.contains(username)){
                return true;
        }
        }
        return false;
    }
}
