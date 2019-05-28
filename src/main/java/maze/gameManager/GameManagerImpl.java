package maze.gameManager;

import Utils.Enums.DirectionsEnum;
import Utils.logging.Logger;
import maze.fileDataParse.FileData;
import Utils.logging.OutputLog;
import maze.player.MazePlayer;
import maze.player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static maze.fileDataParse.FileParse.PLAYER;
import static maze.fileDataParse.FileParse.SPACE;
import static maze.fileDataParse.FileParse.WALL;


public class GameManagerImpl implements GameManager{

    private String[][] mazeWorld;
    protected Map<Point, Integer> bookmarkSequence = new HashMap<>();
    private int bookmarkCounter = 0;
    private final String FOUND = "!";
    private final String NOT_FOUND = "X";
    private Point playerLocation;
    private Point treasureLocation;
    private Point playerPreviousLocation;
    private FileData data;
    private Player player;

    public GameManagerImpl(FileData data) {
        this.mazeWorld = data.getMazeWorld();
        playerLocation = data.getPlayerLocation();
        treasureLocation = data.getTreasureLocation();
        this.data = data;
        player = new MazePlayer();
    }

    protected GameManagerImpl() {   }

    @Override
    public void startGame(OutputLog outputFile) {
        DirectionsEnum direction;
        Point currentLocation = playerLocation;
        int timesToPlay;
        Logger.info("**************** START THE MAZE ****************");
        for (timesToPlay = 0; timesToPlay < data.getMaxSteps(); timesToPlay++) {
            Logger.info("Step No: " + timesToPlay);
            direction = player.move();
            outputFile.writeToOutput(direction.name().charAt(0)+"");
            Logger.info("Player position before move"+ currentLocation.getLocation());
            Logger.info("Go Direction: " + direction);
            currentLocation = move(direction);
            Logger.info("Player position after move "+ currentLocation.getLocation());
            if (direction == DirectionsEnum.BOOKMARK) {
                addBookmark(currentLocation, bookmarkCounter++);
            } else {
                if (isTreasure(currentLocation)) {
                    Logger.info(String.format("Succeeded in %s steps", timesToPlay + 1));
                    outputFile.writeToOutput(FOUND);
                    break;
                } else {
                    if (isWall(currentLocation)) {
                        //TODO Tedy remove all sout to log file.
                        player.hitWall();
                        playerLocation = currentLocation;
                        Logger.info("hit : "+currentLocation);
                        currentLocation = getBackMove(direction);
                        Logger.info("current : "+currentLocation);
                    } else {
                        player.setHitWall(false);
                    }
                    if (isBookmarkLocation(currentLocation)) {
                        player.hitBookmark(getBookmarkSequence(currentLocation));
                    }
                }
            }
            //TODO Tedy remove all sout to log file.
            //playerPreviousLocation = playerLocation;
            playerLocation = currentLocation;
            Logger.info(direction.toString());
            //printMazeWorldAfterChange();
            Logger.info("Current location:" + (int) playerLocation.getX() + "," + (int) playerLocation.getY());
        }
        if (timesToPlay == data.getMaxSteps()) {
            Logger.info(String.format("Failed to solve maze in %s steps", timesToPlay));
            outputFile.writeToOutput(NOT_FOUND);
        }
    }

    @Override
    public void addBookmark(Point currentPoint, int currentSequence) {
        bookmarkSequence.put(currentPoint, currentSequence);
    }

    @Override
    public Point move(DirectionsEnum direction) {
        return movePlayerLocation(direction);
    }

    @Override
    public boolean isWall(Point point) {
    //TODO choose wich one is the correct shoshi.
        return mazeWorld[(int) point.getY()][(int) point.getX()].equals(WALL+"");
//        return mazeWorld[(int) point.getX()][(int) point.getY()].equals("#");
    }

    @Override
    public boolean isTreasure(Point point) {
        return point.equals(treasureLocation);
    }

    protected Point getBackMove(DirectionsEnum direction) {
        switch (direction) {
            case UP:
                return move(DirectionsEnum.DOWN);
            case DOWN:
                return move(DirectionsEnum.UP);
            case RIGHT:
                return move(DirectionsEnum.LEFT);
            case LEFT:
                return move(DirectionsEnum.RIGHT);
            default:
                return move(DirectionsEnum.BOOKMARK);
        }
    }

    public void printMazeWorldAfterChange() {
        if(mazeWorld[(int)playerPreviousLocation.getY()][(int)playerPreviousLocation.getX()].equals(PLAYER+""))
            mazeWorld[(int)playerPreviousLocation.getY()][(int)playerPreviousLocation.getX()] =SPACE+"";
        mazeWorld[(int)playerLocation.getY()][(int)playerLocation.getX()] =PLAYER+"";
        data.printMazeWorld();
        System.out.println("**************************************");
    }
    private boolean isBookmarkLocation(Point currentLocation) {
        return bookmarkSequence.containsKey(currentLocation);
    }

    private int getBookmarkSequence(Point currentLocation) {
        return bookmarkSequence.get(currentLocation);
    }

    protected Point movePlayerLocation(DirectionsEnum directionsEnum) {
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

    protected void setData(FileData data) {
        this.data = data;
    }

    protected void setPlayerLocation(Point playerLocation) {
        this.playerLocation = playerLocation;
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }

}