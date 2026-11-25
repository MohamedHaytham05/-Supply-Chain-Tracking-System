package supplychain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Storage {

    private final String storageID;
    private String       location;
    private String       shelfNumber;
    private int          capacity;
    private boolean      isAvailable;

    private final List<Shipment>    shipments;
    private final List<Examination> examinations;

    public Storage(String storageID, String location, String shelfNumber, int capacity) {
        if (storageID == null || storageID.trim().isEmpty())
            throw new IllegalArgumentException("Storage: storageID cannot be null or empty.");
        if (capacity <= 0)
            throw new IllegalArgumentException("Storage: capacity must be greater than 0.");

        this.storageID    = storageID;
        this.location     = location != null ? location : "Unknown";
        this.shelfNumber  = shelfNumber != null ? shelfNumber : "N/A";
        this.capacity     = capacity;
        this.isAvailable  = true;
        this.shipments    = new ArrayList<>();
        this.examinations = new ArrayList<>();

        Logger.getInstance().log("Storage created: " + storageID + " at " + location);
    }

    public boolean isAvailable() {
        isAvailable = shipments.size() < capacity;
        return isAvailable;
    }

    public void showStorageDetails() {
        System.out.println("=== Storage Details ===");
        System.out.println("ID        : " + storageID);
        System.out.println("Location  : " + location);
        System.out.println("Shelf     : " + shelfNumber);
        System.out.println("Capacity  : " + capacity);
        System.out.println("Slots used: " + shipments.size() + "/" + capacity);
        System.out.println("Available : " + isAvailable());
        if (!shipments.isEmpty()) {
            System.out.println("-- Shipments in Storage --");
            for (Shipment s : shipments)
                System.out.println("  ID: " + s.getShipmentID()
                    + " | Status: "   + s.getStatus()
                    + " | Location: " + s.getCurrentLocation());
        }
        if (!examinations.isEmpty()) {
            System.out.println("-- Examinations on Storage --");
            for (Examination e : examinations)
                System.out.println("  ID: " + e.getInspectionID()
                    + " | Result: " + e.getResult()
                    + " | Date: "   + e.getDate());
        }
        Logger.getInstance().log("Storage showStorageDetails: " + storageID);
    }

    public void updateShelf(String newShelfNumber) {
        if (newShelfNumber == null || newShelfNumber.trim().isEmpty()) {
            Logger.getInstance().log("Storage updateShelf: invalid shelf number rejected.");
            return;
        }
        this.shelfNumber = newShelfNumber;
        Logger.getInstance().log("Storage " + storageID + " shelf updated to: " + shelfNumber);
    }

    public void updateShipments() {
        isAvailable = shipments.size() < capacity;
        Logger.getInstance().log("Storage " + storageID + " updated: "
            + shipments.size() + "/" + capacity + " | available: " + isAvailable);
    }

    public boolean addShipment(Shipment shipment) {
        if (shipment == null) {
            Logger.getInstance().log("Storage " + storageID + " addShipment: null rejected.");
            return false;
        }
        if (!isAvailable()) {
            Logger.getInstance().log("Storage " + storageID
                + " FULL — cannot add: " + shipment.getShipmentID());
            return false;
        }
        if (shipments.contains(shipment)) {
            Logger.getInstance().log("Storage " + storageID
                + " already contains: " + shipment.getShipmentID());
            return true;
        }
        shipments.add(shipment);
        updateShipments();
        Logger.getInstance().log("Storage " + storageID
            + " added shipment: " + shipment.getShipmentID());
        return true;
    }

    public void removeShipment(Shipment shipment) {
        if (shipment == null) return;
        if (shipments.remove(shipment)) {
            updateShipments();
            Logger.getInstance().log("Storage " + storageID
                + " removed shipment: " + shipment.getShipmentID());
        }
    }

    public void addExamination(Examination examination) {
        if (examination == null) return;
        if (!examinations.contains(examination)) {
            examinations.add(examination);
            examination.linkStorage(this);
            Logger.getInstance().log("Storage " + storageID
                + " added examination: " + examination.getInspectionID());
        }
    }

    public void removeExamination(Examination examination) {
        if (examination == null) return;
        if (examinations.remove(examination))
            Logger.getInstance().log("Storage " + storageID
                + " removed examination: " + examination.getInspectionID());
    }

    public int getAvailableSlots() {
        return capacity - shipments.size();
    }

    public boolean containsShipment(Shipment shipment) {
        return shipments.contains(shipment);
    }

    public String  getStorageID()   { return storageID; }
    public String  getLocation()    { return location; }
    public String  getShelfNumber() { return shelfNumber; }
    public int     getCapacity()    { return capacity; }
    public List<Shipment>    getShipments()    { return Collections.unmodifiableList(shipments); }
    public List<Examination> getExaminations() { return Collections.unmodifiableList(examinations); }

    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) return;
        this.location = location;
        Logger.getInstance().log("Storage " + storageID + " location updated to: " + location);
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            Logger.getInstance().log("Storage " + storageID + " setCapacity: invalid value rejected.");
            return;
        }
        this.capacity = capacity;
        updateShipments();
    }

    @Override
    public String toString() {
        return "Storage{ID='" + storageID
             + "', location='" + location
             + "', shelf='"    + shelfNumber
             + "', capacity="  + capacity
             + ", available="  + isAvailable() + "}";
    }
}