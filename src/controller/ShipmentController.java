package controller;

import supplychain.DefaultStatusStrategy;
import supplychain.Delivery;
import supplychain.Event;
import supplychain.Logger;
import supplychain.ROI;
import supplychain.Shipment;
import supplychain.ShipmentFacade;
import supplychain.StatusStrategy;
import supplychain.Storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShipmentController {

    private ShipmentFacade    facade;
    private StatusStrategy    statusStrategy;
    private final ROI         roi;
    private Shipment          processShipment;
    private final List<Event> eventLog;

    public ShipmentController() {
        this.statusStrategy = new DefaultStatusStrategy();
        this.roi            = new ROI();
        this.eventLog       = new ArrayList<>();
        Logger.getInstance().log("ShipmentController initialised.");
    }

    public void createAndStartShipment(String shipmentID,
                                       String originLocation,
                                       String storageID,
                                       String storageLocation,
                                       String shelfNumber,
                                       int    capacity) {

        if (shipmentID == null || shipmentID.trim().isEmpty()) {
            Logger.getInstance().log("ShipmentController createAndStartShipment: invalid shipmentID.");
            return;
        }

        Shipment shipment = new Shipment(shipmentID, "Registered", originLocation);
        Storage  storage  = new Storage(storageID, storageLocation, shelfNumber, capacity);
        Delivery delivery = new Delivery("DEL-" + shipmentID);

        this.facade          = new ShipmentFacade(shipment, storage, delivery);
        this.processShipment = shipment;

        recordEvent("Registered",
            "Shipment " + shipmentID + " created at " + originLocation
            + " | Storage: " + storageID + " | Capacity: " + capacity);

        Logger.getInstance().log("ShipmentController: shipment created — " + shipmentID);
    }

    public void trackShipment() {
        if (!hasActiveShipment("trackShipment")) return;

        System.out.println("=== Tracking Info ===");
        System.out.println("Status   : " + processShipment.getStatus());
        System.out.println("Location : " + processShipment.getCurrentLocation());
        System.out.println("Events   : " + eventLog.size());

        Logger.getInstance().log("ShipmentController trackShipment: "
            + processShipment.getShipmentID());
    }

    public void updateShipmentLocation(String newLocation) {
        if (!hasActiveShipment("updateShipmentLocation")) return;
        if (newLocation == null || newLocation.trim().isEmpty()) {
            Logger.getInstance().log("ShipmentController updateShipmentLocation: invalid location.");
            return;
        }
        processShipment.changeLocation(newLocation);
        recordEvent("Location Updated", "Shipment moved to: " + newLocation);
    }

    public void performInspection() {
        if (!hasFacade("performInspection")) return;

        facade.inspectAndConfirm();

        recordEvent("Inspected",
            "Inspection completed for shipment: " + processShipment.getShipmentID()
            + " | Final status: " + processShipment.getStatus());
    }

    public void confirmDelivery() {
        if (!hasFacade("confirmDelivery")) return;

        facade.getDelivery().setDeliveryStatus("Delivered");
        facade.getDelivery().checkStatus();

        recordEvent("Delivery Confirmed",
            "Delivery " + facade.getDelivery().getDeliveryID() + " confirmed as Delivered.");

        Logger.getInstance().log("ShipmentController confirmDelivery: "
            + facade.getDelivery().getDeliveryID());
    }

    public void getShipmentStatus() {
        if (!hasActiveShipment("getShipmentStatus")) return;

        List<String> eventSummaries = new ArrayList<>();
        for (Event e : eventLog) {
            eventSummaries.add(e.getSummary());
        }

        String derived = statusStrategy.calculateStatus(eventSummaries);

        System.out.println("=== Shipment Status ===");
        System.out.println("ID       : " + processShipment.getShipmentID());
        System.out.println("Status   : " + processShipment.getStatus());
        System.out.println("Derived  : " + derived);
        System.out.println("Location : " + processShipment.getCurrentLocation());
        System.out.println("Events   : " + eventLog.size());
        if (facade != null) {
            System.out.println("Delivery : " + facade.getDelivery().getDeliveryStatus());
            System.out.println("Storage  : " + facade.getStorage().getStorageID()
                + " | Available: " + facade.getStorage().isAvailable()
                + " | Slots left: " + facade.getStorage().getAvailableSlots());
        }

        Logger.getInstance().log("ShipmentController getShipmentStatus derived: " + derived);
    }

    public void runFullProcess() {
        if (!hasFacade("runFullProcess")) return;

        Logger.getInstance().log("ShipmentController runFullProcess START.");

        facade.processShipment();
        facade.trackAndDeliver();
        facade.inspectAndConfirm();

        double cost    = roi.estimateCost(processShipment);
        double revenue = roi.estimateRevenue(facade.getDelivery());
        double roiVal  = roi.calculateROI(processShipment, facade.getDelivery());

        System.out.println("=== Full Process Complete ===");
        System.out.println("Shipment  : " + processShipment.getShipmentID());
        System.out.println("Status    : " + processShipment.getStatus());
        System.out.println("Delivery  : " + facade.getDelivery().getDeliveryStatus());
        System.out.println("Completed : " + facade.isProcessCompleted());
        System.out.println("Events    : " + eventLog.size());
        System.out.printf("Cost      : $%.2f%n", cost);
        System.out.printf("Revenue   : $%.2f%n", revenue);
        System.out.printf("ROI       : $%.2f%n", roiVal);

        recordEvent("Process Complete",
            "Full lifecycle finished. ROI: $" + String.format("%.2f", roiVal));

        Logger.getInstance().log("ShipmentController runFullProcess END.");
    }

    public List<Event> getEventLog() {
        return Collections.unmodifiableList(eventLog);
    }

    private void recordEvent(String type, String description) {
        if (type == null || type.trim().isEmpty()) return;
        if (description == null || description.trim().isEmpty()) return;

        Event event = Event.createEventNow(
            "CTRL-EVT-" + (eventLog.size() + 1),
            type,
            description
        );
        eventLog.add(event);
        Logger.getInstance().log("Event recorded: [" + type + "] " + description);
    }

    private boolean hasActiveShipment(String methodName) {
        if (processShipment == null) {
            Logger.getInstance().log("ShipmentController " + methodName
                + ": no active shipment. Call createAndStartShipment() first.");
            return false;
        }
        return true;
    }

    private boolean hasFacade(String methodName) {
        if (facade == null) {
            Logger.getInstance().log("ShipmentController " + methodName
                + ": no facade. Call createAndStartShipment() first.");
            return false;
        }
        return true;
    }

    public ShipmentFacade getFacade()          { return facade; }
    public Shipment       getProcessShipment() { return processShipment; }
    public StatusStrategy getStatusStrategy()  { return statusStrategy; }
    public ROI            getRoi()             { return roi; }

    public void setStatusStrategy(StatusStrategy strategy) {
        if (strategy == null) return;
        this.statusStrategy = strategy;
        Logger.getInstance().log("ShipmentController StatusStrategy changed to: "
            + strategy.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return "ShipmentController{activeShipment="
             + (processShipment != null ? processShipment.getShipmentID() : "none")
             + ", eventsLogged=" + eventLog.size()
             + ", strategy=" + statusStrategy.getClass().getSimpleName() + "}";
    }
}