package maze.gameManager;

import Utils.DirectionsEnum;

import java.awt.*;

public interface GameManager {
    public void startGame();
    public void addBookmark(Point currentPoint,int currentSequence);
    public Point move(DirectionsEnum direction) ;
    public boolean isWall(Point point);
    public boolean isTreasure(Point point);

}
