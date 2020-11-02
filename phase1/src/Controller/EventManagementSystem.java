package Controller;
import Entity.Attendee;
import Entity.Event;
import UseCase.AttendeeSignUp;
import UseCase.EventScheduler;
import UseCase.UserManager;

import java.util.List;
import java.util.Scanner;

public class EventManagementSystem {

    private UserManager user;

    public String EventSignUp(Attendee attendee) {

        Scanner myObj = new Scanner(System.in);
        System.out.println("What is the name of the event that would you like to sign up for?");
        System.out.println(ShowListOfEvents(attendee));
        String response = myObj.nextLine();
        if (AttendeeSignUp.signUpForEvent()) {
            return "You have successfully signed up for this event.";
        }
        return "Sign up failed. Please try signing up for a different event.";
    }

    public String AttendeeCancelEvent(Integer id) {

        // use the event id


        System.out.println("What is the name of the event that would you like to cancel your spot for?");
        System.out.println(ShowListOfAllowedEvents(attendee));
        String response = myObj.nextLine();
        EventScheduler e = new EventScheduler(attendee);
        Integer eventid = e.getIdByEventName();
        if (currentattendee.cancelSpot(event)) {
            return "You have successfully canceled your spot for this event.";
        }
        return "Cancellation failed. You are not signed up for this event.";
    }

    public String ShowListOfAllowedEvents(Attendee attendee) {
        EventScheduler currentattendee = new EventScheduler(attendee);
        List<Integer> listofevents = currentattendee.getAllowedEvents();
        for (int id = 0; id < listofevents.size() - 1; id++) {
            return EventScheduler.getEventByID(id).toString();
        }
    }

    public String ShowList

    public void AddEvent(Attendee attendee, Event event) {
        EventScheduler currattendee = new EventScheduler(attendee);
        currattendee.addEvent(event);
        System.out.println("You have added this event successfully!");
    }

    public void CancelEvent(Attendee attendee, Event event) {
        EventScheduler currattendee = new EventScheduler(attendee);
        currattendee.removeEvent(event);
        System.out.println("You have canceled this event successfully!");
    }


}