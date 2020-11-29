package UseCase;

import Entity.User;
import Gateway.IGateway2;
import java.util.List;

public interface UserStorer {
    User getUserByUsername(String user);

    void logInUser(String username);

    User getUser();

    List<String> getUserInfoList();

    void addUserToContacts(String user);

    void removeUserFromContacts(String user);

    boolean isPasswordCorrect(String username, String password);

    List<String> getSignedUpUsers();

    List<String> getContactList();

    void CreateUser(String username, String password, String userType);

    void storeContacts(IGateway2 gateway2);
}
