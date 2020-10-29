package entity.entity;

public abstract class user {
    private String username;
    private String password;
    public user(String uname, String pword){
        username = uname;
        password = pword;
    }
    abstract boolean hasBroadcastRights();
    abstract boolean hasEventCreatingRights();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
