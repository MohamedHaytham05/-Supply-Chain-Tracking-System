package supplychain;
import java.util.Date;
import java.util.List;
public class Delivery {

    private String deliveryID;
    private Date deliveryDate;
    private String deliveryStatus;
    private StatusStrategy strategy;

    public Delivery(String deliveryID) {
        if (deliveryID == null || deliveryID.trim().isEmpty()) {
            throw new IllegalArgumentException("Delivery ID cannot be empty.");
        }

        this.deliveryID = deliveryID.trim();
        this.deliveryDate = new Date();
        this.deliveryStatus = "Created";
    }

    public void checkStatus() {
        System.out.println("Delivery Status: " + deliveryStatus);
    }

    public boolean isDelivered() {
        return "Delivered".equalsIgnoreCase(deliveryStatus);
    }

    public void showDeliveryDetails() {
        System.out.println("Delivery ID: " + deliveryID);
        System.out.println("Date: " + deliveryDate);
        System.out.println("Status: " + deliveryStatus);
    }

    public void setStrategy(StatusStrategy strategy) {
        this.strategy = strategy;
    }

    public String checkStatusEvents(List<Event> events) {
        if (events == null) {
            return "No events available.";
        }
        return "Total Events: " + events.size();
    }

    public String getDeliveryID() {
        return deliveryID;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public StatusStrategy getStrategy() {
        return strategy;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
