import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConfigurationPanel extends JPanel {

    private DrawingPanel drawingPanel;
    private JTextField dotCountField;
    private JButton newGameButton;

    public ConfigurationPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("Number of Dots:");
        dotCountField = new JTextField(5);
        newGameButton = new JButton("New Game");

        add(label);
        add(dotCountField);
        add(newGameButton);

        // When the New Game button is clicked, try to read the number of dots and start a new game
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int count = Integer.parseInt(dotCountField.getText());
                    drawingPanel.newGame(count);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ConfigurationPanel.this, "Please enter a valid number.");
                }
            }
        });
    }
}
