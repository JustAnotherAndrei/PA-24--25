import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private ConfigurationPanel configPanel;
    private DrawingPanel drawingPanel;
    private ControlPanel controlPanel;

    public GameFrame() {
        super("Dot Connection Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the drawing panel (center)
        drawingPanel = new DrawingPanel();

        // Create the configuration panel (top) and pass the drawing panel to it
        configPanel = new ConfigurationPanel(drawingPanel);

        // Create the control panel (bottom)
        controlPanel = new ControlPanel();

        // Add panels to the frame
        add(configPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // center on screen
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ensure that Swing components are created and updated on the EDT
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameFrame();
            }
        });
    }
}
