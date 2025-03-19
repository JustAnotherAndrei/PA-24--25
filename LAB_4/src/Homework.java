import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Homework {
    public static void main(String[] args) {
        Runway r1 = new Runway("Pista 1");
        Runway r2 = new Runway("Pista 2");

        List<Runway> runways = Arrays.asList(r1, r2);
        Airport airport = new Airport("Traian Vuia",runways); //e inutil

        Flight f1 = new Flight("F1", LocalTime.of(10, 0), LocalTime.of(10, 30));
        Flight f2 = new Flight("F2", LocalTime.of(10, 15), LocalTime.of(10, 45));
        Flight f3 = new Flight("F3", LocalTime.of(10, 40), LocalTime.of(11, 10));

        Set<Flight> flights = new HashSet<>(Arrays.asList(f1, f2, f3));

        SchedulingProblem problem = new SchedulingProblem(airport, flights);
        problem.solve();
    }
}
