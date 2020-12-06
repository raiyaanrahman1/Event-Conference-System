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
    private EventPanelBuilder panelBuilder = new EventPanelBuilder();

    private List<String> listEvents;
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

    private int selectedEventIndex;
    private JLabel noEventLabel = new JLabel("You are not speaking at any events.");

    public EventOrganizerGUI(EventManagementSystem eventSystem, PanelStack panelStack) {
        this.eventSystem = eventSystem;
        this.panelStack = panelStack;
        buildListModel();
        listListener();
        broadcastButtonListen();
        backButtonListen();
    }

    private void buildListModel(){
        boolean eventsExists = setListEvents();
        if (eventsExists) {
            for (String s : this.listEvents) {
                listModel.addElement(s);
            }
        }
    }

    private boolean setListEvents(){
        boolean eventsExists = false;
        List<String> tempList = new ArrayList<>(); //eventSystem.getBroadcastEventSpeaker();
        tempList.add("Hi");
        if (tempList.isEmpty()) {
            this.noEventLabel.setVisible(true);
        }
        else{
            this.listEvents = tempList;
            this.noEventLabel.setVisible(false);
            eventsExists = true;
        }
        return eventsExists;
    }

    public JPanel startEventPage() {
        panelBuilder.buildBorderLayoutPanel(eventPanel, 20, 20, 40, 20);
        panelBuilder.buildComponentBorderLayout(eventsJLabel, eventPanel, BorderLayout.NORTH, 48);
        jListPanel.setLayout(new GridLayout(1, 1));
        eventPanel.add(jListPanel, BorderLayout.CENTER);
        panelBuilder.buildJListEvents(eventsJList);
        panelBuilder.buildJScrollPane(eventsJScrollPane, jListPanel, eventsJList);
        buttonPanel.setLayout(new BorderLayout());
        eventPanel.add(buttonPanel, BorderLayout.SOUTH);
        northButtonPanel.setLayout(new FlowLayout());
        buttonPanel.add(northButtonPanel, BorderLayout.NORTH);
        panelBuilder.buildComponent(broadcastButton, northButtonPanel, 14);
        panelBuilder.buildComponentBorderLayout(backButton, buttonPanel, BorderLayout.WEST, 14);
        panelBuilder.buildComponent(editButton, northButtonPanel,14);
        panelBuilder.buildComponent(deleteButton, northButtonPanel,14);
        panelBuilder.buildComponent(sortButton, northButtonPanel,14);
        sortButton.setEnabled(true);
        return eventPanel;
    }

    private void backButtonListen(){
        backButton.addActionListener(e -> {
            panelStack.pop();
            JPanel panel = (JPanel) panelStack.pop();
            panelStack.loadPanel(panel);
        });
    }

    private void broadcastButtonListen(){
        broadcastButton.addActionListener(e -> {
            String message = JOptionPane.showInputDialog("Enter the content of your message: ");
            if (message != null){
                eventSystem.broadcast(selectedEventIndex, message);
            }
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
