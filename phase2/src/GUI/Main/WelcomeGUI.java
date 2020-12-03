package GUI.Main;
import Controller.LoginSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeGUI implements ILoginView {
    private JPanel startPanel;
    private JButton LogInButton;
    private JButton signUpButton;
    private JFrame frame;
    private LoginSystem loginSystem = new LoginSystem();
    private LoginGUI loginGUI;
    private SignUpGUI signUpGUI;
    public JPanel getStartPanel() {
        return startPanel;
    }


    public WelcomeGUI(JFrame frame, LoginGUI loginGUI, SignUpGUI signUpGUI) {
        this.frame = frame;
        this.loginGUI = loginGUI;
        this.signUpGUI = signUpGUI;

        LogInButton.addActionListener(e -> frame.setContentPane(loginGUI.logInPage()));

        signUpButton.addActionListener(e -> frame.setContentPane(signUpGUI.signUpPage()));
    }
}
