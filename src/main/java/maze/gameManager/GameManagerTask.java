package maze.gameManager;

import Utils.Enums;
import Utils.logging.Logger;
import maze.fileDataParse.FileData;
import maze.player.Player;

import java.awt.*;

public class GameManagerTask extends GameManagerImpl implements Runnable {

    private final Logger log = Logger.getInstance();
    private Player player;
    private final int NOT_FOUNT = -1;
    private int maxStepsResults;

    public GameManagerTask(FileData data, Player player){
        super(data);
        this.player = player;
    }

    @Override
    public void startGame() {
        Enums.DirectionsEnum direction;
        Point currentLocation = playerLocation;
        int timesToPlay;
        log.info("**************** START THE MAZE ****************");
        for (timesToPlay = 0; timesToPlay < data.getMaxSteps(); timesToPlay++) {
            log.info("Step No: " + timesToPlay);
            direction = player.move();
            log.info("Player position before move"+ currentLocation.getLocation());
            log.info("Go Direction: " + direction);
            currentLocation = move(direction);
            log.info("Player position after move "+ currentLocation.getLocation());
            if (direction == Enums.DirectionsEnum.BOOKMARK) {
                addBookmark(currentLocation, bookmarkCounter++);
            } else {
                if (isTreasure(currentLocation)) {
                    log.info(String.format("Succeeded in %s steps", timesToPlay + 1));
                    maxStepsResults = timesToPlay+1;
                    break;
                } else {
                    if (isWall(currentLocation)) {
                        player.hitWall();
                        playerLocation = currentLocation;
                        log.info("hit : "+currentLocation);
                        currentLocation = getBackMove(direction);
                        log.info("current : "+currentLocation);
                    }
                    if (isBookmarkLocation(currentLocation)) {
                        player.hitBookmark(getBookmarkSequence(currentLocation));
                    }
                }
            }
            //playerPreviousLocation = playerLocation;
            playerLocation = currentLocation;
            log.info(direction.toString());
            //printMazeWorldAfterChange();
            log.info("Current location:" + (int) playerLocation.getX() + "," + (int) playerLocation.getY());
        }
        if (timesToPlay == data.getMaxSteps()) {
            log.info(String.format("Failed to solve maze in %s steps", timesToPlay));
            maxStepsResults = NOT_FOUNT;
        }
    }

    @Override
    public void run() {
        startGame();
    }

    public int getMaxStepsResults() {
        return maxStepsResults;
    }
}
