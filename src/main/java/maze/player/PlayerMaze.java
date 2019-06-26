package maze.player;

import maze.player.directionEnum.playerEnum;
import utils.directionEnum.Enums;
import utils.directionEnum.Enums.MainDirectionsEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.directionEnum.Enums.MainDirectionsEnum.DOWN;
import static utils.directionEnum.Enums.MainDirectionsEnum.UP;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum.BACK;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum.LEFT;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum.RIGHT;
import static maze.player.directionEnum.playerEnum.WalkingDirectionsEnum.STRAIGHT;


public class PlayerMaze extends Player {


    private boolean isBookMark;
    private int bookMarkCounter = -1;
    protected boolean setBookmarkNextMove = true;

    private Map<Integer, List<MainDirectionsEnum>> bookMarkMap;
    final private Map<WalkingDirectionsEnum, MainDirectionsEnum> northMap;
    final private Map<WalkingDirectionsEnum, MainDirectionsEnum> eastMap;
    final private Map<WalkingDirectionsEnum, MainDirectionsEnum> westMap;
    final private Map<WalkingDirectionsEnum, MainDirectionsEnum> southMap;
    final private Map<MainDirectionsEnum, Map<WalkingDirectionsEnum, MainDirectionsEnum>> directionsMap;
    private int bookmarkSeq;
    private boolean fullBookMark;
    private MainDirectionsEnum lastMainDirectionToReturn;

    public void setLastMainDirectionToReturn(MainDirectionsEnum lastMainDirectionToReturn) {
        this.lastMainDirectionToReturn = lastMainDirectionToReturn;
    }

    @Override
    public MainDirectionsEnum move() {
        Map<WalkingDirectionsEnum, MainDirectionsEnum> currentDirectionMap = directionsMap.get(mainDirection);
        MainDirectionsEnum mainDirectionToReturn;
        WalkingDirectionsEnum lastStepDirection;
        if (setBookmarkNextMove) {
            if (!isBookMark) {
                bookMarkCounter++;
                mainDirectionToReturn = MainDirectionsEnum.BOOKMARK;
                bookMarkMap.computeIfAbsent(bookMarkCounter, k -> new ArrayList<>());
                if(isLastMoveHitWall()) {
                    bookMarkMap.get(bookMarkCounter).add(backStep.get(mainDirection));
                }else {bookMarkMap.get(bookMarkCounter).add(mainDirection);}
                setBookmarkNextMove = false;
            } else {
                mainDirectionToReturn = getMainDirectionsEnum(currentDirectionMap, mainDirection);
                if(bookMarkMap.get(bookmarkSeq).size()< 3) {
                    setBookmarkNextMove = false;
                }
                isBookMark = false;
                clearHitWall();
            }
        } else if (isLastMoveHitWall()) {
            mainDirectionToReturn = moveByLastStep(currentDirectionMap);
            if (isBookMark) {
                mainDirectionToReturn = getMainDirectionsEnum(currentDirectionMap, mainDirectionToReturn);
                isBookMark = false;
            }
            clearHitWall();
        } else if (isBookMark) {
            mainDirectionToReturn = currentDirectionMap.get(lastStep);
            mainDirectionToReturn = getMainDirectionsEnum(currentDirectionMap, mainDirectionToReturn);
            isBookMark = false;
        } else {
            lastStepDirection = lastStep;
            mainDirectionToReturn = currentDirectionMap.get(lastStepDirection);
            setMainDirection(mainDirectionToReturn);
            setLastStep(STRAIGHT);

        }
        return mainDirectionToReturn;
    }

    private MainDirectionsEnum getMainDirectionsEnum(Map<WalkingDirectionsEnum, MainDirectionsEnum> currentDirectionMap, MainDirectionsEnum mainDirectionToReturn) {
        int bookMarkSize = bookMarkMap.get(bookmarkSeq).size();
        if (bookMarkSize == 4) {
            mainDirectionToReturn = backStep.get(mainDirectionToReturn);
            setBookmarkNextMove = true;
        }
        if (bookMarkSize == 3) {
            mainDirectionToReturn = moveByLastStep(currentDirectionMap);
            bookMarkMap.get(bookmarkSeq).add(mainDirectionToReturn);
            setBookmarkNextMove = true;
        }
        if (bookMarkSize < 3) {
            if (bookMarkMap.get(bookmarkSeq).contains(mainDirectionToReturn)) {

                while (bookMarkMap.get(bookmarkSeq).contains(mainDirectionToReturn)) {
                    mainDirectionToReturn = moveByLastStep(currentDirectionMap);
                }
            }
            bookMarkMap.get(bookmarkSeq).add(mainDirectionToReturn);
        }
        return mainDirectionToReturn;
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

    private Map<MainDirectionsEnum, MainDirectionsEnum> backStep = new HashMap<>() {{
        put(MainDirectionsEnum.UP, MainDirectionsEnum.DOWN);
        put(MainDirectionsEnum.DOWN, MainDirectionsEnum.UP);
        put(MainDirectionsEnum.RIGHT, MainDirectionsEnum.LEFT);
        put(MainDirectionsEnum.LEFT, MainDirectionsEnum.RIGHT);
    }};

    // initialize all player params in c'tor.
    public PlayerMaze() {
        super();
        bookMarkMap = new HashMap<>();
        isBookMark = false;
        this.lastStep = STRAIGHT;
        this.mainDirection = UP;
        lastMainDirectionToReturn = mainDirection;
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

    @Override
    protected void hitWallHandler() {
        setBookmarkNextMove = true;
    }

    @Override
    public void hitBookmark(int seq) {
        bookmarkSeq = seq;
        isBookMark = true;
    }


}