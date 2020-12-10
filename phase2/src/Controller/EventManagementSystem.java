package Controller;

import Gateway.IGateway2;
import UseCase.EventGetter;
import UseCase.EventManager;
import UseCase.UserManager;
import UseCase.MessageManager;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventManagementSystem {

    private final UserManager user;
    private final EventManager manager;
    IGateway2 eventListGateway;
    private final EventGetter getter;
    private final MessageManager mess;
    private List<List<String>> eventLists = new ArrayList<>();

    /**
     * Creates an EventManagementSystem and initializes its UserManager, EventManager,
     * CreateSpeakerController and MessageManager.
     */
    public EventManagementSystem(UserManager user, EventManager event, MessageManager mess, IGateway2 eventListGateway) {
        this.manager = event;
        this.user = user;
        this.mess = mess;
        getter = manager.eventGetter;
        this.eventListGateway = eventListGateway;
    }


    public List<List<String>> getEventLists(){
        return eventLists;
    }


    /**
     * Gets the type of the logged in user
     * @return the type of the logged in user
     */
    public String getUserType(){
        return user.getUserInfoList().get(2);
    }


    /**
     * Cancels event if the user is an organizer.
     */
    public boolean deleteEvent(int eventId) {
        if (user.getUserInfoList().get(2).equals("O") &&
                getter.getOrganizedEventsByOrganizer(user.getUserInfoList().get(0)).size() > 0 &&
                manager.removeEvent(eventId)) {
            manager.storeEvents(eventListGateway);
            return true;
        }
        return false;
    }

    /**
     * Edits the capacity of an event
     * @param eventID the id of the event we want to edit
     * @param capacity the event's new capacity
     * @return true if the event's capacity is changed to the new capacity
     */
    public boolean editEvent(int eventID, int capacity){
        if (manager.changeRoomCapacity(eventID, capacity)){
            return true;
        }
        return false;
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
                result.add(getter.getEventString(eventID));
            }
        }
        return result;
    }


    /**
     * Allows a user to sign up for an available event.
     */
    public boolean eventSignUp(int eventid){
        if (manager.signUpForEvent(eventid, user.getUserInfoList().get(0),
                user.getUserType(user.getUserInfoList().get(0)))) {
            manager.storeEvents(eventListGateway);
            return true;
        }
        return false;
    }


    /**
     * Allows a user to cancel an event they have signed up for.
     */
    public boolean attendeeCancelEvent(int eventid){
        if (manager.cancelSpot(eventid, user.getUserInfoList().get(0))) {
            manager.storeEvents(eventListGateway);
            return true;
            }
        return false;
    }


    /**
     * Allows an organiser to add an event to the event list.
     * @param eventName the name of the event
     * @param room the name of the room
     * @param ListOfSpeaker the list of Speakers for this event
     * @param cap the capacity of the event
     * @param inputStart the start time of the event
     * @param inputEnd the end time of the event
     */
    public boolean addEvent(String eventName, String room, List<String> ListOfSpeaker, int cap,
                                   LocalDateTime inputStart, LocalDateTime inputEnd){
        if (!user.getUserInfoList().get(2).equals("O")) {
            return false;
        }
        List<String> speaker = checkValidSpeaker(ListOfSpeaker);
        String org = user.getUserInfoList().get(0);
        return manager.addEvent(eventName, room, speaker, org, cap, inputStart, inputEnd);

    }


    /**
     * Allows an organiser to add a VIP event to the event list.
     * @param eventName the name of the event
     * @param room the name of the room
     * @param ListOfSpeaker the list of Speakers for this event
     * @param cap the capacity of the event
     * @param inputStart the start time of the event
     * @param inputEnd the end time of the event
     */
    public boolean addVIPEvent(String eventName, String room, List<String> ListOfSpeaker, int cap,
                                   LocalDateTime inputStart, LocalDateTime inputEnd){
        if (!user.getUserInfoList().get(2).equals("O")) {
            return false;
        }
        List<String> speaker = checkValidSpeaker(ListOfSpeaker);
        String org = user.getUserInfoList().get(0);
        if (!speaker.isEmpty()) {
            return manager.addVIPEvent(eventName, room, speaker, org, cap, inputStart, inputEnd);
        } else {
            return false;
        }
    }


    /**
     * Checks whether the input time is between 9 am and 5 pm.
     * @param inputTime the time to be checked
     */
    public LocalTime checkStartTime (String inputTime){
        LocalTime time = LocalTime.parse(inputTime + ":00");
        int before9 = time.compareTo(LocalTime.parse("09:00:00"));
        int after5 = time.compareTo(LocalTime.parse("17:00:00"));
        if ((before9 >= 0 && !(after5 >= 0))) {
            return LocalTime.parse(inputTime);
        } else {
            return null;
        }
    }


    /**
     * Checks whether the input time is between 10 am and 6 pm.
     * @param inputTime the time to be checked
     */
    public LocalTime checkEndTime (String inputTime){
        LocalTime time = LocalTime.parse(inputTime + ":00");
        int before10 = time.compareTo(LocalTime.parse("10:00:00"));
        int after6 = time.compareTo(LocalTime.parse("18:00:00"));
        if ((before10 >= 0 && !(after6 >= 0))) {
            return LocalTime.parse(inputTime);
        }
        else {
            return null;
        }
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


    /**
     * Sends a message to all the participants of a certain event.
     * @param eventID the id of the event
     * @param message the message to be sent in the broadcast
     */
    public void broadcast(int eventID, String message) {
        List<String> users = getter.getAttendeesInEvent(eventID);
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


    /**
     * Gets the list of Speakers
     * @return a list of Speakers
     */
    public List<String> getSpeakers(){
        return user.getSpeakers();
    }


    /**
     * builds the eventsLists variables where we have:
     *  - the list of all events in toString form
     *  - the list of events this user has signed up for in toString form
     *  - the list of events this Organizer has created in toString form
     *  - the list of events this Speaker is speaking at in toString form
     */
    public void makeListsEvents(){
        eventLists.add(getAllEventList());
        eventLists.add(getAttendeeEventList());
        eventLists.add(getOrganizerEventList());
        eventLists.add(getSpeakerEventList());
    }


    /**
     * Gets the list of events this Speaker is speaking at
     * @return the list of events this Speaker is speaking at in toString form
     */
    private List<String> getSpeakerEventList(){
        return getter.filterEventsBySpeaker(user.getUserInfoList().get(0));
    }


    /**
     *
     * @return the list of events this Speaker is speaking at in toString form
     */
    private List<String> getAllEventList() {
            return formatEventString(getter.getAllEventIDs());
    }


    /**
     * Gets the list of events this Organizer has created
     * @return the list of events this Organizer has created in toString form
     */
    private List<String> getOrganizerEventList(){
        return getter.filterEventsByOrganizer(user.getUserInfoList().get(0));
    }


    /**
     * Gets the list of events this user has signed up for
     * @return the list of events this user has signed up for in toString form
     */
    private List<String> getAttendeeEventList(){
        if (user.getUserInfoList().get(0) != null){
            return formatEventString(getter.getEventListByAttendee(user.getUserInfoList().get(0)));
        }
        else {
            return new ArrayList<>();
        }
    }


    /**
     * Builds a list of strings representing the events that occur at a certain date
     * @param date of the events we want
     * @return  a list of strings representing the events that occur at a certain date
     */
    public List<String> filterEventDate(LocalDate date) {
        return getter.filterEventsByDate(date);
    }


    /**
     * Builds a list of strings representing the events that have a certain speaker
     * @param speaker of the events we want
     * @return  a list of strings representing the events that have a certain speaker
     */
    public List<String> filterSpeakerDate(String speaker) {
        return getter.filterEventsBySpeaker(speaker);
    }

}