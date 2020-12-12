package GUI.EventMenus;

import Controller.EventManagementSystem;
import GUI.Main.PanelStack;
import GUI.PanelBuilder.EventPanelBuilder;

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
     * @param eventSystem the EventManagementSystem that the EventAttendeeGUI communicates with
     * @param panelStack The current instance of PanelStack instantiated in MainGUI
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
        yourEventsListModel.clear();
        eventsListModel.clear();
        eventSystem.makeListsEvents();
        // NorthPanel:
        panelBuilder.buildAttendeeNorthPanel(northPanel, eventsPanel, yourEventsPanel);
        //EventsPanel:
        panelBuilder.buildAttendeeEventsJLabel(eventsPanel, eventsJLabel);
        // EventsJListPanel:
        eventsJList = new JList<>(eventsListModel);
        panelBuilder.buildJScrollPanePanel(eventsJlistPanel, eventsJList, eventsListScroller);
        eventsPanel.add(eventsJlistPanel, BorderLayout.CENTER);
        // EventsButtonPanel
        panelBuilder.buildAttendeeEventsButtonPanel(eventsButtonPanel, signUpButton, sort1Button);
        eventsPanel.add(eventsButtonPanel, BorderLayout.SOUTH);

        // YourEventsPanel:
        panelBuilder.buildAttendeeEventsJLabel(yourEventsPanel, yourEventsJLabel);
        // Your Events JListPanel:
        yourEventsJList = new JList(yourEventsListModel);
        panelBuilder.buildJScrollPanePanel(yourEventsJlistPanel, yourEventsJList, yourEventsListScroller);
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
            JPanel panel = (JPanel) panelStack.pop();
            panel.removeAll();
            panelStack.loadPanel((JPanel) panelStack.pop());
        });
    }

    private void sortButtonListener(){
        sort1Button.addActionListener(e -> panelStack.loadPanel(eventSortGUI.sortPage()));
    }

    private void sort2ButtonListener(){
        sort2Button.addActionListener(e -> panelStack.loadPanel(yourEventSortGUI.sortPage()));
    }

    private void signUpButtonListener(){
        signUpButton.addActionListener(e -> {
            int index = eventsJList.getSelectedIndex();
            if(index!= -1) {
                String event = (String) eventsJList.getSelectedValue();
                if (eventSystem.eventSignUp(Integer.parseInt(event.split("//|")[0]))) {
                    yourEventsListModel.addElement(event);
                    eventsListModel.remove(index);
                    yourEventsJList.setModel(yourEventsListModel);
                    JOptionPane.showMessageDialog(eventsPanel, "You have successfully signed up for this event.");
                }
                else{
                    JOptionPane.showMessageDialog(eventsPanel, "You cannot sign up for this event. \n" +
                            "This event is either restricted or conflicts with your signed up events");
                }
            }
        });
    }

    private void cancelButtonListener(){
        cancelButton.addActionListener(e -> {
            int index = yourEventsJList.getSelectedIndex();
            if(index!= -1) {
                String event = (String) yourEventsJList.getSelectedValue();
                if (eventSystem.attendeeCancelEvent(Integer.parseInt(event.split("//|")[0]))) {
                    eventsListModel.addElement(event);
                    yourEventsListModel.remove(index);
                    eventsJList.setModel(eventsListModel);
                    JOptionPane.showMessageDialog(eventsPanel,"Event cancelled.");
                }
            }
        });
    }

    private void buildEventListModel(){
        List<String> eventList = eventSystem.getEventLists().get(0);
        if (!eventList.isEmpty()){
            for (String event:eventList) {
                eventsListModel.addElement(event);
            }
        }
    }

    private void buildYourEventListModel(){
        List<String> yourEventList = eventSystem.getEventLists().get(1);
        if (!yourEventList.isEmpty()){
            for (String event:yourEventList) {
                yourEventsListModel.addElement(event);
            }
        }
    }
}
