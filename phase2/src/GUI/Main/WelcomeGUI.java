package GUI.Main;

import GUI.PanelBuilder.LoginPanelBuilder;

import javax.swing.*;

public class WelcomeGUI {
    private PanelStack panelStack;
    private JPanel welcomePanel = new JPanel();
    private JLabel welcomeJLabel = new JLabel("the imposter summit");
    private JButton logInButton = new JButton("log in");
    private JButton signUpButton = new JButton("sign up");
    private LoginGUI loginGUI;
    private SignUpGUI signUpGUI;
    private LoginPanelBuilder panelBuilder = new LoginPanelBuilder(welcomePanel);



    public WelcomeGUI(LoginGUI loginGUI, SignUpGUI signUpGUI, PanelStack panelStack) {
        this.loginGUI = loginGUI;
        this.panelStack = panelStack;
        this.signUpGUI = signUpGUI;
        logInButtonListener();
        signUpButtonListener();
    }

    public JPanel WelcomePage(){
        panelBuilder.buildMainPanel();
        panelBuilder.buildPanelLabel(welcomeJLabel,32,  65, 10, 500, 60);
        panelBuilder.buildButton(logInButton, 190, 250, 100, 25);
        panelBuilder.buildButton(signUpButton, 190, 275, 100, 25);
        return welcomePanel;
    }

    private void logInButtonListener(){
        logInButton.addActionListener(e -> this.panelStack.loadPanel(this.loginGUI.logInPage()));
    }

    private void signUpButtonListener(){
        signUpButton.addActionListener(e -> this.panelStack.loadPanel(this.signUpGUI.signUpPage()));
    }
}
