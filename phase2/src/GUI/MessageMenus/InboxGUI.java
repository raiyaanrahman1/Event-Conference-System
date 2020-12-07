package GUI.MessageMenus;

import Controller.MessengerSystem;
import GUI.Main.PanelStack;
import com.sun.org.apache.bcel.internal.util.ClassVector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InboxGUI implements IMessageView {

    private InboxListRenderer listCellRenderer = new InboxListRenderer();
    private MessengerSystem messenger;
    private PanelStack panelStack;
    private MessagePanel panelHelper = new MessagePanel();

    private MessagePanelBuilder inboxBuilder;
    private MessagePanelBuilder archiveBuilder;
    private MessagePanelBuilder replyBuilder;

    private JPanel inboxPanel = new JPanel();
    private JPanel archivePanel = new JPanel();
    private JPanel replyPanel = new JPanel();
    private JPanel viewAllPanel = new JPanel();

    private JButton backButton;

    // inboxPanel elements
    private JScrollPane currInboxPreview;
    private JList inboxJList;
    private DefaultListModel<String> inboxListModel;
    private int currInboxIndex;
    private String currInboxMsg;

    // archivePanel elements
    private JScrollPane currArchivePreview;
    private JList archiveJList;
    private DefaultListModel<String> archiveListModel;
    private int currArchiveIndex;
    private String currArchiveMsg;

    // replyPanel elements
    private JScrollPane currReplyPreview;
    private JList replyJList;
    private DefaultListModel<String> replyListModel;
    private int currReplyIndex;
    private String currReplyMsg;

    // viewAllPanel elements


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

    public InboxGUI(MessengerSystem messenger, PanelStack panelStack) {

        this.messenger = messenger;
        this.panelStack = panelStack;
        inboxBuilder = new MessagePanelBuilder(inboxPanel);
        archiveBuilder = new MessagePanelBuilder(archivePanel);
        replyBuilder = new MessagePanelBuilder(replyPanel);

//        backButton = inboxBuilder.makeBackButton();
//        panelHelper.mainBackListener(panelStack, backButton);
    }

    private void inboxOptionListener(List<JButton> inboxOptions){
        inboxJList.addListSelectionListener(e ->{
            if (!e.getValueIsAdjusting()) {
                currInboxIndex = inboxJList.getSelectedIndex();
                if (currInboxIndex != 1){
                    currInboxMsg = inboxListModel.get(currInboxIndex);
                    currInboxPreview.setViewportView(inboxBuilder.prepareSelectedMessage(currInboxMsg));
                    currInboxPreview.setVisible(true);
                    messenger.markMessageRead(currInboxIndex);
                    panelHelper.enableButtons(inboxOptions);
                }
            }
        });

        // unread
        inboxOptions.get(0).addActionListener(e -> {
            messenger.markMessageUnread(currInboxIndex);
        });

        // reply
        inboxOptions.get(1).addActionListener(e -> {
            panelStack.loadPanel(buildReply());
        });

        // archive
        inboxOptions.get(2).addActionListener(e -> {
            messenger.archiveMessage(currInboxIndex);
            inboxListModel.removeElement(currInboxMsg);
        });

        // delete
        inboxOptions.get(3).addActionListener(e -> {
            messenger.deleteMessage(currInboxIndex);
            inboxListModel.removeElement(currInboxMsg);
        });

        // view all
        inboxOptions.get(4).addActionListener(e -> {
            // TODO: Add mouse listener that informs user what this does
            panelStack.loadPanel(buildViewAll());
        });

    }

    public JPanel mainPage(){
        return buildInbox();
    }

    private JScrollPane loadInbox(){
        inboxListModel = new DefaultListModel<String>();
        for (String m : messenger.viewReceivedMessages()) {
            if(!inboxListModel.contains(m))
                inboxListModel.addElement(m);
        }

        for (int i=0; i < inboxListModel.size(); i++) {
            if (!messenger.viewReceivedMessages().contains(inboxListModel.get(i)))
                inboxListModel.remove(i);
        }

        inboxJList = inboxBuilder.buildJList(inboxListModel);
//        inboxJList.setCellRenderer(listCellRenderer);
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
        panelHelper.disableButtons(inboxOptions);
        inboxOptionListener(inboxOptions);

        JButton viewArchive = inboxBuilder.buildButton("\uD83D\uDCC2", 225,
                60, 50, 20);
        inboxPanel.add(viewArchive);
        viewArchive.addActionListener(e ->{
            panelStack.loadPanel(buildArchive());
        });
        return inboxPanel;
    }

    private JScrollPane loadArchive(){
        archiveListModel = new DefaultListModel<String>();
        for (String m : messenger.viewArchivedMessages()) {
            if(!archiveListModel.contains(m))
                archiveListModel.addElement(m);
        }

        for (int i=0; i < archiveListModel.size(); i++) {
            if (!messenger.viewArchivedMessages().contains(archiveListModel.get(i)))
                archiveListModel.remove(i);
        }

        archiveJList = archiveBuilder.buildJList(archiveListModel);
//        archiveJList.setCellRenderer(listCellRenderer);
        return archiveBuilder.buildMainPane(new JScrollPane(archiveJList), "archive");
    }

    private void archiveListener(JButton unarchive){
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

        unarchive.addActionListener(e -> {
            messenger.unarchiveMessage(currArchiveIndex);
            archiveListModel.removeElement(currArchiveMsg);
            inboxListModel.addElement(currArchiveMsg);
        });
    }

    private JPanel buildArchive(){
        JScrollPane archivePane = loadArchive();
        archivePanel.add(archivePane);
        JButton archiveBack = inboxBuilder.makeBackButton();
        archivePanel.add(archiveBack);
        panelHelper.mainBackListener(panelStack, archiveBack);

        JButton unarchive = archiveBuilder.buildButton("unarchive",310, 260);
        archivePanel.add(unarchive);
        unarchive.setVisible(false);
        archiveListener(unarchive);

        currArchivePreview = archiveBuilder.buildMessagePreview(new JScrollPane());
        archivePanel.add(currArchivePreview);
        currArchivePreview.setVisible(false);

        return archivePanel;
    }

    private JPanel buildReply(){
//        JScrollPane replyPane = new JScrollPane();
//        replyPanel.add(replyPane);
//        replyPanel.add(backButton);
//
//        currInboxPreview = replyBuilder.buildMessagePreview(new JScrollPane());
//        replyPanel.add(currInboxPreview);
//        currInboxPreview.setVisible(false);

        return replyPanel;
    }

    private JPanel buildViewAll() {
        return replyPanel;
    }

}
