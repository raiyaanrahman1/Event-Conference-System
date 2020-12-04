package GUI.EventMenus;

import Controller.EventManagementSystem;
import Controller.LoginSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventSpeakerGUI {
    private EventManagementSystem eventSystem;
    private List<String> listEvents;
    private JList eventJList; //TODO pass in list of events of speakers in parameter of JList
    private JScrollPane listScroller = new JScrollPane();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JLabel eventMenu = new JLabel("EVENTS MENU");
    private JButton exitButton = new JButton();
    private JButton broadcastButton = new JButton();
    private int selectedEventIndex;
    private JPanel jListPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel eventPagePanel = new JPanel();
    private JLabel noEventLabel = new JLabel("You are not speaking at any events.");
    private EventPanelBuilder panelBuilder = new EventPanelBuilder(eventPagePanel);

    public EventSpeakerGUI(EventManagementSystem eventSystem) {
        this.eventSystem = eventSystem;
        buildListModel();
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
        List<String> tempList = new ArrayList<>();//eventSystem.getBroadcastEventSpeaker();
        tempList.add("Hi");
        if (tempList == null) {
            this.noEventLabel.setVisible(true);
        }
        else{
            this.listEvents = tempList;
            this.noEventLabel.setVisible(false);
            eventsExists = true;
        }
        return eventsExists;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setVisible(true);
        LoginSystem loginSystem = new LoginSystem();
        frame.setContentPane(new EventSpeakerGUI(loginSystem.getEventSys()).startEventPage());
    }


    private void exitButtonListen(){
        exitButton.addActionListener(e -> eventPagePanel.setVisible(false));
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
        eventJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                selectedEventIndex = eventJList.getSelectedIndex();
                 broadcastButton.setEnabled(true);
            }
        });
    }

    public JPanel startEventPage() {
        //Panel:
//        framePanel = eventPagePanel;
        eventPagePanel = panelBuilder.build500x500Panel();
        //Title:
        eventMenu = panelBuilder.buildEventMenuLabel(eventMenu);
        eventPagePanel.add(eventMenu, BorderLayout.NORTH);
        eventMenu.setBounds(65, 10, 500, 40);
        // Panel for jlist and jscrollpane:
        jListPanel.setLayout(new GridLayout(1, 1));
        eventPagePanel.add(jListPanel, BorderLayout.CENTER);
        //JList:
        eventJList = new JList(listModel);
        eventJList.setBounds(60, 60, 360, 300);
        eventJList = panelBuilder.buildJListEvents(eventJList);
        listListener();
        //list scroller
        listScroller.setBounds(60, 60, 360, 300);
        listScroller = panelBuilder.buildJScrollPane(listScroller);
        listScroller.setViewportView(eventJList);
        listScroller.setSize(new Dimension(360, 300));
        jListPanel.add(listScroller);
        //ButtonPanel:
        buttonPanel.setLayout(new BorderLayout());
        eventPagePanel.add(buttonPanel, BorderLayout.SOUTH);
        //Broadcast Button
        broadcastButton.setEnabled(false);
        broadcastButton.setText("✉");
        broadcastButton.setFont(new Font("", Font.PLAIN, 24));
        broadcastButton.setBounds(100, 0, 80, 25);
        buttonPanel.add(broadcastButton, BorderLayout.NORTH);
        broadcastButtonListen();
        //Exit Button
        exitButton.setText("Exit");
        exitButton.setBounds(150, 0, 80, 25);
        buttonPanel.add(exitButton, BorderLayout.WEST);
        exitButtonListen();
        //No event Label:
        buttonPanel.add(noEventLabel, BorderLayout.SOUTH);
        return eventPagePanel;
    }

}
