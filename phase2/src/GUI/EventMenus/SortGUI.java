package GUI.EventMenus;

import Controller.EventManagementSystem;
import GUI.Main.PanelStack;
//import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class SortGUI implements ActionListener {
    private EventManagementSystem eventSystem;
    private EventPanelBuilder panelBuilder = new EventPanelBuilder();

    private List<String> listEvents;
    private DefaultListModel<String> listModel;

    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    private JLabel sortJLabel = new JLabel("SORT");
    private JLabel selectJLabel = new JLabel("Please select one of the sorting methods below, then click the SORT button.");

    private JButton backButton = new JButton("Back");
    private JButton sortButton = new JButton("SORT");

    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton dateSelection = new JRadioButton("Date (MM-DD-YY):");
    private JRadioButton speakerSelection = new JRadioButton("Speaker:");

    private String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private JComboBox monthsCombobox = new JComboBox(months);

    private String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private JComboBox daysCombobox = new JComboBox(days);

    private String[] year = {"2020", "2021", "2022", "2023", "2024", "2025"};
    private JComboBox yearCombobox = new JComboBox(year);
    private String[] speakers;
    private JComboBox speakerCombobox;

    private List<String> filteredList;

    private PanelStack panelStack;

    public SortGUI(EventManagementSystem eventSystem, DefaultListModel<String> listModel, PanelStack panelStack) {
        sortButton.addActionListener(this);
        this.eventSystem = eventSystem;
        this.listModel = listModel;
        this.panelStack = panelStack;
        this.speakers = eventSystem.getSpeakers().toArray(new String[0]);
        this.speakerCombobox = new JComboBox(speakers);
        backButtonListen();
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500,500);
//        frame.setResizable(false);
//        frame.setVisible(true);
//        frame.setContentPane(new SortGUI().sortPage());
//        LoginSystem loginSystem = new LoginSystem();
//        frame.setContentPane(new EventSpeakerGUI(loginSystem.getEventSys()).startEventPage());
//    }

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
        dateSelection.setBounds(25, 200, 125, 25);
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

    private void backButtonListen(){
        backButton.addActionListener(e -> {
            panelStack.pop();
            JPanel panel = (JPanel) panelStack.pop();
            panelStack.loadPanel(panel);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String month = (String) monthsCombobox.getSelectedItem();
        String day = (String) daysCombobox.getSelectedItem();
        String year = (String) yearCombobox.getSelectedItem();
        String date = year + "-" + month + "-" + day;
        LocalDate localDate = LocalDate.parse(date);

        if (dateSelection.isSelected()) {
            List<String> filteredDateList = eventSystem.filterEventDate(localDate);
            filteredList = filteredDateList;
        }
        else if (speakerSelection.isSelected()) {
            List<String> filteredSpeakerList = eventSystem.filterSpeakerDate((String) speakerCombobox.getSelectedItem());
            filteredList = filteredSpeakerList;
        }
        eventFilterer();
    }

    private void eventFilterer() {
        listModel.clear();
        for (String event : filteredList) {
            listModel.addElement(event);
        }
    }
}
