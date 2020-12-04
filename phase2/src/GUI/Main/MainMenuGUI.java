package GUI.Main;

import Controller.EventManagementSystem;
import Controller.MessengerSystem;
import GUI.EventMenus.EventGUI;
import GUI.EventMenus.EventSpeakerGUI;
import GUI.MessageMenus.*;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI {
    private ContactsGUI contactGUI;
    private EventGUI eventGUI;;
    private EventSpeakerGUI ems;
    private InboxGUI inboxGUI;
    private JPanel mainMenuPanel = new JPanel();
    private JLabel mainMenuLabel = new JLabel("MAIN MENU");
    private JButton inboxButton = new JButton();
    private JButton contactButton = new JButton();
    private JButton eventsButton = new JButton();
    private JButton logOutButton = new JButton();
    private JFrame frame;


    public MainMenuGUI(EventManagementSystem eventSystem, MessengerSystem messageSystem, JFrame frame){
        eventGUI = new EventGUI(eventSystem);
        // messageGUI = new MessageGUI(messageSystem);
        this.frame = frame;
    }


    private void inboxButtonListen(){
 //       inboxButton.addActionListener(e -> TODO call the appropriate message menu );
    }

    private void contactsButtonListen(){
 //       contactButton.addActionListener(e -> TODO call the appropriate message menu );
    }

    private void eventsButtonListen(){
 //     eventsButton.addActionListener(e -> TODO call the appropriate event menu);
    }

    private void logOutButtonListen(){
        logOutButton.addActionListener(e -> frame.dispose());
    }

    public JPanel startMainMenuPage(){
        //Panel:
        mainMenuPanel.setSize(500, 500);
        mainMenuPanel.setLayout(null);
        //Title:
        mainMenuLabel.setFont(new Font("", Font.BOLD, 48));
        mainMenuLabel.setBounds(95, 10, 500, 40);
        mainMenuPanel.add(mainMenuLabel);
        //Inbox Button
        inboxButton.setText("Inbox");
        inboxButton.setFont(new Font("", Font.PLAIN, 20));
        inboxButton.setBounds(170, 200, 150, 30);
        mainMenuPanel.add(inboxButton);
        inboxButtonListen();
        //Contact Button
        contactButton.setText("Contacts");
        contactButton.setFont(new Font("", Font.PLAIN, 20));
        contactButton.setBounds(170, 230, 150, 30);
        mainMenuPanel.add(contactButton);
        contactsButtonListen();
        //Events Button
        eventsButton.setText("Events");
        eventsButton.setFont(new Font("", Font.PLAIN, 20));
        eventsButton.setBounds(170, 260, 150, 30);
        mainMenuPanel.add(eventsButton);
        eventsButtonListen();
        //Logout Button
        logOutButton.setText("Log Out");
        logOutButton.setFont(new Font("", Font.PLAIN, 20));
        logOutButton.setBounds(170, 290, 150, 30);
        mainMenuPanel.add(logOutButton);
        logOutButtonListen();
        mainMenuPanel.setVisible(true);
        return mainMenuPanel;
    }




}
