package game;

public class Board {
    private final int size;
    private final char[][] cells;

    public Board(int size) {
        this.size = size;
        this.cells = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = '.';
            }
        }
    }

    public synchronized boolean place(int x, int y, char symbol) {
        if (isValid(x, y)) {
            cells[x][y] = symbol;
            return true;
        }
        return false;
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size && cells[x][y] == '.';
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.print(" ".repeat(i));
            for (int j = 0; j < size; j++) {
                System.out.print(cells[i][j] + " ");
            }
            System.out.println();
        }
    }
}
