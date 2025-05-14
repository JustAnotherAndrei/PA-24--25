package graph;

import java.util.*;

public class GraphAnalyzer {
    private final int n;
    private final List<List<Integer>> adj;

    public GraphAnalyzer(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    public List<Set<Integer>> find2ConnectedComponents() {
        List<Set<Integer>> components = new ArrayList<>();
        int[] dfsNum = new int[n];
        int[] low = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dfsNum, -1);

        Stack<Integer> stack = new Stack<>();
        int[] time = {0};

        for (int u = 0; u < n; u++) {
            if (dfsNum[u] == -1)
                dfs(u, -1, dfsNum, low, parent, stack, time, components);
        }
        return components;
    }

    private void dfs(int u, int p, int[] dfsNum, int[] low, int[] parent, Stack<Integer> stack, int[] time, List<Set<Integer>> comps) {
        dfsNum[u] = low[u] = time[0]++;
        stack.push(u);

        for (int v : adj.get(u)) {
            if (v == p) continue;
            if (dfsNum[v] == -1) {
                parent[v] = u;
                dfs(v, u, dfsNum, low, parent, stack, time, comps);
                low[u] = Math.min(low[u], low[v]);

                if (low[v] >= dfsNum[u]) {
                    Set<Integer> comp = new HashSet<>();
                    int node;
                    do {
                        node = stack.pop();
                        comp.add(node);
                    } while (node != v);
                    comp.add(u);
                    if (comp.size() > 2) comps.add(comp);
                }
            } else {
                low[u] = Math.min(low[u], dfsNum[v]);
            }
        }
    }
}
