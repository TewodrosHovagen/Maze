package maze.player;

import maze.DirectionsEnum;
import maze.player.PlayerInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Player extends PlayerAbstract {

    private List<Integer> bookMarks;
    private int bookMarksCounter;
    private int isHitWall;
    private Point startLocation;


    public Player() {
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