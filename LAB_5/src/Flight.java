public class Flight {
    public int id;
    public int startTime; // Landing start time.
    public int endTime;   // Landing end time.
    public Aircraft aircraft; // The aircraft operating this flight.

    // Constructor to initialize a Flight instance.
    public Flight(int id, int startTime, int endTime, Aircraft aircraft) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.aircraft = aircraft;
    }

    // Returns a string representation of the Flight.
    @Override
    public String toString() {
        return "Flight " + id + " [" + startTime + ", " + endTime + "] " + aircraft;
    }
}

