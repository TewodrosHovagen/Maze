package maze.player;

import Utils.directionEnum.Enums.MainDirectionsEnum;

public interface PlayerInterface {

    /**
     * Player move logic
     * @return direction to move to
     */
    MainDirectionsEnum move();

    /**
     * Method to inform the player that now hitWall
     */
    void hitWall();

    /**
     * Method to inform the player that moved into preset bookmark
     * @param seq - index of bookmark
     */
    void hitBookmark(int seq);
}
