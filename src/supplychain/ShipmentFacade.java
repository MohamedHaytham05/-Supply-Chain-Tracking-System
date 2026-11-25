package supplychain;

import java.util.Date;

public class ShipmentFacade {

    private final Shipment shipment;
    private final Storage  storage;
    private final Delivery delivery;
    private boolean        processCompleted;

    public ShipmentFacade(Shipment shipment, Storage storage, Delivery delivery) {
        if (shipment == null)
            throw new IllegalArgumentException("ShipmentFacade: shipment cannot be null.");
        if (storage == null)
            throw new IllegalArgumentException("ShipmentFacade: storage cannot be null.");
        if (delivery == null)
            throw new IllegalArgumentException("ShipmentFacade: delivery cannot be null.");

        this.shipment         = shipment;
        this.storage          = storage;
        this.delivery         = delivery;
        this.processCompleted = false;

        Logger.getInstance().log("ShipmentFacade initialised — Shipment: "
            + shipment.getShipmentID()
            + " | Storage: "  + storage.getStorageID()
            + " | Delivery: " + delivery.getDeliveryID());
    }

    public void processShipment() {
        Logger.getInstance().log("Facade processShipment START: " + shipment.getShipmentID());

        shipment.updateStatus("In Transit");

        boolean placed = storage.addShipment(shipment);

        if (placed) {
            shipment.updateStatus("Storage Arranged");
            Logger.getInstance().log("Shipment stored at " + storage.getStorageID()
                + " | Shelf: "      + storage.getShelfNumber()
                + " | Slots left: " + storage.getAvailableSlots());
        } else {
            shipment.updateStatus("Storage Failed");
            Logger.getInstance().log("Storage " + storage.getStorageID()
                + " is at full capacity (" + storage.getCapacity() + " slots)");
        }

        Logger.getInstance().log("Facade processShipment END: " + shipment.getStatus());
    }

    public void trackAndDeliver() {
        Logger.getInstance().log("Facade trackAndDeliver START: " + shipment.getShipmentID());

        shipment.updateStatus("In Transit");
        Logger.getInstance().log("Dispatched from Storage " + storage.getStorageID()
            + " | Location: " + storage.getLocation());

        delivery.setDeliveryStatus("In Transit");
        storage.removeShipment(shipment);

        Logger.getInstance().log("Facade trackAndDeliver END: " + shipment.getStatus());
    }

    public void inspectAndConfirm() {
        Logger.getInstance().log("Facade inspectAndConfirm START: " + shipment.getShipmentID());

        Examination finalExam = new Examination(
            "EXAM-FINAL-" + shipment.getShipmentID(),
            "Final inspection before delivery confirmation",
            "Passed",
            new Date()
        );

        finalExam.linkShipment(shipment);
        finalExam.showResults();

        if (finalExam.isPassed()) {
            shipment.updateStatus("Delivered");
            delivery.setDeliveryStatus("Delivered");
            delivery.checkStatus();
            processCompleted = true;
            Logger.getInstance().log("Passed inspection — ExamID: " + finalExam.getInspectionID()
                + " | DeliveryID: " + delivery.getDeliveryID());
        } else {
            shipment.updateStatus("Inspection Failed");
            delivery.setDeliveryStatus("On Hold");
            Logger.getInstance().log("Failed inspection — ExamID: " + finalExam.getInspectionID());
        }

        Logger.getInstance().log("Facade inspectAndConfirm END: " + shipment.getStatus());
    }

    public boolean isProcessCompleted() { return processCompleted; }
    public Shipment getShipment()       { return shipment; }
    public Storage  getStorage()        { return storage; }
    public Delivery getDelivery()       { return delivery; }

    @Override
    public String toString() {
        return "ShipmentFacade{shipment=" + shipment.getShipmentID()
             + ", storage="   + storage.getStorageID()
             + ", delivery="  + delivery.getDeliveryID()
             + ", completed=" + processCompleted + "}";
    }
}