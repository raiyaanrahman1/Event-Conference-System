package GUI.Main;

import Controller.EventManagementSystem;
import Controller.MessengerSystem;
import GUI.EventMenus.EventGUI;
import GUI.EventMenus.EventSpeakerGUI;
import GUI.MessageMenus.*;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI {
    private MessengerSystem messageSystem;
    private PanelStack panelStack;
    private ContactsGUI contactGUI;
    private EventGUI eventGUI;
    private InboxGUI inboxGUI;
    private JPanel mainMenuPanel = new JPanel();
    private JLabel mainMenuLabel = new JLabel("MAIN MENU");
    private JButton inboxButton = new JButton("Inbox");
    private JButton contactButton = new JButton("Contacts");
    private JButton eventsButton = new JButton("Events");
    private JButton logOutButton = new JButton("Log Out");
    private LoginPanelBuilder panelBuilder = new LoginPanelBuilder(mainMenuPanel);


    public MainMenuGUI(EventManagementSystem eventSystem, MessengerSystem messageSystem, PanelStack panelStack) {
        eventGUI = new EventGUI(eventSystem);
        this.panelStack = panelStack;
        this.messageSystem = messageSystem;
        inboxGUI = new InboxGUI(messageSystem);
        inboxButtonListen();
        contactsButtonListen();
        eventsButtonListen();
        logOutButtonListen();
    }

    private void inboxButtonListen(){
        inboxButton.addActionListener(e -> panelStack.loadPanel(inboxGUI.mainPage()) );
    }

    private void contactsButtonListen(){
        //contactButton.addActionListener(e -> TODO call the appropriate event menu);
    }

    private void eventsButtonListen(){
   //     eventsButton.addActionListener(e -> TODO call the appropriate event menu);
    }

    private void logOutButtonListen(){
        logOutButton.addActionListener(e -> panelStack.terminateProgram());
    }

    public JPanel startMainMenuPage(){
        //Panel:
        panelBuilder.buildMainPanel();
        //Title:
        panelBuilder.buildPanelLabel(mainMenuLabel,48, 110, 10, 500, 40);
        //Inbox Button
        panelBuilder.buildButton(inboxButton,170, 200, 150, 30);
        //Contact Button
        panelBuilder.buildButton(contactButton,170, 230, 150, 30);
        //Events Button
        panelBuilder.buildButton(eventsButton,170, 260, 150, 30);
        //Logout Button
        panelBuilder.buildButton(logOutButton,170, 290, 150, 30);
        mainMenuPanel.setVisible(true);
        return mainMenuPanel;
    }
}
