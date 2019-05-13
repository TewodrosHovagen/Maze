package maze.gameManager;

import maze.FileDataParse.FileData;
import Utils.DirectionsEnum;
import maze.FileDataParse.FileParse;
import maze.player.Player;
import maze.player.PlayerInterface;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class GameManagerVers1 implements GameManager{

    private static final Logger log = Logger.getLogger(GameManagerVers1.class.getName());
    private String[][] mazeWorld;
    private Map<Integer, Map<Point, Integer>> bookMarksMaps;
    private int bookmarksCounter = 0;
    private Point playerLocation;
    private Point treasureLocation;
    private FileData data;
    private PlayerInterface player;

    public GameManagerVers1(FileData data) {
        this.mazeWorld = data.getMazeWorld();
        playerLocation = data.getPlayerLocation();
        treasureLocation = data.getTreasureLocation();
        bookMarksMaps = new HashMap<>();
        this.data = data;
        player = new Player();
        startGame();
    }

    @Override
    public void startGame() {
//        BasicPlayer player = new BasicPlayer(playerLocation);
        DirectionsEnum direction;
        Point currentLocation;
        int timesToPlay;
        for (timesToPlay = 0; timesToPlay < data.getMaxSteps(); timesToPlay++) {
            log.info("Step No: " + timesToPlay);
            direction = player.move();
            log.info("Go Direction: " + direction);
            currentLocation = move(direction);
            if (isTreasure(currentLocation)) {
                log.info(String.format("Succeeded in %s steps", timesToPlay + 1));
                break;
            }else {
                if (isWall(currentLocation)) {
                    player.hitWall();
                    playerLocation = currentLocation;
                    currentLocation = getBackMove(direction);
                    // add location to bookmark map ??? if wantet
                }
                addBookmark(++bookmarksCounter, currentLocation);
                playerLocation = currentLocation;
                log.info("Current location:" + (int) playerLocation.getX() + "," + (int) playerLocation.getY());
            }
        }
        if (timesToPlay == data.getMaxSteps()) {
            System.out.println(String.format("Failed to solve maze in %s steps", timesToPlay));
        }

    }

    @Override
    public void addBookmark(int index, Point point) {
        Map<Point, Integer> bookmark = new HashMap();
        bookmark.put(point, 1);
        //if already saved same bookmark
        int indexForDelete = deleteAndUpdateIfIsPresetBookmark(index, point);
        if (indexForDelete != -1) {
            // update player.hitbookmark about the deletion
            player.hitBookmark(indexForDelete);
        } else {
            bookMarksMaps.put(index, bookmark);
            player.hitBookmark(index);
        }
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
                addBookmark(++bookmarksCounter, newLocation);
                // update player about new bookmark
                // player.hitBookmark(bookmarksCounter);
                break;


        }

        return newLocation;
    }

    @Override
    public boolean isWall(Point point) {
        if (mazeWorld[(int) point.getY()][(int) point.getX()].equals("#"))
                return true;
        return false;
    }

    @Override
    public boolean isTreasure(Point point) {
        if (mazeWorld[(int) point.getY()][(int) point.getX()].equals("$"))
            return true;
        return false;
    }
//
//
//    private Point findCharAtMaze(String str) {
//        Point point = new Point();
//        for (int i = 0; i < mazeWorld.length; i++) {
//            for (int j = 0; j < mazeWorld[i].length; j++) {
//                if (mazeWorld[i][j].equals(str))
//                    point = new Point(j, i);
//            }
//        }
//        return point;
//    }


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

    private Integer deleteAndUpdateIfIsPresetBookmark(int index, Point point) {
        if(bookMarksMaps.entrySet().size()>0) {
            for (Map.Entry bookmark : bookMarksMaps.entrySet()) {
                // ignore the last added bookmark
                if ((Integer) bookmark.getKey() < bookmarksCounter) {
                    Map<Point, Integer> bm = new HashMap();
                    bm = (HashMap<Point, Integer>) bookmark.getValue();
                    Integer indexForDelete = (Integer) bookmark.getKey();
                    for(Map.Entry bb : bm.entrySet()){
                        if (bb.getKey().equals(point)) {
                            bookMarksMaps.remove(indexForDelete);
                            Map<Point, Integer> newBookmark = new HashMap();
                            newBookmark.put((Point) bb.getKey(), (Integer) bb.getValue() + 1);
                            bookMarksMaps.put(index, newBookmark);
                            return indexForDelete;

                        }
                    }

                }
            }
        }
        return -1;
    }

}
