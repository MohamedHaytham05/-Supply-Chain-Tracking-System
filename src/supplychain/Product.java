package supplychain;

import java.time.LocalDate;
import java.util.Objects;

public class Product {

    private final String productID;
    private String description;
    private int quantity;
    private final LocalDate productionDate;
    private String assignedShipmentID;

    public Product(String productID, String description, int quantity, LocalDate productionDate) {
        if (isBlank(productID)) {
            throw new IllegalArgumentException("Product ID cannot be empty.");
        }
        if (isBlank(description)) {
            throw new IllegalArgumentException("Product description cannot be empty.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative.");
        }
        if (productionDate == null) {
            throw new IllegalArgumentException("Production date cannot be null.");
        }

        this.productID = productID.trim();
        this.description = description.trim();
        this.quantity = quantity;
        this.productionDate = productionDate;
        this.assignedShipmentID = null;
    }

    public String getProductID() {
        return productID;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public String getAssignedShipmentID() {
        return assignedShipmentID;
    }

    public void updateQuantity(int amount) {
        int newQuantity = quantity + amount;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot become negative.");
        }

        quantity = newQuantity;
    }

    public void assignToShipment(String shipmentID) {
        if (isBlank(shipmentID)) {
            throw new IllegalArgumentException("Shipment ID cannot be empty.");
        }

        assignedShipmentID = shipmentID.trim();
    }

    public boolean isAssignedToShipment() {
        return assignedShipmentID != null;
    }

    public String getProductDetails() {
        return "Product ID: " + productID
                + " | Description: " + description
                + " | Quantity: " + quantity
                + " | Production Date: " + productionDate
                + " | Assigned Shipment ID: "
                + (assignedShipmentID == null ? "Not assigned" : assignedShipmentID);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public String toString() {
        return getProductDetails();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Product)) {
            return false;
        }

        Product other = (Product) obj;
        return productID.equals(other.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID);
    }
}