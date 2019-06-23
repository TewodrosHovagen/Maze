package maze.gameManager;

import utils.directionEnum.Enums;
import utils.logging.Logger;
import utils.reports.SingleGameOutputFile;
import maze.player.Player;
import maze.player.PlayerRandom;

import java.awt.Point;
import static maze.fileDataParse.FileParse.*;


public class GameManagerImpl extends GameManager {
    
    private final Logger log = Logger.getInstance();
    private String[][] mazeWorld;
    private final String FOUND = "!";
    private final String NOT_FOUND = "X";
    private Point treasureLocation;
    private SingleGameOutputFile outputFile;
    private String result;
    private int bookmarkCounter = 0;
    private Point playerLocation;
    private Point playerPreviousLocation;
    protected int timesToPlay;

    public GameManagerImpl(MazeData data) {
        this(data, new PlayerRandom());
    }

    GameManagerImpl(MazeData data, Player player) {
        this.mazeWorld = data.getMazeWorld();
        playerLocation = data.getPlayerLocation();
        playerPreviousLocation= playerLocation;
        treasureLocation = data.getTreasureLocation();
        this.mazeData = data;
        this.player = player;
    }

    protected GameManagerImpl() {   }

    @Override
    public void runGame() {
        Enums.MainDirectionsEnum direction;
        Point currentLocation = playerLocation;
        log.info("**************** START THE MAZE ****************");
        for (timesToPlay = 0; timesToPlay < mazeData.getMaxSteps(); timesToPlay++) {
            log.info("Step No: " + timesToPlay);
            direction = player.move();
            gameResultHandler(direction.name().charAt(0)+"");
            log.info("Player position before move"+ currentLocation.getLocation());
            log.info("Go Direction: " + direction);
            currentLocation = move(direction);
            log.info("Player position after move "+ currentLocation.getLocation());
            if (direction == Enums.MainDirectionsEnum.BOOKMARK) {
                addBookmark(currentLocation, bookmarkCounter++);
            } else {
                if (isTreasure(currentLocation)) {
                    log.info(String.format("Succeeded in %s steps", timesToPlay + 1));
                    result = FOUND;
                    gameResultHandler();
                    break;
                } else {
                    if (isWall(currentLocation)) {
                        player.hitWall();
                        log.info("hit : "+currentLocation);
                        currentLocation = playerLocation;
                        log.info("current : "+currentLocation);
                    }
                    if (isBookmarkLocation(currentLocation)) {
                        player.hitBookmark(getBookmarkSequence(currentLocation));
                    }
                }
            }
            if(!(currentLocation==playerPreviousLocation)) {
                playerPreviousLocation.setLocation(playerLocation);
            }
            playerLocation = currentLocation;
            log.info(direction.toString());
//            if (direction!= Enums.MainDirectionsEnum.BOOKMARK) printMazeWorldAfterChange();
            log.info("Current location:" + (int) playerLocation.getX() + "," + (int) playerLocation.getY());
        }
        if (timesToPlay == mazeData.getMaxSteps()) {
            log.info(String.format("Failed to solve maze in %s steps", timesToPlay));
            result = NOT_FOUND;
            gameResultHandler();
        }
    }

    @Override
    protected Point move(Enums.MainDirectionsEnum direction) {
        return movePlayerLocation(direction);
    }

    @Override
    protected boolean isWall(Point point) {
        return mazeWorld[(int) point.getY()][(int) point.getX()].equals(WALL+"");
    }

    @Override
    protected boolean isTreasure(Point point) {
        return point.equals(treasureLocation);
    }

    @Override
    protected void gameResultHandler() {
        outputFile.writeToOutput(result);
    }

    @Override
    protected void gameResultHandler(String out) {
        outputFile.writeToOutput(out);
    }


    protected Point getBackMove(Enums.MainDirectionsEnum direction) {
        switch (direction) {
            case UP:
                return move(Enums.MainDirectionsEnum.DOWN);
            case DOWN:
                return move(Enums.MainDirectionsEnum.UP);
            case RIGHT:
                return move(Enums.MainDirectionsEnum.LEFT);
            case LEFT:
                return move(Enums.MainDirectionsEnum.RIGHT);
            default:
                return move(Enums.MainDirectionsEnum.BOOKMARK);
        }
    }

//    protected Point getBackMove(Enums.MainDirectionsEnum direction) {
//        switch (direction) {
//            case UP:
//                return move(Enums.MainDirectionsEnum.DOWN);
//            case DOWN:
//                return move(Enums.MainDirectionsEnum.UP);
//            case RIGHT:
//                return move(Enums.MainDirectionsEnum.LEFT);
//            case LEFT:
//                return move(Enums.MainDirectionsEnum.RIGHT);
//            default:
//                return move(Enums.MainDirectionsEnum.BOOKMARK);
//        }
//    }


    protected Point movePlayerLocation(Enums.MainDirectionsEnum directionsEnum) {
        return Enums.MainDirectionsEnum.move(directionsEnum,new Point(playerLocation.getLocation()), mazeData);
    }

    protected void setPlayerLocation(Point playerLocation) {
        this.playerLocation = playerLocation;
    }

    public void setOutputFile(SingleGameOutputFile outputFile) { this.outputFile = outputFile;  }

    public void printMazeWorldAfterChange() {
        if(mazeWorld[(int)playerPreviousLocation.getY()][(int)playerPreviousLocation.getX()].equals(PLAYER+""))
            mazeWorld[(int)playerPreviousLocation.getY()][(int)playerPreviousLocation.getX()] =SPACE+"";
        mazeWorld[(int)playerLocation.getY()][(int)playerLocation.getX()] =PLAYER+"";
        mazeData.printMazeWorld();
        System.out.println("**************************************");
    }
}