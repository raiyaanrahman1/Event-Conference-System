package GUI.Main;
import Controller.LoginSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeGUI implements ILoginView {
    private PanelStack panelStack;
    private JPanel startPanel;
    private JButton LogInButton;
    private JButton signUpButton;
    private LoginSystem loginSystem = new LoginSystem();
    private LoginGUI loginGUI;
    private SignUpGUI signUpGUI;
    public JPanel getStartPanel() {
        return startPanel;
    }


    public WelcomeGUI(LoginGUI loginGUI, SignUpGUI signUpGUI, PanelStack panelStack) {
        this.loginGUI = loginGUI;
        this.panelStack = panelStack;
        this.signUpGUI = signUpGUI;

        LogInButton.addActionListener(e -> {
            this.panelStack.loadPanel(loginGUI.logInPage());
        });

        signUpButton.addActionListener(e -> this.panelStack.loadPanel(signUpGUI.signUpPage()));
    }
}
