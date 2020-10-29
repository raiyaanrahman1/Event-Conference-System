package Entity;

public class Message {
    private String content;
    private String receiver; //username of receiver
    private String sender;  //username of sender

    public Message(String content, String receiver, String sender) {
        this.content = content;
        this.receiver = receiver;
        this.sender = sender;
    }
}
