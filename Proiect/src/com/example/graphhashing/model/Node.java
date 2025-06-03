// File: src/com/example/graphhashing/model/Node.java
package com.example.graphhashing.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Reprezintă un nod (vertex) într-un graf:
 * - label: un șir de caractere unic
 * - neighbors: set de noduri adiacente
 */
public class Node {
    private final String label;
    private final Set<Node> neighbors;

    public Node(String label) {
        this.label = label;
        this.neighbors = new HashSet<>();
    }

    public String getLabel() {
        return label;
    }

    public Set<Node> getNeighbors() {
        return neighbors;
    }

    // Adaugă nodul other în lista de vecini
    public void addNeighbor(Node other) {
        neighbors.add(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;
        return Objects.equals(label, node.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return label;
    }
}
