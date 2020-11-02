package Entity;

import com.sun.javafx.webkit.UtilitiesImpl;

public class Message {
    private String content;
    private String receiver; //username of receiver
    private String sender;  //username of sender
    private String date;
    private String time;

    public Message(String content, String receiver, String sender, String date, String time) {
        this.content = content;
        this.receiver = receiver;
        this.sender = sender;
        this.date = date;
        this.time = time;
    }
    //getter for content
    public String getContent() {
        return content;
    }
    //setter for content
    public void setContent(String content) {
        this.content = content;
    }

    //getter for receiver
    public String getReceiver() {
        return receiver;
    }
    //setter for receiver
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    //getter for sender
    public String getSender() {
        return sender;
    }
    //setter for sender
    public void setSender(String sender) {
        this.sender = sender;
    }

}
