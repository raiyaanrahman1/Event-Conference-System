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
    private static Container container;

    public MainMenuGUI(EventManagementSystem eventSystem, MessengerSystem messageSystem){
        eventGUI = new EventGUI(eventSystem);
        messageGUI = new MessageGUI(messageSystem);
        ems = new EventMenuSpeaker(eventSystem);
        startMainMenuPage();
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500,500);
//        frame.setResizable(false);
//        frame.setVisible(true);
//        LoginSystem loginSystem = new LoginSystem();
//        container = new MainMenuGUI(loginSystem.getEventSys(), loginSystem.getMsgSys()).mainMenuPanel;
//        frame.setContentPane(container);
//
//    }

    private void messagesButtonListen(){
        messagesButton.addActionListener(e -> mainMenuPanel.setVisible(false));
    }

    private void eventsButtonListen(){
        eventsButton.addActionListener(e -> mainMenuPanel.setVisible(false));
    }

    private void logOutButtonListen(){
        logOutButton.addActionListener(e -> mainMenuPanel.setVisible(false));
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
        return mainMenuPanel;


    }




}
