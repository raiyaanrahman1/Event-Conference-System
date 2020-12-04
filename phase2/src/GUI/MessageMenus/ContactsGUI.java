package GUI.MessageMenus;

import Controller.MessengerSystem;

import javax.swing.*;

public class ContactsGUI implements IMessageView{
    private MessagePanelBuilder builder;
    private MessengerSystem messenger;

    public ContactsGUI(MessengerSystem messenger){
        this.messenger = messenger;
        builder = new MessagePanelBuilder(new JPanel());
    }

    public JPanel mainPage(){
        return null;
    }
}
