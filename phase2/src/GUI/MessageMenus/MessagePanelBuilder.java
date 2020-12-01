package GUI.MessageMenus;

import javax.swing.*;
import java.util.List;
import java.awt.*;

public class MessagePanelBuilder {
    private MessageComponentFactory factory;

    public MessagePanelBuilder(){}

    public JPanel mainMenu(JPanel canvas){
//        JRadioButton contacts = new JRadioButton("Contacts");
//        JRadioButton inbox = new JRadioButton("Inbox");
//        canvas.add(contacts);
//        canvas.add(inbox);
//        contacts.setVisible(true);
//        contacts.setVisible(true);
        return canvas;
    }

    public JPanel createMessage(String content){
        return null;

    }

    public JPanel buildInbox(List<String> messages){
        return null;
    }

}
