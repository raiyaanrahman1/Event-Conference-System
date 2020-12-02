package GUI.EventMenus;

import Controller.EventManagementSystem;

public class EventGUI implements IEventView {
    private EventManagementSystem eventSystem;
    public EventGUI(EventManagementSystem eventSystem){
        this.eventSystem = eventSystem;
    }
}
