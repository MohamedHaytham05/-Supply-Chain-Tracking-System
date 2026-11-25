package supplychain;

public class Shipper extends User {

    public Shipper(String username, String password, String email) {
        super(username, password, email);
    }

    public void updateShipmentStatus(Shipment shipment, String status) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("updateShipmentStatus failed — not authenticated: " + getUsername());
            return;
        }
        if (shipment == null)
            throw new IllegalArgumentException("Shipment cannot be null.");
        if (status == null || status.trim().isEmpty())
            throw new IllegalArgumentException("Status cannot be empty.");

        shipment.updateStatus(status.trim());
        System.out.println("Shipment " + shipment.getShipmentID()
            + " status updated to: " + status.trim()
            + " by " + getUsername());
        Logger.getInstance().log("Shipper " + getUsername()
            + " updated shipment " + shipment.getShipmentID()
            + " to status: " + status.trim());
    }

    public void trackPickup(Shipment shipment) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("trackPickup failed — not authenticated: " + getUsername());
            return;
        }
        if (shipment == null)
            throw new IllegalArgumentException("Shipment cannot be null.");

        System.out.println("Tracking pickup for shipment: " + shipment.getShipmentID()
            + " | Status: "   + shipment.getStatus()
            + " | Location: " + shipment.getCurrentLocation()
            + " by " + getUsername());
        Logger.getInstance().log("Shipper " + getUsername()
            + " tracked pickup for shipment: " + shipment.getShipmentID());
    }

    @Override
    public boolean login(String username, String password) {
        if (this.getUsername().equals(username) && this.getPassword().equals(password)) {
            setLoggedIn(true);
            System.out.println("Shipper " + username + " logged in successfully.");
            Logger.getInstance().log("Shipper logged in: " + username);
            return true;
        }
        System.out.println("Login failed for Shipper: " + username);
        Logger.getInstance().log("Shipper login failed: " + username);
        return false;
    }

    @Override
    public void signup() {
        System.out.println("Shipper account created for: " + getUsername()
            + " | Email: " + getEmail());
        Logger.getInstance().log("Shipper signed up: " + getUsername());
    }
}