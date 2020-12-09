package GUI.PanelBuilder;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 *  The PanelBuilder class responsible for building the GUIs in the MessageMenus package.
 */
public class MessagePanelBuilder {
    private JPanel mainPanel;
    private JLabel title;
    private Font titleFont;
    private Font infoFont;

    /**
     * Sets the input panel to the mainPanel in this builder and prepares the relevant fonts.
     * @param panel the main panel in this builder
     */
    public MessagePanelBuilder(JPanel panel){
        this.mainPanel = panel;
        titleFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
        infoFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
        makeTitle();
        preparePanel();
    }

    private void makeTitle(){
        title = new JLabel("");
        title.setFont(titleFont);
        title.setBounds(50, 5, 200, 60);
    }

    private void preparePanel(){
        mainPanel.setLayout(null);
        mainPanel.setSize(500, 500);
        mainPanel.add(title);
        mainPanel.setVisible(true);
    }

    /**
     * Builds a JLabel with the input text and sets the font to infoFont.
     * @param text the input text for the JLabel to be built
     * @return the JLabel that was built
     */
    public JLabel buildJLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(infoFont);
        return label;
    }

    /**
     * Prepares a editable JScrollPane that the user can write in.
     * @param title the title of the panel
     * @return a JScrollPane with an editable JTextArea.
     */
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

    /**
     * Builds a generic back button in the top right corner of the panel.
     * @return the built back button
     */
    public JButton makeBackButton(){
        JButton backButton = new JButton("back");
        backButton.setFont(infoFont);
        backButton.setBounds(350, 22, 75, 30);
        return backButton;
    }

    /**
     * Builds a button with a set width and height using the input parameters.
     * @param name the name of the JButton
     * @param xCoordinate the x coordinate of the button's location
     * @param yCoordinate the y coordinate of the button's location
     * @return the built JButton
     */
    public JButton buildButton(String name, int xCoordinate, int yCoordinate){
        JButton button = new JButton(name);
        button.setFont(infoFont);
        button.setBounds(xCoordinate, yCoordinate, 120, 30);
        return button;
    }

    /**
     * Builds a button with the input parameters.
     * @param name the name of the JButton
     * @param xCoordinate the x coordinate of the button's location
     * @param yCoordinate the y coordinate of the button's location
     * @param width the desired width of the button
     * @param height the desired height of the button
     * @return the built JButton
     */
    public JButton buildButton(String name, int xCoordinate, int yCoordinate, int width, int height){
        JButton button = new JButton(name);
        button.setFont(infoFont);
        button.setBounds(xCoordinate, yCoordinate, width, height);
        return button;

    }

    /**
     * Builds a text field with infoFont and a set height and width.
     * @param xCoordinate the x coordinate of the text field's location
     * @param yCoordinate the y coordinate of the text field's location
     * @return the built JTextField
     */
    public JTextField buildTextField(int xCoordinate, int yCoordinate){
        JTextField field = new JTextField();
        field.setBounds(xCoordinate, yCoordinate, 120, 30);
        field.setFont(infoFont);
        return field;
    }

    /**
     * Builds the input JScrollPane and titles and adds them to the main panel.
     * @param jScroll the input JScrollPane to be built
     * @param title the desired panel title
     * @return the built JScrollPane
     */
    public JScrollPane buildMainPane(JScrollPane jScroll, String title){
        this.title.setVisible(true);
        this.title.setText(title);
        jScroll.setBounds(50, 80, 225, 340);
        jScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScroll.setVisible(true);
        mainPanel.add(jScroll);
        mainPanel.add(this.title);
        return jScroll;
    }

    /**
     * Prepares the input message to be presented in the message preview.
     * @param message the input message to be prepared
     * @return a JTextArea containing the formatted message
     */
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

    /**
     * Builds the message preview that is activated when the user clicks on an item
     * from the relevant JLists.
     * @param messagePreview the input JScrollPane representing the message preview
     * @return the built JScrollPane
     */
    public JScrollPane buildMessagePreview(JScrollPane messagePreview){
        messagePreview.setBounds(300, 80, 130, 160);
        messagePreview.setFont(infoFont);
        mainPanel.add(messagePreview);
        return messagePreview;
    }

    /**
     * Builds a list of buttons with a set x coordinate, width and height, using
     * the input parameters.
     * @param options a String[] containing the button texts
     * @param startY the starting y coordinate of the set of buttons
     * @return a list of JButton containing the built buttons
     */
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

    /**
     * Builds a JList with the input list model and formats it.
     * @param list the input DefaultListModel
     * @return the built JList
     */
    public JList<String> buildJList(DefaultListModel<String> list){
        JList<String> jList = new JList<>(list);
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

    /**
     * Builds a JScrollPane representing a message thread with the input
     * messages and formats.
     * @param messages the input list of messages
     * @return the built JScrollPane
     */
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
