package Controller;

import Exceptions.InvalidDateException;
import Exceptions.NoSuchEventException;
import UseCase.EventManager;
import UseCase.UserManager;
import UseCase.MessageManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EventManagementSystem {

    private final UserManager user;
    private final EventManager manager;
    private final MessageManager mess;
    private CreateUserController userController;

    /**
     * Creates an EventManagementSystem and initializes its UserManager, EventManager,
     * CreateSpeakerController and MessageManager.
     */
    public EventManagementSystem(UserManager user, EventManager event, MessageManager mess,
                                 CreateUserController userController) {
        this.manager = event;
        this.user = user;
        this.mess = mess;
        this.userController = userController;
    }


    /**
     * Signs a user up for an event.
     */
    public void eventSignUp() {
        if (manager.getAllowedEvents(user.getUserInfoList().get(0),
                user.getUserType(user.getUserInfoList().get(0))).size() == 0) {
            System.out.println("There are no available events");
        } else {
            eventSignUpHelper();
        }
    }

    /**
     * Cancels a spot at the event of a user that is already signed up to the event.
     */
    public void attendeeCancelEvent() {
        if (manager.getEventListByAttendee(user.getUserInfoList().get(0)).size() == 0) {
            System.out.println("You have not signed up for any events");
        }else{
        attendeeCancelEventHelper();
        }
    }

    /**
     * Adds a new event to event list iff this user is an Organiser.
     */
    public void addEvent() {
        String eventName = ""; // placeholder
        String room = ""; // placeholder
        List<String> ListOfSpeaker = new ArrayList<>(); // placeholder
        int cap = 0; // placeholder
        String inputDate = ""; // placeholder
        String inputStart = ""; // placeholder
        String inputEnd = ""; // placeholder
        LocalTime inputStartTime = checkStartTime(inputStart);
        LocalTime inputEndTime = checkEndTime(inputEnd);
        if (addEventHelper(eventName, room, ListOfSpeaker, cap, inputDate, inputStartTime, inputEndTime)){
            System.out.println("Successfully added event");
        }
        System.out.println("Unsuccessfully added event");
    }
    /**
     * Adds a new VIP event to event list iff this user is an Organiser.
     */
    public void addVIPEvent()  {
        String eventName = ""; // placeholder
        String room = ""; // placeholder
        List<String> ListOfSpeaker = new ArrayList<>(); // placeholder
        int cap = 0; // placeholder
        String inputDate = ""; // placeholder
        String inputStart = ""; // placeholder
        String inputEnd = ""; // placeholder
        LocalTime inputStartTime = checkStartTime(inputStart);
        LocalTime inputEndTime = checkEndTime(inputEnd);
        if (addVIPEventHelper(eventName, room, ListOfSpeaker, cap, inputDate, inputStartTime, inputEndTime)){
            System.out.println("Successfully added VIP event");
        }
        System.out.println("Unsuccessfully added VIP event");
    }
    public void changeCap() {
        //if user is organizer
        //if (manager.changeRoomCapacity(eventID, newCap)) System.out.println("Successfully changed capacity of event");
        //else System.out.println("Unsuccessfully changed capacity of event");
    }

    /**
     * Cancels event if the user is an organizer.
     */
    public void cancelEvent() {
        boolean invalid = true;
        if (user.getUserInfoList().get(2).equals("O")) {
            if (manager.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0)).size() > 0) {
                do {
                    getOrganizerEventList(user.getUserInfoList().get(0));
                    int eventId = 0; //placeholder
                    if (manager.removeEvent(eventId)) {
                        invalid = false;
                        System.out.println("You have successfully cancelled this event.");
                    }else {
                        System.out.println("You have unsuccessfully cancelled this event.");
                    }
                } while (invalid);
            } else {
                System.out.println("You have not organized any events.");
            }
        }
    }

    /**
     * Allows an Organizer to broadcast a message to all Attendees of a specific event they organized.
     */
    public void broadcastEventOrganizer() {
        if (manager.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0)).size() == 0) {
            System.out.println("You have not created any events");
        }else{
            broadcastEventOrganizerHelper();
        }
    }

    /**
     * Allows a Speaker to broadcast a message to all Attendees of a specific event they are speaking at.
     */
    public List<String> getBroadcastEventSpeaker() {
        List<Integer> eventListByIDs = manager.getTalksBySpeaker(user.getUserInfoList().get(0));
        if (eventListByIDs.size() == 0) {
            System.out.println("You are not speaking at any events");
            return null;
        }else{
            return formatEventList(eventListByIDs);
        }
    }

    //formats the list of events for the getBroadcastEventSpeaker method
    private List<String> formatEventList(List<Integer> eventList){
        List<String> listEvents = new ArrayList<>();
        if (eventList.size()> 0) {
            for (Integer eventID : eventList) {
                listEvents.add(manager.getEventString(eventID));
            }
        }
        return listEvents;
    }

    /**
     * Allows a Speaker to broadcast a message to all Attendees of a specific event they are speaking at.
     */
    public void broadcastEventSpeaker() {
        if (manager.getTalksBySpeaker(user.getUserInfoList().get(0)).size() == 0) {
            System.out.println("You are not speaking at any events");
        }else{
            broadcastEventSpeakerHelper();
        }
    }
    /**
     * Takes in
     * @param eventList , a list of ID of events,
     * and prints the corresponding toStrings of the events.
     */
    public List<String> formatEventString(List<Integer> eventList){
        List<String> result = new ArrayList<>();
        if (eventList.size()> 0) {
            for (Integer eventID : eventList) {
                result.add(manager.getEventString(eventID));
            }
        }
        return result;
    }

    /**
     * Allows an Organizer to reschedule a specific event from the list of events they organized.
     */

    public void rescheduleEvent() throws NoSuchEventException {
        if (manager.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0)).size() == 0) {
            System.out.println("You have not created any events");
        }else{
            int eventId = 0;
            String eventName = "";
            String roomName = "";
            List<String> speakerList = new ArrayList<String>();
            int roomCap = 0;
            String organizer = "";
            LocalDate date = LocalDate.parse("");
            LocalTime start = LocalTime.parse("");
            LocalDateTime startTime = LocalDateTime.parse(date + "T" + start);
            LocalTime end = LocalTime.parse("");
            LocalDateTime endTime = LocalDateTime.parse(date + "T" + end);

            rescheduleEventHelper(eventId, eventName, roomName, speakerList, roomCap, organizer, startTime, endTime);
        }
    }

    //HELPER METHODS
    private void eventSignUpHelper(){
            boolean failedSignUp = true;
            do {
            getAvailableEventList(user.getUserInfoList().get(0)); // present allowed events
            int eventId = 0; //placeholder
                if (manager.signUpForEvent(eventId, user.getUserInfoList().get(0),
                        user.getUserType(user.getUserInfoList().get(0)))) {
                    failedSignUp = false;
                System.out.println("Successfully signed up for event");
        } else {
                System.out.println("Unsuccessfully signed up for an event");
        }
    }
        while (failedSignUp);
    }

    private void attendeeCancelEventHelper(){
            boolean invalidCancellation = true;
            do {
            getAttendeeEventList(user.getUserInfoList().get(0));
            int eventId = 0; //placeholder
                if (manager.cancelSpot(eventId, user.getUserInfoList().get(0))) {
                    invalidCancellation = false;
                System.out.println("Successfully cancelled event");
                } else {
                System.out.println("Unsuccessfully cancelled event");
                }
            } while (invalidCancellation);
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

    private boolean addEventHelper(String eventName, String room, List<String> ListOfSpeaker, int cap, String inputDate,
                                   LocalTime inputStart, LocalTime inputEnd){
        if (!user.getUserInfoList().get(2).equals("O")) {
            return false;
        }
        List<String> speaker = checkValidSpeaker(ListOfSpeaker);
        String org = user.getUserInfoList().get(0);
        LocalDateTime startTime = formatDateTime(inputStart, inputDate);
        LocalDateTime endTime = formatDateTime(inputEnd, inputDate);
        if (!speaker.isEmpty()) {
            return manager.addEvent(eventName, room, speaker, org, cap, startTime, endTime);
                } else {
            return false;
        }
    }
    private boolean addVIPEventHelper(String eventName, String room, List<String> ListOfSpeaker, int cap, String inputDate,
                                   LocalTime inputStart, LocalTime inputEnd){
        if (!user.getUserInfoList().get(2).equals("O")) {
            return false;
        }
        List<String> speaker = checkValidSpeaker(ListOfSpeaker);
        String org = user.getUserInfoList().get(0);
        LocalDateTime startTime = formatDateTime(inputStart, inputDate);
        LocalDateTime endTime = formatDateTime(inputEnd, inputDate);
        if (!speaker.isEmpty()) {
            return manager.addVIPEvent(eventName, room, speaker, org, cap, startTime, endTime);
        } else {
            return false;
        }
    }

    private LocalDateTime formatDateTime(LocalTime time, String inputDate) {
        LocalDate date = checkDate(inputDate);

        return LocalDateTime.parse(date + "T" + time);
    }

    private LocalTime checkStartTime (String inputTime){
        LocalTime time = LocalTime.parse(inputTime + ":00:00");
        int before9 = time.compareTo(LocalTime.parse("09:00:00"));
        int after5 = time.compareTo(LocalTime.parse("17:00:00"));
        boolean incorrect = true;
        do if ((before9 >= 0 && !(after5 >= 0))) {
            incorrect = false;
            return LocalTime.parse(inputTime);
        } else {
            System.out.println("Input a time between 9 and 17.");
        }
        while (incorrect);
        return null;
    }

    private LocalTime checkEndTime (String inputTime){
        LocalTime time = LocalTime.parse(inputTime + ":00:00");
        int before10 = time.compareTo(LocalTime.parse("10:00:00"));
        int after6 = time.compareTo(LocalTime.parse("18:00:00"));
        boolean incorrect = true;
        do if ((before10 >= 0 && !(after6 >= 0))) {
            incorrect = false;
            return LocalTime.parse(inputTime);
                } else {
            System.out.println("Input a time between 10 and 18.");
        }
        while (incorrect);
        return null;
                }

    private LocalDate checkDate(String inputDate){
            boolean correct = false;
            do {
                try {
                if (checkDateValid(inputDate)) {
                        correct = true;
                    return LocalDate.parse(inputDate);
                    }
                } catch (InvalidDateException | DateTimeParseException d) {
                System.out.println("Please enter a current date that is in the correct format!");
                }
            } while (!correct);
        return null;
        }

    private List<String> checkValidSpeaker(List<String> ListOfSpeaker){
        List<String> validSpeaker = new ArrayList<>();
        if (ListOfSpeaker.size() > 0) {
            for (String speaker : ListOfSpeaker) {
                if (checkSpeaker(speaker)) {
                    validSpeaker.add(speaker);
        }
    }
            }
        return validSpeaker;
    }

    private void broadcastEventOrganizerHelper(){
        getOrganizerEventList(user.getUserInfoList().get(0));
        int eventID = 0; //placeholder
            if (manager.getAttendeesInEvent(eventID).size() == 0) {
            System.out.println("There are no attendees for this event.");
            } else {
            String message = "message"; // placeholder
                broadcast(eventID, message);
            System.out.println("Successful broadcast");
            }
    }

    private void broadcastEventSpeakerHelper(){
        getSpeakerEventList(user.getUserInfoList().get(0));
        int eventID = 0; // placeholder
        if (manager.getAttendeesInEvent(eventID).size() == 0) {
            System.out.println("There are no attendees for this event.");
        } else {
            String message = "message"; // placeholder
            broadcast(eventID, message);
        }
    }

    public void broadcast(int eventID, String message) {
        List<String> users = manager.getAttendeesInEvent(eventID);
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

    private void rescheduleEventHelper(int eventId, String eventName, String room, List<String> speakerList,
                                       int cap, String org, LocalDateTime start, LocalDateTime end) throws NoSuchEventException {
        manager.setName(eventId, eventName);
        manager.setRoom(eventId, room);
        manager.setSpeakers(eventId, speakerList);
        manager.changeRoomCapacity(eventId, cap);
        manager.setOrganizer(eventId, org);
        manager.setStartTime(eventId, start);
        manager.setEndTime(eventId, end);

    }

    public List<String> getAttendeeEventList(String username){
        return formatEventString(manager.getEventListByAttendee(username));
    }

    public List<String> getSpeakerEventList(String username){
        return manager.filterEventsBySpeaker(username);
    }

    public List<String> getAvailableEventList(String username){
        return formatEventString(manager.getAllowedEvents(username, user.getUserType(username)));
    }

    public List<String> getOrganizerEventList(String username){
        return manager.filterEventsByOrganizer(username);
    }



// NO NEED MENU --> USE GUI
//    /**
//     * The event menu for an Attendee to choose from.
//     */
//    public void eventMenuAttendee() {
//        boolean invalidAnswer = true;
//        do {
//            int option = 0; // placeholder
//            if (option == 1) {
//                this.eventSignUp();
//            } else if (option == 2) {
//                this.attendeeCancelEvent();
//            } else if (option == 3) {
//                presenter.displayEventsByUser();
//            } else if (option == 4) {
//                invalidAnswer = false;
//            } else {
//                presenter.displayTryAgain();
//            }
//        } while (invalidAnswer);
//    }

    // NO NEED MENU --> USE GUI
//    /**
//     * The event menu for an Speaker to choose from.
//     */
//    public void eventMenuSpeaker() {
//        if (user.getUserInfoList().get(2).equals("S")) {
//            boolean invalidAnswer = true;
//            do {
//                int option = presenter.displayEventMenuOptionsSpeaker();
//                if (option == 1) {
//                    presenter.displayEventsBySpeaker();
//                } else if (option == 2) {
//                    this.broadcastEventSpeaker();
//                } else if (option == 3) {
//                    invalidAnswer = false;
//                } else {
//                    presenter.displayTryAgain();
//                }
//            } while (invalidAnswer);
//        }
//    }

    // NO NEED MENU --> USE GUI
//    /**
//     * The event menu for an Organizer to choose from.
//     */
//    public void eventMenuOrganizer() {
//        if (user.getUserInfoList().get(2).equals("O")) {
//            boolean invalidAnswer = true;
//            do {
//                int option = presenter.displayEventMenuOptionsOrganizer();
//                if (option == 1) {
//                    this.eventSignUp();
//                } else if (option == 2) {
//                    this.attendeeCancelEvent();
//                } else if (option == 3) {
//                    presenter.displayEventsByUser();
//                } else if (option == 4) {
//                    this.addEvent();
//                } else if (option == 5) {
//                    this.cancelEvent();
//                } else if (option == 6) {
//                    presenter.displayEventsByOrganizer();
//                } else if (option == 7) {
//                    this.broadcastEventOrganizer();
//                } else if (option == 8) {
//                    userController.CreateSpeaker();
//                } else if (option == 9) {
//                    userController.CreateAttendee()
//                } else if (option == 10) {
//                    userController.CreateVIP();
//                } else if (option == 11) {
//                    invalidAnswer = false;
//                } else {
//                    presenter.displayTryAgain();
//                }
//            } while (invalidAnswer);
//        }
//    }
}