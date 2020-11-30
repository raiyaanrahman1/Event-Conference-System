package GUI.Main;

import javax.swing.*;

public class Main {


    private JPanel panel1;
    private JButton confirmButton;
    private JComboBox comboBox1;

    public Main(){

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
