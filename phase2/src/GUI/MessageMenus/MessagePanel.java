package GUI.MessageMenus;

import GUI.Main.PanelStack;

import javax.swing.*;
import java.util.List;

public class MessagePanel {

    // these were private but I needed to make them public for access from subclasses
    public void enableButtons(List<JButton> options){
        for (JButton button : options){
            button.setEnabled(true);
            button.setVisible(true);
        }
    }

    public void mainBackListener(PanelStack panelStack, JButton backButton, JButton internalBack){
        internalBack.setVisible(false);
        backButton.addActionListener(e -> {
            panelStack.pop();
            panelStack.loadPanel((JPanel) panelStack.pop());
        });
    }

    public void disableButtons(List<JButton> options){
        for (JButton button : options){
//            button.setEnabled(false);
            button.setVisible(false);
        }
    }

}
