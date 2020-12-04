package GUI.Main;
import Controller.LoginSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeGUI implements ILoginView {
    private LoginSystem loginSystem;
    private JPanel startPanel;
    private JButton LogInButton;
    private JButton signUpButton;
    private JFrame frame;
    private LoginGUI loginGUI;
    private SignUpGUI signUpGUI;

    public JPanel getStartPanel() {
        return startPanel;
    }


    public WelcomeGUI(JFrame frame, LoginSystem loginSystem) {
        this.frame = frame;
        this.loginSystem = loginSystem;
        this.loginGUI = new LoginGUI(loginSystem, frame);
        this.signUpGUI = new SignUpGUI(loginSystem, frame);
        LogInButton.addActionListener(e -> frame.setContentPane(loginGUI.logInPage()));
        signUpButton.addActionListener(e -> frame.setContentPane(signUpGUI.signUpPage()));
    }

    public void startProgram(){
        this.frame.setContentPane(getStartPanel());

    }
}
