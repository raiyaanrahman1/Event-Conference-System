package GUI.MessageMenus;

import GUI.Main.PanelStack;

import javax.swing.*;
import java.util.List;

public class PanelHelper {

    public void makeButtonsVisible(List<JButton> options){
        for (JButton button : options){
            button.setVisible(true);
        }
    }

    public void mainBackListener(PanelStack panelStack, JButton backButton){
        backButton.addActionListener(e -> {
            panelStack.pop();
            panelStack.loadPanel((JPanel) panelStack.pop());
        });
    }

    public void makeButtonsInvisible(List<JButton> options){
        for (JButton button : options){
            button.setVisible(false);
        }
    }

}
