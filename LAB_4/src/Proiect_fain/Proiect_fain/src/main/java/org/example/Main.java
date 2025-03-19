package Proiect_fain.Proiect_fain.src.main.java.org.example;


import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // cream un array de locatii cu nume si tip
        Location[] locations = {
                new Location("BetaBase", Type.FRIENDLY),
                new Location("OmicronOutpost", Type.ENEMY),
                new Location("GammaVillage", Type.NEUTRAL),
                new Location("KappaCamp", Type.FRIENDLY),
                new Location("PhiFort", Type.ENEMY),
                new Location("ZetaZone", Type.FRIENDLY),
                new Location("IotaIsland", Type.NEUTRAL),
                new Location("SigmaSettlement", Type.ENEMY)
        };

        // colectam locațiile de tip FRIENDLY intr-un TreeSet
        TreeSet<Location> friendlySet = Arrays.stream(locations)
                .filter(loc -> loc.getType() == Type.FRIENDLY)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Friendly locations:");
        friendlySet.forEach(System.out::println);

        // colectam locatii de tip ENEMY intr-un LinkedList, sortate dupa tip si apoi dupa nume
        LinkedList<Location> enemyList = Arrays.stream(locations)
                .filter(loc -> loc.getType() == Type.ENEMY)
                .sorted(Comparator.comparing(Location::getType)
                        .thenComparing(Location::getName))
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("\nEnemy locations :");
        enemyList.forEach(System.out::println);
    }
}




