package maze.player;

import Utils.logging.Logger;

import java.util.Set;

public abstract class Player implements PlayerInterface {

    protected boolean isHitWall;
    protected Set<Integer> bookMarks;

    @Override
    public void hitWall() {
        Logger.info("You hit the wall");
        isHitWall = true;
    }


    @Override
    public void hitBookmark(int seq) {
        bookMarks.add(seq);
    }
}

