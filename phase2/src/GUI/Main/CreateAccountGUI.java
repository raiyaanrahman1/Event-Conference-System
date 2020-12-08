package GUI.Main;

import Controller.LoginSystem;
import GUI.PanelBuilder.LoginPanelBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class CreateAccountGUI implements ActionListener {
    private PanelStack panelStack;
    private LoginSystem loginSystem;
    private JPanel signUpPanel = new JPanel();
    private JLabel titleLabel = new JLabel("create account");
    private JLabel usernameJLabel = new JLabel("username");
    private JLabel passwordJLabel = new JLabel("password");
    private JTextField usernameTextField = new JTextField(20);
    private JPasswordField passwordTextField = new JPasswordField(20);
    private JButton signUpButton = new JButton("create");
    private String[] userTypes = {"Attendee", "VIP Attendee", "Speaker"};
    private JComboBox typeComboBox = new JComboBox(userTypes);
    private JButton backButton = new JButton("back");
    private LoginPanelBuilder panelBuilder = new LoginPanelBuilder(signUpPanel);


    public CreateAccountGUI(LoginSystem loginSystem, PanelStack panelStack) {
        this.loginSystem = loginSystem;
        this.panelStack = panelStack;
        backButtonListen();
        signUpButton.addActionListener(this);
    }

    public JPanel createAccountPage(){
        // PANEL:
        panelBuilder.buildMainPanel();
        // SIGNUP TITLE:
        panelBuilder.buildPanelLabel(titleLabel, 20, 150, 164, 200, 30);
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
        panelBuilder.buildButton(backButton,10, 430, 80, 25);
        return signUpPanel;
    }

    private void backButtonListen(){
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

        if (!loginSystem.isUser(uname)) {
            if (Objects.equals(typeComboBox.getSelectedItem(), "Attendee")) {
                    loginSystem.signUpUser(uname, pword, "A");
                    JOptionPane.showMessageDialog(signUpPanel, "You have successfully created a Attendee.");
                    usernameTextField.setText("");
                    passwordTextField.setText("");
            }
            else if(Objects.equals(typeComboBox.getSelectedItem(), "VIP Attendee")) {
                loginSystem.signUpUser(uname, pword, "V");
                JOptionPane.showMessageDialog(signUpPanel, "You have successfully created a VIP Attendee.");
                usernameTextField.setText("");
                passwordTextField.setText("");
            }
            else {
                loginSystem.signUpUser(uname, pword, "S");
                JOptionPane.showMessageDialog(signUpPanel, "You have successfully created a Speaker.");
                usernameTextField.setText("");
                passwordTextField.setText("");
            }
        }
        else {
            JOptionPane.showMessageDialog(signUpPanel, "Username already exists. Please select a different username.");
        }
    }
}
