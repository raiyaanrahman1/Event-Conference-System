package GUI.Main;

import javax.swing.*;

public class MainSystem {
    public MainSystem(){
        new MainGUI();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainSystem::new);
    }

}
