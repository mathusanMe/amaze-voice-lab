package com.gytmy.maze.view.game.EndTransition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.gytmy.maze.view.MenuFrameHandler;
import com.gytmy.maze.view.game.Cell;

class DrawShapes extends JPanel {

    private static final Dimension MAX_DIMENSION = MenuFrameHandler.DEFAULT_DIMENSION;

    private static final Color COLOR_TRANSITION = Cell.WALL_COLOR;

    private static final int REMOVE_TIMER_DELAY = 25;
    private static final int APPARITION_TIMER_DELAY = 30;

    private static final int DISAPPEARING_STEP_SPEED = 3;

    private List<AnimationMazePanel> shapeList = new ArrayList<>();

    private List<AnimationObserver> observers = new ArrayList<>();

    private int x = 0;
    private int y = 0;

    DrawShapes(Dimension defaultDimension) {
        setPreferredSize(defaultDimension);

        int square_height = defaultDimension.height / 6;
        int square_width = square_height;

        new Timer(APPARITION_TIMER_DELAY, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                shapeList.add(
                        new AnimationMazePanel(new Rectangle(x, y, square_width, square_height), COLOR_TRANSITION));
                repaint();

                x += square_width;
                if (x > defaultDimension.width) {
                    x = 0;
                    y += square_height;
                }

                if (y >= defaultDimension.height) {
                    ((Timer) evt.getSource()).stop();
                    notifyObservers();
                    return;
                }
            }

        }).start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (AnimationMazePanel shape : shapeList) {
            g2.setColor(shape.getColor());
            g2.fill(shape.getShape());
            g2.draw(shape.getShape());
        }
    }

    public void clearAnimation() {
        resizeWindow();
    }

    private void resizeWindow() {
        new Timer(5, new ActionListener() {

            private Dimension goalFrameDimension = MenuFrameHandler.DEFAULT_DIMENSION;
            private int wantedWitdth = goalFrameDimension.width;
            private int wantedHeight = goalFrameDimension.height;

            @Override
            public void actionPerformed(ActionEvent evt) {
                Dimension frameDimension = MenuFrameHandler.getMainFrame().getSize();

                if (wantedWitdth <= frameDimension.width && wantedHeight <= frameDimension.height) {
                    ((Timer) evt.getSource()).stop();
                    removeAnimation();
                    return;
                }

                int newWidth = Math.min(frameDimension.width + 5, wantedWitdth);

                int newHeight = Math.min(frameDimension.height + 5, wantedHeight);

                shapeList.clear();
                shapeList.add(
                        new AnimationMazePanel(new Rectangle(0, 0, newWidth, newHeight), COLOR_TRANSITION));
                repaint();

                MenuFrameHandler.getMainFrame().setSize(newWidth, newHeight);
                MenuFrameHandler.getMainFrame().repaint();
            }

        }).start();
    }

    private void removeAnimation() {
        new Timer(REMOVE_TIMER_DELAY, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                Color actualColor = shapeList.get(0).getColor();

                if (actualColor.getAlpha() - DISAPPEARING_STEP_SPEED < 0) {
                    ((Timer) evt.getSource()).stop();
                    notifyObservers();
                    return;
                }

                if (actualColor.getAlpha() > 0) {
                    Color newColor = new Color(actualColor.getRed(), actualColor.getGreen(), actualColor.getBlue(),
                            actualColor.getAlpha() - DISAPPEARING_STEP_SPEED);
                    shapeList.clear();
                    shapeList.add(new AnimationMazePanel(new Rectangle(new Point(0, 0), MAX_DIMENSION), newColor));

                    repaint();
                }
            }

        }).start();
    }

    public void addAnimationObserver(AnimationObserver observer) {
        observers.add(observer);
    }

    public void removeAnimationObserver(AnimationObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(AnimationObserver::endAnimationUpdate);
    }

}
