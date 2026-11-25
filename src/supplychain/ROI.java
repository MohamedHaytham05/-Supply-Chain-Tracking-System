package supplychain;

public class ROI {

    public double calculateROI(Shipment s, Delivery d) {
        return estimateRevenue(d) - estimateCost(s);
    }

    public double estimateCost(Shipment s) {
        if (s == null) {
            throw new IllegalArgumentException("Shipment cannot be null.");
        }
        return 100.0;
    }

    public double estimateRevenue(Delivery d) {
        if (d == null) {
            throw new IllegalArgumentException("Delivery cannot be null.");
        }

        return d.isDelivered() ? 200.0 : 50.0;
    }
}
