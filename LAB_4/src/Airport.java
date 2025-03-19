import java.util.List;

public class Airport {
    final private String name;
    final private List<Runway> runways;

    public Airport(String name, List<Runway> runways) {
        this.name = name;
        this.runways = runways;
    }

    public List<Runway> getRunways() {
        return runways;
    }
}

