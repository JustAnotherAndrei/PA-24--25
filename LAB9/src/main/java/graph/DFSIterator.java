// File: src/main/java/graph/DFSIterator.java

package graph;

import org.graph4j.Graph;
import org.graph4j.Vertex;
import org.graph4j.Edge;

import java.util.*;

public class DFSIterator implements Iterator<Vertex> {
    private final Deque<Vertex> stack = new ArrayDeque<>();
    private final Set<Vertex> visited = new HashSet<>();

    public DFSIterator(Graph graph, Vertex start) {
        stack.push(start);
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty() && visited.contains(stack.peek())) {
            stack.pop();
        }
        return !stack.isEmpty();
    }

    @Override
    public Vertex next() {
        Vertex v = stack.pop();
        visited.add(v);
        for (Edge e : v.graph().outEdges(v)) {
            Vertex tgt = e.target();
            if (!visited.contains(tgt)) {
                stack.push(tgt);
            }
        }
        return v;
    }
}
