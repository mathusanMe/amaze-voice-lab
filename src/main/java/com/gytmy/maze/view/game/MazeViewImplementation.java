package com.gytmy.maze.view.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.view.GameOverPanel;
import com.gytmy.maze.view.MenuFrameHandler;
import com.gytmy.maze.view.TimerPanel;
import com.gytmy.utils.ImageManipulator;

public class MazeViewImplementation extends MazeView {
    protected MazeModel model;
    protected MazePanel mazePanel;
    protected TimerPanel timerPanel;
    protected JPanel topPanel;
    private JFrame frame;

    protected static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    protected static final String ENABLED_KEYBOARD_MOVEMENT = "src/resources/images/game/directional_arrows_enabled.png";
    protected static final String DISABLED_KEYBOARD_MOVEMENT = "src/resources/images/game/directional_arrows_disabled.png";
    protected static final int ICON_WIDTH = 51;
    protected static final int ICON_HEIGHT = 33;
    protected static final Dimension ICON_DIMENSION = new Dimension(ICON_WIDTH, ICON_HEIGHT);
    protected static final Icon ENABLED_KEYBOARD_MOVEMENT_ICON = ImageManipulator.resizeImage(
            ENABLED_KEYBOARD_MOVEMENT, ICON_WIDTH, ICON_HEIGHT);
    protected static final Icon DISABLED_KEYBOARD_MOVEMENT_ICON = ImageManipulator.resizeImage(
            DISABLED_KEYBOARD_MOVEMENT, ICON_WIDTH, ICON_HEIGHT);

    protected MazeViewImplementation(MazeModel model, JFrame frame) {
        this.model = model;
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        mazePanel = new MazePanel(model);
    }

    public void startTimer() {
        timerPanel.start();
    }

    @Override
    public void stopTimer() {
        timerPanel.stop();
    }

    public int getTimerCounterInSeconds() {
        return timerPanel.getCounterInSeconds();
    }

    @Override
    public void update(Player player, Direction direction) {
        mazePanel.update(player, direction);
    }

    @Override
    public MazePanel getMazePanel() {
        return mazePanel;
    }

    @Override
    public boolean isTimerCounting() {
        return timerPanel.isCounting();
    }

    @Override
    public void showGameOverPanel() {
        frame.setContentPane(new GameOverPanel(model));
        frame.setPreferredSize(MenuFrameHandler.DEFAULT_DIMENSION);
        MenuFrameHandler.frameUpdate("Game Over");
    }

    @Override
    public void notifyGameStarted() {
        // For this view, nothing needs to be done.
    }

    @Override
    public void notifyGameOver() {
        // EventQueue is used to pause the screen a little bit before showing the game
        // over panel
        EventQueue.invokeLater(
                () -> {
                    try {
                        Thread.sleep(2000);
                        showGameOverPanel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
