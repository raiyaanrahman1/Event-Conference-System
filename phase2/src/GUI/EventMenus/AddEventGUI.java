package GUI.EventMenus;

import Controller.EventManagementSystem;
import Controller.LoginSystem;
import GUI.Main.LoginPanelBuilder;
import GUI.Main.PanelStack;
import UseCase.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class AddEventGUI implements ActionListener {
    private PanelStack panelStack;
    private EventManagementSystem eventSystem;
    private JPanel addEventPanel = new JPanel();
    private JLabel titleLabel = new JLabel("Add New Event");
    private JLabel eventNameJLabel = new JLabel("Event Name:");
    private JLabel speakersJLabel = new JLabel("Speaker(s):");
    private JLabel dateJLabel = new JLabel("Date:");
    private JLabel timeJLabel = new JLabel("Time:");
    private JLabel roomNameJLabel = new JLabel("Room Name:");
    private JLabel capacityJLabel = new JLabel("Capacity");
    private JLabel typeJLabel = new JLabel("Type:");
    private ArrayList<String> speakers = new ArrayList<String>();
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
    private LoginPanelBuilder panelBuilder = new LoginPanelBuilder(addEventPanel);
    private DefaultListModel s = new DefaultListModel();


    public AddEventGUI(EventManagementSystem eventSystem, PanelStack panelStack) {

        this.eventSystem = eventSystem;
        this.panelStack = panelStack;
        backButtonListen();
        addEventButton.addActionListener(this);

    }

    public JPanel addEventPage(){
        // PANEL:
        addEventPanel.setSize(500, 500);
        addEventPanel.setLayout(null);
        // TITLE:
        titleLabel.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 20));
        titleLabel.setBounds(150, 20, 200, 30);
        addEventPanel.add(titleLabel);
        // EVENT NAME:
        eventNameJLabel.setBounds(20, 70, 80, 25);
        addEventPanel.add(eventNameJLabel);
        eventNameTextField.setBounds(120, 70, 165, 25);
        addEventPanel.add(eventNameTextField);

        // SPEAKERS:
        speakersJLabel.setBounds(20, 120, 80, 25);
        addEventPanel.add(speakersJLabel);

        for(int i = 1; i < 20; i++){
            speakers.add("speaker" + i);
        }
        buildListModel();

        speakersJList = new JList(s);
        speakersJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        speakersJScrollPane = new JScrollPane(speakersJList);
        speakersJScrollPane.setBounds(120, 120, 150, 50);
        addEventPanel.add(speakersJScrollPane);

        JTextArea instructions = new JTextArea("Hold ctrl to select multiple ordeselect. Can have no " +
                "        speakers.");
        instructions.setBounds(300, 120, 150, 50);
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setBackground(addEventPanel.getBackground());

        addEventPanel.add(instructions);

        // ROOM NAME
        roomNameJLabel.setBounds(20, 180, 80, 25);
        addEventPanel.add(roomNameJLabel);
        roomNameTextField.setBounds(120, 180, 165, 25);
        addEventPanel.add(roomNameTextField);

        // CAPACITY
        capacityTextField.setBounds(300, 180, 80, 25);
        addEventPanel.add(capacityTextField);

        capacityJLabel.setBounds(310, 200, 80, 25);
        addEventPanel.add(capacityJLabel);

        // DATE
        dateJLabel.setBounds(20, 230, 80, 25);
        addEventPanel.add(dateJLabel);

        // DAY

        dateTextField.setBounds(120, 230, 50, 25);
        addEventPanel.add(dateTextField);

        JLabel dayJLabel = new JLabel("Day");
        dayJLabel.setBounds(130, 250, 80, 25);
        addEventPanel.add(dayJLabel);

        // MONTH
        monthComboBox.setBounds(180, 230, 100, 25);
        addEventPanel.add(monthComboBox);

        JLabel monthJLabel = new JLabel("Month");
        monthJLabel.setBounds(200, 250, 80, 25);
        addEventPanel.add(monthJLabel);

        // YEAR
        yearTextField.setBounds(300, 230, 50, 25);
        addEventPanel.add(yearTextField);

        JLabel yearJLabel = new JLabel("Year");
        yearJLabel.setBounds(310, 250, 80, 25);
        addEventPanel.add(yearJLabel);

        // TIME
        JTextField startTimeTextField = new JTextField();
        startTimeTextField.setBounds(120, 290, 80, 25);
        JLabel startTimeJLabel = new JLabel("Start Time");
        startTimeJLabel.setBounds(130, 310, 80, 25);


        JTextField endTimeTextField = new JTextField();
        endTimeTextField.setBounds(220, 290, 80, 25);
        JLabel endTimeJLabel = new JLabel("End Time");
        endTimeJLabel.setBounds(230, 310, 80, 25);

        addEventPanel.add(startTimeJLabel);
        addEventPanel.add(startTimeTextField);
        addEventPanel.add(endTimeJLabel);
        addEventPanel.add(endTimeTextField);

        // EVENT TYPE
        typeJLabel.setBounds(20, 350, 80, 25);
        addEventPanel.add(typeJLabel);
        //panelBuilder.buildComponent(typeComboBox, 120, 314, 100, 25);
        typeComboBox.setBounds(120, 350, 100, 25);
        addEventPanel.add(typeComboBox);
        // ADD EVENT BUTTON:
        addEventButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        addEventButton.setBounds(190, 400, 125, 25);
        addEventPanel.add(addEventButton);
        // BACK BUTTON:
        panelBuilder.buildButton(backButton,10, 430, 80, 25);
        return addEventPanel;
    }

    private void backButtonListen(){
        backButton.addActionListener(e -> {
            panelStack.pop();
            JPanel panel = (JPanel) panelStack.pop();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String eventName = eventNameTextField.getText();
        System.out.println("button pressed!");
        //eventSystem.addEvent();

//        if (!loginSystem.isUser(uname)) {
//            if (Objects.equals(typeComboBox.getSelectedItem(), "VIP Attendee")) {
//                loginSystem.signUpUser(uname, pword, "V");
//                JOptionPane.showMessageDialog(signUpPanel, "You have successfully created a VIP Attendee.");
//                usernameTextField.setText("");
//                passwordTextField.setText("");
//            }
//            else {
//                loginSystem.signUpUser(uname, pword, "S");
//                JOptionPane.showMessageDialog(signUpPanel, "You have successfully created a Speaker.");
//                usernameTextField.setText("");
//                passwordTextField.setText("");
//            }
//        }
//        else {
//            JOptionPane.showMessageDialog(signUpPanel, "Username already exists. Please select a different username.");
//        }
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500, 500);
//        frame.setResizable(false);
//        frame.setContentPane(new AddEventGUI().addEventPage());
//    }

}
