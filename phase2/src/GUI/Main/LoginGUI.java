package GUI.Main;

import Controller.LoginSystem;

import javax.swing.*;
import java.awt.*;

public class LoginGUI implements ILoginView {
    private LoginSystem loginSystem;
    private JPanel startPanel;
    private JButton confirmButton;
    private JComboBox comboBox1;
    private MainMenuGUI mainMenuGUI;

    public LoginGUI(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        mainMenuGUI = new MainMenuGUI(loginSystem.getEventSys(), loginSystem.getMsgSys());
    }

    public Container startPage(){
        return startPanel;
    }
}
