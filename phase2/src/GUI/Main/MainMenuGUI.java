package GUI.Main;

import Controller.EventManagementSystem;
import Controller.LoginSystem;
import Controller.MessengerSystem;
import GUI.EventMenus.EventGUI;
import GUI.EventMenus.EventMenuSpeaker;
import GUI.MessageMenus.MessageGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI {
    private EventGUI eventGUI;
    private MessageGUI messageGUI;
    private EventMenuSpeaker ems;
    private JPanel mainMenuPanel = new JPanel();
    private JLabel mainMenuLabel = new JLabel("MAIN MENU");
    private JButton messagesButton = new JButton();
    private JButton eventsButton = new JButton();
    private JButton logOutButton = new JButton();
    private JFrame frame;


    public MainMenuGUI(EventManagementSystem eventSystem, MessengerSystem messageSystem, JFrame frame){
        eventGUI = new EventGUI(eventSystem);
        // messageGUI = new MessageGUI(messageSystem);
        this.frame = frame;

    }


    private void messagesButtonListen(){
 //       messagesButton.addActionListener(e -> TODO call the appropriate message menu );
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
        //Messages Button
        messagesButton.setText("Messages");
        messagesButton.setFont(new Font("", Font.PLAIN, 20));
        messagesButton.setBounds(170, 200, 150, 30);
        mainMenuPanel.add(messagesButton);
        messagesButtonListen();
        //Events Button
        eventsButton.setText("Events");
        eventsButton.setFont(new Font("", Font.PLAIN, 20));
        eventsButton.setBounds(170, 230, 150, 30);
        mainMenuPanel.add(eventsButton);
        eventsButtonListen();
        //Logout Button
        logOutButton.setText("Log Out");
        logOutButton.setFont(new Font("", Font.PLAIN, 20));
        logOutButton.setBounds(170, 260, 150, 30);
        mainMenuPanel.add(logOutButton);
        logOutButtonListen();
        mainMenuPanel.setVisible(true);
        return mainMenuPanel;


    }

}
