package Presenter;

import Controller.EventManagementSystem;
import UseCase.UserManager;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * A command line Presenter for the EventManagementSystem
 */
public class EventPresenter {
    private UserManager user;
    private Scanner in = new Scanner(System.in);
    private PrintStream out = System.out;

    // is this really needed here? we call the presenter from
    // the controller.
    private EventManagementSystem events;

    public void EventManagementSystem(UserManager user, EventManagementSystem events){
        this.user = user;
        this.events = events;
    }

    public void mainEventPage(){ //to be displayed for the overall menu page
        attendeeEventMenu();
        organizerEventMenu();
    }
    public void organizerEventMenu() {
        List<String> userInfo = user.getUserInfoList();
        if (userInfo.get(2).equals("O")) {
            events.eventMenu();
        }
        System.out.println("You do not have access.");
    }

    public void attendeeEventMenu(){
        events.eventMenu();
    }

    /**
     * Displays the allowed events that a user is allowed to sign up for.
     */
    private void displayEventsToSignUp() {
        for (Integer id: user.getAllowedEvents()) {
            out.printf("%d:  %s\n", id, user.getToStringByEventID(id));
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

        return Integer.parseInt(in.nextLine());
    }

    /**
     * Displays the events that the user has signed up for.
     */
    public void displayEventsByUser() {
        for (Integer id: user.getUserEvents()) {
            out.printf("%d:  %s\n", id, user.getToStringByEventID(id));
        }
    }

    /**
     * Prompts user for the id of one of all events.
     *
     * @return
     */
    public void displayEventsAll() {
        for (Integer id: user.getAllEventIDs()) {
            out.printf("%d:  %s\n", id, user.getToStringByEventID(id));
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
     * Displays user to try again. (Useful for many things).
     */
    public void displayTryAgain() {
        out.println("Your input is wrong. Please try again.");
    }

    /**
     * Prompts user for a number in a range.
     * TODO: catch errors if input is string
     *
     * @param start
     * @param end
     */
    private int promptForNumberRange(int start, int end) {
        out.printf("Input a number from %d to %d", start, end);
        return Integer.parseInt(in.nextLine());
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
        out.println("Cancelling spot has failed. (Are you signed up for this event?");
    }
}
