package GUI.EventMenus;

import javax.swing.*;
import java.awt.*;
import java.nio.ByteOrder;

public class EventPanelBuilder {
    JPanel panel;
    public EventPanelBuilder(JPanel panel){
        this.panel = panel;
    }

    public JPanel build500x500Panel(){
        this.panel.setSize(500, 500);
        this.panel.setLayout(new BorderLayout());
        this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));
        return panel;
    }

    public JLabel buildEventMenuLabel(JLabel jLabel){
        jLabel.setFont(new Font("", Font.BOLD, 48));
        return jLabel;
    }

    public JList buildJListEvents(JList jList){
        jList.setSelectionBackground(new Color(51, 153, 255));
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(0);
  //      panel.add(jList, BorderLayout.CENTER);
        return jList;
    }

    public JScrollPane buildJScrollPane(JScrollPane jScrollPane){
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVisible(true);
   //     panel.add(jScrollPane, BorderLayout.CENTER);

        return jScrollPane;
    }


}
