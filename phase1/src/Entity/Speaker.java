package Entity;

import java.util.ArrayList;
import java.util.List;

public class Speaker extends User {
    private List<Event> talks;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;

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

    public List<Event> getTalks() {
        return talks;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getReceivedMessages() {
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
