package maze.player;

import Utils.DirectionsEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDummy extends Player {

    private List<Integer> bookMarks;
    private int bookMarksCounter;
    private int isHitWall;
    private Point startLocation;


    public PlayerDummy() {
        bookMarks = new ArrayList<>();
    }


    @Override
    public DirectionsEnum move() {
        switch (isHitWall) {
            case 0:
                return DirectionsEnum.UP;
            case 1:
                return DirectionsEnum.LEFT;
            case 2:
                return DirectionsEnum.RIGHT;
            case 3:
                return DirectionsEnum.DOWN;

            default:
                return DirectionsEnum.UP;
        }

    }


}