package maze.gameManager;

import maze.FileDataParse.FileData;
import maze.FileDataParse.FileParse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class GameManagerWallTreasureNonValidImplTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {Integer.MAX_VALUE, Integer.MAX_VALUE, false, false, "C:\\Maze\\src\\test\\resources\\mazeFileTest.txt"},
                        {Integer.MIN_VALUE, Integer.MIN_VALUE, false, false, "C:\\Maze\\src\\test\\resources\\mazeFileTest.txt"},
                        {-1, -1, false, false, "C:\\Maze\\src\\test\\resources\\mazeFileTest.txt"}
                }
        );
    }

    private int xPosition, yPosition;
    private String fileDir;
    private boolean expectedResultIsWall, expectedResultIsTreasure;

    public GameManagerWallTreasureNonValidImplTest(int xPosition, int yPosition, boolean expectedResultIsWall, boolean expectedResultIsTreasure, String fileDir) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.fileDir = fileDir;
        this.expectedResultIsTreasure = expectedResultIsTreasure;
        this.expectedResultIsWall = expectedResultIsWall;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void isWallNonValid() {
        Point currentPoint = new Point(xPosition, yPosition);
        FileData fileData = new FileParse().parseFileData(fileDir);
        GameManager gameManager = new GameManagerImpl(fileData);
        Assert.assertEquals("The current point a wall when should not be", expectedResultIsWall, gameManager.isWall(currentPoint));
    }

    @Test
    public void isTreasureNonValid() {
        Point currentPoint = new Point(xPosition, yPosition);
        FileData fileData = new FileParse().parseFileData(fileDir);
        GameManager gameManager = new GameManagerImpl(fileData);
        Assert.assertEquals("The current point a wall when should not be", expectedResultIsTreasure, gameManager.isTreasure(currentPoint));
    }
}