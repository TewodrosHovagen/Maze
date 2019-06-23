package maze.player;

import utils.directionEnum.Enums;
import utils.logging.Logger;
import maze.player.directionEnum.playerEnum;

public abstract class Player implements PlayerInterface {

    private final Logger log = Logger.getInstance();
    protected boolean isHitWall;

    @Override
    public void hitWall() {
        log.info("You hit the wall");
        isHitWall = true;
    }

    protected playerEnum.WalkingDirectionsEnum lastStep;
    protected Enums.MainDirectionsEnum mainDirection;

    protected void setHitWall(boolean hitWall) {
        isHitWall = hitWall;
    }

}

