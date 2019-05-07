package maze.gameManager;

import maze.FileDataParse.FileData;
import maze.DirectionsEnum;
import maze.player.BasicPlayer;
import maze.player.LocationSavePlayer;
import maze.player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class GameManagerVers2 implements GameManager{

    @Override
    public void startGame() {

    }

    @Override
    public void addBookmark(int index, Point point) {

    }

    @Override
    public Point move(DirectionsEnum direction) {
        return null;
    }

    @Override
    public boolean isWall(Point point) {
        return false;
    }

    @Override
    public boolean isTreasure(Point point) {
        return false;
    }
}
