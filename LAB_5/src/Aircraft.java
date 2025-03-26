public class Aircraft {
    public String model;
    public String tailNumber;
    public String callSign; // e.g., flight name
    public double wingSpan;
    public int passengerCapacity;
    public double maxPayload;
    public double batteryLife;


    public Aircraft(String model, String tailNumber, String callSign, double wingSpan,
                    int passengerCapacity, double maxPayload, double batteryLife) {
        this.model = model;
        this.tailNumber = tailNumber;
        this.callSign = callSign;
        this.wingSpan = wingSpan;
        this.passengerCapacity = passengerCapacity;
        this.maxPayload = maxPayload;
        this.batteryLife = batteryLife;
    }


    @Override
    public String toString() {
        return callSign + "(" + tailNumber + ")";
    }
}
