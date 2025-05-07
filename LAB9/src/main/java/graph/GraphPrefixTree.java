// File: src/main/java/graph/GraphPrefixTree.java

package graph;

import org.graph4j.Graph;
import org.graph4j.builder.GraphBuilder;
import org.graph4j.EdgeFactory;
import org.graph4j.VertexFactory;

import java.util.*;

public class GraphPrefixTree {
    private final Graph graph;
    private final Map<String, Integer> nodeIds = new HashMap<>();
    private int nextId = 0;

    public GraphPrefixTree() {
        graph = new GraphBuilder()
                .directed()
                .build();
    }

    public void buildFromWords(Collection<String> words) {
        // Create a root node
        int rootId = addNode("ROOT");

        for (String word : words) {
            int parent = rootId;
            for (char c : word.toCharArray()) {
                String key = parent + ":" + c;
                int nodeId = nodeIds.computeIfAbsent(key, k -> addNode(String.valueOf(c)));
                graph.addEdge(EdgeFactory.create(parent, nodeId, String.valueOf(c)));
                parent = nodeId;
            }
        }
    }

    private int addNode(String label) {
        int id = nextId++;
        graph.addVertex(VertexFactory.create(id, label));
        return id;
    }

    public Graph getGraph() {
        return graph;
    }
}
