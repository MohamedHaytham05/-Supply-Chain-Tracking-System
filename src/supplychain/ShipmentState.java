package supplychain;

public interface ShipmentState {
    void updateStatus(Shipment shipment);
    void nextState(Shipment shipment);
    String getStatus();
}

class RegisteredState implements ShipmentState {

    private String current;

    public RegisteredState() {
        this.current = "Registered";
    }

    @Override
    public void updateStatus(Shipment shipment) {
        shipment.updateStatus(current);
        Logger.getInstance().log("State: Shipment "
            + shipment.getShipmentID() + " is " + current);
    }

    @Override
    public void nextState(Shipment shipment) {
        shipment.updateStatus("Arrived at Warehouse");
        System.out.println("Status Updated: Shipment "
            + shipment.getShipmentID() + " has Arrived at Warehouse.");
        Logger.getInstance().log("State transition: Registered → Arrived | "
            + shipment.getShipmentID());
    }

    @Override
    public String getStatus() { return current; }
    public void   setCurrent(String current) { this.current = current; }
    public String getCurrent()               { return current; }
}

class ArrivedState implements ShipmentState {

    private String current;

    public ArrivedState() {
        this.current = "Arrived at Warehouse";
    }

    @Override
    public void updateStatus(Shipment shipment) {
        shipment.updateStatus(current);
        Logger.getInstance().log("State: Shipment "
            + shipment.getShipmentID() + " has " + current);
    }

    @Override
    public void nextState(Shipment shipment) {
        shipment.updateStatus("Storage Arranged");
        System.out.println("Status Updated: Shipment "
            + shipment.getShipmentID() + " is Storage Arranged.");
        Logger.getInstance().log("State transition: Arrived → Storage Arranged | "
            + shipment.getShipmentID());
    }

    @Override
    public String getStatus() { return current; }
    public void   setCurrent(String current) { this.current = current; }
    public String getCurrent()               { return current; }
}

class StorageArrangedState implements ShipmentState {

    private String current;

    public StorageArrangedState() {
        this.current = "Storage Arranged";
    }

    @Override
    public void updateStatus(Shipment shipment) {
        shipment.updateStatus(current);
        Logger.getInstance().log("State: Shipment "
            + shipment.getShipmentID() + " is " + current);
    }

    @Override
    public void nextState(Shipment shipment) {
        shipment.updateStatus("In-Transit to Destination");
        System.out.println("Status Updated: Shipment "
            + shipment.getShipmentID() + " is now In-Transit.");
        Logger.getInstance().log("State transition: Storage Arranged → In-Transit | "
            + shipment.getShipmentID());
    }

    @Override
    public String getStatus() { return current; }
    public void   setCurrent(String current) { this.current = current; }
    public String getCurrent()               { return current; }
}

class InTransitState implements ShipmentState {

    private String current;

    public InTransitState() {
        this.current = "In-Transit to Destination";
    }

    @Override
    public void updateStatus(Shipment shipment) {
        shipment.updateStatus(current);
        Logger.getInstance().log("State: Shipment "
            + shipment.getShipmentID() + " is " + current);
    }

    @Override
    public void nextState(Shipment shipment) {
        shipment.updateStatus("Delivered");
        System.out.println("Status Updated: Shipment "
            + shipment.getShipmentID() + " has been Delivered.");
        Logger.getInstance().log("State transition: In-Transit → Delivered | "
            + shipment.getShipmentID());
    }

    @Override
    public String getStatus() { return current; }
    public void   setCurrent(String current) { this.current = current; }
    public String getCurrent()               { return current; }
}

class DeliveredState implements ShipmentState {

    private String current;

    public DeliveredState() {
        this.current = "Delivered";
    }

    @Override
    public void updateStatus(Shipment shipment) {
        shipment.updateStatus(current);
        Logger.getInstance().log("State: Shipment "
            + shipment.getShipmentID() + " is " + current);
    }

    @Override
    public void nextState(Shipment shipment) {
        System.out.println("Process Finished: Shipment "
            + shipment.getShipmentID() + " is already Delivered.");
        Logger.getInstance().log("State transition attempted on final state: Delivered | "
            + shipment.getShipmentID());
    }

    @Override
    public String getStatus() { return current; }
    public void   setCurrent(String current) { this.current = current; }
    public String getCurrent()               { return current; }
}