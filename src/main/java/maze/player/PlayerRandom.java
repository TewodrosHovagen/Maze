package maze.player;

import Utils.directionEnum.Enums.*;

import java.util.Random;

public class PlayerRandom extends Player {

    @Override
    public MainDirectionsEnum move() {
        return getRandomDirection( new Random().nextInt(5));
    }

    @Override
    public void hitBookmark(int seq) {

    }

    private MainDirectionsEnum getRandomDirection(int dirNum){
        switch (dirNum){
            case 0: return MainDirectionsEnum.UP;
            case 1: return MainDirectionsEnum.DOWN;
            case 2: return MainDirectionsEnum.LEFT;
            case 3: return MainDirectionsEnum.RIGHT;
            case 4: return MainDirectionsEnum.BOOKMARK;
        }
        throw new NumberFormatException("Wrong number was sent");
    }
}
