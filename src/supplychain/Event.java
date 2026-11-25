package supplychain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Event {

    private final String eventID;
    private final String eventType;
    private final String eventDescription;
    private final LocalDateTime timestamp;

    private Event(String eventID, String eventType, String eventDescription, LocalDateTime timestamp) {
        if (isBlank(eventID)) {
            throw new IllegalArgumentException("Event ID cannot be empty.");
        }
        if (isBlank(eventType)) {
            throw new IllegalArgumentException("Event type cannot be empty.");
        }
        if (isBlank(eventDescription)) {
            throw new IllegalArgumentException("Event description cannot be empty.");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null.");
        }

        this.eventID = eventID.trim();
        this.eventType = eventType.trim();
        this.eventDescription = eventDescription.trim();
        this.timestamp = timestamp;
    }

    public static Event createEvent(String eventID, String eventType, String eventDescription, LocalDateTime timestamp) {
        return new Event(eventID, eventType, eventDescription, timestamp);
    }

    public static Event createEventNow(String eventID, String eventType, String eventDescription) {
        return new Event(eventID, eventType, eventDescription, LocalDateTime.now());
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSummary() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return "Event ID: " + eventID
                + " | Type: " + eventType
                + " | Description: " + eventDescription
                + " | Timestamp: " + timestamp.format(formatter);
    }

   
    public boolean isType(String type) {
        if (isBlank(type)) {
            return false;
        }
        return eventType.equalsIgnoreCase(type.trim());
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public String toString() {
        return getSummary();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Event)) {
            return false;
        }

        Event other = (Event) obj;
        return eventID.equals(other.eventID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID);
    }
}