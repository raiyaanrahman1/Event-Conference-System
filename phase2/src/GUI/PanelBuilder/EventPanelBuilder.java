package GUI.PanelBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * Builder class for the event menus
 */
public class EventPanelBuilder {
    private final Font infoFont;

    /**
     * constructor of the eventPanelBuilder which sets the general fonts of the menus
     */
    public EventPanelBuilder(){
        infoFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    }


    /**
     * Builds a panel with BorderLayout
     * @param panel panel we want to build
     * @param top border size of the panel
     * @param left border size size of the panel
     * @param bottom border size size of the panel
     * @param right border size size of the panel
     */
    public void buildBorderLayoutPanel(JPanel panel, int top, int left, int bottom, int right){
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }


    /**
     * Sets the font of a component for a panel with BorderLayout and adds the component to this panel.
     * @param component that we want to add to the panel
     * @param panel the panel we want to add the component in
     * @param place the section in the BorderLayout that we are adding the component in
     * @param size the size of the text font of the component
     */
    public void buildComponentBorderLayout(Component component, JPanel panel, String place, int size){
        component.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, size));
        panel.add(component, place);
    }


    /**
     * Sets the font and disables a component for a panel
     * @param component the component we want to add in the panel
     * @param panel the panel
     * @param size the size of the text font of the component
     */
    public void buildComponent(Component component, JPanel panel, int size){
        component.setEnabled(false);
        component.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, size));
        panel.add(component);
    }


    /**
     * Builds a JList
     * @param jList list of events
     */
    public void buildJListEvents(JList jList){
        jList.setSelectionBackground(new Color(51, 153, 255));
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(0);
    }


    /**
     * Builds a JScrollPane
     * @param jScrollPane contains the JList
     * @param panel that we add the JScrollPane in
     * @param jList of the JScrollPane
     */
    public void buildJScrollPane(JScrollPane jScrollPane, JPanel panel, JList jList){
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setViewportView(jList);
        panel.add(jScrollPane);
    }


    /**
     * Builds the a panel that contains a JScrollPane. Used for the Attendee event menu
     * and the Organizer's "Manage Events" menu.
     * @param eventsJListPanel contains the JScrollPane
     * @param eventsJList is in the JScrollPane
     * @param eventsJScrollPane is add in the panel
     */
    public void buildJScrollPanePanel(JPanel eventsJListPanel, JList eventsJList, JScrollPane eventsJScrollPane){
        eventsJListPanel.setLayout(new GridLayout(1, 1));
        buildJListEvents(eventsJList);
        buildJScrollPane(eventsJScrollPane, eventsJListPanel, eventsJList);
        eventsJScrollPane.setViewportView(eventsJList);
        eventsJListPanel.add(eventsJScrollPane);
    }


    /**
     * Builds the buttons in the Attendee event menu and the Organizer's "Attend Events" menu that are used for
     * signing up for an event, cancelling an event and sorting events.
     *
     * @param eventsButtonPanel the panel that the buttons are added to
     * @param button1 the first button
     * @param button2 the second button
     */
    public void buildAttendeeEventsButtonPanel(JPanel eventsButtonPanel, JButton button1, JButton button2){
        eventsButtonPanel.setLayout(new BorderLayout());
        button1.setFont(infoFont);
        button2.setFont(infoFont);
        eventsButtonPanel.add(button1, BorderLayout.WEST);
        eventsButtonPanel.add(button2, BorderLayout.EAST);
    }


    /**
     * Builds the main panel in the Attendee event menu
     * @param mainPanel of the Attendee event menu
     * @param northPanel located in the north section of the mainPanel
     * @param southPanel located in the south section of the mainPanel
     * @param button the panel located in southPanel
     */
    public void buildAttendeeMainPanel(JPanel mainPanel, JPanel northPanel, JPanel southPanel, JButton button){
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.CENTER);
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        button.setFont(infoFont);
        southPanel.add(button);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
    }


    /**
     * Builds the north panel which contains two other panels, one for the general events list and the other for the
     * list of a events that a user is signed up in.
     * @param northPanel is in our mainPanel
     * @param eventsPanel contains the general events list
     * @param yourEventsPanel containings the list of a events that a user is signed up in
     */
    public void buildAttendeeNorthPanel(JPanel northPanel, JPanel eventsPanel, JPanel yourEventsPanel){
        //Panel:
        northPanel.setLayout(new GridLayout(1, 2));
        // Events Panel:
        buildBorderLayoutPanel(eventsPanel, 10, 20, 20, 35);
        buildBorderLayoutPanel(yourEventsPanel, 10, 20, 20, 20);
        northPanel.add(eventsPanel);
        northPanel.add(yourEventsPanel);
    }


    /**
     * Builds the JLabel in the Attendee event menu. Used for both the general events section (Events)
     * and the signed up events section (Your Events)
     * @param panel  contains this JLabel
     * @param titleJLabel represents title of this panel
     */
    public void buildAttendeeEventsJLabel(JPanel panel, JLabel titleJLabel){
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


}
