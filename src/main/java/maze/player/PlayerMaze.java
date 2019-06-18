package maze.player;

import Utils.directionEnum.Enums.MainDirectionsEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Utils.directionEnum.Enums.MainDirectionsEnum.*;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum.*;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum.LEFT;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum.RIGHT;


public class MazePlayer extends Player {


    private WalkingDirectionsEnum lastStep;
    private MainDirectionsEnum mainDirection;
    private boolean isBookMark;
    private int bookMarkCounter = -1;
    private boolean setBookmarkNextMove = true;

    private Map<Integer, List<MainDirectionsEnum>> bookMarkMap;
    final private Map<WalkingDirectionsEnum, MainDirectionsEnum> northMap;
    final private Map<WalkingDirectionsEnum, MainDirectionsEnum> eastMap;
    final private Map<WalkingDirectionsEnum, MainDirectionsEnum> westMap;
    final private Map<WalkingDirectionsEnum, MainDirectionsEnum> southMap;
    final private Map<MainDirectionsEnum, Map<WalkingDirectionsEnum, MainDirectionsEnum>> directionsMap;
    private int bookmarkSeq;


    @Override
    public MainDirectionsEnum move() {
        Map<WalkingDirectionsEnum, MainDirectionsEnum> currentDirectionMap = directionsMap.get(mainDirection);
        MainDirectionsEnum mainDirectionToReturn;
        if (setBookmarkNextMove&&!isBookMark) {
            bookMarkCounter++;
            mainDirectionToReturn = MainDirectionsEnum.BOOKMARK;

            bookMarkMap.computeIfAbsent(bookMarkCounter, k -> new ArrayList<>());
            bookMarkMap.get(bookMarkCounter).add(mainDirection);
            setBookmarkNextMove = false;
        } else if (isHitWall) {
            mainDirectionToReturn = moveByLastStep(currentDirectionMap);
            if(isBookMark) {
                if (!bookMarkMap.get(bookMarkCounter).contains(mainDirectionToReturn)) {
                    bookMarkMap.get(bookMarkCounter).add(mainDirectionToReturn);
                }
                setBookmarkNextMove=false;
                isBookMark=false;
            }
            isHitWall = false;
        }else if (isBookMark) {
            WalkingDirectionsEnum lastStepDirection = lastStep;
            mainDirectionToReturn = currentDirectionMap.get(lastStepDirection);

            if (bookMarkMap.get(bookMarkCounter).contains(mainDirectionToReturn)) {
                if(bookMarkMap.get(bookMarkCounter).size()<3) {
                    mainDirectionToReturn = moveByLastStep(currentDirectionMap);
                    if (!bookMarkMap.get(bookMarkCounter).contains(mainDirectionToReturn)) {
                        bookMarkMap.get(bookMarkCounter).add(mainDirectionToReturn);
                    }
                }

            } else {
                bookMarkMap.get(bookMarkCounter).add(mainDirectionToReturn);
            }

            isBookMark = false;
        } else {
            WalkingDirectionsEnum lastStepDirection = lastStep;
            mainDirectionToReturn = currentDirectionMap.get(lastStepDirection);
            setMainDirection(mainDirectionToReturn);
            setLastStep(STRAIGHT);

        }
        return mainDirectionToReturn;
    }

    @Override
    public void hitWall() {
        System.out.println("You hit the wall");
        isHitWall = true;
        setBookmarkNextMove = true;
    }

    @Override
    public void hitBookmark(int seq) {
        bookmarkSeq = seq;
        isBookMark = true;
    }

    private MainDirectionsEnum moveByLastStep(Map<WalkingDirectionsEnum, MainDirectionsEnum> currentDirectionMap) {
        MainDirectionsEnum directionToReturn;
        if (lastStep == STRAIGHT) {
            setLastStep(RIGHT);
            directionToReturn = currentDirectionMap.get(RIGHT);
        } else if (lastStep == RIGHT) {
            setLastStep(LEFT);
            directionToReturn = currentDirectionMap.get(LEFT);
        } else {
            setMainDirection(currentDirectionMap.get(BACK));
            setLastStep(STRAIGHT);
            directionToReturn = currentDirectionMap.get(BACK);
        }
        return directionToReturn;
    }




    // initialize all player params in c'tor.
    public MazePlayer() {
        super();
        bookMarkMap = new HashMap<>();
        isBookMark = false;
        this.lastStep = STRAIGHT;
        this.mainDirection = UP;
        this.northMap = new HashMap<>() {{
            put(STRAIGHT, UP);
            put(BACK, DOWN);
            put(RIGHT, MainDirectionsEnum.RIGHT);
            put(LEFT, MainDirectionsEnum.LEFT);
        }};
        this.eastMap = new HashMap<>() {{
            put(STRAIGHT, MainDirectionsEnum.RIGHT);
            put(BACK, MainDirectionsEnum.LEFT);
            put(RIGHT, DOWN);
            put(LEFT, UP);
        }};
        this.westMap = new HashMap<>() {{
            put(STRAIGHT, MainDirectionsEnum.LEFT);
            put(BACK, MainDirectionsEnum.RIGHT);
            put(RIGHT, UP);
            put(LEFT, DOWN);
        }};
        this.southMap = new HashMap<>() {{
            put(STRAIGHT, DOWN);
            put(BACK, UP);
            put(RIGHT, MainDirectionsEnum.LEFT);
            put(LEFT, MainDirectionsEnum.RIGHT);
        }};
        this.directionsMap = new HashMap<>() {{
            put(UP, northMap);
            put(DOWN, southMap);
            put(MainDirectionsEnum.RIGHT, eastMap);
            put(MainDirectionsEnum.LEFT, westMap);
        }};
    }

    public void setLastStep(WalkingDirectionsEnum lastStep) {
        this.lastStep = lastStep;
    }

    public void setMainDirection(MainDirectionsEnum mainDirection) {
        this.mainDirection = mainDirection;
    }

    protected Map<WalkingDirectionsEnum, MainDirectionsEnum> getNorthMap() {
        return northMap;
    }

    protected Map<WalkingDirectionsEnum, MainDirectionsEnum> getEastMap() {
        return eastMap;
    }

    protected Map<WalkingDirectionsEnum, MainDirectionsEnum> getWestMap() {
        return westMap;
    }

    protected Map<WalkingDirectionsEnum, MainDirectionsEnum> getSouthMap() {
        return southMap;
    }

    protected Map<MainDirectionsEnum, Map<WalkingDirectionsEnum, MainDirectionsEnum>> getDirectionsMap() {
        return directionsMap;
    }
}