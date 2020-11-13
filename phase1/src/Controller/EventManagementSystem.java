package Controller;

import UseCase.EventManager;
import UseCase.UserManager;
import UseCase.MessageManager;
import Presenter.EventPresenter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EventManagementSystem {

    private UserManager user;
    private EventPresenter presenter;
    private EventManager manager;
    private MessageManager mess;

    /**
     * Creates an EventManagementSystem and initializes its UserManager, EventManager and MessageManager.
     *
     */
    public EventManagementSystem(UserManager user, EventManager event, MessageManager mess) {
        this.manager = event;
        this.user = user;
        this.mess = mess;
        this.presenter = new EventPresenter(this, this.user, this.manager);
    }


    /**
     * Signs a user up for an event.
     */
    public void eventSignUp() {
        if (manager.getAllowedEvents(user.getUserInfoList().get(0)).size()>0) {
            boolean failedSignUp = true;
            do {
                presenter.displayAllowedEvents();
                int eventId = presenter.promptForEventID();
                if (manager.signUpForEvent(eventId, user.getUserInfoList().get(0))) {
                    presenter.displaySignUpSuccess();
                    failedSignUp = false;
                } else {
                    presenter.displaySignUpFailure();
                    presenter.displayTryAgain();
                }
            } while (failedSignUp);
        }
        presenter.print("There are no events for you to sign up for.");
        presenter.mainEventPage();
    }

    /**
     * Cancels a spot at the event of a user that is already signed up to the event.
     */
    public void AttendeeCancelEvent() {
        if(manager.getEventListByAttendee(user.getUserInfoList().get(0)).size() > 0) {
            boolean invalidCancellation = true;
            do {
                presenter.displayEventsByUser();
                int eventId = presenter.promptForEventID();
                if (manager.cancelSpot(eventId, user.getUserInfoList().get(0))) {
                    presenter.displayCancelSuccess();
                    invalidCancellation = false;
                } else {
                    presenter.displayCancelFailure();
                    presenter.displayTryAgain();
                }
            } while (invalidCancellation);
        }
        presenter.print("You have not signed up for any events.");
        presenter.mainEventPage();
    }

    /**
     * Adds a new event to event list iff this user is an Organiser.
     */
    public void AddEvent() {

        if (user.getUserInfoList().get(2).equals("O")) {
            boolean failedAdding = true;
            do {
                String eventName = presenter.takeString("Enter the name of the event.");
                String room = presenter.takeString("Enter the room where the event will be held.");
                String speaker = presenter.takeString("Enter the Speaker of the event.");
                // want to prompt user to choose from available rooms
                String org = user.getUserInfoList().get(0);
                int cap = Integer.parseInt(presenter.takeString("Enter the capacity of the event."));
                String date = presenter.takeString("Enter the date of the event in the format yyyy-MM-dd.");
                LocalTime time = LocalTime.parse(presenter.takeString("Enter the date of the event as " +
                        "HH in the 24 hour clock format."));
                int before9 = time.compareTo(LocalTime.parse("09:00:00"));
                int after5 = time.compareTo(LocalTime.parse("17:00:00"));
                LocalDateTime datetime = LocalDateTime.parse(date + time);
                if ((before9 >= 0 && !(after5 >= 0))){
                    if (manager.addEvent(eventName, room, speaker, org, cap, datetime)) {
                        presenter.displayAddEventSuccess();
                        failedAdding = false;
                    }else {
                        presenter.displayAddEventFailure();
                        presenter.displayTryAgain();
                    }
                } else {
                    presenter.displayAddEventFailure();
                    presenter.print("Please make sure the event is between 9AM and 5PM.");
                    presenter.displayTryAgain();
                }
            } while (failedAdding);
        }
    }

    /**
     * Cancels event if the user is an organizer.
     */
    public void cancelEvent() {
        if (user.getUserInfoList().get(2).equals("O")) {
            if (manager.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0)).size() > 0) {
                boolean failedCancel = true;
                do {
                    int eventId = presenter.promptForEventID();
                    presenter.displayEventsByOrganizer();
                    if (manager.removeEvent(eventId)) {
                        presenter.displayCancelEventSuccess();
                        failedCancel = false;
                    } else {
                        presenter.displayCancelEventFailure();
                        presenter.displayTryAgain();
                    }
                } while (failedCancel);
            }
            presenter.print("You have not organized any events.");
            presenter.mainEventPage();
        }
    }

    /**
     * The event menu for an Attendee to choose from.
     */
    public void eventMenuAttendee() {
        boolean invalidAnswer = true;
        do{
            int option =  presenter.displayEventMenuOptions();
            if (option == 1) {
                this.eventSignUp();
                invalidAnswer = false;
            } else if (option == 2) {
                this.AttendeeCancelEvent();
                invalidAnswer = false;
            } else if (option == 3) {
                presenter.displayEventsByUser();
                invalidAnswer = false;
            } else {
                presenter.displayTryAgain();
            }
        }while(invalidAnswer);
    }

    /**
     * The event menu for an Speaker to choose from.
     */
    public void eventMenuSpeaker() {
        if (user.getUserInfoList().get(2).equals("S")) {
            boolean invalidAnswer = true;
            do {
                int option = presenter.displayEventMenuOptionsSpeaker();
                if (option == 1) {
                    presenter.displayEventsBySpeaker();
                    invalidAnswer = false;
                } else if (option == 2) {
                    this.broadcastEventSpeaker();
                    invalidAnswer = false;
                } else {
                    presenter.displayTryAgain();
                }
            } while (invalidAnswer);
        }
    }

    /**
     * The event menu for an Organizer to choose from.
     */
    public void eventMenuOrganizer() {
        if (user.getUserInfoList().get(2).equals("O")) {
            boolean invalidAnswer = true;
            do {
                int option = presenter.displayEventMenuOptionsOrganizer();
                if (option == 1) {
                    this.eventSignUp();
                    invalidAnswer = false;
                } else if (option == 2) {
                    this.AttendeeCancelEvent();
                    invalidAnswer = false;
                } else if (option == 3) {
                    presenter.displayEventsByUser();
                    invalidAnswer = false;
                } else if (option == 4) {
                    this.AddEvent();
                    invalidAnswer = false;
                } else if (option == 5) {
                    this.cancelEvent();
                    invalidAnswer = false;
                } else if (option == 6) {
                    presenter.displayEventsByOrganizer();
                    invalidAnswer = false;
                } else if (option == 7) {
                    this.broadcastEventOrganizer();
                    invalidAnswer = false;
                } else if (option == 8) {
                    new CreateSpeakerController().CreateSpeaker();
                    invalidAnswer = false;
                } else {
                    presenter.displayTryAgain();
                }
            } while (invalidAnswer);
        }
    }

    /**
     * Allows an Organizer to broadcast a message to all Attendees of a specific event they organized.
     */
    public void broadcastEventOrganizer(){
        if(manager.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0)).size()>0) {
            presenter.displayEventsByOrganizer();
            int eventID = presenter.promptForEventID();
            String message = presenter.promptForMessage();
            broadcast(eventID, message);
            presenter.displayBroadcastSuccess();
        }else {
            presenter.print("No events to broadcast to.");
        }
    }
//    /**
//     * Allows an Organizer to broadcast a message to all Attendees of a specific event they organized.
//     */
//    public void broadcastOrganizerToSpeaker() {
//
//        presenter.displayEventsByOrganizer();
//        int eventID = presenter.promptForEventID();
//        String message = presenter.promptForMessage();
//        broadcast(eventID, message);
//    }

    /**
     * Allows a Speaker to broadcast a message to all Attendees of a specific event they are speaking at.
     */
    public void broadcastEventSpeaker() {
        if (manager.getTalksBySpeaker(user.getUserInfoList().get(0)).size() > 0) {
            presenter.displayEventsBySpeaker();
            int eventID = presenter.promptForEventID();
            String message = presenter.promptForMessage();
            broadcast(eventID, message);
            presenter.displayBroadcastSuccess();
        }else{
            presenter.print("You are not speaking any events.");
        }
    }

    /**
     * Broadcasts a message to the users of a specific event.
     */
    private void broadcast(int eventID, String message) {
        List<String> users = manager.getUsersInEvent(eventID);
        mess.broadcast(user.getUserInfoList().get(0), users, message);
    }

//    /**
//     * Broadcasts a message to the Speakers of all the events an Organizer organized.
//     */
//    private void broadcastToSpeakers(int eventID, String message) {
//        //for all events in organizer's events
//        //get and add speakers into speaker list
//        //mess.broadcast
//        List<Integer> organizedEvents = manager.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0));
//        List<String> speakerList;
//        }



}