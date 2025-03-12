public class Compulsory {


    public interface PassengerCapable {
        int getPassengerCapacity();
    }

    public interface CargoCapable {
        double getCargoCapacity();
    }

    public static abstract class Aircraft implements Comparable<Aircraft> {
        private String name;
        private String model;
        private String tailNumber;

        public Aircraft(String name, String model, String tailNumber) {
            this.name = name;
            this.model = model;
            this.tailNumber = tailNumber;
        }

        public String getName() {
            return name;
        }

        public String getModel() {
            return model;
        }

        public String getTailNumber() {
            return tailNumber;
        }

        @Override
        public int compareTo(Aircraft other) {
            return this.name.compareTo(other.name);
        }

        @Override
        public String toString() {
            return String.format("%s (%s) - TailNumber: %s", name, model, tailNumber);
        }
    }

    public static class Airliner extends Aircraft implements PassengerCapable {
        private int passengerCapacity;

        public Airliner(String name, String model, String tailNumber, int passengerCapacity) {
            super(name, model, tailNumber);
            this.passengerCapacity = passengerCapacity;
        }

        @Override
        public int getPassengerCapacity() {
            return passengerCapacity;
        }
    }


    public static class Freighter extends Aircraft implements CargoCapable {
        private double cargoCapacity;

        public Freighter(String name, String model, String tailNumber, double cargoCapacity) {
            super(name, model, tailNumber);
            this.cargoCapacity = cargoCapacity;
        }

        @Override
        public double getCargoCapacity() {
            return cargoCapacity;
        }
    }

    public static class Drone extends Aircraft implements CargoCapable {
        private double cargoCapacity;
        private double batteryLife; // proprietate specifica dronelor

        public Drone(String name, String model, String tailNumber, double cargoCapacity, double batteryLife) {
            super(name, model, tailNumber);
            this.cargoCapacity = cargoCapacity;
            this.batteryLife = batteryLife;
        }

        @Override
        public double getCargoCapacity() {
            return cargoCapacity;
        }

        public double getBatteryLife() {
            return batteryLife;
        }
    }

    public static void main(String[] args) {
        // cream cateva obiecte de tip Airliner, Freighter si Drone
        Airliner airbus = new Airliner("BlueSky", "Airbus A320", "AB-123", 180);
        Freighter boeingCargo = new Freighter("CargoMaster", "Boeing 747F", "BC-747", 68000.0);
        Drone deliveryDrone = new Drone("DroneX", "DJI Matrice", "DR-001", 5.0, 2.5);

        // Afișăm obiectele create
        System.out.println("Aeronave create:");
        System.out.println(airbus);
        System.out.println(boeingCargo);
        System.out.println(deliveryDrone);

        // Construim un array cu toate aeronavele care pot transporta marfă
        // (CargoCapable). Airliner NU implementează CargoCapable, deci nu îl includem.
        CargoCapable[] cargoCapableAircraft = new CargoCapable[] {
                boeingCargo,
                deliveryDrone
        };

        // Afișăm array-ul de CargoCapable
        System.out.println("\nAeronave care pot transporta marfa:");
        for (CargoCapable c : cargoCapableAircraft) {
            // Observă că fiecare element este de tip Aircraft și CargoCapable
            // însă pentru afișare apelăm toString() din clasa Aircraft.
            System.out.println(c + " - Cargo Capacity: " + c.getCargoCapacity());
        }
    }
}
