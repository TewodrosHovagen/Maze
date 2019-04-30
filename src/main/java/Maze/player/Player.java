package maze.player;

import maze.DirectionsEnum;

import java.util.HashSet;
import java.util.Set;

public class Player implements PlayerInterface {

    private Set<Integer> bookMarks;
    private int bookMarksCounter;
    private boolean isHitWall;

    public Player() {
        this.bookMarks = new HashSet<>();
    }



    @Override
    public DirectionsEnum move() {
        if (isHitWall) {
            isHitWall=false;
            return DirectionsEnum.LEFT;
        }else {
            return DirectionsEnum.UP;
        }
    }

    @Override
    public void hitWall() {
        System.out.println("You hit the wall");
        isHitWall=true;
    }

    @Override
    public void hitBookmark(int seq) {
        bookMarks.add(seq);
    }
}
