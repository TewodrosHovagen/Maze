package maze.player;

import Utils.logging.Logger;

public abstract class Player implements PlayerInterface {

    private final Logger log = Logger.getInstance();
    protected boolean isHitWall;

    @Override
    public void hitWall() {
        log.info("You hit the wall");
        isHitWall = true;
    }


    protected void setHitWall(boolean hitWall) {
        isHitWall = hitWall;
    }

}

