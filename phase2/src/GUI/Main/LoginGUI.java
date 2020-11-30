package GUI.Main;

import javax.swing.*;
import java.awt.*;

public class LoginGUI implements ILoginView {
    private JPanel startPanel;
    private JButton confirmButton;
    private JComboBox comboBox1;
    private MainMenuGUI mainMenuGUI = new MainMenuGUI();

    public LoginGUI() {
    }

    public Container startPage(){
        return startPanel;
    }
}
