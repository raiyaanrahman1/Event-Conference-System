package GUI.MessageMenus;

import Controller.MessengerSystem;
import GUI.Main.PanelStack;

import javax.swing.*;
import java.awt.*;
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
    // might not need currInboxPane
    private JScrollPane currInboxPane;
    private JTextArea currReplyText;
    private JButton sendButton;
    private JButton mainBackButton;
    private JButton internalBackButton;
    private JButton unarchiveButton;
    private int currMessageIndex;
    private DefaultListModel<String> archiveListModel;
    private JList archiveJList;
//    private JScrollPane archive;

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

        messageOptions.add(viewAllButton);
        for (JButton button : messageOptions){
            mainPanel.add(button);
        }

        mainBackButton = builder.makeBackButton();
        internalBackButton = builder.makeBackButton();
        internalBackButton.setVisible(false);
        unarchiveButton.setVisible(false);
        mainPanel.add(mainBackButton);
        mainPanel.add(internalBackButton);
        mainPanel.add(viewArchiveButton);
//        mainPanel.add(viewAllButton);
        mainPanel.add(unarchiveButton);

        internalBackListener();
        panelHelper.mainBackListener(panelStack, mainBackButton, internalBackButton);
//        unarchiveButton.addActionListener(e -> {
//            messenger.unarchiveMessage(currMessageIndex);
//            archiveListModel.removeElement(currSelectedMsg);
//            inboxListModel.addElement(currSelectedMsg);
//            archiveJList.setModel(archiveListModel);
//
//        });
    }



    private void internalBackListener(){
        internalBackButton.addActionListener(e -> {
            for (Component c : mainPanel.getComponents()){
                c.setVisible(false);
            }
            panelStack.getMainFrame().setContentPane(mainPage());
            viewArchiveButton.setVisible(true);
            viewAllButton.setVisible(true);
            mainBackButton.setVisible(true);
            mainBackButton.setEnabled(true);
        });
    }

    private void loadInbox(){
        inboxListModel = new DefaultListModel<String>();
        for (String m : messenger.viewReceivedMessages()) {
            if(!inboxListModel.contains(m))
                inboxListModel.addElement(m);
        }

        for (int i=0; i < inboxListModel.size(); i++) {
            if (!messenger.viewReceivedMessages().contains(inboxListModel.get(i)))
                inboxListModel.remove(i);
        }

        messageJList = builder.buildJList(inboxListModel);
        messageJList.setCellRenderer(listCellRenderer);
        currInboxPane = builder.buildMainPane(new JScrollPane(messageJList), "inbox");
        mainPanel.add(currInboxPane);
    }
    public JPanel mainPage(){
//        panelHelper.enableButtons(messageOptions);
        loadArchive();
        loadInbox();
        currArchivePane.setVisible(false);
        currInboxPane.setVisible(true);

        currMsgPreview = builder.buildMessagePreview(new JScrollPane());
        currMsgPreview.setVisible(false);
        mainPanel.add(currMsgPreview);
        internalBackButton.setVisible(false);
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
                if(currInboxPane.isVisible())
                    currMessageIndex = messageJList.getSelectedIndex();
                else
                    currMessageIndex = archiveJList.getSelectedIndex();

                if (currMessageIndex != -1) {
                    if (currInboxPane.isVisible()) {
                        currSelectedMsg = inboxListModel.get(currMessageIndex);
                        readMessage();
                        panelHelper.enableButtons(messageOptions);
                    }
                    else {
                        currSelectedMsg = archiveListModel.get(currMessageIndex);
                        // TODO: fix this if all archived messages are 'read' by default
                        panelHelper.enableButtons(messageOptions);
                        messageOptions.get(2).setVisible(false);
                        unarchiveButton.setVisible(true);
                    }
//                    panelHelper.enableButtons(messageOptions);
                    listenToButtons();
                }
            }
        });
    }

    private void loadReplyPanel(){
        panelHelper.disableButtons(messageOptions);
        currInboxPane.setVisible(false);
        Component[] elements = builder.prepareEditablePane("reply");
        currReplyText = (JTextArea) elements[0];
        currReplyPane = (JScrollPane) elements[1];
        sendButton = builder.buildButton("send",310, 260);
        sendButton.setVisible(true);
        mainPanel.add(currReplyPane);
        mainPanel.add(sendButton);
        internalBackListener();
        sendButton.addActionListener(e -> {
            if(!currReplyText.getText().equals(""))
                messenger.replyMessage(currMessageIndex, currReplyText.getText());
            currReplyPane.setVisible(false);
            currMsgPreview.setVisible(false);
            sendButton.setVisible(false);
//            currInboxPane.setVisible(true);
            panelStack.getMainFrame().setContentPane(mainPage());
        });
    }

    private void loadArchive(){
        archiveListModel = new DefaultListModel<>();

        for (String m : messenger.viewArchivedMessages()) {
            if(!archiveListModel.contains(m))
                archiveListModel.addElement(m);
        }

        for (int i=0; i < archiveListModel.size(); i++) {
            if (!messenger.viewArchivedMessages().contains(archiveListModel.get(i)))
                archiveListModel.remove(i);
        }
        archiveJList = builder.buildJList(archiveListModel);
        archiveJList.setCellRenderer(listCellRenderer);
        currArchivePane = builder.buildMainPane(new JScrollPane(archiveJList), "archive");
        currArchivePane.setVisible(true);
        mainPanel.add(currArchivePane);
    }
    private void loadArchivePanel(){
        panelHelper.disableButtons(messageOptions);
//        messageOptions.get(2).setVisible(false);
        //        currThread.setVisible(false);
        currInboxPane.setVisible(false);
        mainBackButton.setVisible(false);
        internalBackButton.setVisible(true);
        viewAllButton.setVisible(false);
        viewArchiveButton.setVisible(false);
        currArchivePane.setVisible(true);
        loadArchive();
        currMsgPreview.setViewportView(builder.prepareSelectedMessage(currSelectedMsg));
        currMsgPreview.setVisible(true);
        unarchiveButton.setVisible(true);
        unarchiveButton.addActionListener(e -> {
            messenger.unarchiveMessage(currMessageIndex);
            archiveListModel.removeElement(currSelectedMsg);
            inboxListModel.addElement(currSelectedMsg);
            archiveJList.setModel(archiveListModel);

        });
    }

    private void loadThreadPanel(){
        panelHelper.disableButtons(messageOptions);
        currInboxPane.setVisible(false);
        currMsgPreview.setVisible(false);
        String[] userInfo = currSelectedMsg.split("\\|");
        currThread = builder.buildMessageThread(messenger.viewMessages(userInfo[0]));
        currThread.setVisible(true);
        mainPanel.add(currThread);
    }
    private void listenToButtons(){
        // unread
        messageOptions.get(0).addActionListener(e -> {
            messenger.markMessageUnread(currMessageIndex);
        });
        // reply
        messageOptions.get(1).addActionListener(e -> {
            mainBackButton.setVisible(false);
            internalBackButton.setVisible(true);
            currMsgPreview.setViewportView(builder.prepareSelectedMessage(currSelectedMsg));
            currMsgPreview.setVisible(true);
            loadReplyPanel();

        });
        // archive
        messageOptions.get(2).addActionListener(e -> {
            messenger.archiveMessage(currMessageIndex);
            archiveListModel.addElement(currSelectedMsg);
            inboxListModel.removeElement(currSelectedMsg);
        });
        // delete
        messageOptions.get(3).addActionListener(e -> {
            messenger.deleteMessage(currMessageIndex);
            inboxListModel.removeElement(currSelectedMsg);
        });
        // view archived messages
        viewArchiveButton.addActionListener(e ->{
            loadArchivePanel();
        });
        // view all messages from the sender in the selected message
        viewAllButton.addActionListener(e -> {
            loadThreadPanel();
        });
    }

}
