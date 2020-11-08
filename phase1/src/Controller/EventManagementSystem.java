package Controller;
import UseCase.UserManager;
import java.util.Scanner;

public class EventManagementSystem {

    private UserManager user;

    public void eventSignUp() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter the id of the event that would you like to sign up for?");
        showListOfAllowedEvents();
        String response = myObj.nextLine();
        if (user.signUpForEvent(Integer.parseInt(response))) {
             System.out.println("You have successfully signed up for this event.");
             returnToEventMenu();
        }
        System.out.println("Sign up failed.\n Enter (1) to sign up for a different event");
        String input = myObj.nextLine();
        int option = Integer.parseInt(input);
        if (option == 1) {
            this.eventSignUp();
        }
        returnToEventMenu();
    }

    public void AttendeeCancelEvent() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter the id of the event that would you like to cancel your spot for? " +
                "The input must be an integer.");
        this.ShowListOfUserEvents();
        String response = myObj.nextLine();
        if (user.cancelSpot(Integer.parseInt(response))) {
            System.out.println("You have successfully canceled your spot for this event.");
            returnToEventMenu();
        }
        System.out.println("Cancellation failed.\n Enter (1) to cancel a different event");
        String input = myObj.nextLine();
        int option = Integer.parseInt(input);
        if (option == 1) {
            this.AttendeeCancelEvent();
        }
        returnToEventMenu();
    }

    public void showListOfAllowedEvents() {

        System.out.println("Here are the ids of the events that you are allowed to sign up for:");
        for (Integer id : user.getAllowedEvents()) {
            System.out.println(id);
        }
        returnToEventMenu();
    }

    public void ShowListOfUserEvents() {
        System.out.println("Here are the ids of the events that you have signed up for:");
        for (Integer id : user.getUserEvents()) {
            System.out.println(id);
        }
        returnToEventMenu();
    }

    public void AddEvent() {

        // if the user puts in a string, the entire program crashes --> fixed by parsing
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter the id of the event you would like to add? The input must be an integer.");
        String eventId = myObj.nextLine();
        if (user.addEvent(Integer.parseInt(eventId))) {
            System.out.println("You have successfully added this event.");
            returnToEventMenuOrganizer();
        }
        System.out.println("Failed to add event.\n Enter (1) to add a different event");
        String input = myObj.nextLine();
        int option = Integer.parseInt(input);
        if (option == 1) {
            this.AddEvent();
        }
        returnToEventMenuOrganizer();
    }

    public void cancelEvent() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event you would like to cancel? The input must be an integer.");
        String eventId = myObj.nextLine();
        if (user.removeEvent(Integer.parseInt(eventId))) {
            System.out.println("You have successfully cancelled this event.");
            returnToEventMenuOrganizer();
        }
        System.out.println("Failed to cancel event. \n Enter (1) to cancel a different event");
        String input = myObj.nextLine();
        int option = Integer.parseInt(input);
        if (option == 1) {
            this.cancelEvent();
        }
        returnToEventMenuOrganizer();
    }

    public void eventMenu() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("\t \t \t \t EVENTS \n (1) Sign Up \n (2) Cancel Spot \n (3) Show List Of Allowed Events " +
                "\n (4) Events You Have Signed Up For");
        System.out.println("Choose a number for one of the options above.");
        String input = myObj.nextLine();
        int option = Integer.parseInt(input);
        if (option == 1) {
            this.eventSignUp();
        }
        else if (option == 2) {
            this.AttendeeCancelEvent();
        }
        else if (option == 3) {
            this.showListOfAllowedEvents();
        }
        else if (option == 4) {
            this.ShowListOfUserEvents();
        }
        else {
            System.out.println("Please enter a number between 1-4.");
            this.eventMenu();
        }

    }
    public void eventMenuOrganizer(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("\t \t \t \t EVENTS \n (1) Add Event \n (2) Cancel Event ");
        System.out.println("Choose a number for one of the options above.");
        String input = myObj.nextLine();
        int option = Integer.parseInt(input);
        if (option == 1) {
            this.AddEvent();
        }
        else if (option == 2) {
            this.cancelEvent();
        }
        else{
            System.out.println("Please either options 1 or 2.");
            this.eventMenuOrganizer();
        }
    }
    private void returnToEventMenu(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Press the Enter key to return to the Event Menu.");
        myObj.nextLine();
        this.eventMenu();
    }
    private void returnToEventMenuOrganizer(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Press the Enter key to return to the Event Menu.");
        myObj.nextLine();
        eventMenuOrganizer();
    }

}