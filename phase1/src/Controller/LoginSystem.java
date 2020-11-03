package Controller;
import Entity.User;
import UseCase.UserManager;
import java.util.Scanner;  // Import the Scanner class

public class LoginSystem {

    //remember to initialize MessengerSystem and EventManagementSystem

    //need to add a sign-out method?

    // helper methods: LogIn, SignUp (one for Attendee and Organizer)
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Would you like to sign up or log in?");
        String answer = myObj.nextLine();
        if (answer.equals("log in")) {
            System.out.println("Enter username");
            String username = myObj.nextLine();  // Read user input
            while (UserManager.getUserByUsername(username) == null) {
                System.out.println("Username does not exist."); // RECURSION??? WE DON'T WANT TO KICK THEM OUT
            }
            System.out.println("Enter a password.");
            String password = myObj.nextLine();
            User userobject = UserManager.getUserByUsername(username);
            if (userobject.getPassword().equals(password)) {
                UserManager usermanager = new UserManager(userobject);
                // WHY IS THE METHOD NOT BEING ACCESSED?
            }

            // TO LOGIN:
            // 1. ask for username
            // 2. check if the username is in the userlist
            // 3. if it is, ask for their password, check to see if they match. If they do, set the current
            // user in UserManager to the one with given username. if not, keep prompting them (with a while loop)
            // 4. if the username is not in the txt file, keep prompting them (with a while loop)

        }
        if (answer.equals("sign up")) {
            System.out.println("Are you an attendee or an organizer?");
            String response = myObj.nextLine();
            if (response.equals("attendee")) {
                System.out.println("Enter a username");
                String username = myObj.nextLine();
                while (UserManager.getUserByUsername(username) != null) {
                    System.out.println("Username is already taken."); // RECURSION??? WE DON'T WANT TO KICK THEM OUT
                }
                System.out.println("Enter a password.");
                String password = myObj.nextLine();
                // write to the text file in the format: username password A
                }
            else if (response.equals("organizer")) {
                System.out.println("Enter your organizer code.");
                String code = myObj.nextLine();
                while (!code.equals("f9h2q6")) {
                    System.out.println("Invalid code."); // RECURSION??? WE DON'T WANT TO KICK THEM OUT
                }
                System.out.println("Enter a username");
                String username = myObj.nextLine();
                while (UserManager.getUserByUsername(username) != null) {
                    System.out.println("Username is already taken."); // RECURSION??? WE DON'T WANT TO KICK THEM OUT
                }
                System.out.println("Enter a password.");
                String password = myObj.nextLine();
                // write to the text file in the format: username password A
            }
            }

            //TO SIGN UP:
            // 1. prompt them with the question 'are you an attendee or an organizer?"
            // 2. if they answer 'attendee': ask them for a username.
                // - check to see if this username is taken in UserManager.userList. if it is, say 'username is
                // already taken' (prompt them again using a while loop). if its not taken, ask them for a password.
                // - write the 'username' and 'password' and 'A' to the text file:      tkharseh 5615fafs A
            // 3. if they answer 'organizer': ask them for their organizer code.
            // 4. if the code they entered matches with 'f9h2q6' then ask them for a username
                // - check to see if this username is taken in UserManager.userList. if it is, say 'username is
                // already taken' (prompt them again using a while loop). if its not taken, ask them for a password.
                // - write the 'username' and 'password' and 'O' to the text file:      tkharseh 5615fafs O

        }

    }
    //LoginSystem Constructor
    public LoginSystem() {

    }

}
