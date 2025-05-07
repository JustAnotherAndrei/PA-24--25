package service;

import model.City;

public class DistanceCalculator {

    public static double calculateDistance(City a, City b) {
        double R = 6371; // Radius of the Earth in km
        double latDistance = Math.toRadians(b.getLatitude() - a.getLatitude());
        double lonDistance = Math.toRadians(b.getLongitude() - a.getLongitude());

        double sinLat = Math.sin(latDistance / 2);
        double sinLon = Math.sin(lonDistance / 2);

        double aVal = sinLat * sinLat + Math.cos(Math.toRadians(a.getLatitude())) *
                Math.cos(Math.toRadians(b.getLatitude())) * sinLon * sinLon;

        double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));
        return R * c;
    }
}
