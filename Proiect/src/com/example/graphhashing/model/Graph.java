// File: src/com/example/graphhashing/model/Graph.java
package com.example.graphhashing.model;

import java.util.*;

/**
 * Reprezintă un graf neorientat folosind listă de adiacență.
 * - nodes: mapare din label -> Node
 * - edges: set de Edge (fiecare muchie stocată o singură dată)
 */
public class Graph {
    private String name;                     // Nume “uman” al grafului, de ex. "G1"
    private final Map<String, Node> nodes;   // label -> Node
    private final Set<Edge> edges;           // setul de muchii

    public Graph(String name) {
        this.name = name;
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }

    // Getter / Setter pentru nume
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adaugă un nod cu label-ul dat; dacă există deja, returnează nodul existent.
     */
    public Node addNode(String label) {
        return nodes.computeIfAbsent(label, lbl -> new Node(lbl));
    }

    /**
     * Adaugă o muchie neorientată între label-urile u și v.
     * Se creează nodurile automat dacă nu există.
     */
    public void addEdge(String labelU, String labelV) {
        Node u = addNode(labelU);
        Node v = addNode(labelV);
        Edge e = new Edge(u, v);
        if (!edges.contains(e)) {
            edges.add(e);
            u.addNeighbor(v);
            v.addNeighbor(u);
        }
    }

    /**
     * Returnează colecția neschemăuită de noduri.
     */
    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    /**
     * Returnează setul neschemăuit de muchii.
     */
    public Set<Edge> getEdges() {
        return Collections.unmodifiableSet(edges);
    }

    /**
     * Găsește un nod după label (sau returnează null dacă nu există).
     */
    public Node getNode(String label) {
        return nodes.get(label);
    }

    public int nodeCount() {
        return nodes.size();
    }

    public int edgeCount() {
        return edges.size();
    }

    @Override
    public String toString() {
        return String.format("Graph{name='%s', |V|=%d, |E|=%d}", name, nodeCount(), edgeCount());
    }
}
