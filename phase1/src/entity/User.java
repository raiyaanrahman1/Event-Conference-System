package entity;

public abstract class User {
    private String username;
    private String password;
    public User(String uname, String pword){
        this.username = uname;
        this.password = pword;
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

