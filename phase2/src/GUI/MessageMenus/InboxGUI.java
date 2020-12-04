package GUI.MessageMenus;

import Controller.LoginSystem;
import Controller.MessengerSystem;
import com.sun.istack.internal.NotNull;
import javafx.scene.control.DialogPane;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InboxGUI implements IMessageView {
    private MessagePanelBuilder builder;
    private MessengerSystem messenger;
    private JScrollPane inbox;
    private DefaultListModel<String> inboxListModel;
    private JList messageJList;
    private JPanel mainPanel = new JPanel();
    private String currSelectedMsg;
    private List<JButton> messageOptions;
    private JScrollPane currMsgPreview;
    private JScrollPane currReplyPane;
    private JTextArea currReplyText;
    private JButton sendButton;
    private JButton mainBackButton;
    private JButton internalBackButton;
    private int currMessageIndex;

    private List<String> testMsgs;
    private JScrollPane currThread;

    public InboxGUI(MessengerSystem messenger){
        this.messenger = messenger;
        builder = new MessagePanelBuilder(mainPanel);
        messageOptions = builder.buildOptions(new String[]{"view", "reply", "archive", "viewAll"}, 260);
        for (JButton button : messageOptions){
            mainPanel.add(button);
            button.setEnabled(false);
        }
        // duplicate code in ContactsGUI, make interface?
        mainBackButton = builder.makeBackButton();
        internalBackButton = builder.makeBackButton();
        mainPanel.add(mainBackButton);
        internalBackButton.setVisible(false);
        mainPanel.add(internalBackButton);
    }
    private void mainBackListener(){
        mainBackButton.setVisible(true);
        internalBackButton.setVisible(false);
        mainBackButton.addActionListener(e -> {
            // go back to main menu
        });
    }

    private void internalBackListener(){
        mainBackButton.setVisible(false);
        internalBackButton.setVisible(true);
        internalBackButton.addActionListener(e ->{
            if (!(currReplyPane == null)){
                currReplyPane.setVisible(false);
                currReplyText.setVisible(false);
                currMsgPreview.setVisible(false);
                sendButton.setEnabled(false);
                sendButton.setVisible(false);

            }
            else if (!(currThread == null)){
                currThread.setVisible(false);
            }
            inbox.setVisible(true);
            enableButtons();
            listListener();
            builder.setTitle("inbox");
        });
    }
    public JPanel mainPage(){
        testMsgs = new ArrayList<>();
        testMsgs.add("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj" +
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj" +
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj"+
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj"+
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj"+
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjjj");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");
        testMsgs.add("HEY");

        inboxListModel = new DefaultListModel<String>();
        for (String m : testMsgs) { // should be messenger.viewReceivedMessages()
            inboxListModel.addElement(m);
        }
        messageJList = builder.buildJList(inboxListModel);
        inbox = new JScrollPane(messageJList);
        inbox.setVisible(true);
        mainPanel.add(builder.buildMainPane(inbox, "inbox"));
        mainPanel.setVisible(true);

        currMsgPreview = builder.buildMessagePreview(new JScrollPane());
        currMsgPreview.setVisible(false);
        mainPanel.add(currMsgPreview);

        listListener();




        return mainPanel;
    }

    public void listListener(){

        messageJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                currMsgPreview.setVisible(false);
                currMessageIndex = messageJList.getSelectedIndex();
                currSelectedMsg = inboxListModel.get(currMessageIndex);
                enableButtons();
                listenToButtons();
            }
        });
    }

    private void loadReplyPanel(){
        hideButtons();
        inbox.setVisible(false);
        // might have to add a JPanel for where the inbox/reply is
        Component[] temp = builder.prepareReplyPane();
        currReplyText = (JTextArea) temp[0];
        currReplyPane = (JScrollPane) temp[1];
        sendButton = builder.buildButton("send");
        sendButton.setVisible(true);
        mainPanel.add(currReplyPane);
        mainPanel.add(sendButton);
        internalBackListener();
        sendButton.addActionListener(e -> {
            if (true) {
                //messenger.replyMessage(currMessageIndex, currReplyText.getText());
                // duplicate code in internalBackListener
                currReplyPane.setVisible(false);
                currReplyText.setVisible(false);
                currMsgPreview.setVisible(false);
                sendButton.setEnabled(false);
                sendButton.setVisible(false);
                inbox.setVisible(true);
                enableButtons();
                listListener();
                builder.setTitle("inbox");
            }
        });
    }


    private void loadThreadPanel(){
        hideButtons();
        inbox.setVisible(false);
        currMsgPreview.setVisible(false);
        // get sender and call messenger.viewMessages(username)
        currThread = builder.buildMessageThread(testMsgs);
        currThread.setVisible(true);
        mainPanel.add(currThread);
    }
    private void listenToButtons(){
        // view
        messageOptions.get(0).addActionListener(e -> {
            currMsgPreview.setViewportView(builder.prepareSelectedMessage(currSelectedMsg));
            currMsgPreview.setVisible(true);
        });
        // reply
        messageOptions.get(1).addActionListener(e -> {
            currMsgPreview.setViewportView(builder.prepareSelectedMessage(currSelectedMsg));
            currMsgPreview.setVisible(true);
            loadReplyPanel();

        });
        // archive
        messageOptions.get(2).addActionListener(e -> {
            //null;
        });
        // viewAll
        messageOptions.get(3).addActionListener(e -> {
            loadThreadPanel();
            internalBackListener();
        });
    }
    private void enableButtons(){
        for (JButton button : messageOptions){
            button.setEnabled(true);
            button.setVisible(true);
        }
    }
    private void hideButtons(){
        for (JButton button : messageOptions){
            button.setVisible(false);
        }
    }
//    public JPanel menuPage() {
//        JPanel panel = builder.buildMain();
//        panel.setVisible(true);
//        JButton[] menuButtons = builder.buildMenuButtons();
//        // might not need a button panel
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(null);
//        buttonPanel.setBounds(20, 80, 250, 250);
//        panel.add(menuButtons[0]);
//        menuButtons[0].setBounds(20, 5, 50, 30);
//        panel.add(menuButtons[1]);
//        menuButtons[0].setBounds(20, 30, 50, 30);
//        panel.add(buttonPanel, BorderLayout.CENTER);
//        menuListen(menuButtons[0], menuButtons[1]);
//
//        return panel;
//    }

//    private void menuListen(JButton inbox, JButton contacts) {
//        inbox.addActionListener(e -> inboxPage());
//        contacts.addActionListener(e -> {
//            //contactPage();
//        });
//    }
//
//    private void backListen(){
//        builder.backButton.addActionListener(e -> menuPage());
//    }

//    public JPanel contactPage(){
////        builder.buildContacts(mainPanel, messenger.getContacts());
//        builder.buildContacts(mainPanel, new ArrayList<>());
//        backListen();
//        return mainPanel;
//    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setVisible(true);
        LoginSystem loginSystem = new LoginSystem();
        InboxGUI test = new InboxGUI(loginSystem.getMsgSys());
        frame.setContentPane(test.mainPage());

    }
}
