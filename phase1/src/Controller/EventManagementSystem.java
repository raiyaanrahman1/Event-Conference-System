package Controller;
import Entity.Attendee;
import Entity.Event;
import Entity.User;
import UseCase.AttendeeSignUp;
import UseCase.EventScheduler;

import java.util.List;

public class EventManagementSystem {
    // Sends options of events to the user.
    // Allows current user to sign up to events.
    // If users have admin rights, allow them to add events.

    public String EventSignUp(Attendee attendee, Event event) {
        AttendeeSignUp currentattendee = new AttendeeSignUp(attendee);
        if (currentattendee.signUpForEvent(event)) {
            return "You have successfully signed up for this event.";
        }
        return "Sign up failed. Please try signing up for a different event.";
    }

    public List<String>
}