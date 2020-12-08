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

    private JLabel sortJLabel = new JLabel("sort");
    private JLabel selectJLabel = new JLabel("<html>Please select one of the sorting methods below, <br/>then click the sort button.");
    private JButton backButton = new JButton("back");
    private JButton sortButton = new JButton("sort");

    private JRadioButton dateSelection = new JRadioButton("date (mm-dd-yy):");
    private JRadioButton speakerSelection = new JRadioButton("speaker:");

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

    public JPanel sortPage(){
        // PANEL:
        mainPanel.setLayout(null);
        mainPanel.setSize(500, 500);
        // SORT LABEL:
        panelBuilder.buildComponentNullLayout(mainPanel, sortJLabel, 24, 25, 25, 200, 25);
        // SELECT LABEL:
        panelBuilder.buildComponentNullLayout(mainPanel, selectJLabel, 14, 25, 70, 500, 100);
        // DATE SELECTION BUTTON:
        panelBuilder.buildComponentNullLayout(mainPanel, dateSelection, 14, 25, 200, 175, 25);
        // MONTH COMBOBOX:
        panelBuilder.buildComponentNullLayout(mainPanel, monthsCombobox, 14, 200, 200, 80, 25);
        // DAY COMBOBOX:
        panelBuilder.buildComponentNullLayout(mainPanel, daysCombobox, 14, 290, 200, 80, 25);
        // YEAR COMBOBOX:
        panelBuilder.buildComponentNullLayout(mainPanel, yearCombobox, 14, 380, 200, 80, 25);
        // SPEAKER SELECTION BUTTON:
        panelBuilder.buildComponentNullLayout(mainPanel, speakerSelection, 14, 25, 230, 150, 25);
        // SPEAKER COMBOBOX:
        panelBuilder.buildComponentNullLayout(mainPanel, speakerCombobox, 14, 200, 230, 260, 25);
        // SORT BUTTON:
        panelBuilder.buildComponentNullLayout(mainPanel, sortButton, 14, 210, 350, 80, 25);
        // BACK BUTTON:
        panelBuilder.buildComponentNullLayout(mainPanel, backButton, 14, 25, 420, 80, 25);
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
