package com.example.dotsgame.utils;

import com.example.dotsgame.model.GameState;
import javax.swing.*;
import java.io.*;

public class Serializer {
    public static void saveState(GameState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savegame.ser"))) {
            out.writeObject(state);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving game.");
        }
    }

    public static GameState loadState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savegame.ser"))) {
            return (GameState) in.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading game.");
            return null;
        }
    }
}
