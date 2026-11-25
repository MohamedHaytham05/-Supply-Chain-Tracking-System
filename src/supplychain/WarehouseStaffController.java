package supplychain;

import java.util.ArrayList;
import java.util.List;

public class WarehouseStaffController {

    public String[] getDashboardStats() {
        return new String[]{"SHP-001", "Available", "1 done", "2 of 4"};
    }

    public void recordArrival(String shipmentID) {
        System.out.println("[LOG] Arrival Recorded: " + shipmentID);
    }

    public void arrangeStorage(String shipID, String storeID, String shelf) {
        System.out.println("[LOG] Storage Set: " + shipID + " in " + storeID + " Shelf " + shelf);
    }

    public List<String> getRecentActivity() {
        List<String> activities = new ArrayList<>();
        activities.add("Shipment SHP-001 arrived (10:00 AM)");
        activities.add("Stored in STR-001 Shelf A1 (10:30 AM)");
        activities.add("Inspection started (11:00 AM)");
        return activities;
    }
}