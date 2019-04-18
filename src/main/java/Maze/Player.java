package Maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface {

    private List<Integer> bookMarks;
    private int bookMarksCounter;
    private int steps;
    private boolean isHitWall;
    private Point playerPoint;

    public Player(Point playerPoint) {
        this.steps = 0;
        this.bookMarks = new ArrayList<>();
        this.playerPoint=playerPoint;
    }


    @Override
    public DirectionsEnum move() {


//        hitBookmark(bookMarksCounter);
        return DirectionsEnum.DOWN;
    }

    @Override
    public void hitWall() {
        System.out.println("you hit wall !!");
    }

    @Override
    public void hitBookmark(int seq) {
        bookMarks.add(seq);
    }
}
