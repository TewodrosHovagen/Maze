package maze.player;

import Utils.Enums.DirectionsEnum;

public interface PlayerInterface {

    /**
     * Player move logic
     * @return direction to move to
     */
    public DirectionsEnum move();

    /**
     * Method to inform the player that now hitWall
     */
    public void hitWall();

    /**
     * Method to inform the player that moved into preset bookmark
     * @param seq - index of bookmark
     */
    public void hitBookmark(int seq);
}
