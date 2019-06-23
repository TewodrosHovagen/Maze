package maze.player;

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
    private boolean setBookmarkNextMove = true;

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
        if (setBookmarkNextMove && !isBookMark) {
            bookMarkCounter++;
            mainDirectionToReturn = MainDirectionsEnum.BOOKMARK;

            bookMarkMap.computeIfAbsent(bookMarkCounter, k -> new ArrayList<>());
            bookMarkMap.get(bookMarkCounter).add(mainDirection);
            if(fullBookMark){
                bookMarkMap.get(bookMarkCounter).add(lastMainDirectionToReturn);
                fullBookMark=false;
            }
            setBookmarkNextMove = false;
        } else if (isHitWall) {
            mainDirectionToReturn = moveByLastStep(currentDirectionMap);
            if (isBookMark) {
                if (!bookMarkMap.get(bookMarkCounter).contains(mainDirectionToReturn)) {
                    bookMarkMap.get(bookMarkCounter).add(mainDirectionToReturn);
                }
                setBookmarkNextMove = false;
                isBookMark = false;
            }
            isHitWall = false;
        } else if (isBookMark) {
            WalkingDirectionsEnum lastStepDirection = lastStep;
            mainDirectionToReturn = currentDirectionMap.get(lastStepDirection);
            if (bookMarkMap.get(bookMarkCounter).size() <= 3) {
            if (bookMarkMap.get(bookMarkCounter).contains(mainDirectionToReturn)) {
                    do {
                        mainDirectionToReturn = moveByLastStep(currentDirectionMap);
                    }while (!bookMarkMap.get(bookMarkCounter).contains(mainDirectionToReturn));
                        bookMarkMap.get(bookMarkCounter).add(mainDirectionToReturn);
                    }

            } else {
                mainDirectionToReturn=backStep.get(mainDirectionToReturn);
                fullBookMark=true;
                setBookmarkNextMove=true;
            }
            isBookMark = false;
        } else {
            WalkingDirectionsEnum lastStepDirection = lastStep;
            mainDirectionToReturn = currentDirectionMap.get(lastStepDirection);
            setLastMainDirectionToReturn(mainDirection);
            setMainDirection(mainDirectionToReturn);
            setLastStep(STRAIGHT);

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
    public void hitWall() {
//        System.out.println("You hit the wall");
        isHitWall = true;
        setBookmarkNextMove = true;
    }

    @Override
    public void hitBookmark(int seq) {
        bookmarkSeq = seq;
        isBookMark = true;
    }


}