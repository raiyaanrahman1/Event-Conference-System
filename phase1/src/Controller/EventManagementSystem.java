package Controller;
import UseCase.UserManager;
import java.util.Scanner;
import Controller.LoginSystem;

public class EventManagementSystem {

    private UserManager user;

    public void eventSignUp() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event that would you like to sign up for?");
        showListOfAllowedEvents();
        String response = myObj.nextLine();
        if (user.signUpForEvent(Integer.parseInt(response))) {
             System.out.println("You have successfully signed up for this event.");
        }
        System.out.println("Sign up failed. Please try signing up for a different event.");
    }

    public void AttendeeCancelEvent() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event that would you like to cancel your spot for? The input must be an integer.");
        this.ShowListOfUserEvents();
        String response = myObj.nextLine();
        if (user.cancelSpot(Integer.parseInt(response))) {
            System.out.println("You have successfully canceled your spot for this event.");
        }
        System.out.println("Cancellation failed. Please try cancelling a different event.");
    }

    public void showListOfAllowedEvents() {

        System.out.println("Here are the ids of the events that you are allowed to sign up for:");
        for (Integer id : user.getAllowedEvents()) {
            System.out.println(id);
        }
    }

    public void ShowListOfUserEvents() {
        System.out.println("Here are the ids of the events that you have signed up for:");
        for (Integer id : user.getUserEvents()) {
            System.out.println(id);
        }
    }

    public void AddEvent() {

        // if the user puts in a string, the entire program crashes

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event you would like to add? The input must be an integer.");
        String eventid = myObj.nextLine();
        if (user.addEvent(Integer.parseInt(eventid))) {
            System.out.println("You have successfully added this event.");
            return;
        }
        System.out.println("You have unsuccessfully added this event.");
    }

    public void CancelEvent() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event you would like to cancel? The input must be an integer.");
        String eventid = myObj.nextLine();
        if (user.removeEvent(Integer.parseInt(eventid))) {
            System.out.println("You have successfully cancelled this event.");
            return;
        }
        System.out.println("You have unsuccessfully cancelled this event.");
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
}