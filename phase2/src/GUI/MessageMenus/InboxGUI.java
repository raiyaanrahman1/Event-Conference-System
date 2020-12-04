package GUI.MessageMenus;

import Controller.LoginSystem;
import Controller.MessengerSystem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.List;

public class InboxGUI implements IMessageView {
    private MessagePanelBuilder builder;
    private MessengerSystem messenger;
    private JButton replyButton;
    private JScrollPane inbox;
    private DefaultListModel<String> inboxListModel;
    private JList messageJList;
    private JPanel mainPanel = new JPanel();
    private String currSelectedMsg;
    private JButton[] messageOptions;
    private JScrollPane currMsgPreview;

    public InboxGUI(MessengerSystem messenger){
        this.messenger = messenger;
        builder = new MessagePanelBuilder(mainPanel);
        messageOptions = builder.buildOptions(); // {view, reply, archive, viewAll}
        for (JButton button : messageOptions){
            mainPanel.add(button);
            button.setEnabled(false);
        }
    }

    public JPanel mainPage(){
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
        messageJList = builder.buildInboxJList(inboxListModel);
        inbox = new JScrollPane(messageJList);

        mainPanel.add(builder.buildInbox(inbox));
        mainPanel.setVisible(true);

        currMsgPreview = builder.buildMessagePreview(new JScrollPane());
        mainPanel.add(currMsgPreview);
        currMsgPreview.setVisible(false);
        listListener();




        return mainPanel;
    }

    public void listListener(){
        messageJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                currMsgPreview.setVisible(false);
                int messageIndex = messageJList.getSelectedIndex();
                currSelectedMsg = inboxListModel.get(messageIndex);
                enableButtons();
                listenToButtons();
            }
        });
    }
    private void listenToButtons(){
//        JScrollPane currPreview = new JScrollPane(
//                builder.prepareSelectedMessage(currSelectedMsg),
//                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        builder.buildMessagePreview(currPreview);
//        currPreview.setVisible(false);

        // view
        messageOptions[0].addActionListener(e -> {
            currMsgPreview.setViewportView(builder.prepareSelectedMessage(currSelectedMsg));
            currMsgPreview.setVisible(true);
        });
        // reply
        messageOptions[1].addActionListener(e -> {
            currMsgPreview.setVisible(false);
        });
        // archive
        messageOptions[2].addActionListener(e -> {
            //null;
        });
        // viewAll
        messageOptions[3].addActionListener(e -> {
            //null;
        });
    }
    private void enableButtons(){
        for (JButton button : messageOptions){
            button.setEnabled(true);
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
