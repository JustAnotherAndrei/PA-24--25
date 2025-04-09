package com.example.dotsgame.gui;

import javax.swing.*;
import java.awt.*;
import com.example.dotsgame.model.GameState;

public class GameFrame extends JFrame {
    public GameFrame() {
        super("Dots Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        GameState state = new GameState();
        GameCanvas canvas = new GameCanvas(state);

        ConfigPanel configPanel = new ConfigPanel(state, canvas);
        ControlPanel controlPanel = new ControlPanel(state, canvas, this);

        add(configPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
}
