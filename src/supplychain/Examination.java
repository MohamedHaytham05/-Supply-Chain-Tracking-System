package supplychain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Examination {

    private final String inspectionID;
    private String       inspDetails;
    private String       result;
    private Date         date;

    private final List<Shipment> shipments;
    private Shipment             linkedShipment;

    private final List<Storage>  storages;
    private Storage              linkedStorage;

    public Examination(String inspectionID, String inspDetails, String result, Date date) {
        if (inspectionID == null || inspectionID.trim().isEmpty())
            throw new IllegalArgumentException("Examination: inspectionID cannot be null or empty.");
        if (result == null || result.trim().isEmpty())
            throw new IllegalArgumentException("Examination: result cannot be null or empty.");

        this.inspectionID = inspectionID;
        this.inspDetails  = inspDetails != null ? inspDetails : "";
        this.result       = result;
        this.date         = date != null ? date : new Date();
        this.shipments    = new ArrayList<>();
        this.storages     = new ArrayList<>();

        Logger.getInstance().log("Examination created: " + inspectionID);
    }

    public void showResults() {
        System.out.println("=== Examination Report ===");
        System.out.println("ID      : " + inspectionID);
        System.out.println("Details : " + inspDetails);
        System.out.println("Result  : " + result);
        System.out.println("Date    : " + date);
        if (linkedShipment != null)
            System.out.println("Shipment: " + linkedShipment.getShipmentID()
                + " | Status: "   + linkedShipment.getStatus()
                + " | Location: " + linkedShipment.getCurrentLocation());
        if (linkedStorage != null)
            System.out.println("Storage : " + linkedStorage.getStorageID()
                + " | Shelf: "           + linkedStorage.getShelfNumber()
                + " | Available slots: " + linkedStorage.getAvailableSlots());
        Logger.getInstance().log("Examination showResults: " + inspectionID + " | " + result);
    }

    public void updateDetails(String newDetails, String newResult) {
        if (newResult == null || newResult.trim().isEmpty()) {
            Logger.getInstance().log("Examination updateDetails: invalid result rejected.");
            return;
        }
        this.inspDetails = newDetails != null ? newDetails : "";
        this.result      = newResult;
        this.date        = new Date();
        Logger.getInstance().log("Examination updated: " + inspectionID + " | result: " + result);
    }

    public void linkShipment(Shipment shipment) {
        if (shipment == null) return;
        if (!shipments.contains(shipment)) {
            shipments.add(shipment);
            this.linkedShipment = shipment;
            Logger.getInstance().log("Examination " + inspectionID
                + " linked to Shipment: " + shipment.getShipmentID());
        }
    }

    public void linkStorage(Storage storage) {
        if (storage == null) return;
        if (!storages.contains(storage)) {
            storages.add(storage);
            this.linkedStorage = storage;
            Logger.getInstance().log("Examination " + inspectionID
                + " linked to Storage: " + storage.getStorageID());
        }
    }

    public boolean isPassed() {
        return "Passed".equalsIgnoreCase(this.result);
    }

    public boolean isLinkedTo(Shipment shipment) {
        return shipments.contains(shipment);
    }

    public boolean isLinkedTo(Storage storage) {
        return storages.contains(storage);
    }

    public String   getInspectionID()   { return inspectionID; }
    public String   getInspDetails()    { return inspDetails; }
    public String   getResult()         { return result; }
    public Date     getDate()           { return new Date(date.getTime()); }
    public Shipment getLinkedShipment() { return linkedShipment; }
    public Storage  getLinkedStorage()  { return linkedStorage; }
    public List<Shipment> getShipments() { return Collections.unmodifiableList(shipments); }
    public List<Storage>  getStorages()  { return Collections.unmodifiableList(storages); }

    public void setResult(String result) {
        if (result == null || result.trim().isEmpty()) return;
        this.result = result;
        Logger.getInstance().log("Examination result set: " + inspectionID + " = " + result);
    }

    public void setInspDetails(String inspDetails) {
        this.inspDetails = inspDetails != null ? inspDetails : "";
    }

    @Override
    public String toString() {
        return "Examination{ID='" + inspectionID
             + "', result='" + result
             + "', date=" + date + "}";
    }
}