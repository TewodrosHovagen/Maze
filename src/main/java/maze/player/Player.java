package maze.player;

import java.util.HashSet;
import java.util.Set;

public abstract class Player implements PlayerAction {

    private int isHitWall = 0;
    private Set<Integer> bookMarks = new HashSet<>();

    @Override
    public void hitWall() {
        System.out.println("You hit the wall");
        isHitWall = isHitWall + 1;
    }

    @Override
    public void hitBookmark(int seq) {
        bookMarks.add(seq);
    }
}

