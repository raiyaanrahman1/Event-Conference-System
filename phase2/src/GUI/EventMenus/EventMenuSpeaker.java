package GUI.EventMenus;

import Controller.EventManagementSystem;
import Controller.LoginSystem;
import GUI.MessageMenus.MessageGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventMenuSpeaker {
    private EventManagementSystem eventSystem;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JPanel framePanel = new JPanel();
    private JPanel eventPagePanel = new JPanel();
    private EventPanelBuilder panelBuilder = new EventPanelBuilder(eventPagePanel);
    private JLabel eventMenu = new JLabel("EVENTS MENU");
    private JList eventJList = new JList(listModel); //TODO pass in list of events of speakers in parameter of JList
    private JButton exitButton = new JButton();
    private JButton broadcastButton = new JButton();
    private JScrollPane listScroller = new JScrollPane(eventJList);
    private String selectedEvent;

    public EventMenuSpeaker(EventManagementSystem eventSystem) {
        this.eventSystem = eventSystem;
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500,500);
//        frame.setResizable(false);
//        frame.setVisible(true);
//        LoginSystem loginSystem = new LoginSystem();
//        frame.setContentPane(new EventMenuSpeaker(loginSystem.getEventSys()).startEventPage());
//
//    }


    private void exitButtonListen(){
        exitButton.addActionListener(e -> eventPagePanel.setVisible(false));
    }

    private void broadcastButtonListen(){
        broadcastButton.addActionListener(e -> {
            String message = JOptionPane.showInputDialog("Enter the content of your message: ");
            if (message != null){
                //TODO make broadcast method in eventManagementSystem
            }

        });
    }


    private void listListener(){
        eventJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                int index = eventJList.getSelectedIndex();
                 selectedEvent = listModel.get(index);
                 broadcastButton.setEnabled(true);
            }
        });
    }

    public JPanel startEventPage() {
        //Panel:
        framePanel = eventPagePanel;
        eventPagePanel = panelBuilder.build500x500Panel();
        //Title:
        eventMenu = panelBuilder.buildEventMenuLabel(eventMenu);
        eventMenu.setBounds(65, 10, 500, 40);
        //JList:
        eventJList.setBounds(60, 60, 360, 300);
        eventJList = panelBuilder.buildJListEvents(eventJList);
        listModel.addElement("a");
        listModel.addElement("b");
        listListener();
        //list scroller
        listScroller = panelBuilder.buildJScrollPane(listScroller);
        //Broadcast Button
        broadcastButton.setEnabled(false);
        broadcastButton.setText("✉");
        broadcastButton.setFont(new Font("", Font.PLAIN, 24));
        broadcastButton.setBounds(200, 370, 80, 25);
        eventPagePanel.add(broadcastButton);
        broadcastButtonListen();
        //Exit Button
        exitButton.setText("Exit");
        exitButton.setBounds(20, 420, 80, 25);
        eventPagePanel.add(exitButton);
        exitButtonListen();
        return eventPagePanel;
    }

}
