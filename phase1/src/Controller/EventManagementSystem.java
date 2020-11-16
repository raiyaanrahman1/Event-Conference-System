package Controller;

import Exceptions.InvalidDateException;
import UseCase.EventManager;
import UseCase.UserManager;
import UseCase.MessageManager;
import Presenter.EventPresenter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EventManagementSystem {

    private UserManager user;
    private EventPresenter presenter;
    private EventManager manager;
    private MessageManager mess;

    /**
     * Creates an EventManagementSystem and initializes its UserManager, EventManager and MessageManager.
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
        if (manager.getAllowedEvents(user.getUserInfoList().get(0)).size() > 0) {
            boolean failedSignUp = true;
            do {
                presenter.displayAllowedEvents();
                int eventId = presenter.promptForEventID();
                if (manager.signUpForEvent(eventId, user.getUserInfoList().get(0))) {
                    presenter.displaySignUpSuccess();
                    failedSignUp = false;
                } else {
                    presenter.displaySignUpFailure();
                }
            } while (failedSignUp);
        } else {
            presenter.print("There are no events for you to sign up for.");
        }
    }

    /**
     * Cancels a spot at the event of a user that is already signed up to the event.
     */
    public void AttendeeCancelEvent() {
        if (manager.getEventListByAttendee(user.getUserInfoList().get(0)).size() > 0) {
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
        } else {
            presenter.print("You have not signed up for any events.");
        }
    }

    private boolean checkDateValid(String date) throws InvalidDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate localDate = LocalDate.from(formatter.parse(date));
        if (localDate.isAfter(LocalDate.now())) {
            return true;
        } else {
            throw new InvalidDateException();
        }

    }

    /**
     * Adds a new event to event list iff this user is an Organiser.
     */
    public void AddEvent() {

        if (user.getUserInfoList().get(2).equals("O")) {
            String eventName = presenter.takeString("Enter the name of the event.");
            String room = presenter.takeString("Enter the room where the event will be held.");

            String speaker;
            boolean invalidSpeaker = true;
            do {
                speaker = presenter.takeString("Enter the Speaker of the event. " +
                        "Enter 'TBA' if the speaker is undecided.");

                if (checkSpeaker(speaker)) {
                    invalidSpeaker = false;
                } else {
                    presenter.print("Please input a valid speaker or TBA.");
                }
            }while (invalidSpeaker);


            String org = user.getUserInfoList().get(0);
            int cap = Integer.parseInt(presenter.takeString("Enter the capacity of the event."));
            boolean correct = false;
            String date = "";
            do {
                try {
                    date = presenter.takeString("Enter the date of the event in the format YYYY-MM-DD.");
                    if (checkDateValid(date)) {
                        correct = true;
                    }
                } catch (InvalidDateException | DateTimeParseException d) {
                    presenter.print("Please enter a current date that is in the correct format!");
                }
            } while (!correct);

            String timeInput = (presenter.takeString("Enter the time of the event as " +
                    "HH in the 24 hour clock format."));
            LocalTime time = LocalTime.parse(timeInput + ":00:00");
            int before9 = time.compareTo(LocalTime.parse("09:00:00"));
            int after5 = time.compareTo(LocalTime.parse("17:00:00"));
            LocalDateTime datetime = LocalDateTime.parse(date + "T" + time);
            if ((before9 >= 0 && !(after5 >= 0))) {
                if (manager.addEvent(eventName, room, speaker, org, cap, datetime)) {
                    presenter.displayAddEventSuccess();
                } else {
                    presenter.displayAddEventFailure();
                    presenter.displayTryAgain();
                }
            } else {
                presenter.displayAddEventFailure();
                presenter.print("Please make sure the event is between 9AM and 5PM.");
            }
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
                    presenter.displayEventsByOrganizer();
                    int eventId = presenter.promptForEventID();
                    if (manager.removeEvent(eventId)) {
                        presenter.displayCancelEventSuccess();
                        failedCancel = false;
                    } else {
                        presenter.displayCancelEventFailure();
                        presenter.displayTryAgain();
                    }
                } while (failedCancel);
            } else {
                presenter.print("You have not organized any events.");
            }
        }
    }

    /**
     * The event menu for an Attendee to choose from.
     */
    public void eventMenuAttendee() {
        boolean invalidAnswer = true;
        do {
            int option = presenter.displayEventMenuOptions();
            if (option == 1) {
                this.eventSignUp();
            } else if (option == 2) {
                this.AttendeeCancelEvent();
            } else if (option == 3) {
                presenter.displayEventsByUser();
            } else if (option == 4) {
                invalidAnswer = false;
            } else {
                presenter.displayTryAgain();
            }
        } while (invalidAnswer);
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
                } else if (option == 2) {
                    this.broadcastEventSpeaker();
                } else if (option == 3) {
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
                } else if (option == 2) {
                    this.AttendeeCancelEvent();
                } else if (option == 3) {
                    presenter.displayEventsByUser();
                } else if (option == 4) {
                    this.AddEvent();
                } else if (option == 5) {
                    this.cancelEvent();
                } else if (option == 6) {
                    presenter.displayEventsByOrganizer();
                } else if (option == 7) {
                    this.broadcastEventOrganizer();
                } else if (option == 8) {
                    new CreateSpeakerController().CreateSpeaker();
                } else if (option == 9) {
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
    public void broadcastEventOrganizer() {
        if (manager.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0)).size() > 0) {
            presenter.displayEventsByOrganizer();
            int eventID = presenter.promptForEventID();
            if (manager.getUsersInEvent(eventID).size() == 0) {
                presenter.print("There are no attendees for this event.");
            } else {
                String message = presenter.promptForMessage();
                broadcast(eventID, message);
                presenter.displayBroadcastSuccess();
            }
        } else {
            presenter.print("No events to broadcast to.");
        }
    }

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
        } else {
            presenter.print("You are not speaking any events.");
        }
    }

    private void broadcast(int eventID, String message) {
        List<String> users = manager.getUsersInEvent(eventID);
        mess.broadcast(user.getUserInfoList().get(0), users, message);
    }


    private boolean checkSpeaker(String input) {
        if (input.equals("TBA")) {
            return true;
        } else {
            if (user.getUserByUsername(input) != null) {
                return user.getUserByUsername(input).getUserType().equals("S");
            }
        }
        return false;
    }
}