package Presenter;

import Controller.EventManagementSystem;
import UseCase.EventManager;
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
    private EventManagementSystem events;
    private EventManager manager;

    /**
     * Creates an EventPresenter instance.
     * @param event the EventManagementSystem instance instantiated in LoginSystem
     * @param user the UserManager instance instantiated in LoginSystem
     * @param manager the EventManager instance instantiated in LoginSystem
     */
    public EventPresenter(EventManagementSystem event, UserManager user, EventManager manager){
        this.events = event;
        this.user = user;
        this.manager = manager;

    }

    /**
     * Displays the appropriate event menu based on the type of logged-in user.
     */
    public void mainEventPage(){
        if (user.getUserInfoList().get(2).equals("O")) {
            events.eventMenuOrganizer();
        }else if (user.getUserInfoList().get(2).equals("S")){
            events.eventMenuSpeaker();
        }else{
            events.eventMenuAttendee();
        }
    }

    /**
     * Prompts user for the id of event they want to sign up for.
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
        List<Integer> userEvents = manager.getEventListByAttendee(user.getUserInfoList().get(0));
        if (userEvents.size() == 0){
            print("You have not signed up for any events.");
        }
        formatEventString(userEvents);
    }

    /**
     * Shows the list of events an Organizer.
     */
    public void displayEventsByOrganizer() {
        List<Integer> organizerEvents = manager.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0));
        if (organizerEvents.size() == 0){
            print("You have not organized any events.");
        }
        formatEventString(organizerEvents);
    }

    /**
     * Shows the list of events a Speaker is speaking at.
     */
    public void displayEventsBySpeaker() {
        List<Integer> speakerEvents = manager.getTalksBySpeaker(user.getUserInfoList().get(0));
        if (speakerEvents.size() == 0) {
            print("You are not speaking any events.");
        }
        formatEventString(speakerEvents);
    }

    /**
     * Shows the list of events a user is allowed to sign up for.
     */
    public void displayAllowedEvents() {
        List<Integer> allowedEvents = manager.getAllowedEvents(user.getUserInfoList().get(0));
        if (allowedEvents.size() == 0){
            print("There are no events for you to sign up for.");
        }
        formatEventString(allowedEvents);
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
        out.println("You have failed to signed up for this event.");
    }

    /**
     * Displays the event menu and returns the number of the option
     * the user has chosen.
     *
     * @return  the number of the event chosen.
     */
    public int displayEventMenuOptions() {
        System.out.println("========== Events Menu ==========");
        out.println("1. Sign up for event");
        out.println("2. Cancel spot in event");
        out.println("3. See your events");
        out.println("4. Return to Main Menu");
        out.println("==================================");
        System.out.println("Input the number of the option you wish to choose:");

        return promptForNumberRange(1, 4);
    }

    /**
     * Displays the event menu for Organizers and returns the number of the option
     * the user has chosen.
     *
     * @return  the number of the event chosen.
     */
    public int displayEventMenuOptionsOrganizer() {
        System.out.println("========== EVENTS MENU ==========");
        out.println("1. Sign up for event");
        out.println("2. Cancel spot in event");
        out.println("3. See your events");
        out.println("4. Add an event");
        out.println("5. Cancel an event");
        out.println("6. See the events you organized");
        out.println("7. Broadcast an event");
        out.println("8. Create a Speaker account");
        out.println("9. Create an Attendee account");
        out.println("10. Create a VIP account");
        out.println("11. Return to Main Menu");
        out.println("==================================");

        return promptForNumberRange(1, 11);
    }

    /**
     * Displays the event menu for Organizers and returns the number of the option
     * the user has chosen.
     *
     * @return  the number of the event chosen.
     */
    public int displayEventMenuOptionsSpeaker() {
        System.out.println("========== EVENTS MENU ==========");
        out.println("1. See your events");
        out.println("2. Broadcast an event");
        out.println("3. Return to Main Menu");
        out.println("==================================");
        out.println("Input the number of the option you wish to choose:");

        return promptForNumberRange(1, 3);
    }

    /**
     * Displays user to try again. (Useful for many things).
     */
    public void displayTryAgain() {
        out.println("Your input is wrong. Please try a different event.");
    }

    private int promptForNumberRange(int start, int end) {
        out.printf("Input a number from %d to %d\n", start, end);
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

    /**
     * Display to organizer user that adding event has succeeded.
     */
    public void displayAddEventSuccess() {
        out.println("You have successfully added this event.");
    }

    /**
     * Display to organizer user that adding event has failed.
     */
    public void displayAddEventFailure() {
        out.println("You have failed adding this event.");
    }

    /**
     * Displays whether the user has signed up successfully for an
     * event.
     */
    public void displayBroadcastSuccess() {
        out.println("You have successfully broadcasted your message.");
    }

    public String promptForMessage() {
        Scanner sc = new Scanner(System.in);
        out.println("Enter the message you want to broadcast.");
        out.println("Put it all in one single line.");
        return sc.nextLine();
    }

    /**
     * Takes in the content to be printed to the UI and returns the String response
     * @param content which representing the content to be returned.
     * @return the user's response as a String.
     */
    public String takeString(String content){
        Scanner sc = new Scanner(System.in);
        out.println(content);
        return sc.nextLine();
    }

    /**
     * Returns a message
     * @param content represents the content to be printed.
     */
    public void print(String content){
        out.println(content);
    }


    /**
     * Takes in
     * @param eventList , a list of ID of events,
     * and prints the corresponding toStrings of the events.
     */
    public void formatEventString(List<Integer> eventList){
        if (eventList.size()> 0) {
            for (Integer eventID : eventList) {
                System.out.println(manager.getEventString(eventID));
            }

        }
    }

}

