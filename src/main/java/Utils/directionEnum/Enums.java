package Utils.directionEnum;

import maze.gameManager.MazeData;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Enums {

    public enum MainDirectionsEnum {
        UP, RIGHT, DOWN, LEFT, BOOKMARK;

        private static final List<MainDirectionsEnum> VALUES = List.of(values());

        private static final Random RANDOM = new Random();

        public static MainDirectionsEnum randomMainDirection() {
            return VALUES.get(RANDOM.nextInt(VALUES.size() - 1));
        }

        public static Point move(MainDirectionsEnum directionsEnum, Point playerLocation, MazeData mazdeData) {

            switch (directionsEnum) {
                case UP:
                    return moveUP(playerLocation, mazdeData.getRows());
                case DOWN:
                    return moveDown(playerLocation, mazdeData.getRows());
                case RIGHT:
                    return moveRight(playerLocation, mazdeData.getColumns());
                case LEFT:
                    return moveLeft(playerLocation, mazdeData.getColumns());
                default:
                    return playerLocation;
            }
        }

        private static Point moveDown(Point playerLocation, int rows) {
            playerLocation.move(playerLocation.x, playerLocation.y + 1);
            if (playerLocation.y >= rows) {
                playerLocation.move(playerLocation.x, 0);
            }
            return playerLocation;
        }

        private static Point moveUP(Point playerLocation, int rows) {
            playerLocation.move(playerLocation.x, playerLocation.y - 1);
            if (playerLocation.y < 0) {
                playerLocation.move(playerLocation.x, rows-1);
            }
            return playerLocation;
        }

        private static Point moveLeft(Point playerLocation, int column) {
            playerLocation.move(playerLocation.x - 1, playerLocation.y);
            if (playerLocation.x < 0) {
                playerLocation.move(column - 1, playerLocation.y);
            }
            return playerLocation;
        }

        private static Point moveRight(Point playerLocation, int column) {
            playerLocation.move(playerLocation.x + 1, playerLocation.y);
            if (playerLocation.x >= column) {
                playerLocation.move(0, playerLocation.y);
            }
            return playerLocation;
        }
    }
}
