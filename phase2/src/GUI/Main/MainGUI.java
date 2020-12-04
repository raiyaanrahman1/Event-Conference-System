package GUI.Main;

import Controller.LoginSystem;

import javax.swing.*;

public class MainGUI extends JFrame{
    private LoginGUI loginGUI = new LoginGUI(new LoginSystem());

    public MainGUI(){
        setContentPane(loginGUI.startPage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        pack();
        setResizable(false);
        setVisible(true);
    }

}
