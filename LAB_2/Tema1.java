import java.util.Random;

public class Tema1 {
    private final int n, k;
    private final int[][] adjacencyMatrix;
    private int[] degrees;
    private int edgeCount = 0;
    private final double EDGE_PROBABILITY = 0.3; // Probabilitate pentru muchii aditionale

    public Tema1(int n, int k) {
        this.n = n;
        this.k = k;
        this.adjacencyMatrix = new int[n][n];
        this.degrees = new int[n];
    }

    // Metoda care construieste graful
    public void generateGraph() {
        Random rand = new Random();

        // 1. Cream o clica de dimensiune K, unde clica = subgraf complet al unui graf
        if (k <= n) {
            for (int i = 0; i < k; i++) {
                for (int j = i + 1; j < k; j++) {
                    adjacencyMatrix[i][j] = 1;
                    adjacencyMatrix[j][i] = 1;
                }
            }
        }

        // 2. Creăm o multime stabila de dimensiune K (nodurile [n-K, ..., n-1])
        if (k <= n) {
            int startStable = n - k;
            for (int i = startStable; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    adjacencyMatrix[i][j] = 0;
                    adjacencyMatrix[j][i] = 0;
                }
            }
        }

        // 3. adaugam muchii random
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adjacencyMatrix[i][j] == 0) {
                    if (rand.nextDouble() < EDGE_PROBABILITY) {
                        adjacencyMatrix[i][j] = 1;
                        adjacencyMatrix[j][i] = 1;
                    }
                }
            }
        }

        // 4. Calculăm gradul fiecărui nod
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    degrees[i]++;
                }
            }
        }

        // 5. Calculăm numărul de muchii
        for (int i = 0; i < n; i++) {
            edgeCount += degrees[i];
        }
        edgeCount /= 2; // Deoarece fiecare muchie este numărată de două ori
    }

    // Afiseaza info despre graf
    public void displayGraphInfo() {
        int maxDegree = 0, minDegree = n;
        int degreeSum = 0;

        for (int i = 0; i < n; i++) {
            if (degrees[i] > maxDegree) maxDegree = degrees[i];
            if (degrees[i] < minDegree) minDegree = degrees[i];
            degreeSum += degrees[i];
        }

        // Afișare
        System.out.println("=== Informatii despre graf ===");
        System.out.println("Numar de noduri (n): " + n);
        System.out.println("Numar de muchii (m): " + edgeCount);
        System.out.println("Grad maxim (Δ): " + maxDegree);
        System.out.println("Grad minim (δ): " + minDegree);
        System.out.println("Suma gradelor: " + degreeSum);
        System.out.println("Verificare 2*m = " + (2 * edgeCount));

        if (degreeSum == 2 * edgeCount) {
            System.out.println("✔ Suma gradelor e corecta (2*m).");
        } else {
            System.out.println("⚠ EROARE: Suma gradelor nu corespunde cu 2*m!");
        }
    }

    // metoda pt a afisa matricea intr-un mod compact
    public void displayAdjacencyMatrix() {
        System.out.println("= Matricea de adiacenta =");

        if (n <= 20) {
            // Dacă n e suficient de mic, afisam întreaga matrice
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(adjacencyMatrix[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            // Dacă n este mare, afișăm doar colțul de sus (primele 20 de rânduri/coloane)
            System.out.println("(Afisez doar primele 20 de linii si coloane)");
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    System.out.print(adjacencyMatrix[i][j] + " ");
                }
                System.out.println("...");
            }
            System.out.println("...");
        }
    }

    public static void main(String[] args) {
        // Verificăm argumentele
        if (args.length < 2) {
            System.out.println("Utilizare: java GraphGenerator <n> <k>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        // initializam generatorul de grafuri si apoi generam graful
        Tema1 graph = new Tema1(n, k);
        long startTime = System.currentTimeMillis();
        graph.generateGraph();
        long endTime = System.currentTimeMillis();

        // afisam info despre graf
        graph.displayGraphInfo();
        graph.displayAdjacencyMatrix();

        // Dacă n este mare, afisam timpul de executie
        if (n > 30_000) {
            System.out.println("\n⏱ Timp de executie: " + (endTime - startTime) + " ms");
        }
    }
}
