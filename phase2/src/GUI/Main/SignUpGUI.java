package GUI.Main;

import Controller.PasswordChecker;
import Controller.LoginSystem;
import GUI.PanelBuilder.LoginPanelBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SignUpGUI implements ActionListener {
    private final PanelStack panelStack;
    private final LoginSystem loginSystem;
    private final JPanel signUpPanel = new JPanel();
    private final JLabel titleLabel = new JLabel("sign up");
    private final JLabel usernameJLabel = new JLabel("username");
    private final JLabel passwordJLabel = new JLabel("password");
    private final JLabel programTitleJLabel = new JLabel("the imposter summit");
    private final JTextField usernameTextField = new JTextField(20);
    private final JPasswordField passwordTextField = new JPasswordField(20);
    private final JButton signUpButton = new JButton("sign up");
    private final String[] userTypes = {"attendee", "organizer"};
    private final JComboBox typeComboBox = new JComboBox(userTypes);
    private final MainMenuGUI mainMenuGUI;
    private final JButton backButton = new JButton("back");
    private final LoginPanelBuilder panelBuilder = new LoginPanelBuilder(signUpPanel);


    public SignUpGUI(MainMenuGUI menu, LoginSystem loginSystem, PanelStack panelStack) {
        this.loginSystem = loginSystem;
        this.panelStack = panelStack;
        mainMenuGUI = menu;
        signUpPage();
        backButtonListen();
        signUpButton.addActionListener(this);
    }

    public JPanel signUpPage() {
        // PANEL:
        panelBuilder.buildMainPanel();
        // SIGNUP TITLE:
        panelBuilder.buildPanelLabel(titleLabel, 20, 200, 164, 200, 30);
        // PROGRAM TITLE:
        panelBuilder.buildPanelLabel(programTitleJLabel, 32, 65, 10, 500, 60);
        // USERNAME:
        panelBuilder.buildComponent(usernameJLabel, 123, 214, 80, 25);
        panelBuilder.buildComponent(usernameTextField, 193, 214, 165, 25);
        // PASSWORD:
        panelBuilder.buildComponent(passwordJLabel, 123, 264, 80, 25);
        panelBuilder.buildComponent(passwordTextField, 193, 264, 165, 25);
        // COMBOBOX:
        panelBuilder.buildComponent(typeComboBox, 190, 314, 100, 25);
        // SIGNUP BUTTON:
        panelBuilder.buildButton(signUpButton, 190, 364, 100, 25);
        // BACK BUTTON:
        panelBuilder.buildButton(backButton, 10, 430, 80, 25);
        return signUpPanel;
    }

    private void backButtonListen() {
        backButton.addActionListener(e -> {
            panelStack.pop();
            JPanel panel = (JPanel) panelStack.pop();
            panelStack.loadPanel(panel);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String uname = usernameTextField.getText();
        String pword = passwordTextField.getText();
        PasswordChecker checker = new PasswordChecker();
        if (checker.scorePassword(pword).equals("Strong Password") ||
                checker.scorePassword(pword).equals("Medium Password") ) {
                if (!loginSystem.isUser(uname)) {
                    if (Objects.equals(typeComboBox.getSelectedItem(), "attendee")){
                        loginSystem.signUpUser(uname, pword, "A");
                    JOptionPane.showMessageDialog(signUpPanel,
                            String.format("You have successfully signed up as an Attendee.\nPassword Strength: %s",
                                    checker.scorePassword(pword)));
                        loginSystem.canLogin(uname, pword);
                        panelStack.loadPanel(mainMenuGUI.startMainMenuPage());
                } else {
                    String input = JOptionPane.showInputDialog("Please enter the Organizer code.");
                        if (input.equals("AmongUs")) {
                            loginSystem.signUpUser(uname, pword, "O");
                            JOptionPane.showMessageDialog(signUpPanel,
                                    String.format("You have successfully signed up as an Organizer.\nPassword Strength: %s",
                                            checker.scorePassword(pword)));
                            loginSystem.canLogin(uname, pword);
                            panelStack.loadPanel(mainMenuGUI.startMainMenuPage());
                        } else {
                        JOptionPane.showMessageDialog(signUpPanel, "Invalid Organizer code.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(signUpPanel, "Username already exists. Please select a different username.");
            }
        } else {
            JOptionPane.showMessageDialog(signUpPanel,
                    "Password is weak. Please make sure your password has the following:\n"+
                    "- 8-20 characters \n" +
                            "- At least one uppercase \n" +
                            "- At least one lowercase \n" +
                            "- At least one number");
        }
    }
}
