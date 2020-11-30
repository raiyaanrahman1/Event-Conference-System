package GUI.Main;

import Controller.LoginSystem;

import javax.swing.*;

public class MainSystem {
    public MainSystem(){
        MainGUI guis = new MainGUI();
        LoginSystem presenters = new LoginSystem();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                new MainSystem();
            }
        });
    }

}
