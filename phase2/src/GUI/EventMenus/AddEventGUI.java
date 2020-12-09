package GUI.EventMenus;

import Controller.EventManagementSystem;
import Exceptions.InvalidDateException;
import GUI.Main.PanelStack;
import GUI.PanelBuilder.EventPanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class AddEventGUI implements ActionListener {
    private PanelStack panelStack;
    private EventManagementSystem eventSystem;
    private JPanel addEventPanel = new JPanel();
    private JLabel titleLabel = new JLabel("Add New Event");
    private JLabel eventNameJLabel = new JLabel("Event Name:");
    private JLabel speakersJLabel = new JLabel("Speaker(s):");
    private JLabel dateJLabel = new JLabel("Date:");
    private JLabel roomNameJLabel = new JLabel("Room Name:");
    private JLabel capacityJLabel = new JLabel("Capacity");
    private JLabel typeJLabel = new JLabel("Type:");
    private JLabel dayJLabel = new JLabel("Day");
    private JLabel monthJLabel = new JLabel("Month");
    private JLabel yearJLabel = new JLabel("Year");
    private JLabel startTimeJLabel = new JLabel("Start Time");
    private JLabel endTimeJLabel = new JLabel("End Time");
    private JLabel time = new JLabel("<html> Time:<br/>(HH:MM)");
    private JTextField startTimeTextField;
    private JTextField endTimeTextField;
    private java.util.List<String> speakers;
    private JList speakersJList;
    private JScrollPane speakersJScrollPane;
    private JTextField eventNameTextField = new JTextField(20);
    private JTextField roomNameTextField = new JTextField(20);
    private JTextField capacityTextField = new JTextField(20);
    private JTextField dateTextField = new JTextField(20);
    private JTextField yearTextField = new JTextField(20);
    private JButton addEventButton = new JButton("Add Event");
    private String[] eventTypes = {"Regular", "VIP Only"};
    private String[] months = {"January","February","March","April","May","June","July","August","September","October",
            "November","December"};
    private JComboBox typeComboBox = new JComboBox(eventTypes);
    private JComboBox monthComboBox = new JComboBox(months);
    private JButton backButton = new JButton("Back");
    private EventPanelBuilder panelBuilder = new EventPanelBuilder();
    private DefaultListModel s = new DefaultListModel();


    /**
     * Creates an AddEventGUI and initializes its EventManagementSystem and the panelStack
     *
     * @param eventSystem the EventManagementSystem that the AddEventGUI communicates with
     * @param panelStack The current instance of PanelStack instantiated in MainGUI
     */
    public AddEventGUI(EventManagementSystem eventSystem, PanelStack panelStack) {
        this.eventSystem = eventSystem;
        this.panelStack = panelStack;
        backButtonListen();
        addEventButton.addActionListener(this);

    }

    /**
     * Builds and loads the Add Event Page
     *
     * @return The add event panel
     */
    public JPanel addEventPage(){
        // PANEL:
        addEventPanel.setSize(500, 500);
        addEventPanel.setLayout(null);
        // TITLE:
        panelBuilder.buildComponentNullLayout(addEventPanel, titleLabel, 24, 150, 20, 200, 30);
        // EVENT NAME:
        panelBuilder.buildComponentNullLayout(addEventPanel, eventNameJLabel, 12, 20, 70, 80, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, eventNameTextField, 14, 120, 70, 165, 25);
        // SPEAKERS:
        panelBuilder.buildComponentNullLayout(addEventPanel, speakersJLabel, 12, 20, 120, 80, 25);
        speakers = eventSystem.getSpeakers();
        buildListModel();

        speakersJList = new JList(s);
        speakersJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        speakersJList.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 14));
        speakersJScrollPane = new JScrollPane(speakersJList);
        panelBuilder.buildComponentNullLayout(addEventPanel, speakersJScrollPane, 10, 120, 120, 150, 50);

        JTextArea instructions = new JTextArea("Hold ctrl to select  multiple speakers.   Can have no speakers.");
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setBackground(addEventPanel.getBackground());
        panelBuilder.buildComponentNullLayout(addEventPanel, instructions, 12, 300, 120, 150, 50);
        // ROOM NAME
        panelBuilder.buildComponentNullLayout(addEventPanel, roomNameJLabel, 12, 20, 180, 80, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, roomNameTextField, 12, 120, 180, 165, 25);
        // CAPACITY
        panelBuilder.buildComponentNullLayout(addEventPanel, capacityTextField, 12, 300, 180, 80, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, capacityJLabel, 12, 310, 200, 80, 25);
        // DATE
        panelBuilder.buildComponentNullLayout(addEventPanel, dateJLabel, 12, 20, 230, 80, 25);
        // DAY
        panelBuilder.buildComponentNullLayout(addEventPanel, dateTextField, 12, 120, 230, 50, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, dayJLabel, 12, 130, 250, 80, 25);
        // MONTH
        panelBuilder.buildComponentNullLayout(addEventPanel, monthComboBox, 12, 180, 230, 100, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, monthJLabel, 12, 200, 250, 80, 25);
        // YEAR
        panelBuilder.buildComponentNullLayout(addEventPanel, yearTextField, 12, 300, 230, 50, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, yearJLabel, 12, 310, 250, 80, 25);
        // TIME
        startTimeTextField = new JTextField();
        panelBuilder.buildComponentNullLayout(addEventPanel, time, 12, 20, 270, 90, 75);
        panelBuilder.buildComponentNullLayout(addEventPanel, startTimeTextField, 12, 120, 290, 80, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, startTimeJLabel, 12, 125, 310, 80, 25);
        endTimeTextField = new JTextField();
        panelBuilder.buildComponentNullLayout(addEventPanel, endTimeTextField, 12, 260, 290, 80, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, endTimeJLabel, 12, 270, 310, 80, 25);
        // EVENT TYPE
        panelBuilder.buildComponentNullLayout(addEventPanel, typeJLabel, 12, 20, 350, 80, 25);
        panelBuilder.buildComponentNullLayout(addEventPanel, typeComboBox, 12, 120, 350, 100, 25);
        // ADD EVENT BUTTON:
        panelBuilder.buildComponentNullLayout(addEventPanel, addEventButton, 14, 190, 400, 125, 25);
        // BACK BUTTON:
        panelBuilder.buildComponentNullLayout(addEventPanel, backButton, 14, 10, 430, 80, 25);
        return addEventPanel;
    }

    private void backButtonListen(){
        backButton.addActionListener(e -> {
            panelStack.pop();
            panelStack.pop();
            JPanel panel = new EventOrganizerGUI(eventSystem, panelStack).startEventPage();
            panelStack.loadPanel(panel);
        });
    }

    private void buildListModel(){
        if (!speakers.isEmpty()){
            for (String event:speakers) {
                s.addElement(event);
            }
        }
    }

    /**
     * Builds and loads the Add Event Page
     *
     * @return The add event panel
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String eventName = eventNameTextField.getText();
        int[] speakerIndexes = speakersJList.getSelectedIndices();
        ArrayList<String> selectedSpeakers = new ArrayList<>();
        for(int i : speakerIndexes){
            selectedSpeakers.add(speakers.get(i));
        }
        String day = dateTextField.getText();
        String month = (String)monthComboBox.getSelectedItem();
        String year = yearTextField.getText();
        String startTime = startTimeTextField.getText();
        String endTime = endTimeTextField.getText();
        String roomName = roomNameTextField.getText();
        String type = (String)typeComboBox.getSelectedItem();
        String capacity = capacityTextField.getText();

        int cap = -1;
        boolean cont = true;
        int m = -1;
        LocalDate date = null;
        LocalDateTime start = null;
        LocalDateTime end = null;
        try {
            cap = Integer.parseInt(capacity);
            m = Arrays.asList(months).indexOf(month) + 1;
            if(eventSystem.checkStartTime(startTime) == null || eventSystem.checkEndTime(endTime) == null) {
                throw new InvalidDateException();
            }
            date = LocalDate.of(Integer.parseInt(year), m, Integer.parseInt(day));
            start = LocalDateTime.of(date, eventSystem.checkStartTime(startTime));
            end = LocalDateTime.of(date, eventSystem.checkEndTime(endTime));
            if(!start.isAfter(LocalDateTime.now()) || !end.isAfter(start)) throw new InvalidDateException();
        }
        catch (InvalidDateException invalidDateException){
            JOptionPane.showMessageDialog(addEventPanel, "The date and time of the event " +
                    "must be after the current date and" +
                    "time and must be between 9:00 and 17:00");
            cont = false;
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(addEventPanel, "One of your fields is in an incorrect format." +
                    " Please try again.");
            cont = false;
        }

        if (cont){
            if(type.equals("Regular")){
                if (eventSystem.addEvent(eventName, roomName, selectedSpeakers, cap, start, end)){
                    JOptionPane.showMessageDialog(addEventPanel, "Successfully added event");
                }
                else JOptionPane.showMessageDialog(addEventPanel, "The event could not be added");
            }
            else if (type.equals("VIP Only")){
                if (eventSystem.addVIPEvent(eventName, roomName, selectedSpeakers, cap, start, end)){
                    JOptionPane.showMessageDialog(addEventPanel, "Successfully added event");
                }
                else JOptionPane.showMessageDialog(addEventPanel, "The event could not be added");
            }

        }
    }
}
