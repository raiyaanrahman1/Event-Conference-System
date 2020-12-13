package GUI.EventMenus;

import Controller.EventManagementSystem;
import GUI.Main.PanelStack;
import GUI.PanelBuilder.EventPanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EventAttendeeGUI {
    private final SortGUI yourEventSortGUI;
    private final SortGUI eventSortGUI;
    private final PanelStack panelStack;
    private JList eventsJList = new JList();
    private JList yourEventsJList = new JList();

    private final JLabel eventsJLabel = new JLabel("Events");
    private final JLabel yourEventsJLabel = new JLabel("Your Events");

    private final JButton signUpButton = new JButton("Sign Up");
    private final JButton sort1Button = new JButton("Sort");
    private final JButton sort2Button = new JButton("Sort");
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton backButton = new JButton("Back");

    private final JPanel eventsButtonPanel = new JPanel();
    private final JPanel yourEventsButtonPanel = new JPanel();
    private final JPanel eventsPanel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel eventsJlistPanel = new JPanel();
    private final JPanel yourEventsJlistPanel = new JPanel();
    private final JPanel yourEventsPanel = new JPanel();
    private final JPanel northPanel = new JPanel();
    private final JPanel southPanel = new JPanel();

    private final EventPanelBuilder panelBuilder = new EventPanelBuilder();

    private final DefaultListModel<String> eventsListModel = new DefaultListModel<>();
    private final DefaultListModel<String> yourEventsListModel = new DefaultListModel<>();

    private final EventManagementSystem eventSystem;

    private final JScrollPane eventsListScroller = new JScrollPane();
    private final JScrollPane yourEventsListScroller = new JScrollPane();

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
        signUpButtonListener();
        cancelButtonListener();
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
