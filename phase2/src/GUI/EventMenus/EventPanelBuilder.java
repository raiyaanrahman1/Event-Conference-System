package GUI.EventMenus;
import javax.swing.*;
import java.awt.*;


public class EventPanelBuilder {

    public EventPanelBuilder(){}

    public void buildBorderLayoutPanel(JPanel panel, int top, int left, int bottom, int right){
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    public void buildJListEvents(JList jList){
        jList.setSelectionBackground(new Color(51, 153, 255));
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(0);
    }

    public void buildJScrollPane(JScrollPane jScrollPane){
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVisible(true);
    }

    public void buildAttendeeEventsJListPanel(JPanel eventsJListPanel, JList eventsJList, JScrollPane eventsJScrollPane){
        eventsJListPanel.setLayout(new GridLayout(1, 1));
        buildJListEvents(eventsJList);
        buildJScrollPane(eventsJScrollPane);
        eventsJScrollPane.setViewportView(eventsJList);
        eventsJListPanel.add(eventsJScrollPane);
    }

    public void buildAttendeeEventsButtonPanel(JPanel eventsButtonPanel, JButton button1, JButton button2){
        eventsButtonPanel.setLayout(new BorderLayout());
        eventsButtonPanel.add(button1, BorderLayout.WEST);
        eventsButtonPanel.add(button2, BorderLayout.EAST);
    }

    public void buildAttendeeMainPanel(JPanel mainPanel, JPanel northPanel, JPanel southPanel, JButton button){
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.CENTER);
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
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
        titleJLabel.setFont(new Font("", Font.BOLD, 32));
        panel.add(titleJLabel, BorderLayout.NORTH);
    }


}
