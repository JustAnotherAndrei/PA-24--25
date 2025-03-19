import java.time.LocalTime;

public class Flight {
    final private String id;
    final private LocalTime landingStart;
    final private LocalTime landingEnd;

    public Flight(String id, LocalTime landingStart, LocalTime landingEnd) {
        this.id = id;
        this.landingStart = landingStart;
        this.landingEnd = landingEnd;
    }

    public String getId() {
        return id;
    }

    public LocalTime getLandingStart() {
        return landingStart;
    }

    public LocalTime getLandingEnd() {
        return landingEnd;
    }

    public boolean conflictsWith(Flight other) {
        return !(this.landingEnd.isAfter(other.landingStart) || this.landingStart.isBefore(other.landingEnd));
    }
}

