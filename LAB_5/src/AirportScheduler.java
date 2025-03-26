import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class AirportScheduler {
    private static List<List<Flight>> solution = null;


    public static List<Flight> generateRandomFlights(int numFlights) {
        List<Flight> flights = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numFlights; i++) {
            int start = rand.nextInt(100);
            int duration = 5 + rand.nextInt(16);
            int end = start + duration;
            Aircraft ac = new Aircraft("Model-" + (1 + rand.nextInt(5)),
                    "TN-" + (100 + i),
                    "FlightCall-" + i,
                    20 + rand.nextDouble() * 30,
                    100 + rand.nextInt(200),
                    5000 + rand.nextDouble() * 5000,
                    1 + rand.nextDouble() * 4);
            flights.add(new Flight(i, start, end, ac));
        }
        return flights;
    }


    public static boolean scheduleFlights(List<Flight> flights, int runwayCount) {
        solution = new ArrayList<>();
        // Initialize each runway as an empty list.
        for (int i = 0; i < runwayCount; i++) {
            solution.add(new ArrayList<>());
        }
        // Start the backtracking process from the first flight.
        return backtrack(flights, 0, solution);
    }


    private static boolean backtrack(List<Flight> flights, int index, List<List<Flight>> runways) {
        // Base case: all flights are assigned.
        if (index == flights.size()) {
            return isEquitable(runways);
        }

        Flight currentFlight = flights.get(index);

        // Try to assign the current flight to each runway.
        for (List<Flight> runway : runways) {
            if (runway.isEmpty() || currentFlight.startTime >= runway.get(runway.size() - 1).endTime) {
                runway.add(currentFlight); // Place the flight on the runway.
                if (backtrack(flights, index + 1, runways)) {
                    return true;
                }
                runway.remove(runway.size() - 1); // Backtrack.
            }
        }
        return false; // No valid assignment
    }


    private static boolean isEquitable(List<List<Flight>> runways) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (List<Flight> runway : runways) {
            int count = runway.size();
            min = Math.min(min, count);
            max = Math.max(max, count);
        }
        return (max - min) <= 1;
    }


    public static void printSchedule(List<List<Flight>> runways) {
        int r = 1;
        for (List<Flight> runway : runways) {
            System.out.println("Runway " + r + " (" + runway.size() + " flights):");
            for (Flight f : runway) {
                System.out.println("  " + f);
            }
            r++;
        }
    }

    public static List<List<Flight>> getSolution() {
        return solution;
    }

    public static void sortFlightsByStartTime(List<Flight> flights) {
        Collections.sort(flights, Comparator.comparingInt(f -> f.startTime));
    }
}

