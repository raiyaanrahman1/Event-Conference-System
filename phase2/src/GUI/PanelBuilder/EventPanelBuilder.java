package GUI.PanelBuilder;
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

    public void buildComponentNullLayout(JPanel panel, Component component, int size, int x, int y, int width, int height){
        component.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, size));
        component.setBounds(x, y, width, height);
        panel.add(component);
    }


}
