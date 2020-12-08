package GUI.PanelBuilder;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class MessagePanelBuilder {
    private JPanel mainPanel;
    private JLabel title;
    private Font titleFont;
    private Font infoFont;


    public MessagePanelBuilder(JPanel panel){
        this.mainPanel = panel;
        titleFont = new Font(Font.MONOSPACED, Font.TYPE1_FONT, 40);
        infoFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
        makeTitle();
        preparePanel(mainPanel);
    }

    private void preparePanel(JPanel panel){
        mainPanel.setLayout(null);
        mainPanel.setSize(500, 500);
        mainPanel.add(title);
        mainPanel.setVisible(true);
    }

    public JLabel buildJLabel(String message){
        JLabel label = new JLabel(message);
        label.setFont(infoFont);
        return label;
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

    public JButton buildButton(String name, int xCoordinate, int yCoordinate, int width, int height){
        JButton button = new JButton(name);
        button.setFont(infoFont);
        button.setBounds(xCoordinate, yCoordinate, width, height);
        return button;

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

    private void makeTitle(){
        title = new JLabel("");
        title.setFont(titleFont);
        title.setBounds(50, 5, 200, 60);
    }

    public JScrollPane buildMainPane(JScrollPane jScroll, String name){
        title.setVisible(true);
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
        messagePreview.setBounds(300, 80, 130, 160);
        messagePreview.setFont(infoFont);
        mainPanel.add(messagePreview);
        return messagePreview;
    }

    public JTextArea prepareSelectedMessage(String message){
        String[] msgContent = message.split("\\|");
        String formattedMessage = msgContent[0] + "\n" + msgContent[2] + "\nSent at: \n" +
                msgContent[1];
        JTextArea content = new JTextArea(formattedMessage);
        content.setFont(infoFont);
        content.setVisible(true);
        content.setEditable(false);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        return content;
    }

    public List<JButton> buildOptions(String[] options, int startY){
        List<JButton> buttons = new ArrayList<>();
        int y = startY;
        for (String option: options){
            JButton button = new JButton(option);
            button.setFont(infoFont);
            button.setBounds(305, y, 120, 30);
            y += 40;
            buttons.add(button);
            mainPanel.add(button);
        }

        return buttons;
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
        text.setLineWrap(true);
        JScrollPane pane = new JScrollPane(text, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setBounds(50, 80, 225, 340);
        return pane;

    }

}
