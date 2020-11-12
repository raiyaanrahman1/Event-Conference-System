package Presenter;

import Controller.EventManagementSystem;
import UseCase.UserManager;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * A command line Presenter for the EventManagementSystem
 */
public class EventPresenter {
    private UserManager user;
    private Scanner in = new Scanner(System.in);
    private PrintStream out = System.out;

//    // is this really needed here? we call the presenter from the controller.
    private EventManagementSystem events;

    public void mainEventPage(){ //to be displayed for the overall menu page
        events.eventMenuAttendee();
        if (user.getUserInfoList().get(2).equals("O")) {
            events.eventMenuOrganizer();
        }
    }


    /**
     * Prompts user for the id of event they want to sign up for.
     * TODO: catch errors for wrong input (i.e String rather than
     *
     * @return  the id of the event
     */
    public int promptForEventID() {
        out.println("Type the ID of the event that you want to choose.");
        out.println("The ID must be an integer.");

        return Integer.parseInt(in.next());
    }

    /**
     * Displays the events that the user has signed up for.
     */
    public void displayEventsByUser() {
        for (Integer id: events.ListOfUserEvents()) {
            out.printf("%d:  %s\n", id, user.getUserInfoList().get(0));
        }
    }

    /**
     * Prompts user for the id of one of all events.
     */
    public void displayEventsByOrganizer() {
        for (String event: events.showOrganizedEvents()) {
            out.print(event);
        }
    }

    /**
     * Displays whether the user has signed up successfully for an
     * event.
     */
    public void displaySignUpSuccess() {
        out.println("You have successfully signed up for this event.");
    }

    /**
     * Displays whether the user has signed up unsuccessfully for an
     * event.
     */
    public void displaySignUpFailure() {
        out.println("You have failed to signed up for this event. Is this event available?");
    }

    /**
     * Displays the event menu and returns the number of the option
     * the user has chosen.
     *
     * @return  the number of the event chosen.
     */
    public int displayEventMenuOptions() {
        System.out.println("EVENTS");
        out.println("(1) Sign up for event");
        out.println("(2) Cancel spot in event");
        out.println("(3) See your events");
        System.out.println("Choose a number for one of the options above.");

        return promptForNumberRange(1, 3);
    }

    /**
     * Displays the event menu for Organizers and returns the number of the option
     * the user has chosen.
     *
     * @return  the number of the event chosen.
     */
    public int displayEventMenuOptionsOrganizer() {
        System.out.println("EVENTS");
        out.println("(1) Sign up for event");
        out.println("(2) Cancel spot in event");
        out.println("(3) See your events");
        out.println("(4) Add an event");
        out.println("(5) Cancel an event");
        out.println("(6) See the events you organized");
        out.println("(7) Broadcast an event.");
        out.println("Choose a number for one of the options above.");

        return promptForNumberRange(1, 6);
    }

    /**
     * Displays the event menu for Organizers and returns the number of the option
     * the user has chosen.
     *
     * @return  the number of the event chosen.
     */
    public int displayEventMenuOptionsSpeaker() {
        System.out.println("EVENTS");
        out.println("(1) See your events");
        out.println("(2) Broadcast an event.");
        out.println("Choose a number for one of the options above.");

        return promptForNumberRange(1, 2);
    }

    /**
     * Displays user to try again. (Useful for many things).
     */
    public void displayTryAgain() {
        out.println("Your input is wrong. Please try again.");
    }

    /**
     * Prompts user for a number in a range.
     * TODO: catch errors if input is string
     *
     * @param start the minimum value of the valid range
     * @param end the maximum value of the valid range
     */
    private int promptForNumberRange(int start, int end) {
        out.printf("Input a number from %d to %d", start, end);
        return Integer.parseInt(in.next());
    }

    /**
     * Display to user that cancelling event has succeeded.
     */
    public void displayCancelSuccess() {
        out.println("You have successfully cancelled your spot in this event.");
    }

    /**
     * Display to user that cancelling event has failed.
     */
    public void displayCancelFailure() {
        out.println("Cancelling spot has failed. Are you signed up for this event?");
    }

    /**
     * Display to user that cancelling event has succeeded.
     */
    public void displayCancelEventSuccess() {
        out.println("You have successfully cancelled this event.");
    }

    /**
     * Display to user that cancelling event has failed.
     */
    public void displayCancelEventFailure() {
        out.println("Cancelling event has failed. Has the event been created?");
    }

    public String promptForMessage() {
        out.println("Enter the message you want to broadcast.");
        out.println("Put it all in one single line.");
        return in.next();
    }
}
