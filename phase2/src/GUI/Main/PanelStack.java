package GUI.Main;

import javax.swing.*;
import java.util.Stack;

public class PanelStack extends Stack {

    private JFrame mainFrame;

    public PanelStack(JFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    public void loadPanel(JPanel panel){
        this.add(panel);
        mainFrame.setContentPane(panel);
        mainFrame.revalidate();
    }

}
