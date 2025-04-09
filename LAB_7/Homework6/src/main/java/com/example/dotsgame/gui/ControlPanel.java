package com.example.dotsgame.gui;

import javax.swing.*;
import com.example.dotsgame.model.GameState;
import com.example.dotsgame.utils.Serializer;
import com.example.dotsgame.utils.ImageExporter;
import java.awt.event.*;

public class ControlPanel extends JPanel {
    public ControlPanel(GameState state, GameCanvas canvas, JFrame frame) {
        JButton save = new JButton("Save");
        JButton load = new JButton("Load");
        JButton export = new JButton("Export PNG");
        JButton exit = new JButton("Exit");

        save.addActionListener(e -> Serializer.saveState(state));
        load.addActionListener(e -> {
            GameState loaded = Serializer.loadState();
            if (loaded != null) {
                state.copyFrom(loaded);
                canvas.repaint();
            }
        });
        export.addActionListener(e -> ImageExporter.exportComponent(canvas, "game_board.png"));
        exit.addActionListener(e -> frame.dispose());

        add(save);
        add(load);
        add(export);
        add(exit);
    }
}
