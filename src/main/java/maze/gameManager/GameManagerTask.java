package maze.gameManager;

import utils.logging.Logger;
import maze.player.Player;

public class GameManagerTask extends GameManagerImpl implements Runnable {

    private final Logger log = Logger.getInstance();
    private final int NOT_FOUNT = -1;
    private int maxStepsResults;

    public GameManagerTask(MazeData data, Player player) {
        super(data, player);
    }

    @Override
    public void run() {
        runGame();
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
