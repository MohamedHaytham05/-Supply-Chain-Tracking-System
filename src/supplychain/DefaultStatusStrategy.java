package supplychain;
import java.util.List;

public class DefaultStatusStrategy implements StatusStrategy {

    @Override
    public String calculateStatus(List<String> statuses) {

        if (statuses == null || statuses.isEmpty()) {
            return "Pending";
        }

        String lastStatus = statuses.get(statuses.size() - 1);

        if (isBlank(lastStatus)) {
            return "Unknown";
        }

        switch (lastStatus.trim().toUpperCase()) {

            case "SHIPPED":
                return "In Transit";

            case "DELIVERED":
                return "Delivered";

            case "CANCELLED":
                return "Cancelled";

            default:
                return "Processing";
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
