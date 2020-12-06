package UseCase;

import Entity.Message;
import Exceptions.NoSuchMessageException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MessageMap implements MessageCollection {

    /*
     * A message key-value map. Each key is an user's username which
     * points to a list of all messages received by that user.
     */
    private final Map<String, List<Message>> messages;

    /**
     * Initializes the message map.
     */
    public MessageMap() {
        messages = new HashMap<>();
    }

    @Override
    public Message find(Message message) throws NoSuchMessageException {
        if (this.contains(message.getReceiver())) {
            for (Message mess: this.messages.get(message.getReceiver())) {
                if (message.equals(mess)) {
                    return mess;
                }
            }
        }

        throw new NoSuchMessageException();
    }

    @Override
    public List<Message> get(String receiver) {
        if (!this.contains(receiver)) {
            return new ArrayList<>();
        } else {
            return this.messages.get(receiver);
        }
    }

    @Override
    public boolean contains(String receiver) {
        return this.messages.containsKey(receiver);
    }

    @Override
    public boolean add(Message message) {
        if (!this.contains(message.getReceiver())) {
            this.messages.put(message.getReceiver(), new ArrayList<>());
        }

        return this.get(message.getReceiver()).add(message);
    }

    @Override
    public boolean remove(Message message) {
        String receiver = message.getReceiver();
        if (!this.contains(receiver)) {
            return false;
        } else {
            return this.get(receiver).remove(message);
        }
    }

    public List<Message> getAll() {
        List<Message> all = new ArrayList<>();

        for (List<Message> userMessages: this.messages.values()) {
            all.addAll(userMessages);
        }

        return all;
    }
}
