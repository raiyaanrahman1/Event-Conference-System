package Entity;

public abstract class User {
    private String username;
    private String password;
    public User(String uname, String pword){
        this.username = uname;
        this.password = pword;
    }
    public abstract boolean hasBroadcastRights();
    public abstract boolean hasEventCreatingRights();

    //getter for usename
    public String getUsername() {
        return username;
    }

    //getter for password
    public String getPassword() {
        return password;
    }

    //setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    //setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}

