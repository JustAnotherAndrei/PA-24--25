package com.example.dotsgame;

import javax.swing.SwingUtilities;
import com.example.dotsgame.gui.GameFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame().setVisible(true));
    }
}

