package com.example.dotsgame.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.example.dotsgame.model.*;

public class GameCanvas extends JPanel {
    private final GameState state;
    private Dot selectedDot = null;

    public GameCanvas(GameState state) {
        this.state = state;
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                for (Dot d : state.getDots()) {
                    if (d.contains(e.getPoint())) {
                        if (selectedDot == null) {
                            selectedDot = d;
                        } else if (!selectedDot.equals(d)) {
                            state.addLine(selectedDot, d);
                            selectedDot = null;
                            repaint();
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw all lines
        for (Line line : state.getLines()) {
            g2d.setColor(line.getPlayerColor());
            g2d.drawLine(line.getA().x, line.getA().y, line.getB().x, line.getB().y);
        }

        // Draw all dots
        for (Dot dot : state.getDots()) {
            g2d.setColor(Color.BLACK);
            g2d.fillOval(dot.x - 5, dot.y - 5, 10, 10);
        }
    }
}

