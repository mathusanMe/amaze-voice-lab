package com.gytmy.labyrinth.model;

import org.junit.validator.PublicClassValidator;

import com.gytmy.labyrinth.model.generators.BoardGenerator;
import com.gytmy.labyrinth.model.generators.DepthFirstGenerator;
import com.gytmy.labyrinth.model.generators.OneDimensionBoardGenerator;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.utils.Coordinates;

public class LabyrinthModelFactory {

    private LabyrinthModelFactory() {
    }

    public static LabyrinthModel createLabyrinth(int length, Player[] players) {
        BoardGenerator generator = getBoardGenerator(length, 0, null);
        return createLabyrinth(generator, null, null, players);
    }

    public static LabyrinthModel createLabyrinth(int length, int height, Player[] players) {
        BoardGenerator generator = getBoardGenerator(length, height, null);
        return createLabyrinth(generator, null, null, players);
    }

    public static LabyrinthModel createLabyrinth(int length, int height, Coordinates initialCell, Player[] players) {
        BoardGenerator generator = getBoardGenerator(length, height, initialCell);
        return createLabyrinth(generator, initialCell, null, players);
    }

    public static LabyrinthModel createLabyrinth(int length, int height, Coordinates initialCell, Coordinates endCell,
            Player[] players) {
        BoardGenerator generator = getBoardGenerator(length, height, initialCell);
        return createLabyrinth(generator, initialCell, endCell, players);
    }

    public static LabyrinthModel createLabyrinth(BoardGenerator generator, Player[] players) {
        return createLabyrinth(generator, null, null, players);
    }

    public static LabyrinthModel createLabyrinth(BoardGenerator generator, Coordinates initialCell, Coordinates endCell,
            Player[] players) {
        return new LabyrinthModelImplementation(generator, initialCell, endCell, players);
    }

    private static BoardGenerator getBoardGenerator(int width, int height, Coordinates initialCell) {
        if (height <= 3) {
            return new OneDimensionBoardGenerator(width);
        }
        return new DepthFirstGenerator(width, height, initialCell);

    }

}
