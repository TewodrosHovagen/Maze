package maze.player;

import maze.DirectionsEnum;
import maze.player.PlayerInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Player implements PlayerInterface {

    private List<Integer> bookMarks;
    private int bookMarksCounter;
    private int steps;
    private boolean isHitWall;
//    private Point playerPoint;

    public Player(Point playerPoint) {
        this.steps = 0;
        this.bookMarks = new ArrayList<>();
//        this.playerPoint=playerPoint;
    }




    @Override
    public DirectionsEnum move() {
        if (isHitWall) {
            isHitWall=false;
            return DirectionsEnum.LEFT;
        }else {
            return  DirectionsEnum.UP;
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