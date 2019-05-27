package maze.gameManager;

import Utils.Enums.DirectionsEnum;
import maze.fileDataParse.FileData;
import Utils.logging.OutputLog;
import maze.player.Player;
import maze.player.PlayerDummy;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class GameManagerImpl implements GameManager{

    private static final Logger log = Logger.getLogger(GameManagerImpl.class.getName());
    private String[][] mazeWorld;
    protected Map<Point, Integer> bookmarkSequence = new HashMap<>();
    private int bookmarkCounter = 0;
    private Point playerLocation;
    private Point treasureLocation;
    private FileData data;
    private Player player;

    public GameManagerImpl(FileData data) {
        this.mazeWorld = data.getMazeWorld();
        playerLocation = data.getPlayerLocation();
        treasureLocation = data.getTreasureLocation();
        this.data = data;
        player = new PlayerDummy();
        startGame();
    }

    protected GameManagerImpl() {

    }

    @Override
    public void startGame() {
        DirectionsEnum direction;
        Point currentLocation = playerLocation;
        int timesToPlay;
        log.info("**************** START THE MAZE ****************");
        for (timesToPlay = 0; timesToPlay < data.getMaxSteps(); timesToPlay++) {
            log.info("Step No: " + timesToPlay);
            direction = player.move();
            OutputLog.writeToOutput(direction.name().charAt(0)+"");
            log.info("Player position before move"+ currentLocation.getLocation());
            log.info("Go Direction: " + direction);
            currentLocation = move(direction);
            log.info("Player position after move "+ currentLocation.getLocation());
            if (direction == DirectionsEnum.BOOKMARK) {
                addBookmark(currentLocation, bookmarkCounter++);
            } else {
                if (isTreasure(currentLocation)) {
                    System.out.println(String.format("Succeeded in %s steps", timesToPlay + 1));
                    OutputLog.writeToOutput("!");
                    break;
                } else {
                    if (isWall(currentLocation)) {
                        //TODO Tedy remove all sout to log file.
                        player.hitWall();
                        playerLocation = currentLocation;
                        System.out.println("hit : "+currentLocation);
                        currentLocation = getBackMove(direction);
                        System.out.println("current : "+currentLocation);
                    } else {
                        player.setHitWall(false);
                    }
                    if (isBookmarkLocation(currentLocation)) {
                        player.hitBookmark(getBookmarkSequence(currentLocation));
                    }
                }
            }
            //TODO Tedy remove all sout to log file.
            playerLocation = currentLocation;
            System.out.println(player.getMainDirection());
            System.out.println(direction);
            printMazeWorldAfterChange();
            log.info("Current location:" + (int) playerLocation.getX() + "," + (int) playerLocation.getY());
            OutputLog.writeToOutput("X");
        }
        if (timesToPlay == data.getMaxSteps()) {
            System.out.println(String.format("Failed to solve maze in %s steps", timesToPlay));
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
        //return mazeWorld[(int) point.getY()][(int) point.getX()].equals("#");
        return mazeWorld[(int) point.getX()][(int) point.getY()].equals("#");
    }

    @Override
    public boolean isTreasure(Point point) {
        return point.equals(treasureLocation);
    }

    private Point getBackMove(DirectionsEnum direction) {
        switch (direction) {
            case NORTH:
                return move(DirectionsEnum.SOUTH);
            case SOUTH:
                return move(DirectionsEnum.NORTH);
            case EAST:
                return move(DirectionsEnum.WEST);
            case WEST:
                return move(DirectionsEnum.EAST);
            default:
                return new Point();
        }
    }

    public void printMazeWorldAfterChange() {

        for (int i = 0; i < mazeWorld.length * mazeWorld[0].length; i++) {
            for (int j = 0; j < mazeWorld[0].length; j++) {
                if (mazeWorld[i][j].equals("@")) {
                    mazeWorld[i][j] = " ";
                    mazeWorld[playerLocation.x][playerLocation.y] = "@";
                    //TODO Tedy fix printMaze after change.
//                    printMazeWorld();
                    return;
                }
            }
        }
    }

    public void addtMazeWorldBookMark() {
        //TODO Tedy add bookMark to map if needed.
        mazeWorld[playerLocation.x][playerLocation.y] = "b";
        printMazeWorldAfterChange();
    }


    private boolean isBookmarkLocation(Point currentLocation) {
        return bookmarkSequence.containsKey(currentLocation);
    }

    private int getBookmarkSequence(Point currentLocation) {
        return bookmarkSequence.get(currentLocation);
    }

    private Point movePlayerLocation(DirectionsEnum directionsEnum) {
        Point newLocation = null;
        switch (directionsEnum) {
            case NORTH:
                int newPosition = playerLocation.y + 1;
                if (newPosition >= data.getColumns())
                    newLocation = new Point(playerLocation.x,0);
                else
                    newLocation = new Point(playerLocation.x,newPosition);
                break;
            case SOUTH:
                newPosition = playerLocation.y - 1;
                if (newPosition < 0)
                    newLocation = new Point(playerLocation.x,data.getColumns()-1);
                else
                    newLocation = new Point(playerLocation.x, newPosition);
                break;
            case WEST:
                newPosition = playerLocation.x- 1;
                if (newPosition < 0)
                    newLocation = new Point(data.getRows()-1, playerLocation.y);
                else
                    newLocation = new Point(newPosition, playerLocation.y);
                break;
            case EAST:
                newPosition = playerLocation.x + 1;
                if (newPosition >=data.getRows())
                    newLocation = new Point(0, playerLocation.y);
                else
                    newLocation = new Point(newPosition, playerLocation.y);
                break;
            case BOOKMARK:
//                newLocation = new Point(playerLocation.x, playerLocation.y);
                break;
        }
        return newLocation;
    }
}