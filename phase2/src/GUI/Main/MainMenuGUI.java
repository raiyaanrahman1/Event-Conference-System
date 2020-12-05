package GUI.Main;

import Controller.EventManagementSystem;
import Controller.LoginSystem;
import Controller.MessengerSystem;
import GUI.EventMenus.EventAttendeeGUI;
import GUI.EventMenus.EventGUI;
import GUI.EventMenus.EventOrganizerGUI;
import GUI.EventMenus.EventSpeakerGUI;
import GUI.MessageMenus.*;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI {
    private MessengerSystem messageSystem;
    private EventManagementSystem eventSystem;
    private LoginSystem loginSystem;
    private PanelStack panelStack;
    private ContactsGUI contactGUI;
    private EventSpeakerGUI eventSpeakerGUI;
    private EventAttendeeGUI eventAttendeeGUI;
    private EventOrganizerGUI eventOrganizerGUI;
    private CreateAccountGUI createAccountGUI;
    private InboxGUI inboxGUI;
    private JPanel mainMenuPanel = new JPanel();
    private JLabel mainMenuLabel = new JLabel("MAIN MENU");
    private JButton inboxButton = new JButton("Inbox");
    private JButton contactButton = new JButton("Contacts");
    private JButton eventsButton = new JButton("Events");
    private JButton logOutButton = new JButton("Log Out");
    private JButton createAccountButton = new JButton("Create Account");
    private LoginPanelBuilder panelBuilder = new LoginPanelBuilder(mainMenuPanel);


    public MainMenuGUI(LoginSystem loginSystem, EventManagementSystem eventSystem, MessengerSystem messageSystem, PanelStack panelStack) {
        this.panelStack = panelStack;
        this.messageSystem = messageSystem;
        this.eventSystem = eventSystem;
        this.loginSystem = loginSystem;
        eventSpeakerGUI = new EventSpeakerGUI(eventSystem, panelStack);
        eventAttendeeGUI = new EventAttendeeGUI(eventSystem, panelStack);
        eventOrganizerGUI = new EventOrganizerGUI(eventSystem, panelStack);
        createAccountGUI = new CreateAccountGUI(loginSystem, panelStack);
        inboxGUI = new InboxGUI(messageSystem);
        inboxButtonListen();
        contactsButtonListen();
        eventsButtonListen();
        createAccountButtonListen();
        logOutButtonListen();
    }

    private void inboxButtonListen(){
        inboxButton.addActionListener(e -> panelStack.loadPanel(inboxGUI.mainPage()) );
    }

    private void contactsButtonListen(){
        //contactButton.addActionListener(e -> TODO call the appropriate event menu);
    }

    private void eventsButtonListen(){
        eventsButton.addActionListener(e -> {
            String type = eventSystem.getUserType();
            if (type.equals("S")){
                panelStack.loadPanel(eventSpeakerGUI.startEventPage());
            }
            else if (type.equals("A")){
                panelStack.loadPanel(eventAttendeeGUI.startEventPage());
            }
            else{
                Object[] options = {"Attend Events", "Manage Events", "Create Account"};
                int n = JOptionPane.showOptionDialog(mainMenuPanel,
                        "Where would you like to go?",
                        "",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);
                if( n == 0){
                    panelStack.loadPanel(eventAttendeeGUI.startEventPage());
                }
                else if(n == 1){
                    panelStack.loadPanel(eventOrganizerGUI.startEventPage());
                }
            }
        });
    }

    private void createAccountButtonListen(){
        createAccountButton.addActionListener(e -> panelStack.loadPanel(createAccountGUI.createAccountPage()));
    }


    private void logOutButtonListen(){
        logOutButton.addActionListener(e -> panelStack.terminateProgram());
    }

    public JPanel startMainMenuPage(){
        //Panel:
        panelBuilder.buildMainPanel();
        //Title:
        panelBuilder.buildPanelLabel(mainMenuLabel,48, 110, 10, 500, 40);
        //CreateAccount Button:
        panelBuilder.buildButton(createAccountButton,170, 170, 150, 30);
        //Inbox Button
        panelBuilder.buildButton(inboxButton,170, 200, 150, 30);
        //Contact Button
        panelBuilder.buildButton(contactButton,170, 230, 150, 30);
        //Events Button
        panelBuilder.buildButton(eventsButton,170, 260, 150, 30);
        //Logout Button
        panelBuilder.buildButton(logOutButton,170, 290, 150, 30);
        if (!eventSystem.getUserType().equals("O")){
            createAccountButton.setVisible(false);
        }

        return mainMenuPanel;
    }
}
