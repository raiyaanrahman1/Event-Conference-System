package GUI.EventMenus;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import java.awt.*;


public class EventPanelBuilder {
    private Font infoFont;

    public EventPanelBuilder(){
        infoFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    }

    public void buildBorderLayoutPanel(JPanel panel, int top, int left, int bottom, int right){
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    public void buildComponentBorderLayout(Component component, JPanel panel, String place, int size){
        component.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, size));
        panel.add(component, place);
    }

    public void buildComponent(Component component, JPanel panel, int size){
        component.setEnabled(false);
        component.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, size));
        panel.add(component);
    }

    public void buildJListEvents(JList jList){
        jList.setSelectionBackground(new Color(51, 153, 255));
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(0);
    }

    public void buildJScrollPane(JScrollPane jScrollPane, JPanel panel, JList jList){
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setViewportView(jList);
        panel.add(jScrollPane);
    }

    public void buildAttendeeEventsJListPanel(JPanel eventsJListPanel, JList eventsJList, JScrollPane eventsJScrollPane){
        eventsJListPanel.setLayout(new GridLayout(1, 1));
        buildJListEvents(eventsJList);
        buildJScrollPane(eventsJScrollPane, eventsJListPanel, eventsJList);
        eventsJScrollPane.setViewportView(eventsJList);
        eventsJListPanel.add(eventsJScrollPane);
    }

    public void buildAttendeeEventsButtonPanel(JPanel eventsButtonPanel, JButton button1, JButton button2){
        eventsButtonPanel.setLayout(new BorderLayout());
        button1.setFont(infoFont);
        button2.setFont(infoFont);
        eventsButtonPanel.add(button1, BorderLayout.WEST);
        eventsButtonPanel.add(button2, BorderLayout.EAST);
    }

    public void buildAttendeeMainPanel(JPanel mainPanel, JPanel northPanel, JPanel southPanel, JButton button){
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.CENTER);
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        button.setFont(infoFont);
        southPanel.add(button);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
    }

    public void buildAttendeeNorthPanel(JPanel northPanel, JPanel eventsPanel, JPanel yourEventsPanel){
        //Panel:
        northPanel.setLayout(new GridLayout(1, 2));
        // Events Panel:
        buildBorderLayoutPanel(eventsPanel, 10, 20, 20, 35);
        buildBorderLayoutPanel(yourEventsPanel, 10, 20, 20, 20);
        northPanel.add(eventsPanel);
        northPanel.add(yourEventsPanel);
    }

    public void buildAttendeeEventsPanel(JPanel panel, JLabel titleJLabel){
        titleJLabel.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 24));
        panel.add(titleJLabel, BorderLayout.NORTH);
    }

    /**
     * Builds a component for a given panel with a null layout
     *
     * @param panel the panel that will have the given component
     * @param component the component that will be built and added to the panel
     * @param size the font size of the text of the component
     * @param x the x location of the component
     * @param y the y location of the component
     * @param width the width of the component
     * @param height the height of the component
     */
    public void buildComponentNullLayout(JPanel panel, Component component, int size, int x, int y, int width, int height){
        component.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, size));
        component.setBounds(x, y, width, height);
        panel.add(component);
    }

//    public void buildSortPanel(JPanel panel, JLabel title, JLabel question){
//        // PANEL:
//        panel.setLayout(null);
//        panel.setSize(500, 500);
//        // SORT LABEL:
//        title.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 24));
//        title.setBounds(25, 25, 200, 25);
//        panel.add(title);
//        // SELECT LABEL:
//        question.setBounds(25, 70, 500, 100);
//        question.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(question);
//    }
//
//    public void buildSortLabels(JPanel panel, JRadioButton title1, JRadioButton title2) {
//        // SPEAKER SELECTION BUTTON:
//        title1.setBounds(25, 230, 150, 25);
//        title1.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(title1);
//        // DATE SELECTION BUTTON:
//        title2.setBounds(25, 200, 175, 25);
//        title2.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(title2);
//    }
//
//    public void buildSortButtons(JPanel panel, JButton button1, JButton button2) {
//        // SORT BUTTON:
//        button1.setBounds(210, 350, 80, 25);
//        button1.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(button1);
//        // BACK BUTTON:
//        button2.setBounds(25, 420, 80, 25);
//        button2.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(button2);
//    }
//
//    public void buildDateComboBoxes(JPanel panel, JComboBox comboBox1, JComboBox comboBox2, JComboBox comboBox3){
//        // MONTH COMBOBOX:
//        comboBox1.setBounds(200, 200, 80, 25);
//        comboBox1.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(comboBox1);
//        // DAY COMBOBOX:
//        comboBox2.setBounds(290, 200, 80, 25);
//        comboBox2.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(comboBox2);
//        // YEAR COMBOBOX:
//        comboBox3.setBounds(380, 200, 80, 25);
//        comboBox3.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(comboBox3);
//    }
//
//    public void buildSpeakerComboBox(JPanel panel, JComboBox comboBox){
//        // SPEAKER COMBOBOX:
//        comboBox.setBounds(200, 230, 260, 25);
//        comboBox.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
//        panel.add(comboBox);
//    }




}
