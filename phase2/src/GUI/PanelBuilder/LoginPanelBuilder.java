package GUI.PanelBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * The builder of the classes in the package Main
 */
public class LoginPanelBuilder {
    private JPanel panel;
    private Font infoFont;


    /**
     * Sets the panel of this builder class
     * @param panel the panel that is used throughout this builder class
     */
    public LoginPanelBuilder(JPanel panel){
        this.panel = panel;
        infoFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    }


    /**
     * @return the font used in this program
     */
    public Font getInfoFont(){
        return infoFont;
    }
    public void buildMainPanel(){
        this.panel.setSize(500, 500);
        this.panel.setLayout(null);
    }


    /**
     * Builds the JLabel of the panel
     * @param jLabel represents the title of the panel
     * @param size of the text font of the jLabel
     * @param x represents the x coordinate of the jLabel
     * @param y represents the y coordinate of the jLabel
     * @param width of the jLabel
     * @param height of the jLabel
     */
    public void buildPanelLabel(JLabel jLabel, int size, int x, int y, int width, int height){
        jLabel.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, size));
        jLabel.setBounds(x, y, width, height);
        this.panel.add(jLabel);
    }


    /**
     * Builds a component that is added to the panel
     * @param component represents the component that is added to the panel
     * @param x represents the x coordinate of the component
     * @param y represents the y coordinate of the component
     * @param width of the component
     * @param height of the component
     */
    public void buildComponent(Component component, int x, int y, int width, int height){
        component.setBounds(x, y, width, height);
        panel.add(component);
    }


    /**
     * Builds a button that is added to the panel
     * @param button represents the component that is added to the panel
     * @param x represents the x coordinate of the button
     * @param y represents the y coordinate of the button
     * @param width of the button
     * @param height of the button
     */
    public void buildButton(JButton button, int x, int y, int width, int height){
        button.setFont(infoFont);
        button.setBounds(x, y, width, height);
        panel.add(button);
    }


}
