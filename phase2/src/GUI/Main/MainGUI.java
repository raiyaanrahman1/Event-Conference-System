package GUI.Main;

import GUI.EventMenus.EventGUI;
import GUI.MessageMenus.MessageGUI;

import javax.swing.*;

public class MainGUI extends JFrame{
    private LoginGUI loginGUI = new LoginGUI();

    public MainGUI(){
        setContentPane(loginGUI.startPage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }

}
