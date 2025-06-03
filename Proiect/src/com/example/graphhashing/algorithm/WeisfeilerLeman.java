// File: src/com/example/graphhashing/algorithm/WeisfeilerLeman.java
package com.example.graphhashing.algorithm;

import com.example.graphhashing.model.Graph;
import com.example.graphhashing.model.Node;

import java.util.*;

/**
 * Implementarea algoritmului Weisfeiler-Leman (WL) pentru hashing de grafuri.
 * Se face color-refinement iterativ până la stabilizare și apoi se construiește
 * un șir canonic ("hash") pe baza histogramelor de culori.
 */
public class WeisfeilerLeman {

    /**
     * Calculează hash-ul WL pentru graful dat și returnează un șir canonic.
     *
     * @param graph graful de procesat
     * @return șirul hash, de forma "1:count1;2:count2;..."
     */
    public static String computeHash(Graph graph) {
        // 1) Inițial: toate nodurile au culoarea 1
        Map<Node, Integer> colors = new HashMap<>();
        for (Node node : graph.getNodes()) {
            colors.put(node, 1);
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            Map<String, Integer> signatureToColor = new HashMap<>(); // semnătură -> nouă culoare
            Map<Node, Integer> newColors = new HashMap<>();
            int nextColorId = 1;

            // 2) Pentru fiecare nod, construim semnătura
            for (Node node : graph.getNodes()) {
                List<Integer> neighborColors = new ArrayList<>();
                for (Node nbr : node.getNeighbors()) {
                    neighborColors.add(colors.get(nbr));
                }
                Collections.sort(neighborColors);

                // semnătură = "culoareVeche|[listaCulorilorVecinilor]"
                StringBuilder sigBuilder = new StringBuilder();
                sigBuilder.append(colors.get(node)).append("|");
                sigBuilder.append(neighborColors.toString());
                String signature = sigBuilder.toString();

                // atribuim (sau reutilizăm) o culoare nouă pentru această semnătură
                if (!signatureToColor.containsKey(signature)) {
                    signatureToColor.put(signature, nextColorId++);
                }
                newColors.put(node, signatureToColor.get(signature));
            }

            // 3) Verificăm dacă vreo culoare s-a schimbat
            for (Node node : graph.getNodes()) {
                if (!newColors.get(node).equals(colors.get(node))) {
                    changed = true;
                    break;
                }
            }

            // 4) Pregătim pentru următoarea iterație
            colors = newColors;
        }

        // 5) Construim histograma finală: culoare -> număr de noduri
        Map<Integer, Integer> histogram = new TreeMap<>();
        for (Integer col : colors.values()) {
            histogram.put(col, histogram.getOrDefault(col, 0) + 1);
        }

        // 6) Creăm șirul canonic: "1:count1;2:count2;..."
        StringBuilder hashBuilder = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : histogram.entrySet()) {
            hashBuilder.append(entry.getKey())
                    .append(":")
                    .append(entry.getValue())
                    .append(";");
        }
        if (hashBuilder.length() > 0) {
            hashBuilder.setLength(hashBuilder.length() - 1); // eliminăm ultimul ';'
        }
        return hashBuilder.toString();
    }

    /**
     * Compară două grafuri prin hash-ul WL: dacă hash-urile sunt identice,
     * considerăm (pentru acest proiect) că sunt izomorfe. Dacă diferă, sigur nu sunt.
     *
     * @param g1 primul graf
     * @param g2 al doilea graf
     * @return true dacă hash-urile sunt egale, false altfel
     */
    public static boolean areIsomorphic(Graph g1, Graph g2) {
        String h1 = computeHash(g1);
        String h2 = computeHash(g2);
        return h1.equals(h2);
    }
}
