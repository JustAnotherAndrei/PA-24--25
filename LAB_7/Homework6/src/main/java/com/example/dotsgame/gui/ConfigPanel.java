package com.example.dotsgame.gui;

import javax.swing.*;
import com.example.dotsgame.model.GameState;

public class ConfigPanel extends JPanel {
    public ConfigPanel(GameState state, GameCanvas canvas) {
        JLabel label = new JLabel("Number of Dots:");
        JTextField field = new JTextField("10", 5);
        JButton generateButton = new JButton("New Game");

        generateButton.addActionListener(e -> {
            try {
                int numDots = Integer.parseInt(field.getText());
                state.generateDots(numDots, canvas.getWidth(), canvas.getHeight());
                canvas.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number!");
            }
        });

        add(label);
        add(field);
        add(generateButton);
    }
}
