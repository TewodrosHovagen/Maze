package maze.player;

import utils.directionEnum.Enums;
import utils.logging.Logger;
import maze.player.directionEnum.playerEnum;

public abstract class Player implements PlayerInterface {

    private final Logger log = Logger.getInstance();
    private boolean isHitWall;
    protected playerEnum.WalkingDirectionsEnum lastStep;
    protected Enums.MainDirectionsEnum mainDirection;

    @Override
    public final void hitWall() {
        log.info("You hit the wall");
        isHitWall = true;
        hitWallHandler();
    }

    protected void hitWallHandler() {}

    protected boolean isLastMoveHitWall() {
        return isHitWall;
    }

    protected void clearHitWall() {
        isHitWall = false;
    }

    // for unit tests
    void setHitWall(boolean hitWall) {
        isHitWall = hitWall;
    }

}

