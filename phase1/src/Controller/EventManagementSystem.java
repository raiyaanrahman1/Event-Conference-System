package Controller;
import UseCase.UserManager;
import java.util.Scanner;
import Controller.LoginSystem;

public class EventManagementSystem {

    private UserManager user;

    public void EventSignUp() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event that would you like to sign up for?");
        ShowListOfAllowedEvents();
        String response = myObj.nextLine();
        if (user.signUpForEvent(Integer.parseInt(response))) {
             System.out.println("You have successfully signed up for this event.");
        }
        System.out.println("Sign up failed. Please try signing up for a different event.");
    }

    public void AttendeeCancelEvent() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event that would you like to cancel your spot for?");
        ShowListOfUserEvents();
        String response = myObj.nextLine();
        if (user.cancelSpot(Integer.parseInt(response))) {
            System.out.println("You have successfully canceled your spot for this event.");
        }
        System.out.println("Cancellation failed. Please try cancelling a different event.");
    }

    public void ShowListOfAllowedEvents() {

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

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event you would like to add?");
        String eventid = myObj.nextLine();
        if (user.addEvent(Integer.parseInt(eventid))) {
            System.out.println("You have successfully added this event.");
        }
        System.out.println("You have unsuccessfully added this event.");
    }

    public void CancelEvent() {

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the id of the event you would like to cancel?");
        String eventid = myObj.nextLine();
        if (user.removeEvent(Integer.parseInt(eventid))) {
            System.out.println("You have successfully cancelled this event.");
        }
        System.out.println("You have unsuccessfully cancelled this event.");
    }
}