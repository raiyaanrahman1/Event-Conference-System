package GUI.MessageMenus;

import GUI.Main.PanelStack;

import javax.swing.*;
import java.util.List;

/**
 * A Helper class that contains functionality shared by multiple GUIs.
 */
public class PanelHelper {

    /**
     * Makes all the buttons in a list of buttons visible.
     * @param options the list of JButtons
     */
    public void makeButtonsVisible(List<JButton> options){
        for (JButton button : options){
            button.setVisible(true);
        }
    }

    /**
     * Pops the current panel in the panelStack and pushes the previous
     * panel when the backButton is clicked.
     * @param panelStack the main panelStack instantiated at MainGUI
     * @param backButton the input back button
     */
    public void mainBackListener(PanelStack panelStack, JButton backButton){
        backButton.addActionListener(e -> {
            JPanel panel = (JPanel) panelStack.pop();
            panel.removeAll();
            panelStack.loadPanel((JPanel) panelStack.pop());
        });
    }

    /**
     * Makes all the buttons in a list of buttons invisible.
     * @param options the list of JButtons
     */
    public void makeButtonsInvisible(List<JButton> options){
        for (JButton button : options){
            button.setVisible(false);
        }
    }

}
