import java.util.ArrayList;
import java.util.List;

public class Runway {
    final private String name;
    final private List<Flight> assignedFlights;

    public Runway(String name) {
        this.name = name;
        this.assignedFlights = new ArrayList<>();
    }

    public boolean canAccommodate(Flight flight) {
        for (Flight f : assignedFlights) {
            if (f.conflictsWith(flight)) {
                return false;
            }
        }
        return true;
    }

    public void assignFlight(Flight flight) {
        assignedFlights.add(flight);
    }

    public String getName() {
        return name;
    }

    public List<Flight> getAssignedFlights() {
        return assignedFlights;
    }
}
