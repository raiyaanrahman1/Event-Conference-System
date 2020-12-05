package GUI.MessageMenus;

import Controller.MessengerSystem;
import GUI.Main.PanelStack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InboxGUI implements IMessageView {
    private MessagePanel panelHelper = new MessagePanel();
    private PanelStack panelStack;
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

    private InboxListRenderer listCellRenderer = new InboxListRenderer();

    private class InboxListRenderer extends DefaultListCellRenderer {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (messenger.isRead(index))
                    c.setBackground(Color.LIGHT_GRAY);
                else
                    c.setBackground(Color.WHITE);
                return c;
            }
    }
    public InboxGUI(MessengerSystem messenger, PanelStack panelStack){

        this.messenger = messenger;
        this.panelStack = panelStack;
        builder = new MessagePanelBuilder(mainPanel);
        // unread, reply, archive, delete, view All, view archive
        messageOptions = builder.buildOptions(new String[]{"unread", "reply", "archive", "view All"}, 260);
        for (JButton button : messageOptions){
            mainPanel.add(button);
        }
        panelHelper.enableButtons(messageOptions);
        // duplicate code in ContactsGUI, make interface?
        mainBackButton = builder.makeBackButton();
        internalBackButton = builder.makeBackButton();
        mainPanel.add(mainBackButton);
        mainPanel.add(internalBackButton);
        mainBackButton.setEnabled(true);
        internalBackButton.setEnabled(false);
        panelHelper.mainBackListener(panelStack, mainBackButton, internalBackButton);
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
            panelHelper.enableButtons(messageOptions);
            listListener();
            builder.setTitle("inbox");
        });
    }

    public JPanel mainPage(){
        inboxListModel = new DefaultListModel<String>();
        for (String m : messenger.viewReceivedMessages()) {
            inboxListModel.addElement(m);
        }
        messageJList = builder.buildJList(inboxListModel);
        messageJList.setCellRenderer(listCellRenderer);
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

    private void readMessage(){
        currMsgPreview.setViewportView(builder.prepareSelectedMessage(currSelectedMsg));
        currMsgPreview.setVisible(true);
        //messageJList.getCellRenderer().getListCellRendererComponent()


    }

    public void listListener(){

        messageJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
//                currMessageIndex = messageJList.getSelectedIndex();
                currMessageIndex = e.getLastIndex();
                currSelectedMsg = inboxListModel.get(currMessageIndex);
                readMessage();
                panelHelper.enableButtons(messageOptions);
                listenToButtons();
            }
        });
    }

    private void loadReplyPanel(){
        panelHelper.disableButtons(messageOptions);
        inbox.setVisible(false);
        // might have to add a JPanel for where the inbox/reply is
        Component[] elements = builder.prepareEditablePane("reply");
        currReplyText = (JTextArea) elements[0];
        currReplyPane = (JScrollPane) elements[1];
        sendButton = builder.buildButton("send",310, 260);
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
                panelHelper.enableButtons(messageOptions);
                listListener();
                builder.setTitle("inbox");
            }
        });
    }


    private void loadThreadPanel(){
        panelHelper.disableButtons(messageOptions);
        inbox.setVisible(false);
        currMsgPreview.setVisible(false);
        // get sender and call messenger.viewMessages(username)
        currThread = builder.buildMessageThread(testMsgs);
        currThread.setVisible(true);
        mainPanel.add(currThread);
    }
    private void listenToButtons(){
        // unread
        messageOptions.get(0).addActionListener(e -> {
            // need method to return current message state
            messenger.markMessageRead(currMessageIndex);
            Component c = messageJList.getComponent(currMessageIndex);
            c.setBackground(Color.WHITE);
        });
        // reply
        messageOptions.get(1).addActionListener(e -> {
            currMsgPreview.setViewportView(builder.prepareSelectedMessage(currSelectedMsg));
            currMsgPreview.setVisible(true);
            loadReplyPanel();

        });
        // archive
        messageOptions.get(2).addActionListener(e -> {
            messenger.archiveMessage(currMessageIndex);
        });
        // viewAll
        messageOptions.get(3).addActionListener(e -> {
            loadThreadPanel();
            internalBackListener();
        });
    }

//
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Main");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500,500);
//        frame.setResizable(false);
//        frame.setVisible(true);
//        LoginSystem loginSystem = new LoginSystem();
//        InboxGUI test = new InboxGUI(loginSystem.getMsgSys());
//        frame.setContentPane(test.mainPage());
//
//    }
}
