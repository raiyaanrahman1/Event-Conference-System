package GUI.MessageMenus;

import Controller.MessengerSystem;
import GUI.Main.PanelStack;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContactsGUI implements IMessageView{
    private MessagePanel panelHelper = new MessagePanel();
    private PanelStack panelStack;
    private MessagePanelBuilder builder;
    private MessengerSystem messenger;
    private JPanel mainPanel = new JPanel();
    private DefaultListModel<String> contactListModel = new DefaultListModel<>();
    private List<JButton> options;
    private JButton addButton;
    private JButton mainBackButton;
    private JButton internalBackButton;
    private JList contactsJList;
    private JScrollPane contacts;
    private int currContactIndex;
    private String currSelectedContact;
    private JTextArea currMessageText;
    private JScrollPane currMessagePane;
    private JTextField addUserTextField;


    public ContactsGUI(MessengerSystem messenger, PanelStack panelStack){
        this.messenger = messenger;
        this.panelStack = panelStack;
        builder = new MessagePanelBuilder(mainPanel);

        options = builder.buildOptions(new String[]{"send msg", "remove", "view msgs"}, 300);
        addButton = builder.buildButton("add", 310, 80);
        mainBackButton = builder.makeBackButton();
        internalBackButton = builder.makeBackButton();

        for (JButton button : options){
            mainPanel.add(button);
        }
        mainPanel.add(addButton);
        mainPanel.add(mainBackButton);
        mainPanel.add(internalBackButton);
        internalBackButton.setEnabled(false);

        addButton.addActionListener(e -> addListener());
        panelHelper.mainBackListener(panelStack, mainBackButton, internalBackButton);
    }

    private void internalBackListener(){
        internalBackButton.setVisible(true);
        internalBackButton.setEnabled(true);
        mainBackButton.setVisible(false);
        internalBackButton.addActionListener(e -> {
            if (addUserTextField.isVisible()){
                addUserTextField.setVisible(false);
            }
            //listListener();
        });
    }

//    private void loadMainPage(){
////        for (Component c : mainPanel.getComponents()){
////            if (!c.getClass().equals("JLabel"))
////                c.setVisible(false);
////        }
//        mainPanel = mainPage();
//    }

    public void loadContacts(){
        contactListModel.clear();
        for (String s: messenger.getContacts()){
            contactListModel.addElement(s);
        }
        contactsJList = builder.buildJList(contactListModel);
        contacts = builder.buildMainPane(new JScrollPane(contactsJList), "contacts");
        //contacts.setVisible(true);
        mainPanel.add(contacts);
    }
    public JPanel mainPage(){
        addUserTextField = builder.buildTextField(310, 130);
        addUserTextField.setVisible(false);
        mainPanel.add(addUserTextField);
        loadContacts();
        listListener();
        return mainPanel;
    }

    public void listListener(){
        contactsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                //currContactIndex = contactsJList.getSelectedIndex();
                currContactIndex = e.getLastIndex();
                currSelectedContact = contactListModel.get(currContactIndex);
                panelHelper.enableButtons(options);
                listenToButtons();
            }
        });
    }
    private void addListener(){
        internalBackListener();
        panelHelper.disableButtons(options);
        addUserTextField.setVisible(true);
        addButton.addActionListener(e -> {
            if (!messenger.addUser(addUserTextField.getText())) {
                JOptionPane.showMessageDialog(mainPanel, "User does not exist or is already in your contacts!");
            }
            else {
                messenger.addUser(addUserTextField.getText());
                JOptionPane.showMessageDialog(mainPanel, "User successfully added!");
                addUserTextField.setText("");
                loadContacts();
            }
        });

    }

    private void listenToButtons() {
        options.get(0).addActionListener(e ->{
//            contacts.setEnabled(false);
//            contacts.setVisible(false);
            internalBackListener();
            sendMsgPanel();
        });
        options.get(1).addActionListener(e -> {
            messenger.removeUser(currSelectedContact);
            loadContacts();
        });
        options.get(2).addActionListener(e ->{
            internalBackListener();
            viewMsgsPanel();
        });
    }

    private void sendMsgPanel(){
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(null);
//        contacts.setEnabled(false);
        addUserTextField.setVisible(false);
        panelHelper.disableButtons(options);
        contacts.setVisible(false);
        addButton.setVisible(false);
        options.get(0).setEnabled(true);
        options.get(0).setVisible(true);
        Component[] elements = builder.prepareEditablePane("send msg");
        currMessageText = (JTextArea) elements[0];
        currMessagePane = (JScrollPane) elements[1];
        currMessagePane.setVisible(true);
        sendPanel.add(currMessagePane);
        mainPanel = sendPanel;
        internalBackListener();
        options.get(0).addActionListener(e -> {
            messenger.messageUser(currSelectedContact,
                currMessageText.getText());
        });
    }

    private void viewMsgsPanel(){
        contacts.setVisible(false);

    }

}
