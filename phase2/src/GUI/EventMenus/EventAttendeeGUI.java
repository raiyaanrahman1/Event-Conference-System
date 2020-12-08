package GUI.EventMenus;

import Controller.EventManagementSystem;
import GUI.Main.PanelStack;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EventAttendeeGUI {
    private SortGUI yourEventSortGUI;
    private SortGUI eventSortGUI;
    private PanelStack panelStack;
    private JList eventsJList = new JList();
    private JList yourEventsJList = new JList();

    private JLabel eventsJLabel = new JLabel("Events");
    private JLabel yourEventsJLabel = new JLabel("Your Events");

    private JButton signUpButton = new JButton("Sign Up");
    private JButton sort1Button = new JButton("Sort");
    private JButton sort2Button = new JButton("Sort");
    private JButton cancelButton = new JButton("Cancel");
    private JButton backButton = new JButton("Back");

    private JPanel eventsButtonPanel = new JPanel();
    private JPanel yourEventsButtonPanel = new JPanel();
    private JPanel eventsPanel = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JPanel eventsJlistPanel = new JPanel();
    private JPanel yourEventsJlistPanel = new JPanel();
    private JPanel yourEventsPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();

    private EventPanelBuilder panelBuilder = new EventPanelBuilder();

    private DefaultListModel<String> eventsListModel = new DefaultListModel<>();
    private DefaultListModel<String> yourEventsListModel = new DefaultListModel<>();

    private EventManagementSystem eventSystem;

    private JScrollPane eventsListScroller = new JScrollPane();
    private JScrollPane yourEventsListScroller = new JScrollPane();

    /**
     * Creates an EventAttendeeGUI and initializes its EventManagementSystem and the panelStack
     *
     * @param eventSystem the EventManagementSystem that the AddEventGUI communicates with
     * @param panelStack the stack containing all the panels that have been loaded
     */
    public EventAttendeeGUI(EventManagementSystem eventSystem, PanelStack panelStack) {
        this.eventSystem = eventSystem;
        this.panelStack = panelStack;
        eventSortGUI = new SortGUI(eventSystem, eventsListModel, panelStack);
        yourEventSortGUI = new SortGUI(eventSystem, yourEventsListModel, panelStack);
        backButtonListen();
        sortButtonListener();
        sort2ButtonListener();
    }

    /**
     * Builds and loads the Event Page for Attendees
     *
     * @return The Event panel for Attendees
     */
    public JPanel startEventPage() {
        // NorthPanel:
        panelBuilder.buildAttendeeNorthPanel(northPanel, eventsPanel, yourEventsPanel);
        //EventsPanel:
        panelBuilder.buildAttendeeEventsPanel(eventsPanel, eventsJLabel);
        // EventsJListPanel:
        eventsJList = new JList<String>(eventsListModel);
        panelBuilder.buildAttendeeEventsJListPanel(eventsJlistPanel, eventsJList, eventsListScroller);
        eventsPanel.add(eventsJlistPanel, BorderLayout.CENTER);
        // EventsButtonPanel
        panelBuilder.buildAttendeeEventsButtonPanel(eventsButtonPanel, signUpButton, sort1Button);
        eventsPanel.add(eventsButtonPanel, BorderLayout.SOUTH);

        // YourEventsPanel:
        panelBuilder.buildAttendeeEventsPanel(yourEventsPanel, yourEventsJLabel);
        // Your Events JListPanel:
        yourEventsJList = new JList(yourEventsListModel);
        panelBuilder.buildAttendeeEventsJListPanel(yourEventsJlistPanel, yourEventsJList, yourEventsListScroller);
        yourEventsPanel.add(yourEventsJlistPanel, BorderLayout.CENTER);
        //YourEventsButtonPanel:
        panelBuilder.buildAttendeeEventsButtonPanel(yourEventsButtonPanel, cancelButton, sort2Button);
        yourEventsPanel.add(yourEventsButtonPanel, BorderLayout.SOUTH);

        //MainPanel
        panelBuilder.buildAttendeeMainPanel(mainPanel, northPanel, southPanel, backButton);
        buildEventListModel();
        buildYourEventListModel();
        signUpButtonListener();
        cancelButtonListener();
        return mainPanel;
    }

    private void backButtonListen(){
        backButton.addActionListener(e -> {
            yourEventsListModel.clear();
            eventsListModel.clear();
            panelStack.pop();
            JPanel panel = (JPanel) panelStack.pop();
            panelStack.loadPanel(panel);
        });
    }

    private void sortButtonListener(){
        sort1Button.addActionListener(e -> {
            panelStack.loadPanel(eventSortGUI.sortPage());
        });
    }

    private void sort2ButtonListener(){
        sort2Button.addActionListener(e -> {
            panelStack.loadPanel(yourEventSortGUI.sortPage());
        });
    }

    private void signUpButtonListener(){
        signUpButton.addActionListener(e -> {
            int index = eventsJList.getSelectedIndex();
            String event = (String) eventsJList.getSelectedValue();
            boolean canSignUp = eventSystem.eventSignUp(Integer.parseInt(event.split("//|")[0]), eventsPanel);
            if (canSignUp){
                yourEventsListModel.addElement(event);
                eventsListModel.remove(index);
                yourEventsJList.setModel(yourEventsListModel);
            }
        });
    }

    private void cancelButtonListener(){
        cancelButton.addActionListener(e -> {
            int index = yourEventsJList.getSelectedIndex();
            String event = (String) yourEventsJList.getSelectedValue();
            eventSystem.attendeeCancelEvent(Integer.parseInt(event.split("//|")[0]), yourEventsPanel);
            eventsListModel.addElement(event);
            yourEventsListModel.remove(index);
            eventsJList.setModel(eventsListModel);
        });
    }

    private void buildEventListModel(){
        List<String> eventList = eventSystem.getAllEventList();
        if (!eventList.isEmpty()){
            for (String event:eventList) {
                eventsListModel.addElement(event);
            }
        }
    }

    private void buildYourEventListModel(){
        List<String> yourEventList = eventSystem.getAttendeeEventList();
        if (!yourEventList.isEmpty()){
            for (String event:yourEventList) {
                yourEventsListModel.addElement(event);
            }
        }
    }
}
