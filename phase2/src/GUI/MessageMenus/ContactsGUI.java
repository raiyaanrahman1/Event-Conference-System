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
    private JScrollPane currViewMessagePane;
    private JButton internalAddButton;
    private JButton internalSendButton;


    public ContactsGUI(MessengerSystem messenger, PanelStack panelStack){
        this.messenger = messenger;
        this.panelStack = panelStack;
        builder = new MessagePanelBuilder(mainPanel);

        options = builder.buildOptions(new String[]{"send msg", "remove", "view msgs"}, 300);
        internalSendButton = builder.buildButton("send", 310, 300);
        addButton = builder.buildButton("add", 310, 80);
        internalAddButton = builder.buildButton("add", 310, 80);
        mainBackButton = builder.makeBackButton();
        internalBackButton = builder.makeBackButton();

        for (JButton button : options){
            mainPanel.add(button);
        }
        mainPanel.add(addButton);
        mainPanel.add(internalAddButton);
        mainPanel.add(mainBackButton);
        mainPanel.add(internalBackButton);
        mainPanel.add(internalSendButton);
        internalBackButton.setVisible(false);
        internalAddButton.setVisible(false);
        internalSendButton.setVisible(false);

        addButton.addActionListener(e -> addListener());
        panelHelper.mainBackListener(panelStack, mainBackButton, internalBackButton);
        internalBackListener();
        addUserTextField = builder.buildTextField(310, 130);
        addUserTextField.setVisible(false);
        mainPanel.add(addUserTextField);
    }

    private void internalBackListener(){
        internalBackButton.addActionListener(e -> {
            for (Component c : mainPanel.getComponents()){
                c.setVisible(false);
            }
            mainBackButton.setVisible(true);
            addButton.setVisible(true);
            panelStack.getMainFrame().setContentPane(mainPage());
        });
    }

    public void loadContacts(){
        mainBackButton.setVisible(true);
        for (String s: messenger.getContacts()){
            if (!contactListModel.contains(s))
                contactListModel.addElement(s);
        }

        for (int i=0; i < contactListModel.size(); i++) {
            if (!messenger.getContacts().contains(contactListModel.get(i)))
                contactListModel.remove(i);
        }


        contactsJList = builder.buildJList(contactListModel);
        contacts = builder.buildMainPane(new JScrollPane(contactsJList), "contacts");
        mainPanel.add(contacts);
    }
    public JPanel mainPage(){
        mainBackButton.setVisible(true);
        internalBackButton.setVisible(false);
        loadContacts();
        listListener();
        return mainPanel;
    }

    public void listListener(){
        contactsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                currContactIndex = contactsJList.getSelectedIndex();
//                currContactIndex = e.getLastIndex();
                if (currContactIndex != -1) {
                    currSelectedContact = contactListModel.get(currContactIndex);
                    panelHelper.enableButtons(options);
                    listenToButtons();
                }
            }
        });
    }
    private void addListener(){
        mainBackButton.setVisible(false);
        internalBackButton.setVisible(true);
        panelHelper.disableButtons(options);
        addButton.setVisible(false);
        internalAddButton.setVisible(true);
        addUserTextField.setVisible(true);
        internalAddButton.addActionListener(e -> {
            if (!messenger.addUser(addUserTextField.getText())) {
                JOptionPane.showMessageDialog(mainPanel, "User does not exist or is already in your contacts!");
                addUserTextField.setText("");
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
        // send msg
        options.get(0).addActionListener(e ->{
            mainBackButton.setVisible(false);
            internalBackButton.setVisible(true);
            sendMsgPanel();
        });

        // remove
        options.get(1).addActionListener(e -> {
            messenger.removeUser(currSelectedContact);
            contactListModel.removeElement(currSelectedContact);
//            contacts.revalidate();
//            loadContacts();
//            listListener();
//            currContactIndex = contactsJList.getSelectedIndex();
//            contacts.setViewportView(contactsJList);

        });

        // view msgs
        options.get(2).addActionListener(e ->{
            mainBackButton.setVisible(false);
            internalBackButton.setVisible(true);
            viewMsgsPanel();
        });
    }

    private void sendMsgPanel(){
        addUserTextField.setVisible(false);
        panelHelper.disableButtons(options);
        contacts.setVisible(false);
        addButton.setVisible(false);
        internalSendButton.setVisible(true);
        Component[] elements = builder.prepareEditablePane("send msg");
        currMessageText = (JTextArea) elements[0];
        currMessagePane = (JScrollPane) elements[1];
        currMessagePane.setVisible(true);
        mainPanel.add(currMessagePane);
        internalSendButton.addActionListener(e -> {
            messenger.messageUser(currSelectedContact,
                currMessageText.getText());
            currMessagePane.setVisible(false);
            internalSendButton.setVisible(false);
            contacts.setVisible(true);
        });
    }

    private void viewMsgsPanel(){
        addUserTextField.setVisible(false);
        panelHelper.disableButtons(options);
        //currMessagePane.setVisible(false);
        contacts.setVisible(false);

        addButton.setVisible(false);
        DefaultListModel<String> list = new DefaultListModel<>();
        for (String s: messenger.viewMessages(currSelectedContact)){
            list.addElement(s);
        }
        JList messages = builder.buildJList(list);
        currViewMessagePane = builder.buildMainPane(new JScrollPane(messages), "view all");
        currViewMessagePane.setVisible(true);
        mainPanel.add(currViewMessagePane);
    }

}
