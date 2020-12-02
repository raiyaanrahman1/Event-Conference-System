package GUI.MessageMenus;

import Controller.LoginSystem;
import Controller.MessengerSystem;
import Gateway.IGateway;
import UseCase.MessageManager;
import UseCase.UserManager;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageGUI implements IMessageView {
    private MessagePanelBuilder builder = new MessagePanelBuilder();
    private MessengerSystem messenger;
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel buttonPanel;
    private JLabel titleLabel;
    private JPanel canvas;
    private JButton inbox;
    private JButton contacts;
    private JPanel menuPanel;
    private JButton backButton;
    private JPanel space;

    // include back button

    public MessageGUI(MessengerSystem messenger){
        this.messenger = messenger;
        // canvas.setSize(500, 500);
//        canvas.add(titlePanel);
//        canvas.add(infoPanel);
//        canvas.add(buttonPanel);
        // canvas.setVisible(true);
        // maybe dont call it here
        menuPage();
//        titlePanel.setVisible(true);
//        infoPanel.setVisible(true);
//        contacts.setVisible(false);
//        inbox.setVisible(false);
//        buttonPanel.setVisible(true);
    }


    public JPanel menuPage(){
        backButton.setVisible(false);
        titleLabel.setText(" Messages");
        titleLabel.setVisible(true);
        contacts.setVisible(true);
        inbox.setVisible(true);
        menuListen();
        //infoPanel = builder.mainMenu(infoPanel);
        return canvas;
    }

    private void menuListen() {
        inbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inboxPage();
            }
        });
        contacts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactPage();
            }
        });
    }

    private void backListen(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPage();
            }
        });
    }
    public void inboxPage(){
        contacts.setVisible(false);
        inbox.setVisible(false);
        backButton.setVisible(true);
        backListen();
        titleLabel.setText(" Inbox");
        //infoPanel = builder.buildInbox(messenger.viewReceivedMessages());
    }

    public void contactPage(){
        titleLabel.setText(" Contacts");
        contacts.setVisible(false);
        inbox.setVisible(false);
        backButton.setVisible(true);
        backListen();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setVisible(true);
        LoginSystem loginSystem = new LoginSystem();
        MessageGUI test = new MessageGUI(loginSystem.getMsgSys());
        frame.setContentPane(test.menuPage());

    }
}
