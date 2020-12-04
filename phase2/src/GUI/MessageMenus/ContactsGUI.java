package GUI.MessageMenus;

import Controller.LoginSystem;
import Controller.MessengerSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ContactsGUI implements IMessageView{
    private MessagePanelBuilder builder;
    private MessengerSystem messenger;
    private JPanel mainPanel = new JPanel();
    private DefaultListModel<String> contactListModel = new DefaultListModel<>();
    private JButton[] contactOptions;
    private JButton addButton;
    private JButton mainBackButton;
    private JButton internalBackButton;
    private JList contactsJList;
    private JScrollPane contacts;


    public ContactsGUI(MessengerSystem messenger){
        this.messenger = messenger;
        builder = new MessagePanelBuilder(mainPanel);

        contactOptions = builder.buildOptions(new String[]{"send msg", "remove", "view msgs"}, 300);

        mainBackButton = builder.makeBackButton();
        internalBackButton = builder.makeBackButton();
        mainPanel.add(mainBackButton);
        internalBackButton.setVisible(false);
        mainPanel.add(internalBackButton);
    }

    public JPanel mainPage(){
        //messenger.getContacts()
        List<String> testMsgs = new ArrayList<>();
        testMsgs.add("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj" +
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj" +
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj"+
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj"+
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj"+
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        for (String s: testMsgs){
            contactListModel.addElement(s);
        }
        contactsJList = new JList(contactListModel);
        contacts = new JScrollPane(contactsJList);
        contacts.setVisible(true);
        mainPanel.add(builder.buildMainPane(contacts, "contacts"));
        mainPanel.setVisible(true);
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setVisible(true);
        LoginSystem loginSystem = new LoginSystem();
        ContactsGUI test = new ContactsGUI(loginSystem.getMsgSys());
        frame.setContentPane(test.mainPage());

    }
}
