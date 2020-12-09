package GUI.Main;

import Controller.LoginSystem;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.util.Stack;

public class MainGUI {

    private JFrame mainFrame = new JFrame();
    private PanelStack panelStack = new PanelStack(mainFrame);
    private LoginSystem loginSystem = new LoginSystem();
    private MainMenuGUI mainMenuGUI = new MainMenuGUI(loginSystem, loginSystem.getEventSys(), loginSystem.getMsgSys(), panelStack);
    private LoginGUI loginGUI = new LoginGUI(mainMenuGUI, loginSystem, panelStack);
    private SignUpGUI signUpGUI = new SignUpGUI(mainMenuGUI, loginSystem, panelStack);
    private JPanel currentJPanel = new JPanel();
    private WelcomeGUI welcomeGUI = new WelcomeGUI(loginGUI, signUpGUI, panelStack);

    public MainGUI(){
        currentJPanel.setSize(500, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 500);
        mainFrame.setResizable(false);
        mainFrame.setTitle("the imposter summit");
        run();
        exitListener();
    }

    public void exitListener() {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                loginSystem.signOut();
            }
        });
    }

    public void run(){
        panelStack.loadPanel(welcomeGUI.WelcomePage());
    }
}
