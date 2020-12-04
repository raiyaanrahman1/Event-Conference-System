package GUI.Main;

import Controller.LoginSystem;

import javax.swing.*;

public class MainGUI {

    private JFrame mainFrame = new JFrame();
    private LoginSystem loginSystem = new LoginSystem();
    private LoginGUI loginGUI = new LoginGUI(loginSystem, mainFrame);
    private SignUpGUI signUpGUI = new SignUpGUI(loginSystem, mainFrame);
    private MainMenuGUI mainMenuGUI = new MainMenuGUI(loginSystem.getEventSys(), loginSystem.getMsgSys(), mainFrame);
    private JPanel currentJPanel = new JPanel();
    private WelcomeGUI languageSelectionGUI = new WelcomeGUI(mainFrame, loginGUI, signUpGUI);

    public MainGUI(){
        currentJPanel.setSize(500, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 500);
        mainFrame.setResizable(false);
//        mainFrame.setContentPane(languageSelectionGUI.getStartPanel());
        run();
    }

    public void run(){
        mainFrame.setContentPane(signUpGUI.signUpPage());
        if (!signUpGUI.signUpPage().isVisible()){
            mainFrame.setContentPane(mainMenuGUI.startMainMenuPage());
        }
    }
}
