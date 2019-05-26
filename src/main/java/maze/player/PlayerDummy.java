package maze.player;

import Utils.Enums.DirectionsEnum;
import Utils.Enums.WalkingDirectionsEnum;

import java.util.Map;

import static Utils.Enums.WalkingDirectionsEnum.*;

public class PlayerDummy extends Player {

    @Override
    public DirectionsEnum move() {
        Map<WalkingDirectionsEnum, DirectionsEnum> currentDirectionMap = getDirectionsMap().get(getMainDirection());
        if (isHitWall()) {
            if (getLastStep() == STRAIGHT) {
                setLastStep(RIGHT);
                return currentDirectionMap.get(RIGHT);
            } else if (getLastStep() == RIGHT) {
                setLastStep(LEFT);
                return currentDirectionMap.get(LEFT);
            } else if (getLastStep() == LEFT) {
                setLastStep(WalkingDirectionsEnum.BOOKMARK);
                return DirectionsEnum.BOOKMARK;
            } else {
                setMainDirection(currentDirectionMap.get(BACK));
                setLastStep(STRAIGHT);
                return currentDirectionMap.get(BACK);
            }
        } else {
            setMainDirection(currentDirectionMap.get(getLastStep()));
            setLastStep(STRAIGHT);
            return getMainDirection();
        }
    }
}