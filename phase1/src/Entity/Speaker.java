package Entity;

import java.util.ArrayList;
import java.util.List;

public class Speaker extends User {
    private List<Event> talks;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;

    //Speaker constructor
    public Speaker(String uname, String pword) {
        super(uname, pword);
        this.talks = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.receivedMessages = new ArrayList<>();
    }
    @Override
    public boolean hasBroadcastRights() {
        return true;
    }

    @Override
    public boolean hasEventCreatingRights() {
        return false;
    }

    //getter for talks list
    public List<Event> getTalks() {
        return talks;
    }//appending an event to the talk list
    public void addTalk(Event talk){
        talks.add(talk);
    }

    //getter for sentMessages
    public List<Message> getSentMessages() {
        return sentMessages;
    }//appending a messages to sentMessages
    public void addSentMessages(Message message){
        sentMessages.add(message);
    }

    //getter for receivedMessages
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }
    //appending a message to the receivedMessages
    public void addReceivedMessages(Message message){
        receivedMessages.add(message);
    }
}
