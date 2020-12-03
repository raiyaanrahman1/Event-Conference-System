package GUI.Main;

import Controller.LoginSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI implements ILoginView, ActionListener {
    private LoginSystem loginSystem;
    private JPanel loginPanel = new JPanel();
    private JLabel titleLabel = new JLabel();
    private JLabel username = new JLabel();
    private JLabel password = new JLabel();
    private JLabel programTitle = new JLabel();
    private JTextField usertext = new JTextField(20);
    private JPasswordField passtext = new JPasswordField(20);
    private JButton logInButton = new JButton();
    private boolean loggedIn = false;
     private MainMenuGUI mainMenuGUI;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setResizable(false);
        LoginSystem loginSystem = new LoginSystem();
        frame.setContentPane(new LoginGUI(loginSystem).loginPanel);
        frame.setVisible(true);
    }

    public LoginGUI(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        logInPage();
        this.loggedIn = false;
        mainMenuGUI = new MainMenuGUI(loginSystem.getEventSys(), loginSystem.getMsgSys());
    }

    public boolean getIsVisible() {
        return loginPanel.isVisible();
    }

    public JPanel logInPage(){
        // PANEL:
        loginPanel.setLayout(null);
        loginPanel.setSize(500, 500);
        loginPanel.setVisible(true);
        // PROGRAM TITLE:
        programTitle.setText("THE AMONG US SUMMIT");
        programTitle.setBounds(125, 10, 500, 60);
        programTitle.setFont(new Font("", Font.BOLD, 20));
        loginPanel.add(programTitle);
        // LOGIN TITLE:
        titleLabel.setText("Login");
        titleLabel.setFont(new Font("", Font.BOLD, 20));
        titleLabel.setVisible(true);
        titleLabel.setBounds(229, 164, 80, 30);
        loginPanel.add(titleLabel);
        // USERNAME:
        username.setText("Username");
        username.setVisible(true);
        username.setBounds(123, 214, 80, 25);
        loginPanel.add(username);
        usertext.setBounds(193, 214, 165, 25);
        loginPanel.add(usertext);
        // PASSWORD:
        password.setText("Password");
        password.setVisible(true);
        password.setBounds(123, 264, 80, 25);
        loginPanel.add(password);
        passtext.setBounds(193, 264, 165, 25);
        loginPanel.add(passtext);
        // LOGIN BUTTON:
        logInButton.setText("Login");
        logInButton.setBounds(214, 344, 80, 25);
        logInButton.setVisible(true);
        loginPanel.add(logInButton);
        logInButton.addActionListener(this);
        return loginPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String uname = usertext.getText();
        String pword = passtext.getText();

        if (loginSystem.canLogin(uname, pword)) {
            JOptionPane.showMessageDialog(loginPanel, "You have successfully logged in");
            loginPanel.setVisible(false);
//            mainMenuGUI.startMainMenuPage();
        }
        else {
            JOptionPane.showMessageDialog(loginPanel, "Invalid username or password.");
        }

    }
}
