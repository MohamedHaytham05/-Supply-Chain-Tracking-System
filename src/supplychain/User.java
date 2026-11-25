package supplychain;
public abstract class User {

    private String  username;
    private String  password;
    private String  email;
    private boolean isLoggedIn;

    public User(String username, String password, String email) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username cannot be empty.");
        if (password == null || password.trim().isEmpty())
            throw new IllegalArgumentException("Password cannot be empty.");
        this.username   = username.trim();
        this.password   = password;
        this.isLoggedIn = false;
        setEmail(email);
    }

    public abstract boolean login(String username, String password);

    public abstract void signup();

    public final void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email.trim();
        } else {
            System.out.println("Warning: Invalid email for user: " + this.username);
            this.email = "pending@system.com";
        }
    }

    public void logout() {
        this.isLoggedIn = false;
        Logger.getInstance().log("User logged out: " + username);
    }

    public boolean isAuthenticated() { return isLoggedIn; }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) return;
        this.username = username.trim();
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) return;
        this.password = password;
    }

    public void setLoggedIn(boolean status) {
        this.isLoggedIn = status;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail()    { return email; }

    @Override
    public String toString() {
        return getClass().getSimpleName()
             + "{username='" + username
             + "', email='"  + email
             + "', loggedIn=" + isLoggedIn + "}";
    }
}
