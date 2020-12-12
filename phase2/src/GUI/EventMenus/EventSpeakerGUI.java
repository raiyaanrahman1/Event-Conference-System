package GUI.EventMenus;

import Controller.EventManagementSystem;
import GUI.Main.PanelStack;
import GUI.PanelBuilder.EventPanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EventSpeakerGUI {
    private final PanelStack panelStack;
    private final EventManagementSystem eventSystem;
    private final EventPanelBuilder panelBuilder = new EventPanelBuilder();

    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    private List<String> eventList;

    private final JPanel eventPanel = new JPanel();
    private final JPanel jListPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();

    private final JLabel eventsJLabel = new JLabel("EVENTS MENU");

    private JList eventsJList = new JList(); //TODO pass in list of events of speakers in parameter of JList

    private final JScrollPane eventsJScrollPane = new JScrollPane();
    private final JButton backButton = new JButton("Back");
    private final JButton broadcastButton = new JButton("✉");


    /**
     * Creates an EventSpeakerGUI and initializes its EventManagementSystem and the panelStack
     *
     * @param eventSystem the EventManagementSystem that the EventSpeakerGUI communicates with
     * @param panelStack The current instance of PanelStack instantiated in MainGUI
     */
    public EventSpeakerGUI(EventManagementSystem eventSystem, PanelStack panelStack) {
        this.eventSystem = eventSystem;
        this.panelStack = panelStack;
    }

    /**
     * Builds and loads the Event Page for Speakers
     *
     * @return The Event panel for Speakers
     */
    public JPanel startEventPage() {
        listModel.clear();
        eventSystem.makeListsEvents();
        panelBuilder.buildBorderLayoutPanel(eventPanel, 20, 20, 40, 20);
        eventsJLabel.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 48));
        eventPanel.add(eventsJLabel, BorderLayout.NORTH);
        buildListModel();
        eventsJList = new JList(listModel);
        panelBuilder.buildJScrollPanePanel(jListPanel, eventsJList, eventsJScrollPane);
        eventPanel.add(jListPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new BorderLayout());
        eventPanel.add(buttonPanel, BorderLayout.SOUTH);
        broadcastButton.setEnabled(false);
        broadcastButton.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 24));
        buttonPanel.add(broadcastButton, BorderLayout.NORTH);
        backButton.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
        buttonPanel.add(backButton, BorderLayout.WEST);
        listListener();
        broadcastButtonListen();
        backButtonListen();
        return eventPanel;
    }


    private void buildListModel(){
        this.eventList = eventSystem.getEventLists().get(3);
        if (!eventList.isEmpty()){
            for (String event:eventList) {
                listModel.addElement(event);
            }
        }
    }

    private void backButtonListen(){
        backButton.addActionListener(e -> {
            JPanel panel = (JPanel) panelStack.pop();
            panel.removeAll();
            panelStack.loadPanel((JPanel) panelStack.pop());
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

    private void listListener(){
        eventsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                broadcastButton.setEnabled(true);
            }
        });
    }

}
