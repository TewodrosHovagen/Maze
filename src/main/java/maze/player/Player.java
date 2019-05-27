package maze.player;

import Utils.Enums.*;
import Utils.logging.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static Utils.Enums.DirectionsEnum.*;
import static Utils.Enums.WalkingDirectionsEnum.*;

public abstract class Player implements PlayerAction {

    // initialize all player params in c'tor.
    public Player() {
        this.isHitWall = false;
        this.bookMarks = new HashSet<>();
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

    private boolean isHitWall;
    private Set<Integer> bookMarks;
    private WalkingDirectionsEnum lastStep;
    private DirectionsEnum mainDirection;
    final private Map<WalkingDirectionsEnum, DirectionsEnum> northMap;
    final private Map<WalkingDirectionsEnum, DirectionsEnum> eastMap;
    final private Map<WalkingDirectionsEnum, DirectionsEnum> westMap;
    final private Map<WalkingDirectionsEnum, DirectionsEnum> southMap;
    final private Map<DirectionsEnum, Map<WalkingDirectionsEnum, DirectionsEnum>> directionsMap;


    public boolean isHitWall() {
        return isHitWall;
    }

    public void setHitWall(boolean hitWall) {
        isHitWall = hitWall;
    }

    public Set<Integer> getBookMarks() {
        return bookMarks;
    }

    public void setBookMarks(Set<Integer> bookMarks) {
        this.bookMarks = bookMarks;
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
    public void hitWall() {
        Logger.info("You hit the wall");
        isHitWall = true;
    }


    @Override
    public void hitBookmark(int seq) {
        bookMarks.add(seq);
    }
}

