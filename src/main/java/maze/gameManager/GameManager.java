package maze.gameManager;

import maze.FileDataParse.FileData;
import maze.DirectionsEnum;
import maze.player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class GameManager {
    private String[][] mazeWorld;
    private Map<Integer,Map<Point,Integer>> bookMarksMaps;
    private int bookmarksCounter = 0;
    private Point playerLocation;
    private Point treasureLocation;
    private FileData data;
    private Player player;

    public GameManager(String[][] mazeWorld){
        this.mazeWorld = mazeWorld;
        playerLocation = findPlayerLocation();
        treasureLocation = findTreasureLocation();
        bookMarksMaps = new HashMap<>();
        data = new FileData();
        player  = new Player(playerLocation);
        startGame();
    }

    public void startGame(){
//        Player player = new Player(playerLocation);
        DirectionsEnum direction;
        Point currentLocation;
        int i;
        for (i = 0; i < data.getMaxSteps(); i++) {
            System.out.println("Step No: " + i);
            direction = player.move();
            System.out.println("Go Direction: " + direction);
            currentLocation = move(direction);
            if(isTreasure(currentLocation)){
                System.out.println(String.format("Succeeded in %s steps", i+1));
                break;
            }
            if(isWall(currentLocation)){
                player.hitWall();
                playerLocation = currentLocation;
                currentLocation = getBackMove(direction);
                // add location to bookmark map ??? if wantet
            }

            playerLocation = currentLocation;
            System.out.println("Current location:" + (int)playerLocation.getX() + "," +  (int)playerLocation.getY());

        }
        if (i == data.getMaxSteps()){
            System.out.println(String.format("Failed to solve maze in %s steps", i));
        }

    }
    private void addBookmark(int index, Point point){
        Map<Point, Integer> bookmark =  new HashMap();
        bookmark.put(point,1);
        //if already saved same bookmark
        int indexForDelete = deleteAndUpdateIfIsPresetBookmark(index, point);
        if (indexForDelete != -1){
            // update player.hitbookmark about the deletion
            player.hitBookmark(indexForDelete);
        }
        else
            bookMarksMaps.put(index, bookmark);
    }
    private Integer deleteAndUpdateIfIsPresetBookmark(int index, Point point){
        for(Map.Entry bookmark : bookMarksMaps.entrySet()){
            // ignore the last added bookmark
            if((Integer)bookmark.getKey() < bookmarksCounter) {
                Map.Entry<Point, Integer> bm = (Map.Entry<Point, Integer>) bookmark.getValue();
                Integer indexForDelete = (Integer) bookmark.getKey();
                if (bm.getKey().equals(point)) {
                    bookMarksMaps.remove(indexForDelete);
                    Map<Point, Integer> newBookmark = new HashMap();
                    newBookmark.put(bm.getKey(), bm.getValue() + 1);
                    bookMarksMaps.put(index, newBookmark);
                    return indexForDelete;

                }
            }
        }
        return -1 ;
    }
    private Point getBackMove(DirectionsEnum direction){
        switch(direction){
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
    private Point move(DirectionsEnum direction){
        Point newLocation = new Point();
        int temp;
        switch (direction){
            case UP:
                temp  = playerLocation.y - 1;
                if (temp < 0)
                    newLocation = new Point(playerLocation.x, data.getRows()- 1 );
                else
                    newLocation = new Point(playerLocation.x, temp );
                break;
            case DOWN:
                temp = playerLocation.y + 1;
                if (temp >= data.getRows())
                    newLocation = new Point(playerLocation.x, 0);
                else
                    newLocation = new Point(playerLocation.x, temp );
                break;
            case LEFT:
                temp = playerLocation.x - 1;
                if (temp < 0)
                    newLocation = new Point(data.getColumns() - 1, playerLocation.y);
                else
                    newLocation = new Point(temp, playerLocation.y);
                break;
            case RIGHT:
                temp = playerLocation.x + 1;
                if(temp >= data.getColumns())
                    newLocation = new Point(0, playerLocation.y);
                else
                    newLocation = new Point(temp, playerLocation.y);
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
    private boolean isWall(Point point){
        if (mazeWorld[(int)point.getX()][(int)point.getY()].equals("#"))
            return true;
        return false;
    }
    private boolean isTreasure(Point point){
        if (mazeWorld[(int)point.getX()][(int)point.getY()].equals("$"))
            return true;
        return false;
    }

    private Point findPlayerLocation(){
        return findCharAtMaze("@");
    }
    private Point findTreasureLocation(){
        return findCharAtMaze("$");
    }
    private Point findCharAtMaze(String str){
        Point point = new Point();
        for (int i = 0; i < mazeWorld.length; i++) {
            for (int j = 0; j < mazeWorld[i].length; j++) {
                if (mazeWorld[i][j].equals(str) )
                    point = new Point(j,i);
            }
        }
        return point;
    }
}
