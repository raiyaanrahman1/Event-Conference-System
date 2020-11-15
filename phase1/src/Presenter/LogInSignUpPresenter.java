package Presenter;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * A command line Presenter for the LogInSignSystem
 */

public class LogInSignUpPresenter {
    private final Scanner in = new Scanner(System.in);
    private final PrintStream out = System.out;

    /**
     * A Display menu which directs the user.
     * @return integer representing whether the user chose Messages, Events or Log out
     */
    public int menu(){
        System.out.println("========== MAIN MENU ==========");
        out.println("1. Messages");
        out.println("2. Events");
        out.println("3. Log out");
        out.println("Input the number of the option you wish to choose:");

        return in.nextInt();
    }
    /**
     * A Display menu which options sign up or log in.
     * @return integer representing the option that was chosen.
     */
    public int wel(){
        boolean incorrectOption = false;
        int answer;
        do {
            System.out.println("========== INSERT NAME OF PROGRAM HERE ==========");
            out.println("1. Sign Up");
            out.println("2. Log In");
            out.println("Input the number of the option you wish to choose:");
            answer = in.nextInt();
            if (answer != 1 && answer != 2){
                System.out.println("You have entered an incorrect input. Please enter a valid input.");
                incorrectOption = true;
            }
        } while (incorrectOption);
        return answer;
    }
    /**
     * Reads the user input
     * @return String representing the user input.
     */
    public String readLine() {
        return in.next();
    }
    /**
     * Returns a message
     * @param content which representing the content to be returned.
     */
    public void print(String content){
        out.println(content);
    }
}




