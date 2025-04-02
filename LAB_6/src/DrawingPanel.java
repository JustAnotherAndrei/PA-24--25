import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel {

    private ArrayList<Dot> dots;
    private Random rand;

    public DrawingPanel() {
        dots = new ArrayList<>();
        rand = new Random();
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
    }

    /**
     * Starts a new game by generating a specified number of dots.
     * @param dotCount the number of dots to generate.
     */
    public void newGame(int dotCount) {
        dots.clear();
        // Generate random dots within the panel bounds.
        // Note: getWidth() and getHeight() return the current size.
        for (int i = 0; i < dotCount; i++) {
            int x = rand.nextInt(Math.max(getWidth() - 20, 1)) + 10; // leave a margin of 10
            int y = rand.nextInt(Math.max(getHeight() - 20, 1)) + 10;
            dots.add(new Dot(x, y));
        }
        repaint(); // Request a redraw of the panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw each dot as a small filled circle
        g.setColor(Color.BLACK);
        for (Dot dot : dots) {
            int radius = 5; // radius for the dot
            g.fillOval(dot.getX() - radius, dot.getY() - radius, 2 * radius, 2 * radius);
        }
    }
}
