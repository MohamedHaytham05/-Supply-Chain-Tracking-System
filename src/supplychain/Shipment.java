package supplychain;
import java.util.ArrayList;
import java.util.List;

public class Shipment {

    private String shipmentID;
    private String status;
    private String currentLocation;
    private List<Event> events;

    public Shipment(String shipmentID, String status, String currentLocation) {
        if (shipmentID == null || shipmentID.trim().isEmpty()) {
            throw new IllegalArgumentException("Shipment ID cannot be empty.");
        }

        this.shipmentID = shipmentID.trim();
        this.status = status;
        this.currentLocation = currentLocation;
        this.events = new ArrayList<>();
    }

    public void updateStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty.");
        }
        this.status = status.trim();
    }

    public void changeLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
        this.currentLocation = location.trim();
    }

    public void showShipmentDetails() {
        System.out.println("Shipment ID: " + shipmentID);
        System.out.println("Status: " + status);
        System.out.println("Current Location: " + currentLocation);
    }

    public void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }
        events.add(event);
    }

    public String getShipmentID() {
        return shipmentID;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    // Setters
    public void setShipmentID(String shipmentID) {
        this.shipmentID = shipmentID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
