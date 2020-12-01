package GUI.Main;

import Controller.LoginSystem;

import javax.swing.*;

public class MainSystem {
    public MainSystem(){
        MainGUI main = new MainGUI();
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
