package supplychain;

public class Manufacturer extends User {

    public Manufacturer(String username, String password, String email) {
        super(username, password, email);
    }

    public void regProduct(String productID) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("regProduct failed — not authenticated: " + getUsername());
            return;
        }
        if (productID == null || productID.trim().isEmpty())
            throw new IllegalArgumentException("Product ID cannot be empty.");

        System.out.println("Product registered: " + productID.trim()
            + " by " + getUsername());
        Logger.getInstance().log("Manufacturer " + getUsername()
            + " registered product: " + productID.trim());
    }

    public void exaProduct(String productID) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("exaProduct failed — not authenticated: " + getUsername());
            return;
        }
        if (productID == null || productID.trim().isEmpty())
            throw new IllegalArgumentException("Product ID cannot be empty.");

        System.out.println("Product exported: " + productID.trim()
            + " by " + getUsername());
        Logger.getInstance().log("Manufacturer " + getUsername()
            + " exported product: " + productID.trim());
    }

    @Override
    public boolean login(String username, String password) {
        if (this.getUsername().equals(username) && this.getPassword().equals(password)) {
            setLoggedIn(true);
            System.out.println("Manufacturer " + username + " logged in successfully.");
            Logger.getInstance().log("Manufacturer logged in: " + username);
            return true;
        }
        System.out.println("Login failed for Manufacturer: " + username);
        Logger.getInstance().log("Manufacturer login failed: " + username);
        return false;
    }

    @Override
    public void signup() {
        System.out.println("Manufacturer account created for: " + getUsername()
            + " | Email: " + getEmail());
        Logger.getInstance().log("Manufacturer signed up: " + getUsername());
    }
}