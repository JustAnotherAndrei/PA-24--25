import java.util.HashSet;
import java.util.Set;

public class SchedulingProblem {
    final private Airport airport;
    final private Set<Flight> flights;

    public SchedulingProblem(Airport airport, Set<Flight> flights) {
        this.airport = airport;
        this.flights = flights;
    }

    public void solve() {
        for (Flight flight : flights) {
            for (Runway runway : airport.getRunways()) {
                if (runway.canAccommodate(flight)) {
                    runway.assignFlight(flight);
                    System.out.println("Flight " + flight.getId() + " assigned to runway " + runway.getName());
                    break;
                }
            }
        }
    }
}
