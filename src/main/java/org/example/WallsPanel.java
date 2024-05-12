package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class WallsPanel extends JPanel {
    private List<Falak> falak; // List to hold wall objects

    public WallsPanel(List<Falak> falak) {
        this.falak = falak;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Iterate through the wall objects and draw them
        for (Falak fal : falak) {
            g.fillRect((int) fal.hely.x, (int) fal.hely.y, 5, 5);
        }
    }
}