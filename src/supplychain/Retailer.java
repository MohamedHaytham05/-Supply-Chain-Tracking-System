package supplychain;

public class Retailer extends User {

    public Retailer(String username, String password, String email) {
        super(username, password, email);
    }

    public void confirmDelivery(Delivery delivery) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("confirmDelivery failed — not authenticated: " + getUsername());
            return;
        }
        if (delivery == null)
            throw new IllegalArgumentException("Delivery cannot be null.");

        delivery.setDeliveryStatus("Delivered");
        delivery.checkStatus();
        System.out.println("Retailer " + getUsername()
            + " confirmed delivery: " + delivery.getDeliveryID());
        Logger.getInstance().log("Retailer " + getUsername()
            + " confirmed delivery: " + delivery.getDeliveryID());
    }

    public void finalInspection(Shipment shipment) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("finalInspection failed — not authenticated: " + getUsername());
            return;
        }
        if (shipment == null)
            throw new IllegalArgumentException("Shipment cannot be null.");

        Examination exam = new Examination(
            "FINAL-EXAM-" + shipment.getShipmentID() + "-" + System.currentTimeMillis(),
            "Final inspection by Retailer " + getUsername(),
            "Passed",
            new java.util.Date()
        );

        exam.linkShipment(shipment);
        exam.showResults();

        if (exam.isPassed()) {
            shipment.updateStatus("Final Inspection Passed");
            System.out.println("Final inspection PASSED for shipment: "
                + shipment.getShipmentID());
        } else {
            shipment.updateStatus("Final Inspection Failed");
            System.out.println("Final inspection FAILED for shipment: "
                + shipment.getShipmentID());
        }

        Logger.getInstance().log("Retailer " + getUsername()
            + " performed final inspection on shipment: " + shipment.getShipmentID()
            + " | Result: " + exam.getResult());
    }

    @Override
    public boolean login(String username, String password) {
        if (this.getUsername().equals(username) && this.getPassword().equals(password)) {
            setLoggedIn(true);
            System.out.println("Retailer " + username + " logged in successfully.");
            Logger.getInstance().log("Retailer logged in: " + username);
            return true;
        }
        System.out.println("Login failed for Retailer: " + username);
        Logger.getInstance().log("Retailer login failed: " + username);
        return false;
    }

    @Override
    public void signup() {
        System.out.println("Retailer account created for: " + getUsername()
            + " | Email: " + getEmail());
        Logger.getInstance().log("Retailer signed up: " + getUsername());
    }
}