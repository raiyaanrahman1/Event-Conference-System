package GUI.MessageMenus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;

public class MessagePanelBuilder {
    private JPanel mainPanel;
    private JLabel title;
    private JButton backButton;
    private Font titleFont;
    private Font infoFont;


    public MessagePanelBuilder(JPanel panel){
        this.mainPanel = panel;
        titleFont = new Font(Font.MONOSPACED, Font.TYPE1_FONT, 40);
        infoFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
        makeTitle();
        makeBackButton();
        preparePanel(mainPanel);
    }

    private void preparePanel(JPanel panel){
        mainPanel.setLayout(null);
        mainPanel.setSize(500, 500);
        mainPanel.add(backButton);
        mainPanel.add(title);
        mainPanel.setVisible(true);
    }

    public void setMainPanel(JPanel other){
        this.mainPanel = other;
        preparePanel(other);

    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

    public void makeBackButton(){
        backButton = new JButton("back");
        backButton.setFont(infoFont);
        backButton.setBounds(350, 22, 75, 30);
//        backButton.setSize(new Dimension(25, 20));
//        backButton.setVerticalAlignment(backButton.TOP);
//        backButton.setHorizontalAlignment(backButton.LEFT);
    }

    private void makeTitle(){
        title = new JLabel("");
        title.setFont(titleFont);
        title.setBounds(50, 5, 120, 60);
//        title.setSize(new Dimension(25, 20));
//        title.setVerticalAlignment(title.TOP);
//        title.setVerticalAlignment(title.CENTER);
    }

    public JPanel buildMain(){
        title.setText("Messages");
        backButton.setVisible(false);
        return mainPanel;
    }

//    public JButton[] buildMenuButtons(){
//        JButton inbox = new JButton("Inbox");
//        inbox.setSize(50, 20);
//        JButton contacts = new JButton("Contacts");
//        contacts.setSize(50,20 );
//        return new JButton[]{inbox, contacts};
//    }
//
//    public JPanel createMessage(String content){
//        return new JPanel();
//
//    }

    public JScrollPane buildInbox(JScrollPane jScroll){
        title.setText("inbox");
        jScroll.setBounds(50, 80, 225, 340);
        jScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScroll.setVisible(true);
        mainPanel.add(jScroll);
        mainPanel.add(title);
        return jScroll;
    }
    public JScrollPane buildMessagePreview(JScrollPane messagePreview){
        messagePreview.setBounds(300, 80, 125, 160);
        //messagePreview.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //messagePreview.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(messagePreview);
//        messagePreview.setVisible(true);
        return messagePreview;
    }

    public JTextArea prepareSelectedMessage(String message){
        JTextArea content = new JTextArea(message);
        content.setVisible(true);
        content.setEditable(false);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        return content;
    }
    public JButton[] buildOptions(){
        // make this shorter using iteration
        // view the current selected message
        JButton viewMsg = new JButton("view");
        viewMsg.setFont(infoFont);
        viewMsg.setBounds(310, 260, 100, 30);
        //viewMsg.setVisible(true);
        //mainPanel.add(viewMsg);

        // reply to the current selected message
        JButton reply = new JButton("reply");
        reply.setFont(infoFont);
        reply.setBounds(310, 300, 100, 30);
        //reply.setEnabled(false);
        //mainPanel.add(reply);

        // archive selected message
        JButton archive = new JButton("archive");
        archive.setFont(infoFont);
        archive.setBounds(310, 340, 100, 30);
        //archive.setEnabled(false);
        //mainPanel.add(archive);


        // view all received messages from sender
        JButton viewAll = new JButton("view all");
        viewAll.setFont(infoFont);
        viewAll.setBounds(310, 380, 100, 30);
        //viewAll.setEnabled(false);
        //mainPanel.add(viewAll);

        return new JButton[]{viewMsg, reply, archive, viewAll};
    }


    public JList buildInboxJList(DefaultListModel<String> msgs){

        JList messageList = new JList(msgs);
        messageList.setBounds(50,80, 220,300);
        messageList.setFixedCellHeight(40);
        messageList.setFixedCellWidth(100);
        messageList.setFont(infoFont);
        messageList.setSelectionBackground(Color.cyan);
        messageList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setVisibleRowCount(0);
        mainPanel.add(messageList);
        return messageList;
    }

//    public JPanel buildInbox(List<String> messages){
//        preparePanel(mainPanel);
//        new JScrollPane()
//        title.setText("Inbox");
//        backButton.setVisible(true);
//        JPanel infoPanel = new JPanel();
//        for (String msg : messages){
//            infoPanel.add(createMessage(msg));
//        }
//        mainPanel.add(infoPanel);
//        return mainPanel;
//    }

//    public void buildContacts(JPanel canvas, List<String> contactList){
//        title.setText("Contacts");
//        contacts.setVisible(false);
//        inbox.setVisible(false);
//        backButton.setVisible(true);
//    }


//    public void buildInbox(JPanel canvas, List<String> messages){
//        title.setText("Inbox");
//        contacts.setVisible(false);
//        inbox.setVisible(false);
//        backButton.setVisible(true);
//    }

//    public JPanel buildMain(JPanel canvas){
//        title.setText("Messages");
//        titlePanel.setVisible(true);
//        backButton.setVisible(false);
//        canvas.add(titlePanel, BorderLayout.NORTH);
//
//        inbox = new JButton("Inbox");
//        inbox.setSize(50, 20);
//        contacts = new JButton("Contacts");
//        contacts.setSize(50,20 );
//        infoPanel.add(inbox);
//        infoPanel.add(contacts);
//        canvas.add(infoPanel, BorderLayout.CENTER);
//        return canvas;
//    }

//    public void initialiseCanvas(JPanel[] mainPanels){
//        titlePanel = new JPanel(new BorderLayout(150,0));
//        titlePanel.setBounds(150,0,300,20);

//        title = new JLabel("");
//        title.setSize(new Dimension(25, 20));
//        title.setFont(Font.getFont("JetBrains Mono"));
//        title.setVerticalAlignment(title.TOP);
//        title.setVerticalAlignment(title.CENTER);


//        backButton = new JButton("Back");
//        backButton.setSize(new Dimension(25, 20));
//        backButton.setVerticalAlignment(backButton.TOP);
//        backButton.setHorizontalAlignment(backButton.LEFT);
//        titlePanel.add(backButton, BorderLayout.WEST);
//        titlePanel.add(title, BorderLayout.CENTER);

//
//        infoPanel = new JPanel(new GridLayout(3,4,50,50));
//        infoPanel.setSize(new Dimension(400, 100));
//    }

}
