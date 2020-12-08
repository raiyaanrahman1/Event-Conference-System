package GUI.EventMenus;

import Controller.EventManagementSystem;

public class EventGUI implements IEventView {
    private EventManagementSystem eventSystem;

    /**
     * Creates a EventGUI and initializes its EventManagementSystem
     *
     * @param eventSystem the EventManagementSystem that the EventGUI communicates with
     */
    public EventGUI(EventManagementSystem eventSystem){
        this.eventSystem = eventSystem;
    }
}
