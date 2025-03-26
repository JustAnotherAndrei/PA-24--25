import java.util.List;

public class Bonus3 {
    public static void main(String[] args) {
        int numFlights = 15;
        int runwayCount = 25;

        List<Flight> flights = AirportScheduler.generateRandomFlights(numFlights);

        AirportScheduler.sortFlightsByStartTime(flights);

        System.out.println("Generated Flights:");
        for (Flight f : flights) {
            System.out.println(f);
        }
        System.out.println("\nTrying to schedule on " + runwayCount + " runways...");

        // Attempt to schedule the flights using the initial runway count.
        if (AirportScheduler.scheduleFlights(flights, runwayCount)) {
            System.out.println("\nEquitable schedule found with " + runwayCount + " runways:");
            AirportScheduler.printSchedule(AirportScheduler.getSolution());
        } else {
            System.out.println("\nNo equitable schedule possible with " + runwayCount + " runways.");
            // Increase runway count until an equitable schedule is found.
            int minRunways = runwayCount + 1;
            boolean found = false;
            while (!found) {
                System.out.println("Trying with " + minRunways + " runways...");
                if (AirportScheduler.scheduleFlights(flights, minRunways)) {
                    System.out.println("\nEquitable schedule found with " + minRunways + " runways:");
                    AirportScheduler.printSchedule(AirportScheduler.getSolution());
                    found = true;
                } else {
                    minRunways++;
                }
            }
        }
    }
}

