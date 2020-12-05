package GUI.MessageMenus;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class MessagePanelBuilder {
    private JPanel mainPanel;
    private JLabel title;
    //private JButton backButton;
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
        mainPanel.add(title);
        mainPanel.setVisible(true);
    }

    public Component[] prepareEditablePane(String title){
        this.title.setText(title);
        JTextArea textArea = new JTextArea();
        textArea.setBounds(50, 80, 225, 340);
        textArea.setEditable(true);
        textArea.setVisible(true);
        textArea.setFont(infoFont);
        JScrollPane pane = new JScrollPane(textArea,  ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setBounds(50, 80, 225, 340);
        pane.setVisible(true);
        return new Component[]{textArea, pane};
    }
    public JButton makeBackButton(){
        JButton backButton = new JButton("back");
        backButton.setFont(infoFont);
        backButton.setBounds(350, 22, 75, 30);
        return backButton;
    }

    public JButton buildButton(String name, int xCoordinate, int yCoordinate){
        JButton button = new JButton(name);
        button.setFont(infoFont);
        button.setBounds(xCoordinate, yCoordinate, 120, 30);
        return button;

    }

    public JTextField buildTextField(int xCoordinate, int yCoordinate){
        JTextField field = new JTextField();
        field.setBounds(xCoordinate, yCoordinate, 120, 30);
        field.setFont(infoFont);
        return field;
    }
    // do we need this
    private void makeTitle(){
        title = new JLabel("");
        title.setFont(titleFont);
        title.setBounds(50, 5, 200, 60);
    }

    public JScrollPane buildMainPane(JScrollPane jScroll, String name){
        title.setText(name);
        jScroll.setBounds(50, 80, 225, 340);
        jScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScroll.setVisible(true);
        mainPanel.add(jScroll);
        mainPanel.add(title);
        return jScroll;
    }

    public void setTitle(String name){
        title.setText(name);
    }
    public JScrollPane buildMessagePreview(JScrollPane messagePreview){
        messagePreview.setBounds(300, 80, 125, 160);
        messagePreview.setFont(infoFont);
        mainPanel.add(messagePreview);
        return messagePreview;
    }

    public JTextArea prepareSelectedMessage(String message){
        JTextArea content = new JTextArea(message);
        content.setFont(infoFont);
        content.setVisible(true);
        content.setEditable(false);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        return content;
    }
    public List<JButton> buildOptions(String[] options, int startY){
        // make this shorter using iteration
        List<JButton> buttons = new ArrayList<>();
        int y = startY;
        for (String option: options){
            JButton button = new JButton(option);
            button.setFont(infoFont);
            button.setBounds(310, y, 120, 30);
            y += 40;
            buttons.add(button);
            mainPanel.add(button);
        }

        return buttons;
//
//        // view the current selected message
//        JButton viewMsg = new JButton("view");
//        viewMsg.setFont(infoFont);
//        viewMsg.setBounds(310, 260, 100, 30);
//
//
//        // reply to the current selected message
//        JButton reply = new JButton("reply");
//        reply.setFont(infoFont);
//        reply.setBounds(310, 300, 100, 30);
//
//        // archive selected message
//        JButton archive = new JButton("archive");
//        archive.setFont(infoFont);
//        archive.setBounds(310, 340, 100, 30);
//
//        // view all received messages from sender
//        JButton viewAll = new JButton("view all");
//        viewAll.setFont(infoFont);
//        viewAll.setBounds(310, 380, 100, 30);
//
//        return new JButton[]{viewMsg, reply, archive, viewAll};
    }


    public JList buildJList(DefaultListModel<String> list){

        JList jList = new JList(list);
        jList.setBounds(50,80, 220,300);
        jList.setFixedCellHeight(40);
        jList.setFixedCellWidth(100);
        jList.setFont(infoFont);
        jList.setSelectionBackground(Color.cyan);
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(0);
        mainPanel.add(jList);
        return jList;
    }

    public JScrollPane buildMessageThread(List<String> messages){
        title.setText("received");
        String fullThread = "";
        for (String msg : messages){
            fullThread += msg + "\n";
        }
        JTextArea text = new JTextArea(fullThread);
        text.setFont(infoFont);
        text.setEditable(false);
        JScrollPane pane = new JScrollPane(text, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setBounds(50, 80, 225, 340);
        return pane;

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
