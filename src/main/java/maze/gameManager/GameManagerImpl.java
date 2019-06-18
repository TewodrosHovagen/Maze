package maze.gameManager;

import Utils.directionEnum.Enums;
import Utils.logging.Logger;
import Utils.logging.OutputLog;
import maze.fileDataParse.FileData;
import maze.player.PlayerMaze;
import maze.player.Player;
import maze.player.PlayerRandom;

import java.awt.*;
import java.util.Calendar;

import static maze.fileDataParse.FileParse.PLAYER;
import static maze.fileDataParse.FileParse.SPACE;
import static maze.fileDataParse.FileParse.WALL;


public class GameManagerImpl extends GameManager {
    
    private final Logger log = Logger.getInstance();
    private String[][] mazeWorld;
    private final String FOUND = "!";
    private final String NOT_FOUND = "X";
    private Point treasureLocation;
    private OutputLog outputFile;
    private String result;
    private int bookmarkCounter = 0;
    private Point playerLocation;
    private Point playerPreviousLocation;
    protected int timesToPlay;

    public GameManagerImpl(FileData data) {
        this(data, new PlayerRandom());
    }

    GameManagerImpl(FileData data, Player player) {
        this.mazeWorld = data.getMazeWorld();
        playerLocation = data.getPlayerLocation();
        treasureLocation = data.getTreasureLocation();
        this.data = data;
        this.player = player;
    }

    protected GameManagerImpl() {   }

    @Override
    public void startGame() {
        Enums.MainDirectionsEnum direction;
        Point currentLocation = playerLocation;
        log.info("**************** START THE MAZE ****************");
        for (timesToPlay = 0; timesToPlay < data.getMaxSteps(); timesToPlay++) {
            log.info("Step No: " + timesToPlay);
            direction = player.move();
            outputFile.writeToOutput(direction.name().charAt(0)+"");
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
                        playerLocation = currentLocation;
                        log.info("hit : "+currentLocation);
                        currentLocation = getBackMove(direction);
                        log.info("current : "+currentLocation);
                    }
                    if (isBookmarkLocation(currentLocation)) {
                        player.hitBookmark(getBookmarkSequence(currentLocation));
                    }
                }
            }
//            playerPreviousLocation = playerLocation;
            playerLocation = currentLocation;
            log.info(direction.toString());
//            if (direction!= Enums.MainDirectionsEnum.BOOKMARK) printMazeWorldAfterChange();
            log.info("Current location:" + (int) playerLocation.getX() + "," + (int) playerLocation.getY());
        }
        if (timesToPlay == data.getMaxSteps()) {
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


    protected Point movePlayerLocation(Enums.MainDirectionsEnum directionsEnum) {
        Point newLocation = null;
        switch (directionsEnum) {
            case DOWN:
                int newPosition = playerLocation.y + 1;
                if (newPosition >= data.getRows()) {
                    newLocation = new Point(playerLocation.x, 0);
                }else {
                    newLocation = new Point(playerLocation.x, newPosition);
                }
                break;
            case UP:
                newPosition = playerLocation.y - 1;
                if (newPosition < 0) {
                    newLocation = new Point(playerLocation.x, data.getRows() - 1);
                }else {
                    newLocation = new Point(playerLocation.x, newPosition);
                }
                break;
            case LEFT:
                newPosition = playerLocation.x - 1;
                if (newPosition < 0) {
                    newLocation = new Point(data.getColumns() - 1, playerLocation.y);
                }
                else {
                    newLocation = new Point(newPosition, playerLocation.y);
                }
                break;
            case RIGHT:
                newPosition = playerLocation.x + 1;
                if (newPosition >= data.getColumns()) {
                    newLocation = new Point(0, playerLocation.y);
                }else {
                    newLocation = new Point(newPosition, playerLocation.y);
                }
                break;
            case BOOKMARK:
                newLocation = new Point(playerLocation.x, playerLocation.y);
                break;
        }
        return newLocation;
    }

    protected void setPlayerLocation(Point playerLocation) {
        this.playerLocation = playerLocation;
    }

    public void setOutputFile(OutputLog outputFile) { this.outputFile = outputFile;  }

    public void printMazeWorldAfterChange() {
        if(mazeWorld[(int)playerPreviousLocation.getY()][(int)playerPreviousLocation.getX()].equals(PLAYER+""))
            mazeWorld[(int)playerPreviousLocation.getY()][(int)playerPreviousLocation.getX()] =SPACE+"";
        mazeWorld[(int)playerLocation.getY()][(int)playerLocation.getX()] =PLAYER+"";
        data.printMazeWorld();
        System.out.println("**************************************");
    }
}