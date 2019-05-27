package maze.player;

import Utils.Enums.DirectionsEnum;

public interface PlayerInterface {


    DirectionsEnum move();

    void hitWall();

    void hitBookmark(int seq);
}
