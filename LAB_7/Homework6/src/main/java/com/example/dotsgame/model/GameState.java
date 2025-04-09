package com.example.dotsgame.model;

import java.util.*;
import java.io.Serializable;

public class GameState implements Serializable {
    private List<Dot> dots = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private int currentPlayer = 0;

    public List<Dot> getDots() { return dots; }
    public List<Line> getLines() { return lines; }

    public void generateDots(int count, int width, int height) {
        dots.clear();
        lines.clear();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            dots.add(new Dot(rand.nextInt(width - 40) + 20, rand.nextInt(height - 80) + 40));
        }
    }

    public void addLine(Dot a, Dot b) {
        lines.add(new Line(a, b, currentPlayer));
        currentPlayer = 1 - currentPlayer;
    }

    public void copyFrom(GameState other) {
        this.dots = new ArrayList<>(other.dots);
        this.lines = new ArrayList<>(other.lines);
        this.currentPlayer = other.currentPlayer;
    }
}
