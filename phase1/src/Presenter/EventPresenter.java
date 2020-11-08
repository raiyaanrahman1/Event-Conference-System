package Presenter;

import Controller.EventManagementSystem;
import UseCase.UserManager;

import java.util.List;

public class EventPresenter {
    private UserManager user;
    private EventManagementSystem events;

    public void EventManagementSystem(UserManager user, EventManagementSystem events){
        this.user = user;
        this.events = events;
    }

    public void mainEventPage(){ //to be displayed for the overall menu page
        attendeeEventMenu();
        organizerEventMenu();
    }
    public void organizerEventMenu() {
        List<String> userInfo = user.getUserInfoList();
        if (userInfo.get(2).equals("O")) {
            events.eventMenu();
        }
        System.out.println("You do not have access.");
    }

    public void attendeeEventMenu(){
        events.eventMenu();
    }
}
