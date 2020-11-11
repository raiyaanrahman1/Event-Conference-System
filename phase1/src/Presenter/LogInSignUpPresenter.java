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
        System.out.println("\t\t\t\tWHERE SHOULD I TAKE YOU?\n");
        out.println("(1) Messages");
        out.println("(2) Events");
        out.println("(3) Log out");
        out.println("Choose a number for one of the options above. \t\t\t\t(Must be 1 2 or 3)");
        return in.nextInt();
    }
    /**
     * A Display menu which options sign up or log in.
     * @return integer representing the option that was chosen.
     */
    public int wel(){
        out.println("Please choose 1 or 2: \n Would you like to 1. sign up or 2. log in?");
        return in.nextInt();
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




