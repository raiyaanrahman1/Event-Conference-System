package Controller;

//import Entity.Attendee;

import UseCase.EventManager;
import UseCase.UserManager;
import Presenter.EventPresenter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class EventManagementSystem {

    private UserManager user;
    private EventPresenter presenter;

    EventManager manager;


    public void eventSignUp() {
        // showListOfAllowedEvents and promptIDEventSignUp
        boolean failedSignUp = true;
        do {
            int eventId = presenter.promptForEventID();
            ListOfAllowedEvents();
            if (manager.signUpForEvent(eventId, user.getUserInfoList().get(0))) { // signUpForEvent should take in ID
                // displaySignUpOutcome
                presenter.displaySignUpSuccess();
                failedSignUp = false;
            } else {
                presenter.displaySignUpFailure();
                presenter.displayTryAgain();
            }
            // maybe instead, the failure should state, try again, and loop over.
        } while (failedSignUp);
    }

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

    public List<Integer> ListOfAllowedEvents() {
        return manager.getAllowedEvents(user.getUserInfoList().get(0));
    }

    public List<Integer> ListOfUserEvents() {
        return manager.getEventListByAttendee(user.getUserInfoList().get(0));
    }

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
                String eventName = myObj.nextLine();
                System.out.println("Enter the room where the event will be held.");
                String room = myObj.nextLine();
                System.out.println("Enter the Speaker of the event.");
                String speaker = myObj.nextLine();
                System.out.println("Enter the Organizer of the event.");
                String org = myObj.nextLine();
                System.out.println("Enter the capacity of the event.");
                int cap = Integer.parseInt(myObj.nextLine());
                System.out.println("Enter the date of the event in the format yyyy-MM-dd.");
                String date = myObj.nextLine();
                System.out.println("Enter the date of the event as HH in the 24 hour clock format.");
                LocalTime time = LocalTime.parse(myObj.nextLine());
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
        // use display event menu here.
        do{
            int option =  presenter.displayEventMenuOptions();
            if (option == 1) {
                this.eventSignUp();
                invalidAnswer = false;
            } else if (option == 2) {
                this.AttendeeCancelEvent();
                invalidAnswer = false;
            } else if (option == 3) {
                this.ListOfAllowedEvents();
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
                    this.AddEvent();
                    invalidAnswer = false;
                } else if (option == 2) {
                    this.cancelEvent();
                    invalidAnswer = false;
                } else if (option == 3) {
                    this.showOrganizedEvents();
                    invalidAnswer = false;
                } else {
                    //maybe print try again or something
                    presenter.displayTryAgain();
                }
            } while (invalidAnswer);
        }
    }

    public void MainEventPage(){
        presenter.mainEventPage();
    }
}