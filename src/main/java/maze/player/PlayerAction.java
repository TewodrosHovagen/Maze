package maze.player;

import Utils.DirectionsEnum;

public interface PlayerAction {


    public DirectionsEnum move();

    public void hitWall();

    public void hitBookmark(int seq);
}
