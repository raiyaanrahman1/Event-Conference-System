package Presenter;

import Controller.EventManagementSystem;
import UseCase.UserManager;

import java.util.List;

/**
 * A command line Presenter for the EventManagementSystem
 */
public class EventPresenter {
    private UserManager user;

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
     * TODO: Implement
     */
    private void showListOfAllowedEvents() {

    }

    /**
     * Prompts user for the id of event they want to sign up for.
     * TODO: Implement
     *
     * @return  the id of the event
     */
    public int promptForEventIDSignUp() {
        return 0;
    }

    /**
     * Prompts user for the id of event he has signed up for.
     * TODO: Implement
     *
     * @return
     */
    public int promptForEventIDFromUser() {
        return 0;
    }

    /**
     * Prompts user for the id of one of all events.
     * TODO: Implement
     *
     * @return
     */
    public int promptForEventIDAll() {
        return 0;
    }

    /**
     * Displays whether the user has signed up successfully for an
     * event.
     * TODO: Implement
     */
    public void displaySignUpSuccess() {

    }

    /**
     * Displays the event menu and returns the number of the option
     * the user has chosen.
     * TODO: Implement
     *
     * @return  the number of the event chosen.
     */
    public int displayEventMenuOptions() {
        return 0;
    }

    /**
     * Displays user to try again. (Useful for many things).
     * TODO: Implement
     */
    public void displayTryAgain() {

    }

    /**
     * Prompts user for a number in a range.
     * TODO: Implement
     *
     * @param start
     * @param end
     */
    private void promptForNumberRange(int start, int end) {

    }

    /**
     * Display to user that cancelling event has succeeded.
     * TODO: Implement
     */
    public void displayCancelSuccess() {

    }

    /**
     * Display to user that cancelling event has failed.
     * TODO: Implement
     */
    public void displayCancelFailure() {

    }
}
