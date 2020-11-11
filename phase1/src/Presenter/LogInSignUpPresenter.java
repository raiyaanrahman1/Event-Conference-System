package Presenter;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * A command line Presenter for the LogInSignSystem
 */

public class LogInSignUpPresenter {
    private final Scanner in = new Scanner(System.in);
    private final PrintStream out = System.out;

    public int menu(){
        System.out.println("\t\t\t\tWHERE SHOULD I TAKE YOU?\n");
        out.println("(1) Messages");
        out.println("(2) Events");
        out.println("(3) Log out");
        out.println("Choose a number for one of the options above. \t\t\t\t(Must be 1 2 or 3)");
        return Integer.parseInt(in.nextLine());
    }

    public int wel(){
        out.println("Please choose 1 or 2: \n Would you like to 1. sign up or 2. log in?");
        return Integer.parseInt(in.nextLine());
    }

    public String readLine() {
        return in.nextLine();
    }

    public void print(String content){
        out.println(content);
    }
}




