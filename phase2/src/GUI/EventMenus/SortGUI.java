package GUI.EventMenus;

import Controller.EventManagementSystem;
import Controller.LoginSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SortGUI {
    private EventManagementSystem eventSystem;
    private EventPanelBuilder panelBuilder = new EventPanelBuilder();

    private List<String> listEvents;
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    private JLabel sortJLabel = new JLabel("SORT");
    private JLabel selectJLabel = new JLabel("Please select one of the sorting methods below, then click the SORT button.");

    private JButton backButton = new JButton("Back");
    private JButton sortButton = new JButton("SORT");

    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton dateSelection = new JRadioButton("Date:");
    private JRadioButton speakerSelection = new JRadioButton("Speaker:");

    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private JComboBox monthsCombobox = new JComboBox(months);

    private Integer[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
            25, 26, 27, 28, 29, 30, 31};
    private JComboBox daysCombobox = new JComboBox(days);

    private Integer[] year = {2020, 2021, 2022, 2023, 2024, 2025};
    private JComboBox yearCombobox = new JComboBox(year);
    private String[] speakers = {"Tariq", "Hala", "Aya"};
    private JComboBox speakerCombobox = new JComboBox(speakers);

    public SortGUI() {
        sortPage();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setContentPane(new SortGUI().sortPage());
//        LoginSystem loginSystem = new LoginSystem();
//        frame.setContentPane(new EventSpeakerGUI(loginSystem.getEventSys()).startEventPage());
    }

    public JPanel sortPage(){
        // PANEL:
        mainPanel.setLayout(null);
        mainPanel.setSize(500, 500);
        // SORT LABEL:
        sortJLabel.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, 24));
        sortJLabel.setBounds(25, 25, 200, 25);
        mainPanel.add(sortJLabel);
        // SELECT LABEL:
        selectJLabel.setBounds(25, 70, 500, 100);
        mainPanel.add(selectJLabel);
        // RADIO BUTTON GROUP:
        buttonGroup.add(dateSelection);
        buttonGroup.add(speakerSelection);
        // DATE SELECTION BUTTON:
        dateSelection.setBounds(25, 200, 80, 25);
        mainPanel.add(dateSelection);
        // MONTH COMBOBOX:
        monthsCombobox.setBounds(150, 200, 80, 25);
        mainPanel.add(monthsCombobox);
        // DAY COMBOBOX:
        daysCombobox.setBounds(250, 200, 80, 25);
        mainPanel.add(daysCombobox);
        // YEAR COMBOBOX:
        yearCombobox.setBounds(350, 200, 80, 25);
        mainPanel.add(yearCombobox);
        // SPEAKER SELECTION BUTTON:
        speakerSelection.setBounds(25, 230, 80, 25);
        mainPanel.add(speakerSelection);
        // SPEAKER COMBOBOX:
        speakerCombobox.setBounds(150, 230, 80, 25);
        mainPanel.add(speakerCombobox);
        // SORT BUTTON:
        sortButton.setBounds(210, 350, 80, 25);
        mainPanel.add(sortButton);
        // BACK BUTTON:
        backButton.setBounds(25, 420, 80, 25);
        mainPanel.add(backButton);

        return mainPanel;
    }
}
