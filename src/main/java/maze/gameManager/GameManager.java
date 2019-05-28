package maze.gameManager;

import Utils.Enums.*;

import java.awt.*;

public interface GameManager {
    /**
     * Start the game
     */
    public void startGame();

    /**
     * Add bookmark to bookmark map
     * @param currentPoint - location to bookmark
     * @param currentSequence - index per each boomark
     */
    public void addBookmark(Point currentPoint,int currentSequence);

    /**
     * Manager move functinality
     * @param direction - direction got from player
     * @return - point of location after move
     */
    public Point move(DirectionsEnum direction) ;

    /**
     * Manager check if location point is marked as wall
     * @param point - location point
     * @return true of indeed wall
     */
    public boolean isWall(Point point);

    /**
     * Manager check if location point is marked as treasure
     * @param point - location point
     * @return true of indeed treasure
     */
    public boolean isTreasure(Point point);

}
