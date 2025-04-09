package com.example.dotsgame.model;

import java.awt.*;
import java.io.Serializable;

public class Line implements Serializable {
    private final Dot a, b;
    private final int player; // 0 for blue, 1 for red

    public Line(Dot a, Dot b, int player) {
        this.a = a;
        this.b = b;
        this.player = player;
    }

    public Dot getA() { return a; }
    public Dot getB() { return b; }
    public double length() { return a.distance(b); }
    public Color getPlayerColor() { return player == 0 ? Color.BLUE : Color.RED; }
}
