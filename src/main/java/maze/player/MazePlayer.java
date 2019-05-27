package maze.player;

import Utils.Enums.DirectionsEnum;
import Utils.Enums.WalkingDirectionsEnum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static Utils.Enums.DirectionsEnum.*;
import static Utils.Enums.WalkingDirectionsEnum.*;

public class MazePlayer extends Player {


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
        this.bookMarks = new HashSet<>(); //map integer to list of directions
        this.lastStep = STRAIGHT;
        this.mainDirection = NORTH;
        this.northMap = new HashMap<>() {{
            put(STRAIGHT, NORTH);
            put(BACK, SOUTH);
            put(RIGHT, EAST);
            put(LEFT, WEST);
        }};
        this.eastMap = new HashMap<>() {{
            put(STRAIGHT, EAST);
            put(BACK, WEST);
            put(RIGHT, SOUTH);
            put(LEFT, NORTH);
        }};
        this.westMap = new HashMap<>() {{
            put(STRAIGHT, WEST);
            put(BACK, EAST);
            put(RIGHT, NORTH);
            put(LEFT, SOUTH);
        }};
        this.southMap = new HashMap<>() {{
            put(STRAIGHT, SOUTH);
            put(BACK, NORTH);
            put(RIGHT, WEST);
            put(LEFT, EAST);
        }};
        this.directionsMap = new HashMap<>() {{
            put(NORTH, northMap);
            put(SOUTH, southMap);
            put(EAST, eastMap);
            put(WEST, westMap);
        }};
    }

    public WalkingDirectionsEnum getLastStep() {
        return lastStep;
    }

    public void setLastStep(WalkingDirectionsEnum lastStep) {
        this.lastStep = lastStep;
    }

    public DirectionsEnum getMainDirection() {
        return mainDirection;
    }

    public void setMainDirection(DirectionsEnum mainDirection) {
        this.mainDirection = mainDirection;
    }

    public Map<WalkingDirectionsEnum, DirectionsEnum> getNorthMap() {
        return northMap;
    }

    public Map<WalkingDirectionsEnum, DirectionsEnum> getEastMap() {
        return eastMap;
    }

    public Map<WalkingDirectionsEnum, DirectionsEnum> getWestMap() {
        return westMap;
    }

    public Map<WalkingDirectionsEnum, DirectionsEnum> getSouthMap() {
        return southMap;
    }

    public Map<DirectionsEnum, Map<WalkingDirectionsEnum, DirectionsEnum>> getDirectionsMap() {
        return directionsMap;
    }


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