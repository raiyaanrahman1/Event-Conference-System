package UseCase;

import Entity.Attendee;
import Entity.Organizer;
import Entity.Speaker;
import Entity.User;
import Gateway.IGateway2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Deals with gateway and storage
 */
public class UserStore {
    public List<User> userList;
    public IGateway2 gateway2;
    public List<String> formattedContacts;

    public UserStore(IGateway2 gateway2){
        this.gateway2 = gateway2;
        this.formattedContacts = getStoredContacts();
    }

    /**
     * Adds a list of user information
     *
     * @param userInfo all the users information
     */
    public void createUserList(ArrayList<List<String>> userInfo) {
        userList = new ArrayList<>();
        for (List<String> u : userInfo) {
            addUserToList(u);
        }
    }

    /**
     * Adds user to list based on type
     *
     * @param userInfo username of the user
     */
    public void addUserToList(List<String> userInfo) {
        String type = userInfo.get(2);
        if (type.equals("A")) {
            User u = new Attendee(userInfo.get(0), userInfo.get(1));
            userList.add(u);
            readContacts(u);
        } else if (type.equals("O")) {
            User u = new Organizer(userInfo.get(0), userInfo.get(1));
            userList.add(u);
            readContacts(u);
        } else if (type.equals("S")) {
            User u = new Speaker(userInfo.get(0), userInfo.get(1));
            userList.add(u);
            readContacts(u);
        }
    }

    private void readContacts(User user) {
        List<String> contactsList = new ArrayList<>();
        for (String contacts : this.formattedContacts) {
            String[] tokens = contacts.split("\\|");
            if (user.getUsername().equals(tokens[0])) {
                contactsList.addAll(Arrays.asList(tokens).subList(1, tokens.length));
            }
        }
        user.setContacts(contactsList);
    }

    // Each string should be formatted in the manner:
    // (Logged in user's username)|(contact1's username)|(contact2's username)|...
    private List<String> getStoredContacts() {
        this.gateway2.openForRead();
        List<String> formattedContacts = new ArrayList<>();
        while (this.gateway2.hasNext()) {
            formattedContacts.add(this.gateway2.next());
        }
        this.gateway2.closeForRead();
        return formattedContacts;
    }

    /**
     * Stores the users contacts
     */
    public void storeContacts() {
        if (this.gateway2.openForWrite()) {
            for (User u : this.userList) {
                StringBuilder contactList = new StringBuilder(u.getUsername());
                for (String contact : u.getContacts()) {
                    contactList.append("|").append(contact);
                }
                this.gateway2.write(contactList.toString());
            }
            this.gateway2.closeForWrite();
        }
    }
}
