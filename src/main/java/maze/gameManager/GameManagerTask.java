package maze.gameManager;

import utils.logging.Logger;
import maze.player.Player;

public class GameManagerTask extends GameManagerImpl implements Runnable {

    private final Logger log = Logger.getInstance();
    private final int TREASURE_NOT_FOUNT = -1;
    private int playerStepsResults;

    public GameManagerTask(MazeData data, Player player) {
        super(data, player);
    }

    @Override
    public void run() {
        runGame();
    }

    public int getPlayerStepsResults() {
        return playerStepsResults;
    }

    @Override
    protected void gameResultHandler() {
        if(timesToPlay == mazeData.getMaxSteps()) {
            playerStepsResults = TREASURE_NOT_FOUNT;
        }else{
            playerStepsResults = timesToPlay;
        }
        log.info("Game result is :" + playerStepsResults);
    }

    @Override
    protected void gameResultHandler(String out) {    }
}
