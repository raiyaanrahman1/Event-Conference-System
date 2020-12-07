package GUI.EventMenus;

import Controller.EventManagementSystem;
import Controller.LoginSystem;
import GUI.Main.PanelStack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventOrganizerGUI {
    private PanelStack panelStack;
    private EventManagementSystem eventSystem;
    private SortGUI eventSortGUI;

    private EventPanelBuilder panelBuilder = new EventPanelBuilder();

    private DefaultListModel<String> listModel = new DefaultListModel<>();


    private JPanel eventPanel = new JPanel();
    private JPanel jListPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel northButtonPanel = new JPanel();

    private JLabel eventsJLabel = new JLabel("EVENTS MENU");

    private JList eventsJList = new JList(listModel); //TODO pass in list of events of speakers in parameter of JList

    private JScrollPane eventsJScrollPane = new JScrollPane();
    private JButton backButton = new JButton("Back");
    private JButton broadcastButton = new JButton("✉");
    private JButton editButton = new JButton("Edit");
    private JButton deleteButton = new JButton("Delete");
    private JButton sortButton = new JButton("Sort");
    private JButton addEventButton = new JButton("Add Event");

    private int selectedEventIndex;
    private JLabel noEventLabel = new JLabel("You are not speaking at any events.");

    public EventOrganizerGUI(EventManagementSystem eventSystem, PanelStack panelStack) {
        this.eventSystem = eventSystem;
        this.panelStack = panelStack;
        eventSortGUI = new SortGUI(eventSystem, listModel, panelStack);
        broadcastButtonListen();
        backButtonListen();
        sortButtonListener();
        addEventButtonListener();
    }

    private void buildListModel(){
        List<String> eventList = eventSystem.getOrganizerEventList();
        if (!eventList.isEmpty()){
            for (String event:eventList) {
                listModel.addElement(event);
            }
        }
    }


    public JPanel startEventPage() {
        panelBuilder.buildBorderLayoutPanel(eventPanel, 20, 20, 40, 20);
        panelBuilder.buildComponentBorderLayout(eventsJLabel, eventPanel, BorderLayout.NORTH, 48);
        buildListModel();
        eventsJList = new JList(listModel);
        panelBuilder.buildAttendeeEventsJListPanel(jListPanel, eventsJList, eventsJScrollPane);
        eventPanel.add(jListPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new BorderLayout());
        eventPanel.add(buttonPanel, BorderLayout.SOUTH);
        northButtonPanel.setLayout(new FlowLayout());
        buttonPanel.add(northButtonPanel, BorderLayout.NORTH);
        panelBuilder.buildComponent(broadcastButton, northButtonPanel, 14);
        panelBuilder.buildComponentBorderLayout(backButton, buttonPanel, BorderLayout.WEST, 14);
        panelBuilder.buildComponent(editButton, northButtonPanel,14);
        panelBuilder.buildComponent(deleteButton, northButtonPanel,14);
        panelBuilder.buildComponent(sortButton, northButtonPanel,14);
        panelBuilder.buildComponentBorderLayout(addEventButton, buttonPanel, BorderLayout.EAST, 14);
        sortButton.setEnabled(true);
        deleteButtonListen();
        listListener();
        return eventPanel;
    }

    private void backButtonListen(){
        backButton.addActionListener(e -> {
            listModel.clear();
            panelStack.pop();
            JPanel panel = (JPanel) panelStack.pop();
            panelStack.loadPanel(panel);
        });
    }

    private void addEventButtonListener(){
        addEventButton.addActionListener(e -> {
            panelStack.loadPanel(new AddEventGUI(eventSystem, panelStack).addEventPage());
        });
    }

    private void deleteButtonListen(){
        deleteButton.addActionListener(e -> {
            String event = eventsJList.getSelectedValue().toString();
            eventSystem.cancelEvent(eventPanel, Integer.parseInt(event.substring(0, 1)));
            listModel.removeElementAt(eventsJList.getSelectedIndex());
            eventsJList.setModel(listModel);
        });
    }

    private void broadcastButtonListen(){
        broadcastButton.addActionListener(e -> {
            if(!eventsJList.isSelectionEmpty()){
                String message = JOptionPane.showInputDialog("Enter the content of your message: ");
                if (message != null) {
                    String event = eventsJList.getSelectedValue().toString();
                    eventSystem.broadcast(Integer.parseInt(event.substring(0, 1)), message);
                }
            }
        });
    }

    private void sortButtonListener(){
        sortButton.addActionListener(e -> {
            panelStack.loadPanel(eventSortGUI.sortPage());
        });
    }

    private void listListener(){
        eventsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                selectedEventIndex = eventsJList.getSelectedIndex();
                broadcastButton.setEnabled(true);
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);

            }
        });
    }

}
