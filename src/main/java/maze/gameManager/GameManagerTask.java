package maze.gameManager;

import Utils.directionEnum.Enums;
import Utils.logging.Logger;
import maze.fileDataParse.FileData;
import maze.player.Player;
import java.awt.*;

public class GameManagerTask extends GameManagerImpl implements Runnable {

    private final Logger log = Logger.getInstance();
    private final int NOT_FOUNT = -1;
    private int maxStepsResults;

    public GameManagerTask(FileData data, Player player) {
        super(data, player);
    }

    @Override
    public void run() {
        startGame();
    }

    public int getMaxStepsResults() {
        return maxStepsResults;
    }

    @Override
    protected void gameResultHandler() {
        if(timesToPlay == data.getMaxSteps()) {
            maxStepsResults = NOT_FOUNT;
        }else{
            maxStepsResults = timesToPlay;
        }
        log.info("Game result is :" + maxStepsResults);
    }
}
