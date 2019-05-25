package maze.gameManager;

import maze.fileDataParse.FileData;
import Utils.DirectionsEnum;
import maze.player.PlayerDummy;
import maze.player.PlayerAction;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class GameManagerImpl implements GameManager{

    private static final Logger log = Logger.getLogger(GameManagerImpl.class.getName());
    private String[][] mazeWorld;
    private Map<Point, Integer> bookmarkSequence;
    private int bookmarkCounter = 0;
    private Point playerLocation;
    private Point treasureLocation;
    private FileData data;
    private PlayerAction player;

    public GameManagerImpl(FileData data) {
        this.mazeWorld = data.getMazeWorld();
        playerLocation = data.getPlayerLocation();
        treasureLocation = data.getTreasureLocation();
        bookmarkSequence = new HashMap<>();
        this.data = data;
        player = new PlayerDummy();
        startGame();
    }

    @Override
    public void startGame() {
        DirectionsEnum direction;
        Point currentLocation;
        int timesToPlay;
        for (timesToPlay = 0; timesToPlay < data.getMaxSteps(); timesToPlay++) {
            log.info("Step No: " + timesToPlay);
            direction = player.move();
            log.info("Go Direction: " + direction);
            currentLocation = move(direction);
            if(direction == DirectionsEnum.BOOKMARK){
                addBookmark(currentLocation, bookmarkCounter++);
            }else {
                if (isTreasure(currentLocation)) {
                    log.info(String.format("Succeeded in %s steps", timesToPlay + 1));
                    break;
                } else {
                    if (isWall(currentLocation)) {
                        player.hitWall();
                        playerLocation = currentLocation;
                        currentLocation = getBackMove(direction);
                    }
                    if(isBookmarkLocation(currentLocation)){
                        player.hitBookmark(getBookmarkSequence(currentLocation));
                    }
                }
            }
            playerLocation = currentLocation;
            log.info("Current location:" + (int) playerLocation.getX() + "," + (int) playerLocation.getY());
        }
        if (timesToPlay == data.getMaxSteps()) {
            System.out.println(String.format("Failed to solve maze in %s steps", timesToPlay));
        }
    }

    @Override
    public void addBookmark(Point currentPoint,int currentSequence) {
        bookmarkSequence.put(currentPoint,currentSequence);
    }

    @Override
    public Point move(DirectionsEnum direction) {
        Point newLocation = new Point();
        int newPosition;
        switch (direction) {
            case UP:
                newPosition = playerLocation.y - 1;
                if (newPosition < 0)
                    newLocation = new Point(playerLocation.x, data.getRows() - 1);
                else
                    newLocation = new Point(playerLocation.x, newPosition);
                break;
            case DOWN:
                newPosition = playerLocation.y + 1;
                if (newPosition >= data.getRows())
                    newLocation = new Point(playerLocation.x, 0);
                else
                    newLocation = new Point(playerLocation.x, newPosition);
                break;
            case LEFT:
                newPosition = playerLocation.x - 1;
                if (newPosition < 0)
                    newLocation = new Point(data.getColumns() - 1, playerLocation.y);
                else
                    newLocation = new Point(newPosition, playerLocation.y);
                break;
            case RIGHT:
                newPosition = playerLocation.x + 1;
                if (newPosition >= data.getColumns())
                    newLocation = new Point(0, playerLocation.y);
                else
                    newLocation = new Point(newPosition, playerLocation.y);
                break;
            case BOOKMARK:
                newLocation = new Point(playerLocation.x, playerLocation.y);
                break;
        }
        return newLocation;
    }

    @Override
    public boolean isWall(Point point) {
        return  (mazeWorld[(int) point.getY()][(int) point.getX()].equals("#"));
    }

    @Override
    public boolean isTreasure(Point point) {
        return point.equals(treasureLocation);
    }

    private Point getBackMove(DirectionsEnum direction) {
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
                return new Point();
        }
    }

    private boolean isBookmarkLocation(Point currentLocation) {
        return bookmarkSequence.containsKey(currentLocation);
    }

    private int getBookmarkSequence(Point currentLocation) {
        return bookmarkSequence.get(currentLocation);
    }

}
