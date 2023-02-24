package com.gytmy.labyrinth.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.utils.Coordinates;

public class Cell extends JPanel {

    public static final Color UNINITIALIZED_CELL_COLOR = Color.DARK_GRAY;
    public static final int CELL_SIZE = 24;

    private Coordinates coordinates;
    private List<Player> players;
    private LabyrinthModel model;

    public Cell(Coordinates coordinates, List<Player> players, LabyrinthModel model) {
        this.coordinates = coordinates;
        this.players = players;
        this.model = model;
        init();
    }

    private void init() {
        if (players.isEmpty()) {
            initNonPlayerCell();
        } else {
            initPlayersCell(players.size());
        }

        Dimension preferredSize = new Dimension(CELL_SIZE, CELL_SIZE);
        setPreferredSize(preferredSize);
    }

    private void initNonPlayerCell() {
        if (!colorCellInitialOrExit()) {
            colorCellPathOrWall();
        }
    }

    private boolean colorCellInitialOrExit() {
        if (model.isInitialCell(coordinates)) {
            setBackground(Color.decode("#94b0c2"));
            return true;

        } else if (model.isExitCell(coordinates)) {
            setBackground(Color.decode("#ee8695"));
            return true;
        }

        return false;
    }

    private void colorCellPathOrWall() {
        if (model.isWall(coordinates)) {
            setBackground(Color.decode("#2e222f"));

        } else {
            setBackground(Color.decode("#FFF8EA"));
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
        update();
    }

    public void removePlayer(Player player) {
        players.remove(player);
        update();
    }

    public void update() {
        removeAll();
        init();
        revalidate();
        repaint();
    }

    private void initPlayersCell(int nbPlayers) {
        switch (nbPlayers) {
            case 1:
            case 2:
            case 3:
                create123PlayersCell(nbPlayers);
                break;
            case 4:
                create4PlayersCell();
                break;
            case 5:
                create5PlayersCell();
                break;
            default:
                throw new IllegalArgumentException("Too many players in the cell");
        }

    }

    private void create123PlayersCell(int nbPlayers) {
        GridLayout layout = new GridLayout(1, nbPlayers);
        setLayout(layout);
        addPlayerPanelsCase1234();
    }

    private void create4PlayersCell() {
        GridLayout layout = new GridLayout(2, 2);
        setLayout(layout);
        addPlayerPanelsCase1234();
    }

    private void addPlayerPanelsCase1234() {
        for (Player player : players) {
            JPanel playerPanel = new JPanel();
            playerPanel.setBackground(player.getColor());
            add(playerPanel);
        }
    }

    private void create5PlayersCell() {
        GridLayout layout = new GridLayout(4, 4);
        setLayout(layout);
        createPanelOf5PlayersCase();
    }

    private void createPanelOf5PlayersCase() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Player player = getPlayerIn5PlayerPanel(col, row);
                addPlayerPanel(player);
            }
        }
    }

    private Player getPlayerIn5PlayerPanel(int col, int row) {
        Player res;
        if (isPlayer1Panel(col, row)) {
            res = players.get(0);
        } else if (isPlayer2Panel(col, row)) {
            res = players.get(1);
        } else if (isPlayer3Panel(col, row)) {
            res = players.get(2);
        } else if (isPlayer4Panel(col, row)) {
            res = players.get(3);
        } else // 5 Players
            res = players.get(4);
        return res;
    }

    private void addPlayerPanel(Player player) {
        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(player.getColor());
        add(playerPanel);
    }

    private static boolean isPlayer1Panel(int col, int row) {
        return (col == 0 && row == 0) ||
                (col == 1 && row == 0) ||
                (col == 0 && row == 1);
    }

    private static boolean isPlayer2Panel(int col, int row) {
        return (col == 2 && row == 0) ||
                (col == 3 && row == 0) ||
                (col == 3 && row == 1);
    }

    private static boolean isPlayer3Panel(int col, int row) {
        return (col == 0 && row == 2) ||
                (col == 0 && row == 3) ||
                (col == 1 && row == 3);
    }

    private static boolean isPlayer4Panel(int col, int row) {
        return (col == 2 && row == 3) ||
                (col == 3 && row == 3) ||
                (col == 3 && row == 2);
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }
}
