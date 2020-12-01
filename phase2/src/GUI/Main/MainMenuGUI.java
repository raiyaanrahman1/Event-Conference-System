package GUI.Main;

import Controller.EventManagementSystem;
import Controller.MessengerSystem;
import GUI.EventMenus.EventGUI;
import GUI.MessageMenus.MessageGUI;

public class MainMenuGUI {
    private EventGUI eventGUI;
    private MessageGUI messageGUI;


    public MainMenuGUI(EventManagementSystem eventSystem, MessengerSystem messageSystem){
        eventGUI = new EventGUI(eventSystem);
        messageGUI = new MessageGUI(messageSystem);
    }
}
