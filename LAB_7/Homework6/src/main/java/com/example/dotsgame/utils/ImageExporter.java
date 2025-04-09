package com.example.dotsgame.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageExporter {
    public static void exportComponent(JComponent comp, String filename) {
        BufferedImage image = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        comp.paint(g2);
        g2.dispose();
        try {
            ImageIO.write(image, "png", new File(filename));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to export image.");
        }
    }
}
