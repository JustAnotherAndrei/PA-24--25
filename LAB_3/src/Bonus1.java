import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bonus1 {
    static class Graph {
        private final int n;                 // nr de noduri
        private final boolean[][] adjMatrix;
        public Graph(int n) { // creeaza un graf cu n noduri, dar fara muchii
            this.n = n;
            this.adjMatrix = new boolean[n][n];
        }

        public void randomize(double p, Random rand){ // p = probabilitatea de a avea o muchie intre doua noduri
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (rand.nextDouble() < p) {
                        adjMatrix[i][j] = true;
                        adjMatrix[j][i] = true;
                    }
                }
            }
        }

        public Graph complement() {
            Graph comp = new Graph(n);
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (!adjMatrix[i][j]) {
                        comp.adjMatrix[i][j] = true;
                        comp.adjMatrix[j][i] = true;
                    }
                }
            }
            return comp;
        }

        // OBS: pt n si k mari, backtracking-ul va fi costisitor, but for the sake of bonus...

        public boolean hasCliqueOfSizeAtLeastK(int k) {
            // initial, toate nodurile vor fi candidati pentru clica
            List<Integer> candidateVertices = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                candidateVertices.add(i);
            }

            // functie recursiva care incearca sa construiasca o clica
            return backtrackClique(new ArrayList<>(), candidateVertices, k);
        }

        private boolean backtrackClique(List<Integer> currentClique,
                                        List<Integer> candidates,
                                        int k) {
            if (currentClique.size() >= k) {
                return true;
            }

            // dacă nu mai am suficiente noduri în candidates pt a ajunge la marimea k, ma opresc
            if (currentClique.size() + candidates.size() < k) {
                return false;
            }

            for (int i = 0; i < candidates.size(); i++) {
                int v = candidates.get(i);

                // verific daca v este conectat la toate nodurile
                // din currentClique, pt a mentine prop de clica
                boolean canAdd = true;
                for (int c : currentClique) {
                    if (!adjMatrix[v][c]) {
                        canAdd = false;
                        break;
                    }
                }

                if (canAdd) {
                    currentClique.add(v);
                    List<Integer> newCandidates = new ArrayList<>();
                    for (int j = i + 1; j < candidates.size(); j++) {
                        int w = candidates.get(j);
                        // w trebuie sa fie conectat cu v si cu toti din clica
                        if (adjMatrix[v][w]) {
                            // verific și cu toți ceilalți din currentClique
                            boolean allConnected = true;
                            for (int c : currentClique) {
                                if (!adjMatrix[w][c]) {
                                    allConnected = false;
                                    break;
                                }
                            }
                            if (allConnected) {
                                newCandidates.add(w);
                            }
                        }
                    }

                    if (backtrackClique(currentClique, newCandidates, k)) {
                        return true;
                    }

                    // backtrack: il scot pe v din clica
                    currentClique.remove(currentClique.size() - 1);
                }
            }

            // daca am incercat toti candidatii si nu am gasit o clica de dimensiune >= k
            return false;
        }
    }

    public static void main(String[] args) {
        int[] testN = {3, 4, 6, 8, 10};
        int k = 4;
        double p = 0.45;
        int T = 3;

        Random rand = new Random();

        // 2. Parcurgem valorile de n
        for (int n : testN) {
            System.out.println("####################################");
            System.out.println("TEST pentru n = " + n);
            System.out.println("####################################");

            for (int t = 1; t <= T; t++) {
                Graph g = new Graph(n);
                g.randomize(p, rand);

                boolean hasClique = g.hasCliqueOfSizeAtLeastK(k);

                Graph gComplement = g.complement();
                boolean hasStableSet = gComplement.hasCliqueOfSizeAtLeastK(k);

                System.out.println("Test #" + t + " -> "
                        + "Are clica de dimensiune >= " + k + "? " + hasClique
                        + " | Are multime stabila >= " + k + "? " + hasStableSet);
            }
        }
    }
}
