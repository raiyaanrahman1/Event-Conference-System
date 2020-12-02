package GUI.EventMenus;

import Controller.EventManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EventMenuSpeaker extends JPanel{
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private EventPanelBuilder panelBuilder = new EventPanelBuilder();
    private JPanel eventPagePanel = new JPanel();
    private JLabel eventMenu = new JLabel("EVENTS MENU");
    private JList eventJList = new JList(listModel); //TODO pass in list of events of speakers in parameter of JList
    private JButton exitButton = new JButton();
    private JButton broadcastButton = new JButton();
    private JScrollPane listScroller = new JScrollPane(eventJList);


    public void EventMenuSpeaker() {
    }

    public JPanel startEventPage() {
        //Panel:
        eventPagePanel.setSize(500, 500);
        eventPagePanel.setLayout(null);
        //Title:
        eventMenu.setFont(new Font("", Font.BOLD, 48));
        eventMenu.setBounds(65, 10, 500, 40);
        eventPagePanel.add(eventMenu);
        //JList:
        eventJList.setSelectionBackground(new Color(51, 153, 255));
        eventJList.setBounds(60, 60, 360, 300);
        eventJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        eventJList.setLayoutOrientation(JList.VERTICAL);
        eventJList.setVisibleRowCount(0);
        listModel.addElement("a");
        listModel.addElement("b");
        listScroller.setPreferredSize(new Dimension(250,360));
        listScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        eventPagePanel.add(listScroller);
        eventPagePanel.add(eventJList);
        //Broadcast Button
        broadcastButton.setEnabled(false);
        broadcastButton.setText("✉");
        broadcastButton.setFont(new Font("", Font.PLAIN, 24));
        broadcastButton.setBounds(200, 370, 80, 25);
        eventPagePanel.add(broadcastButton);
        //Exit Button
        exitButton.setText("Exit");
        exitButton.setBounds(20, 420, 80, 25);
        eventPagePanel.add(exitButton);

        return eventPagePanel;
    }


}
