import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A trivial Hex AI that chooses a random valid move among all empty cells.
 *
 * The AI plays as RED by convention. Whenever nextMove() is invoked, it
 * scans the current board for empty cells, picks one uniformly at random,
 * and returns its (row, col).
 */
public class AI {
    private final Random rand = new Random();

    /**
     * Given the current game board, pick one random empty cell.
     *
     * @param board a 2D array of size N×N, where 0=empty, 1=BLUE, 2=RED.
     * @return an int[2] = {row, col} of chosen move, or null if board is full.
     */
    public int[] nextMove(int[][] board) {
        int n = board.length;
        List<int[]> emptyCells = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (board[r][c] == 0) {
                    emptyCells.add(new int[]{r, c});
                }
            }
        }
        if (emptyCells.isEmpty()) {
            return null;
        }
        return emptyCells.get(rand.nextInt(emptyCells.size()));
    }
}
