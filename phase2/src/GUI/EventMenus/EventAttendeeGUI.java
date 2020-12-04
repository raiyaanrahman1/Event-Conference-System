package GUI.EventMenus;

import Controller.EventManagementSystem;
import Controller.LoginSystem;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventAttendeeGUI {

    private JList eventsJList = new JList();
    private JList yourEventsJList = new JList();
    private JLabel events = new JLabel("Events");
    private JLabel yourEvents = new JLabel("Your Events");
    private JButton signUp = new JButton("Sign Up");
    private JButton sort1 = new JButton("Sort");
    private JButton sort2 = new JButton("Sort");
    private JButton cancel = new JButton("Cancel");
    private JButton exitButton = new JButton("Exit");
    private JPanel eventsButtonPanel = new JPanel();
    private JPanel yourEventsButtonPanel = new JPanel();
    private JPanel eventsPanel = new JPanel();
    private JPanel exitButtonPanel = new JPanel();
    private JPanel eventsJlistPanel = new JPanel();
    private JPanel yourEventsJlistPanel = new JPanel();
    private JPanel yourEventsPanel = new JPanel();
    private JPanel framePanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private EventPanelBuilder panelBuilder = new EventPanelBuilder(framePanel);
    private DefaultListModel<String> eventsListModel = new DefaultListModel<>();
    private DefaultListModel<String> yourEventsListModel = new DefaultListModel<>();
    private EventManagementSystem eventSystem;
    private JScrollPane eventslistScroller = new JScrollPane();
    private JScrollPane youreventslistScroller = new JScrollPane();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setVisible(true);
        LoginSystem loginSystem = new LoginSystem();
        frame.setContentPane(new EventAttendeeGUI(loginSystem.getEventSys()).startEventPage());
    }

    public EventAttendeeGUI(EventManagementSystem eventSystem) {
        this.eventSystem = eventSystem;
    }

    public JPanel startEventPage() {
        //Panel:
        framePanel.setSize(500, 500);
        framePanel.setLayout(new GridLayout(1, 2));
        // Events Panel:
        eventsPanel.setLayout(new BorderLayout());
        eventsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 35));
        framePanel.add(eventsPanel);
        // Events Title:
        events.setFont(new Font("", Font.BOLD, 32));
        eventsPanel.add(events, BorderLayout.NORTH);
        // Events JListPanel:
        eventsJlistPanel.setLayout(new GridLayout(1, 1));
        eventsPanel.add(eventsJlistPanel, BorderLayout.CENTER);
        // Events JList:
        eventsJList = new JList(eventsListModel);
        eventsJList = panelBuilder.buildJListEvents(eventsJList);
        // JScroll Panel
        eventslistScroller = panelBuilder.buildJScrollPane(eventslistScroller);
        eventslistScroller.setViewportView(eventsJList);
        eventsJlistPanel.add(eventslistScroller);
        // Button Panel
        eventsButtonPanel.setLayout(new BorderLayout());
        eventsPanel.add(eventsButtonPanel, BorderLayout.SOUTH);
        // SignUp Button
        eventsButtonPanel.add(signUp, BorderLayout.WEST);
        // Sort Button
        eventsButtonPanel.add(sort1, BorderLayout.EAST);
        // Your Events Panel:
        yourEventsPanel.setLayout(new BorderLayout());
        yourEventsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        framePanel.add(yourEventsPanel);
        // Your Events Title:
        yourEvents.setFont(new Font("", Font.BOLD, 32));
        yourEventsPanel.add(yourEvents, BorderLayout.NORTH);
        // Your Events JListPanel:
        yourEventsJlistPanel.setLayout(new GridLayout(1, 1));
        yourEventsPanel.add(yourEventsJlistPanel, BorderLayout.CENTER);
        // Your Events JList:
        yourEventsJList = new JList(yourEventsListModel);
        yourEventsJList = panelBuilder.buildJListEvents(yourEventsJList);
        // Your JScroll Panel
        youreventslistScroller = panelBuilder.buildJScrollPane(youreventslistScroller);
        youreventslistScroller.setViewportView(yourEventsJList);
        yourEventsJlistPanel.add(youreventslistScroller);
        // Your Button Panel
        yourEventsButtonPanel.setLayout(new BorderLayout());
        yourEventsPanel.add(yourEventsButtonPanel, BorderLayout.SOUTH);
        // Your SignUp Button
        yourEventsButtonPanel.add(cancel, BorderLayout.WEST);
        // Your Sort Button
        yourEventsButtonPanel.add(sort2, BorderLayout.EAST);
        // Exit Button Panel
        exitButtonPanel.setLayout(new BorderLayout());
        exitButtonPanel.add(framePanel, BorderLayout.CENTER);
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(exitButton);
        exitButtonPanel.add(southPanel, BorderLayout.SOUTH);
        exitButtonListen();
        return exitButtonPanel;
    }

    private void exitButtonListen(){
        exitButton.addActionListener(e -> exitButtonPanel.setVisible(false));
    }


}
