package maze.player;

import java.util.Set;

public abstract class Player implements PlayerAction {

    protected boolean isHitWall;
    protected Set<Integer> bookMarks;

    @Override
    public void hitWall() {
        System.out.println("You hit the wall");
        isHitWall = true;
    }


    @Override
    public void hitBookmark(int seq) {
        bookMarks.add(seq);
    }
}

