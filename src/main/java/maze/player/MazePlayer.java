package maze.player;

import Utils.Enums.DirectionsEnum;
import Utils.Enums.WalkingDirectionsEnum;
import Utils.logging.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MazePlayer extends Player {

    private final Logger log = Logger.getInstance();
    private WalkingDirectionsEnum lastStep;
    private DirectionsEnum mainDirection;
    final private Map<WalkingDirectionsEnum, DirectionsEnum> northMap;
    final private Map<WalkingDirectionsEnum, DirectionsEnum> eastMap;
    final private Map<WalkingDirectionsEnum, DirectionsEnum> westMap;
    final private Map<WalkingDirectionsEnum, DirectionsEnum> southMap;
    final private Map<DirectionsEnum, Map<WalkingDirectionsEnum, DirectionsEnum>> directionsMap;


    // initialize all player params in c'tor.
    public MazePlayer() {
        this.isHitWall = false;
        this.lastStep = WalkingDirectionsEnum.STRAIGHT;
        this.mainDirection = DirectionsEnum.UP;
        this.northMap = new HashMap<>() {{
            put(WalkingDirectionsEnum.STRAIGHT, DirectionsEnum.UP);
            put(WalkingDirectionsEnum.BACK, DirectionsEnum.DOWN);
            put(WalkingDirectionsEnum.RIGHT, DirectionsEnum.RIGHT);
            put(WalkingDirectionsEnum.LEFT, DirectionsEnum.LEFT);
        }};
        this.eastMap = new HashMap<>() {{
            put(WalkingDirectionsEnum.STRAIGHT, DirectionsEnum.RIGHT);
            put(WalkingDirectionsEnum.BACK, DirectionsEnum.LEFT);
            put(WalkingDirectionsEnum.RIGHT, DirectionsEnum.DOWN);
            put(WalkingDirectionsEnum.LEFT, DirectionsEnum.UP);
        }};
        this.westMap = new HashMap<>() {{
            put(WalkingDirectionsEnum.STRAIGHT, DirectionsEnum.LEFT);
            put(WalkingDirectionsEnum.BACK, DirectionsEnum.RIGHT);
            put(WalkingDirectionsEnum.RIGHT, DirectionsEnum.UP);
            put(WalkingDirectionsEnum.LEFT, DirectionsEnum.DOWN);
        }};
        this.southMap = new HashMap<>() {{
            put(WalkingDirectionsEnum.STRAIGHT, DirectionsEnum.DOWN);
            put(WalkingDirectionsEnum.BACK, DirectionsEnum.UP);
            put(WalkingDirectionsEnum.RIGHT, DirectionsEnum.LEFT);
            put(WalkingDirectionsEnum.LEFT, DirectionsEnum.RIGHT);
        }};
        this.directionsMap = new HashMap<>() {{
            put(DirectionsEnum.UP, northMap);
            put(DirectionsEnum.DOWN, southMap);
            put(DirectionsEnum.RIGHT, eastMap);
            put(DirectionsEnum.LEFT, westMap);
        }};
    }

    protected WalkingDirectionsEnum getLastStep() {
        return lastStep;
    }

    protected void setLastStep(WalkingDirectionsEnum lastStep) {
        this.lastStep = lastStep;
    }

    protected DirectionsEnum getMainDirection() {
        return mainDirection;
    }

    protected void setMainDirection(DirectionsEnum mainDirection) {
        this.mainDirection = mainDirection;
    }

    protected Map<WalkingDirectionsEnum, DirectionsEnum> getNorthMap() {
        return northMap;
    }

    protected Map<WalkingDirectionsEnum, DirectionsEnum> getEastMap() {
        return eastMap;
    }

    protected Map<WalkingDirectionsEnum, DirectionsEnum> getWestMap() {
        return westMap;
    }

    protected Map<WalkingDirectionsEnum, DirectionsEnum> getSouthMap() {
        return southMap;
    }

    protected Map<DirectionsEnum, Map<WalkingDirectionsEnum, DirectionsEnum>> getDirectionsMap() {
        return directionsMap;
    }


    @Override
    public DirectionsEnum move() {
        Map<WalkingDirectionsEnum, DirectionsEnum> currentDirectionMap = getDirectionsMap().get(getMainDirection());
        if (isHitWall()) {
            setHitWall(false);
            if (getLastStep() == WalkingDirectionsEnum.STRAIGHT) {
                setLastStep(WalkingDirectionsEnum.RIGHT);
                return currentDirectionMap.get(WalkingDirectionsEnum.RIGHT);
            } else if (getLastStep() == WalkingDirectionsEnum.RIGHT) {
                setLastStep(WalkingDirectionsEnum.LEFT);
                return currentDirectionMap.get(WalkingDirectionsEnum.LEFT);
            } else if (getLastStep() == WalkingDirectionsEnum.LEFT) {
                setLastStep(WalkingDirectionsEnum.BOOKMARK);
                addBookmark(currentSequence);
                return DirectionsEnum.BOOKMARK;
            } else {
                setMainDirection(currentDirectionMap.get(WalkingDirectionsEnum.BACK));
                setLastStep(WalkingDirectionsEnum.STRAIGHT);
                return currentDirectionMap.get(WalkingDirectionsEnum.BACK);
            }
        } else {
            setMainDirection(currentDirectionMap.get(getLastStep()));
            setLastStep(WalkingDirectionsEnum.STRAIGHT);
            return getMainDirection();
        }
    }
}