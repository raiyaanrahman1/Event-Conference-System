package GUI.Main;

import javax.swing.*;
import java.awt.*;

public class LoginPanelBuilder {
    private JPanel panel;
    private Font titleFont;
    private Font infoFont;

    public LoginPanelBuilder(JPanel panel){
        this.panel = panel;
        titleFont = new Font(Font.MONOSPACED, Font.TYPE1_FONT, 20);
        infoFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    }

    public void buildMainPanel(){
        this.panel.setSize(500, 500);
        this.panel.setLayout(null);
    }

    public void buildPanelLabel(JLabel jLabel, int size, int x, int y, int width, int height){
        jLabel.setFont(new Font(Font.MONOSPACED, Font.TYPE1_FONT, size));
        jLabel.setBounds(x, y, width, height);
        this.panel.add(jLabel);
    }

    public void buildComponent(Component component, int x, int y, int width, int height){
        component.setBounds(x, y, width, height);
        panel.add(component);
    }

    public void buildButton(JButton button, int x, int y, int width, int height){
        button.setFont(infoFont);
        button.setBounds(x, y, width, height);
        panel.add(button);
    }


}
