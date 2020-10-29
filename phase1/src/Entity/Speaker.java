package Entity;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

public class Speaker extends User {
    private ArrayList<Event> talks;
    private ArrayList<Message> sentMessages;
    private ArrayList<Message> receivedMessages;

    public Speaker(String uname, String pword) {
        super(uname, pword);
        this.talks = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.receivedMessages = new ArrayList<>();
    }
    @Override
    boolean hasBroadcastRights() {
        return true;
    }

    @Override
    boolean hasEventCreatingRights() {
        return false;
    }

    public ArrayList<Event> getTalks() {
        return talks;
    }

    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void addTalk(Event talk){
        talks.add(talk);
    }

    public void addSentMessage(Message message){
        sentMessages.add(message);
    }

    public void addReceivedMessages(Message message){
        receivedMessages.add(message);
    }
}
