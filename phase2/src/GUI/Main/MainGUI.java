package GUI.Main;

import Controller.LoginSystem;
import GUI.Main.SignUpGUI;
import GUI.Main.LoginGUI;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame{

    private LoginSystem loginSystem = new LoginSystem();
    private SignUpGUI signUpGUI = new SignUpGUI(loginSystem);
    private LoginGUI loginGUI = new LoginGUI(loginSystem);
    private MainMenuGUI mainMenuGUI = new MainMenuGUI(loginSystem.getEventSys(), loginSystem.getMsgSys());
    private JPanel currentJPanel = new JPanel();

    public MainGUI(){
        currentJPanel.setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        logInRun();
    }

    public void logInRun(){
        setContentPane(loginGUI.logInPage());
//        if (!loginGUI.getIsVisible()) {
//            setContentPane(mainMenuGUI.startMainMenuPage());
//        }

//        boolean run = true;
//        do {
////            currentJPanel = loginGUI.logInPage();
//            boolean isLoggedIn = loginGUI.getLoggedIn();
//            if (isLoggedIn) {
//                setContentPane(mainMenuGUI.startMainMenuPage());
//                }
//            else {
//                run = false;
//            }
//        } while (run);

    }

}
