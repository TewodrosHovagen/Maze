package maze.player;

import Utils.Enums.DirectionsEnum;

public interface PlayerAction {


    DirectionsEnum move();

    void hitWall();

    void hitBookmark(int seq);
}
