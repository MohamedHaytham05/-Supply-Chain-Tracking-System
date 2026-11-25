package supplychain;
import java.util.List;

public interface StatusStrategy {

    String calculateStatus(List<String> statuses);
}