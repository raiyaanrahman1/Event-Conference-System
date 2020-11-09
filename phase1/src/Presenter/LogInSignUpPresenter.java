package Presenter;

import Entity.User;
import sun.awt.geom.AreaOp;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * A command line Presenter for the LogInSignSystem
 */

public class LogInSignUpPresenter {
    private Scanner in = new Scanner(System.in);
    private PrintStream out = System.out;

    public int menu(){
        System.out.println("\t\t\t\tEVENTS\n");
        out.println("(1) Sign up for event");
        out.println("(2) Cancel spot in event");
        out.println("(3) See your events");
        out.println("Choose a number for one of the options above. \t\t\t\t(Must be 1 2 or 3)");
        return Integer.parseInt(in.nextLine());
    }

}



