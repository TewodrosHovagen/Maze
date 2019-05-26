package maze.player;

import Utils.DirectionsEnum;

import java.util.Random;

public class PlayerRandom extends Player {

    @Override
    public DirectionsEnum move() {
        return getRandomDirection( new Random().nextInt(5));
    }

    private DirectionsEnum getRandomDirection(int dirNum){
        switch (dirNum){
            case 0: return DirectionsEnum.UP;
            case 1: return DirectionsEnum.DOWN;
            case 2: return DirectionsEnum.LEFT;
            case 3: return DirectionsEnum.RIGHT;
            case 4: return DirectionsEnum.BOOKMARK;
        }
        throw new NumberFormatException("Wrong number was sent");
    }
}
