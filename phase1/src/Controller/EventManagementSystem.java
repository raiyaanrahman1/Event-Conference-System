package Controller;

//import Entity.Attendee;

import UseCase.EventManager;
import UseCase.UserManager;
import UseCase.MessageManager;
import Presenter.EventPresenter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

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
        this.presenter = new EventPresenter();
    }
    /**
     * Signs a user up for an event.
     */
    public void eventSignUp() {
        boolean failedSignUp = true;
        do {
            int eventId = presenter.promptForEventID();
            ListOfAllowedEvents();
            if (manager.signUpForEvent(eventId, user.getUserInfoList().get(0))) {
                presenter.displaySignUpSuccess();
                failedSignUp = false;
            } else {
                presenter.displaySignUpFailure();
                presenter.displayTryAgain();
            }
        } while (failedSignUp);
    }

    /**
     * Cancels a spot at the event of a user that is already signed up to the event.
     */
    public void AttendeeCancelEvent() {
        boolean invalidCancellation = true;
        do{
            int eventId = presenter.promptForEventID();
            presenter.displayEventsByUser();
            if (manager.cancelSpot(eventId, user.getUserInfoList().get(0))) { //cancelSpot should take in ID
                presenter.displayCancelSuccess();
                invalidCancellation = false;
            }else{
                presenter.displayCancelFailure();
                presenter.displayTryAgain();
        }
        } while (invalidCancellation);

    }

    /**
     * Shows a list of events a user is allowed to sign up for. A user is allowed to sign up iff they have not already
     * done so and the event capacity is not reached.
     * @return a list of IDs of the events.
     */
    public List<Integer> ListOfAllowedEvents() {
        return manager.getAllowedEvents(user.getUserInfoList().get(0));
    }

    /**
     * Gets the list of events a user is signed up for.
     * @return a list of event IDs corresponding to the events that this user has signed up for.
     */
    public List<Integer> ListOfUserEvents() {
        return manager.getEventListByAttendee(user.getUserInfoList().get(0));
    }

    /**
     * Gets the list of events an organizer has created.
     * @return a list of event IDs corresponding to the events this user organised.
     */
    public List<String> showOrganizedEvents() {
        return manager.getOrganizedEventsString(user.getUserInfoList().get(0));
    }

    public void AddEvent() {

        // if the user puts in a string, the entire program crashes --> fixed by parsing
        Scanner myObj = new Scanner(System.in);
        if (user.getUserInfoList().get(2).equals("O")) {
            boolean failedAdding = true;
            do {
                System.out.println("Enter the name of the event.");
                String eventName = myObj.next();
                System.out.println("Enter the room where the event will be held.");
                String room = myObj.next();
                System.out.println("Enter the Speaker of the event.");
                String speaker = myObj.next();
                System.out.println("Enter the Organizer of the event.");
                String org = myObj.next();
                System.out.println("Enter the capacity of the event.");
                int cap = Integer.parseInt(myObj.next());
                System.out.println("Enter the date of the event in the format yyyy-MM-dd.");
                String date = myObj.next();
                System.out.println("Enter the date of the event as HH in the 24 hour clock format.");
                LocalTime time = LocalTime.parse(myObj.next());
                int before9 = time.compareTo(LocalTime.parse("09:00:00"));
                int after5 = time.compareTo(LocalTime.parse("17:00:00"));
                LocalDateTime datetime = LocalDateTime.parse(date + time);
                if ((before9 >= 0 && !(after5 >= 0))){
                if (manager.addEvent(eventName, room, speaker, org, cap, datetime)) {
                    System.out.println("You have successfully added this event.");
                    failedAdding = false;
                }
                } else {
                    System.out.println("Failed to add event.");
                    System.out.println("Please make sure the event is between 9AM and 5PM.");
                    presenter.displayTryAgain();
                }
            } while (failedAdding);
        }
    }

    public void cancelEvent() {
        if (user.getUserInfoList().get(2).equals("O")) {
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
    }

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
                this.ListOfUserEvents();
                invalidAnswer = false;
            } else {
                presenter.displayTryAgain();
            }
        }while(invalidAnswer);
    }

    public void eventMenuSpeaker() {
        boolean invalidAnswer = true;
        // use display event menu here.
        do{
            int option =  presenter.displayEventMenuOptionsSpeaker();
            if (option == 1) {
                this.ListOfSpeakerEvents();
                invalidAnswer = false;
            } else if (option == 2) {
                this.broadcastEventSpeaker();
                invalidAnswer = false;
            } else {
                presenter.displayTryAgain();
            }
        }while(invalidAnswer);
    }

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
                    this.ListOfUserEvents();
                    invalidAnswer = false;
                } else if (option == 4) {
                    this.AddEvent();
                    invalidAnswer = false;
                } else if (option == 5) {
                    this.cancelEvent();
                    invalidAnswer = false;
                } else if (option == 6) {
                    this.showOrganizedEvents();
                    invalidAnswer = false;
                } else if (option == 7) {
                    this.broadcastEvent();
                    invalidAnswer = false;
                } else {
                    presenter.displayTryAgain();
                }
            } while (invalidAnswer);
        }
    }

    public void MainEventPage(){
        presenter.mainEventPage();
    }

    public void broadcastEvent() {
        presenter.displayEventsByOrganizer();
        int eventID = presenter.promptForEventID();
        String message = presenter.promptForMessage();
        broadcast(eventID, message);
    }

    public void broadcastEventSpeaker() {

        int eventID = presenter.promptForEventID();
        String message = presenter.promptForMessage();
        broadcast(eventID, message);
    }

    private void broadcast(int eventID, String message) {
        List<String> users = manager.getUsersInEvent(eventID);
        mess.broadcast(user.getUserInfoList().get(0), users, message);
    }

    public List<Integer> ListOfSpeakerEvents() {
        if (user.getUserInfoList().get(2).equals("S")) {
            return manager.getTalksBySpeaker(user.getUserInfoList().get(0));
        }return null;
    }

}