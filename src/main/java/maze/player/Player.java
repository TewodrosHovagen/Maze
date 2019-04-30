package maze.player;

import maze.DirectionsEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface {

    private List<Integer> bookMarks;
    private int bookMarksCounter;
    private int steps;
    private boolean isHitWall;
    private int tryiesCounter;
    private DirectionsEnum lastDirection;
//    private Point playerPoint;

    public Player(Point playerPoint) {
        this.steps = 0;
        this.bookMarks = new ArrayList<>();
//        this.playerPoint=playerPoint;
        tryiesCounter = 0;
    }




    @Override
    public DirectionsEnum move() {
        if(isHitWall) {

//            tryiesCounter = 0;
            tryiesCounter++;
            if (tryiesCounter == 1) {
                lastDirection = DirectionsEnum.UP;
                return  lastDirection;
//            isHitWall=false;
            }else if(tryiesCounter == 2) {
                lastDirection =  DirectionsEnum.LEFT;
                return  lastDirection;
            }else if(tryiesCounter == 3) {
                lastDirection =  DirectionsEnum.DOWN;
                return  lastDirection;
            }else  if(tryiesCounter == 4){
                lastDirection =  DirectionsEnum.RIGHT;
                return  lastDirection;
            }else{
                return  lastDirection;
            }

        }
        else{
            isHitWall = false;
            tryiesCounter = 0;
        }
        return  DirectionsEnum.UP;
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
