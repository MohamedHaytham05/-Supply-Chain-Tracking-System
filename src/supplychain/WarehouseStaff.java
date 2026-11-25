package supplychain;
import java.util.Date;
public class WarehouseStaff extends User {

    public WarehouseStaff(String username, String password, String email) {
        super(username, password, email);
    }

    public void arrangeStorage(Storage storage, Shipment shipment) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("arrangeStorage failed — not authenticated: " + getUsername());
            return;
        }
        if (storage == null || shipment == null) {
            System.out.println("Error: Storage and Shipment cannot be null.");
            return;
        }

        boolean placed = storage.addShipment(shipment);

        if (placed) {
            shipment.changeLocation("Storage " + storage.getStorageID()
                + " | Shelf: " + storage.getShelfNumber());
            System.out.println("Success: Shipment " + shipment.getShipmentID()
                + " arranged in Storage " + storage.getStorageID());
            Logger.getInstance().log("WarehouseStaff " + getUsername()
                + " arranged shipment " + shipment.getShipmentID()
                + " in storage " + storage.getStorageID());
        } else {
            System.out.println("Failed: No available space in Storage "
                + storage.getStorageID());
            Logger.getInstance().log("arrangeStorage failed — storage full: "
                + storage.getStorageID());
        }
    }

    public void recordArrival(Shipment shipment) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("recordArrival failed — not authenticated: " + getUsername());
            return;
        }
        if (shipment == null) {
            System.out.println("Error: Shipment cannot be null.");
            return;
        }

        shipment.updateStatus("Arrived at Warehouse");
        System.out.println("Arrival recorded for shipment: " + shipment.getShipmentID()
            + " by " + getUsername());
        Logger.getInstance().log("WarehouseStaff " + getUsername()
            + " recorded arrival of shipment: " + shipment.getShipmentID());
    }

    public void performInspection(Shipment shipment) {
        if (!isAuthenticated()) {
            System.out.println("Error: " + getUsername() + " must login first.");
            Logger.getInstance().log("performInspection failed — not authenticated: " + getUsername());
            return;
        }
        if (shipment == null) {
            System.out.println("Error: Shipment cannot be null.");
            return;
        }

        Examination exam = new Examination(
            "EXAM-" + shipment.getShipmentID() + "-" + System.currentTimeMillis(),
            "Visual check by " + getUsername(),
            "Passed",
            new Date()
        );

        exam.linkShipment(shipment);
        exam.showResults();

        if (exam.isPassed()) {
            shipment.updateStatus("Inspected & Ready");
            System.out.println("Inspection PASSED for shipment: " + shipment.getShipmentID());
        } else {
            shipment.updateStatus("Inspection Failed");
            System.out.println("Inspection FAILED for shipment: " + shipment.getShipmentID());
        }

        Logger.getInstance().log("WarehouseStaff " + getUsername()
            + " performed inspection on shipment: " + shipment.getShipmentID()
            + " | Result: " + exam.getResult());
    }

    @Override
    public boolean login(String username, String password) {
        if (this.getUsername().equals(username) && this.getPassword().equals(password)) {
            setLoggedIn(true);
            System.out.println("WarehouseStaff " + username + " logged in successfully.");
            Logger.getInstance().log("WarehouseStaff logged in: " + username);
            return true;
        }
        System.out.println("Login failed for WarehouseStaff: " + username);
        Logger.getInstance().log("WarehouseStaff login failed: " + username);
        return false;
    }

    @Override
    public void signup() {
        System.out.println("WarehouseStaff account created for: " + getUsername()
            + " | Email: " + getEmail());
        Logger.getInstance().log("WarehouseStaff signed up: " + getUsername());
    }
}