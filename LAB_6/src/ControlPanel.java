import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControlPanel extends JPanel {

    private JButton loadButton;
    private JButton saveButton;
    private JButton exitButton;

    public ControlPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        exitButton = new JButton("Exit");

        add(loadButton);
        add(saveButton);
        add(exitButton);

        // Dummy implementation for Load button
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ControlPanel.this, "Load functionality not implemented.");
            }
        });

        // Dummy implementation for Save button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ControlPanel.this, "Save functionality not implemented.");
            }
        });

        // Exit the application
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}

