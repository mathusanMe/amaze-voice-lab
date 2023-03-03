package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.gytmy.labyrinth.view.Cell;

public class GameModeSelectorPanel extends JPanel {

    public static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    public static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private GameModeSelector gameModeSelector;
    protected JPanel gameModeSettingsPanel;

    public GameModeSelectorPanel() {
        setBackground(BACKGROUND_COLOR);
        initGameModeSelector();
        initGameModeSettingsPanel();
    }

    private void initGameModeSelector() {
        gameModeSelector = new GameModeSelector();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        add(gameModeSelector, gbc);
        revalidate();
        repaint();
    }

    private void initGameModeSettingsPanel() {
        gameModeSettingsPanel = new JPanel();
        gameModeSelector.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gameModeSelector.updateGameModeSettingsPanel((GameMode) gameModeSelector.getSelectedItem());
        gameModeSettingsPanel.setBackground(BACKGROUND_COLOR);
        add(gameModeSettingsPanel, gbc);
        revalidate();
        repaint();
    }

    private class GameModeSelector extends JComboBox<GameMode> {
        GameMode lastSelectedGameMode;

        public GameModeSelector() {
            for (GameMode gameMode : GameMode.values()) {
                addItem(gameMode);
            }

            addActionListener(e -> {
                GameMode gameMode = (GameMode) getSelectedItem();
                updateGameModeSettingsPanel(gameMode);
            });

            setBackground(BACKGROUND_COLOR);
            setForeground(FOREGROUND_COLOR);
        }

        private void updateGameModeSettingsPanel(GameMode gameMode) {
            cleanOldPanel();
            lastSelectedGameMode = (GameMode) getSelectedItem();
            GameModeSettingsPanelHandler handler = GameModeSettingsPanelFactory
                    .getGameModeSettingsPanel(gameMode);
            handler.initPanel(gameModeSettingsPanel);
        }

        private void cleanOldPanel() {
            if (lastSelectedGameMode == null) {
                return;
            }

            GameModeSettingsPanelHandler handler = GameModeSettingsPanelFactory
                    .getGameModeSettingsPanel(lastSelectedGameMode);
            handler.cleanPanel(gameModeSettingsPanel);

        }

    }

    public static void main(String[] args) {
        GameModeSelectorPanel panel = new GameModeSelectorPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.add(panel);
        frame.setVisible(true);

    }
}
