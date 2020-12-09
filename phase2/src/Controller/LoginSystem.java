package Controller;

import Entity.Attendee;
import Entity.Organizer;
import Gateway.IGateway;
import Gateway.IGateway2;
import Gateway.InfoFileGateway;
import Gateway.LoginFileGateway;
import UseCase.*;

/**
 * Class which manages the Logging in and Signing up of a User
 */

public class LoginSystem {
    IGateway loginFileGateway = new LoginFileGateway("phase2/src/Controller/LogInInformation.txt");
    IGateway2 messageListInformationGateway = new InfoFileGateway("phase2/src/Controller/MessageListInformation.txt");
    IGateway2 archiveFileGateway = new InfoFileGateway("phase2/src/Controller/archive.txt");
    IGateway2 contactListGateway = new InfoFileGateway("phase2/src/Controller/contactListInfo.txt");
    IGateway2 eventListGateway = new InfoFileGateway("phase2/src/Controller/eventListInfo.txt");
    UserManager userManager = new UserManager(loginFileGateway, contactListGateway);
    EventManager eventMan = new EventManager(eventListGateway);
    MessageCollection messages = new MessageMap(messageListInformationGateway);
    MessageCollection archive = new MessageMap(archiveFileGateway);
    MessageManager messageMan = new MessageManager(messages, archive);
    MessengerSystem msgSys = new MessengerSystem(userManager, messageMan);
    EventManagementSystem eventSys = new EventManagementSystem(userManager, eventMan, messageMan, eventListGateway);


    /**
     * Creates a new LoginSystem instance.
     */
    public LoginSystem() {
    }

    /**
     * Gets the EventManagementSystem
     *
     * @return event management system
     */
    public EventManagementSystem getEventSys() {
        return this.eventSys;
    }

    /**
     * Gets the MessageSystem
     *
     * @return message management system
     */
    public MessengerSystem getMsgSys() {
        return this.msgSys;
    }


    /**
     * Signs out the user
     */
    public void signOut() {
        messageMan.storeMessages(messageListInformationGateway, archiveFileGateway);
        userManager.storeContacts();
        eventMan.storeEvents(eventListGateway);
    }

    /**
     * Determines if given username and password can log in
     */
    public boolean canLogin(String username, String password) {
        return userManager.logInUser(username, password);
    }

    /**
     * Determines if given username exists in list of users
     */
    public boolean isUser(String username) {
        return userManager.getSignedUpUsers().contains(username);
    }

    /**
     * Signs in the user
     */
    public void signUpUser(String username, String password, String userType) {
        userManager.CreateUser(username, password, userType);
    }

}