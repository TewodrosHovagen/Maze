package maze.gameManager;

import Utils.directionEnum.Enums;
import maze.player.Player;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public abstract class GameManager implements GameManagerInterface{

    Map<Point, Integer> bookmarkSequence = new HashMap<>();
    protected Player player;
    protected MazeData data;

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
    protected abstract Point move(Enums.MainDirectionsEnum direction);

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

    /**
     * The game result handler deals with the game output value.
     */
    protected abstract void gameResultHandler();

    protected abstract void gameResultHandler(String out);

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MazeData getData() {
        return data;
    }

    public void setData(MazeData data) {
        this.data = data;
    }
}
