package GUI.MessageMenus;

import Controller.MessengerSystem;
import GUI.Main.PanelStack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InboxGUI implements IMessageView {
    private JButton viewAllButton;
    private JButton viewArchiveButton;
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
    private JScrollPane currArchivePane;
    private JScrollPane currReplyPane;
    private JScrollPane currPane = new JScrollPane();
    private JTextArea currReplyText;
    private JButton sendButton;
    private JButton mainBackButton;
    private JButton internalBackButton;
    private JButton unarchiveButton;
    private int currMessageIndex;

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
        messageOptions = builder.buildOptions(new String[]{"unread", "reply", "archive", "delete"}, 260);
        viewArchiveButton = builder.buildButton("\uD83D\uDCC2", 225, 60, 50, 20);
        viewAllButton = builder.buildButton("\uD83D\uDCEA", 175, 60, 50, 20);
        unarchiveButton = builder.buildButton("unarchive", 310 ,340 );

        for (JButton button : messageOptions){
            mainPanel.add(button);
        }

        mainBackButton = builder.makeBackButton();
        internalBackButton = builder.makeBackButton();
        mainPanel.add(mainBackButton);
        mainPanel.add(internalBackButton);
        mainPanel.add(viewArchiveButton);
        mainPanel.add(viewAllButton);
        mainPanel.add(unarchiveButton);
        mainPanel.add(currPane);
    }



    private void internalBackListener(){
        mainBackButton.setVisible(false);
        internalBackButton.setVisible(true);
        internalBackButton.setEnabled(true);
        internalBackButton.addActionListener(e ->{
            //panelStack.loadPanel(mainPage());
//            if (!(currReplyPane == null)){
//                currReplyPane.setVisible(false);
//                currReplyText.setVisible(false);
//                currMsgPreview.setVisible(false);
//                sendButton.setEnabled(false);
//                sendButton.setVisible(false);
//
//            }
//            else if (!(currThread == null)){
//                currThread.setVisible(false);
//            }
//            inbox.setVisible(true);
//            panelHelper.enableButtons(messageOptions);
//            listListener();
//            builder.setTitle("inbox");
        });
    }

    private void loadInbox(){
        inboxListModel = new DefaultListModel<String>();
        for (String m : messenger.viewReceivedMessages()) {
            inboxListModel.addElement(m);
        }
        messageJList = builder.buildJList(inboxListModel);
        messageJList.setCellRenderer(listCellRenderer);
        inbox = new JScrollPane(messageJList);
        //mainPanel.add(builder.buildMainPane(inbox, "inbox"));
        currPane = builder.buildMainPane(inbox, "inbox");
    }
    public JPanel mainPage(){
        unarchiveButton.setVisible(false);
        panelHelper.enableButtons(messageOptions);
        mainBackButton.setEnabled(true);
        internalBackButton.setEnabled(false);
        panelHelper.mainBackListener(panelStack, mainBackButton, internalBackButton);
        loadInbox();
        //mainPanel.setVisible(true);

        currMsgPreview = builder.buildMessagePreview(new JScrollPane());
        currMsgPreview.setVisible(false);
        mainPanel.add(currMsgPreview);
        listListener();
        return mainPanel;
    }

    private void readMessage(){
        currMsgPreview.setViewportView(builder.prepareSelectedMessage(currSelectedMsg));
        currMsgPreview.setVisible(true);
        messenger.markMessageRead(currMessageIndex);
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

        Component[] elements = builder.prepareEditablePane("reply");
        currReplyText = (JTextArea) elements[0];
        currReplyPane = (JScrollPane) elements[1];
        sendButton = builder.buildButton("send",310, 260);
        sendButton.setVisible(true);
        mainPanel.add(currReplyPane);
        mainPanel.add(sendButton);
        internalBackListener();
        sendButton.addActionListener(e -> {
                messenger.replyMessage(currMessageIndex, currReplyText.getText());
                currReplyPane.setVisible(false);
                currReplyText.setVisible(false);
                currMsgPreview.setVisible(false);
                sendButton.setEnabled(false);
                sendButton.setVisible(false);
                mainPage();
        });
    }
    private void loadArchivePanel(){
//        panelHelper.disableButtons(messageOptions);
        messageOptions.get(2).setVisible(false);
        //inbox.setVisible(false);
        currMsgPreview.setVisible(false);

        DefaultListModel<String> archive = new DefaultListModel<String>();
        for (String m : messenger.viewArchivedMessages()) {
            archive.addElement(m);
        }
        JList archiveJList = builder.buildJList(inboxListModel);
        archiveJList.setCellRenderer(listCellRenderer);
        currArchivePane = builder.buildMainPane(new JScrollPane(archiveJList), "archive");
        mainPanel.add(currArchivePane);
        unarchiveButton.setVisible(true);
        unarchiveButton.addActionListener(e -> messenger.unarchiveMessage(currMessageIndex));
//        JScrollPane archivePane = builder.buildMainPane(new JScrollPane(archiveJList), "archive");
//        archivePane.setVisible(true);
//        mainPanel.add(archivePane);
    }

    private void loadThreadPanel(){
        panelHelper.disableButtons(messageOptions);
        inbox.setVisible(false);
        currMsgPreview.setVisible(false);
        String[] userInfo = currSelectedMsg.split("\\|");
        currThread = builder.buildMessageThread(messenger.viewMessages(userInfo[0]));
        currThread.setVisible(true);
        mainPanel.add(currThread);
    }
    private void listenToButtons(){
        // unread
        messageOptions.get(0).addActionListener(e -> {
            // need method to return current message state
            messenger.markMessageUnread(currMessageIndex);
            //Component c = messageJList.getComponent(currMessageIndex);
            //c.setBackground(Color.WHITE);
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
        // delete
        messageOptions.get(3).addActionListener(e -> {
            messenger.deleteMessage(currMessageIndex);
        });
        // view archived messages
        viewArchiveButton.addActionListener(e ->{
//            builder.setTitle("archive");
//            messageJList.setVisible(false);
//            inbox.setViewportView(loadArchivePanel());
            inbox.setVisible(false);
            loadArchivePanel();
            internalBackListener();
        });
        // view all messages from the sender in the selected message
        viewAllButton.addActionListener(e -> {
            loadThreadPanel();
            internalBackListener();
        });
    }

}
