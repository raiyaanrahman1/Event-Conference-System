package GUI.MessageMenus;

import Controller.MessengerSystem;
import GUI.Main.PanelStack;
import GUI.PanelBuilder.MessagePanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InboxGUI {
    /**
     *  The GUI class responsible for the Inbox Option in the Main Menu.
     */

    private MessengerSystem messenger;
    private PanelStack panelStack;
    private PanelHelper panelHelper = new PanelHelper();

    private MessagePanelBuilder inboxBuilder;
    private MessagePanelBuilder archiveBuilder;
    private MessagePanelBuilder replyBuilder;
    private MessagePanelBuilder viewAllBuilder;

    private JPanel inboxPanel = new JPanel();
    private JPanel archivePanel = new JPanel();
    private JPanel replyPanel = new JPanel();
    private JPanel viewAllPanel = new JPanel();

    // inboxPanel elements
    private JScrollPane currInboxPreview;
    private JList inboxJList;
    private DefaultListModel<String> inboxListModel = new DefaultListModel<>();
    private int currInboxIndex;
    private String currInboxMsg;

    // archivePanel elements
    private JScrollPane currArchivePreview;
    private JList archiveJList;
    private DefaultListModel<String> archiveListModel = new DefaultListModel<>();
    private int currArchiveIndex;
    private String currArchiveMsg;

    // replyPanel elements
    private JScrollPane currReplyPreview;
    private JTextArea currReplyText;

    // viewAllPanel elements
    private JScrollPane currThread;

    /*
     *  A DefaultListCellRenderer implementation specific to elements in the inboxJList.
     */
    private void setInboxListCellRendererComponent(){
        inboxJList.setCellRenderer(new DefaultListCellRenderer() {
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
        });
    }

    /*
     *  A DefaultListCellRenderer implementation specific to elements in the archiveJList.
     */
    private void setArchiveListCellRendererComponent(){
        archiveJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                setBackground(Color.LIGHT_GRAY);
                return c;
            }
        });
    }

    /**
     * Instantiates InboxGUI with the current running instance of MessengerSystem and PanelStack, and
     * instantiates the relevant builders for each panel.
     *
     * @param messenger The current instance of MessengerSystem instantiated in MainGUI
     * @param panelStack The current instance of PanelStack instantiated in MainGUI
     */
    public InboxGUI(MessengerSystem messenger, PanelStack panelStack) {

        this.messenger = messenger;
        this.panelStack = panelStack;
        inboxBuilder = new MessagePanelBuilder(inboxPanel);
        archiveBuilder = new MessagePanelBuilder(archivePanel);
        replyBuilder = new MessagePanelBuilder(replyPanel);
        viewAllBuilder = new MessagePanelBuilder(viewAllPanel);
    }

    /**
     * Builds the main page for InboxGUI
     * @return a JPanel showing this user's inbox and possible options
     */
    public JPanel mainPage(){
        return buildInbox();
    }

    private void inboxOptionListener(List<JButton> inboxOptions){
        // unread
        inboxOptions.get(0).addActionListener(e -> {
            messenger.markMessageUnread(currInboxIndex);
            panelHelper.makeButtonsInvisible(inboxOptions);
            inboxJList.updateUI();
            inboxPanel.validate();
        });

        // reply
        inboxOptions.get(1).addActionListener(e -> panelStack.loadPanel(buildReply()));

        // archive
        inboxOptions.get(2).addActionListener(e -> {
            messenger.archiveMessage(currInboxIndex);
            inboxListModel.removeElementAt(currInboxIndex);
            archiveListModel.addElement(currInboxMsg);
            currInboxPreview.setVisible(false);
            panelHelper.makeButtonsInvisible(inboxOptions);
        });

        // delete
        inboxOptions.get(3).addActionListener(e -> {
            messenger.deleteMessage(currInboxIndex);
            inboxListModel.removeElementAt(currInboxIndex);
            panelHelper.makeButtonsInvisible(inboxOptions);
        });

        // view all
        inboxOptions.get(4).addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    inboxBuilder.buildJLabel("Do you want to view all your received messages from this sender?"),
                    "Wait...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (dialogResult == JOptionPane.OK_OPTION){
                panelStack.loadPanel(buildViewAll());
            }
        });

    }

    private JScrollPane loadInbox(){
        for (String m : messenger.viewReceivedMessages()) {
            if(!inboxListModel.contains(m))
                inboxListModel.addElement(m);
        }

        for (int i=0; i < inboxListModel.size(); i++) {
            if (!messenger.viewReceivedMessages().contains(inboxListModel.get(i)))
                inboxListModel.removeElementAt(i);
        }

        inboxJList = inboxBuilder.buildJList(inboxListModel);
        return inboxBuilder.buildMainPane(new JScrollPane(inboxJList), "inbox");
    }


    private JPanel buildInbox(){
        JScrollPane inboxPane = loadInbox();
        inboxPanel.add(inboxPane);
        JButton inboxBack = inboxBuilder.makeBackButton();
        inboxPanel.add(inboxBack);
        panelHelper.mainBackListener(panelStack, inboxBack);

        currInboxPreview = inboxBuilder.buildMessagePreview(new JScrollPane());
        inboxPanel.add(currInboxPreview);
        currInboxPreview.setVisible(false);

        List<JButton> inboxOptions = inboxBuilder.buildOptions(new String[]{"unread", "reply",
                "archive", "delete"}, 260);
        inboxOptions.add(inboxBuilder.buildButton("\uD83D\uDCEA", 175,
                60, 50, 20));
        for (JButton button : inboxOptions){
            inboxPanel.add(button);
        }
        panelHelper.makeButtonsInvisible(inboxOptions);
        inboxOptionListener(inboxOptions);
        inboxJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                currInboxIndex = inboxJList.getSelectedIndex();
                if (currInboxIndex != -1){
                    currInboxMsg = inboxListModel.get(currInboxIndex);
                    currInboxPreview.setViewportView(inboxBuilder.prepareSelectedMessage(currInboxMsg));
                    currInboxPreview.setVisible(true);
                    messenger.markMessageRead(currInboxIndex);
                    panelHelper.makeButtonsVisible(inboxOptions);
                }
            }
        });

        JButton viewArchive = inboxBuilder.buildButton("\uD83D\uDCC2", 225,
                60, 50, 20);
        inboxPanel.add(viewArchive);
        viewArchive.addActionListener(e -> panelStack.loadPanel(buildArchive()));
        setInboxListCellRendererComponent();
        return inboxPanel;
    }

    private JScrollPane loadArchive(){
        for (String m : messenger.viewArchivedMessages()) {
            if(!archiveListModel.contains(m))
                archiveListModel.addElement(m);
        }

        for (int i=0; i < archiveListModel.size(); i++) {
            if (!messenger.viewArchivedMessages().contains(archiveListModel.get(i)))
                archiveListModel.removeElementAt(i);
        }

        archiveJList = archiveBuilder.buildJList(archiveListModel);
        return archiveBuilder.buildMainPane(new JScrollPane(archiveJList), "archive");

    }

    private void archiveListener(JButton unarchive){
        unarchive.addActionListener(e -> {
            messenger.unarchiveMessage(currArchiveIndex);
            archiveListModel.removeElementAt(currArchiveIndex);
            inboxListModel.addElement(currArchiveMsg);
            currArchivePreview.setVisible(false);
            unarchive.setVisible(false);


        });
    }

    private JPanel buildArchive(){
        JScrollPane archivePane = loadArchive();

        archivePanel.add(archivePane);
        JButton archiveBack = archiveBuilder.makeBackButton();
        archivePanel.add(archiveBack);
        panelHelper.mainBackListener(panelStack, archiveBack);

        JButton unarchive = archiveBuilder.buildButton("unarchive",310, 260);
        archivePanel.add(unarchive);
        unarchive.setVisible(false);
        archiveListener(unarchive);
        archiveJList.addListSelectionListener(e->{
            if (!e.getValueIsAdjusting()) {
                currArchiveIndex = archiveJList.getSelectedIndex();
                if (currArchiveIndex != -1) {
                    currArchiveMsg = archiveListModel.get(currArchiveIndex);
                    currArchivePreview.setViewportView(archiveBuilder.prepareSelectedMessage(currArchiveMsg));
                    currArchivePreview.setVisible(true);
                    unarchive.setVisible(true);
                }
            }
        });
        currArchivePreview = archiveBuilder.buildMessagePreview(new JScrollPane());
        archivePanel.add(currArchivePreview);
        currArchivePreview.setVisible(false);
        setArchiveListCellRendererComponent();
        return archivePanel;
    }

    private JPanel buildReply() {
        JButton replyBack = replyBuilder.makeBackButton();
        replyPanel.add(replyBack);
        panelHelper.mainBackListener(panelStack, replyBack);

        Component[] elements = replyBuilder.prepareEditablePane("reply");
        currReplyText = (JTextArea) elements[0];
        JScrollPane currReplyPane = (JScrollPane) elements[1];
        replyPanel.add(currReplyPane);
        JButton sendButton = replyBuilder.buildButton("send", 305, 260);
        sendButton.setVisible(true);
        replyPanel.add(sendButton);
        currReplyPreview = replyBuilder.buildMessagePreview(new JScrollPane(
                replyBuilder.prepareSelectedMessage(currInboxMsg)));
        replyPanel.add(currReplyPreview);
        sendButton.addActionListener(e -> {
            if (!currReplyText.getText().equals("")) {
                messenger.replyMessage(currInboxIndex, currReplyText.getText());
                JOptionPane.showMessageDialog(null, replyBuilder.buildJLabel("Message sent successfully!"),
                        "Nice!", JOptionPane.INFORMATION_MESSAGE);
                currReplyText.setText("");
            } else {
                JOptionPane.showMessageDialog(null, replyBuilder.buildJLabel("Please enter a message!"),
                        "Hey!", JOptionPane.WARNING_MESSAGE);
            }
        });
        return replyPanel;
    }

    private JPanel buildViewAll() {
        JButton viewAllBack = viewAllBuilder.makeBackButton();
        viewAllPanel.add(viewAllBack);
        panelHelper.mainBackListener(panelStack, viewAllBack);
        String[] userInfo = currInboxMsg.split("\\|");
        currThread = viewAllBuilder.buildMessageThread(messenger.viewMessages(userInfo[0]));
        viewAllPanel.add(currThread);
        return viewAllPanel;
    }

}
