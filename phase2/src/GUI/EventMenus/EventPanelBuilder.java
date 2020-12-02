package GUI.EventMenus;

import javax.swing.*;
import java.awt.*;

public class EventPanelBuilder {
    JPanel panel;
    public EventPanelBuilder(JPanel panel){
        this.panel = panel;
    }

    public JPanel build500x500Panel(){
        this.panel.setSize(500, 500);
        this.panel.setLayout(null);
        return panel;
    }

    public JLabel buildEventMenuLabel(JLabel jLabel){
        jLabel.setFont(new Font("", Font.BOLD, 48));
        panel.add(jLabel);
        return jLabel;
    }

    public JList buildJListEvents(JList jList){
        jList.setSelectionBackground(new Color(51, 153, 255));
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(0);
        panel.add(jList);
        return jList;
    }

    public JScrollPane buildJScrollPane(JScrollPane jScrollPane){
        jScrollPane.setPreferredSize(new Dimension(250,360));
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jScrollPane);
        return jScrollPane;
    }


}
