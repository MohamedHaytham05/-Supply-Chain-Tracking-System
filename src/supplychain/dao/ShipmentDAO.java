package supplychain.dao;

import java.sql.*;
import db.DBConnection;

public class ShipmentDAO {

    public boolean addShipment(
            String shipmentId,
            String status,
            String origin,
            String currentLocation,
            String storageId,
            String shelf,
            String deliveryId,
            int products,
            boolean registered,
            String shipmentDesc,
            String pickupInfo
    ) {

        String sql = "INSERT INTO shipments (" +
                "shipment_id, status, origin, current_location, " +
                "storage_id, shelf, delivery_id, products, " +
                "created, registered, shipmentDesc, pickupInfo" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shipmentId);
            ps.setString(2, status);
            ps.setString(3, origin);
            ps.setString(4, currentLocation);
            ps.setString(5, storageId);
            ps.setString(6, shelf);
            ps.setString(7, deliveryId);
            ps.setInt(8, products);
            ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            ps.setBoolean(10, registered);
            ps.setString(11, shipmentDesc);
            ps.setString(12, pickupInfo);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getAllShipments() {

        String sql = "SELECT * FROM shipments";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}