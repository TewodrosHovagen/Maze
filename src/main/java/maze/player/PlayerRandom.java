package maze.player;

import Utils.Enums.*;

import java.util.Random;

public class PlayerRandom extends Player {

    @Override
    public DirectionsEnum move() {
        return getRandomDirection( new Random().nextInt(5));
    }

    private DirectionsEnum getRandomDirection(int dirNum){
        switch (dirNum){
            case 0: return DirectionsEnum.NORTH;
            case 1: return DirectionsEnum.SOUTH;
            case 2: return DirectionsEnum.WEST;
            case 3: return DirectionsEnum.EAST;
            case 4: return DirectionsEnum.BOOKMARK;
        }
        throw new NumberFormatException("Wrong number was sent");
    }
}
