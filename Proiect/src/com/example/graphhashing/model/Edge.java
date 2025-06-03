// File: src/com/example/graphhashing/model/Edge.java
package com.example.graphhashing.model;

import java.util.Objects;

/**
 * Reprezintă o muchie neorientată între două noduri.
 * Pentru a evita dublurile, ordonăm nodurile după label (lexicografic).
 */
public class Edge {
    private final Node u;
    private final Node v;

    public Edge(Node u, Node v) {
        // Impunem un ordin consistent: u.label <= v.label
        if (u.getLabel().compareTo(v.getLabel()) <= 0) {
            this.u = u;
            this.v = v;
        } else {
            this.u = v;
            this.v = u;
        }
    }

    public Node getU() {
        return u;
    }

    public Node getV() {
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Objects.equals(u, edge.u) && Objects.equals(v, edge.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v);
    }

    @Override
    public String toString() {
        return String.format("(%s - %s)", u.getLabel(), v.getLabel());
    }
}

