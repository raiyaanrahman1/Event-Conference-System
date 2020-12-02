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
    private JPanel currentJPanel;

    public MainGUI(){
        setContentPane(currentJPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void logInRun(){
        boolean run = true;
        do {
            currentJPanel = loginGUI.logInPage();
            boolean isLoggedIn = loginGUI.getLoggedIn();
            if (isLoggedIn) {
                setContentPane();
                }
            } else if (option == 2) {
                mainContactPageRun(msgPres.mainContactPage(getContacts()));
            } else if (option == 3) {
                mainAddUserPageRun();
            } else {
                run = false;
            }
        } while (run);

    }

}
