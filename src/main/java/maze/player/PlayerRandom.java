package maze.player;

import Utils.directionEnum.Enums.MainDirectionsEnum;

import java.util.HashMap;
import java.util.Map;

public class PlayerRandom extends Player {

    private MainDirectionsEnum newRandomDirection;

    public PlayerRandom() {
        this.newRandomDirection = MainDirectionsEnum.randomMainDirection();
        mainDirection = MainDirectionsEnum.randomMainDirection();
    }


    @Override
    public MainDirectionsEnum move() {
        if (isHitWall) {
            do {
                newRandomDirection = MainDirectionsEnum.randomMainDirection();
            } while (newRandomDirection.equals(mainDirection) /*||
                    newRandomDirection.name() == backStep.get(mainDirection).name()*/);
            isHitWall=false;
            return mainDirection = newRandomDirection;
        }
        return mainDirection;
    }

    private Map<MainDirectionsEnum, MainDirectionsEnum> backStep = new HashMap<>() {{
        put(MainDirectionsEnum.UP, MainDirectionsEnum.DOWN);
        put(MainDirectionsEnum.DOWN, MainDirectionsEnum.UP);
        put(MainDirectionsEnum.RIGHT, MainDirectionsEnum.LEFT);
        put(MainDirectionsEnum.LEFT, MainDirectionsEnum.RIGHT);
    }};

    @Override
    public void hitBookmark(int seq) {

    }

}
