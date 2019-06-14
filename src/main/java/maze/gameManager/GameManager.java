package maze.gameManager;

import Utils.Enums;
import maze.fileDataParse.FileData;
import maze.player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class GameManager implements GameManagerInterface{

    Map<Point, Integer> bookmarkSequence = new HashMap<>();
    protected Player player;
    protected FileData data;

    /**
     * Add bookmark to bookmark map
     * @param currentPoint - location to bookmark
     * @param currentSequence - index per each bookmark
     */

    void addBookmark(Point currentPoint, int currentSequence) {
        bookmarkSequence.put(currentPoint, currentSequence);
    }

    /**
     * The method check if the given point is location on a bookmark position
     * @param currentLocation
     * @return
     */
    boolean isBookmarkLocation(Point currentLocation) {
        return bookmarkSequence.containsKey(currentLocation);
    }

    /**
     * The method get a location and from the location return it's sequence
     * @param currentLocation - Point of the bookmark location.
     * @return - sequence value.
     */
    int getBookmarkSequence(Point currentLocation) {
        return bookmarkSequence.get(currentLocation);
    }


    /**
     * Manager move functionality
     * @param direction - direction got from player
     * @return - point of location after move
     */
    protected abstract Point move(Enums.DirectionsEnum direction);

    /**
     * Manager check if location point is marked as wall
     * @param point - location point
     * @return true of indeed wall
     */
    protected abstract boolean isWall(Point point);

    /**
     * Manager check if location point is marked as treasure
     * @param point - location point
     * @return true of indeed treasure
     */
    protected abstract boolean isTreasure(Point point);

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public FileData getData() {
        return data;
    }

    public void setData(FileData data) {
        this.data = data;
    }
}
