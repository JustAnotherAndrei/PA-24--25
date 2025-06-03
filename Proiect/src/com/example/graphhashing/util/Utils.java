// File: src/com/example/graphhashing/util/Utils.java
package com.example.graphhashing.util;

import com.example.graphhashing.model.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Funcții utilitare, de exemplu pentru încărcarea unui
 * graf dintr-un fișier text (lista de muchii).
 *
 * Formatul fișierului: fiecare linie e "u v" (două label-uri separate prin spațiu) → muchie neorientată.
 * Liniile goale sau cele care încep cu '#' sunt ignorate.
 */
public class Utils {

    /**
     * Încarcă un graf dintr-un fișier de tip edge-list.
     *
     * @param filePath  calea spre fișier
     * @param graphName numele dorit al grafului
     * @return obiect Graph construit
     * @throws IOException dacă fișierul nu există sau are linii invalide
     */
    public static Graph loadGraphFromEdgeList(String filePath, String graphName) throws IOException {
        Graph graph = new Graph(graphName);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] tokens = line.split("\\s+");
                if (tokens.length != 2) {
                    throw new IOException("Linie invalidă în edge-list: " + line);
                }
                String u = tokens[0];
                String v = tokens[1];
                graph.addEdge(u, v);
            }
        }
        return graph;
    }
}
