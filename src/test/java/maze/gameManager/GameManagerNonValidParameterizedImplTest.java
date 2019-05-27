package maze.gameManager;

import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class GameManagerNonValidParameterizedImplTest {

    private int rowPosition, colPosition;
    private String fileDir = "C:\\Maze\\src\\test\\resources\\mazeFileTest.txt";
    private boolean expectedResultIsWall, expectedResultIsTreasure;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {Integer.MAX_VALUE, Integer.MAX_VALUE, false, false},
                        {Integer.MIN_VALUE, Integer.MIN_VALUE, false, false},
                        {-1, -1, false, false}
                }
        );
    }

    public GameManagerNonValidParameterizedImplTest(int rowPosition, int colPosition, boolean expectedResultIsWall, boolean expectedResultIsTreasure) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.expectedResultIsTreasure = expectedResultIsTreasure;
        this.expectedResultIsWall = expectedResultIsWall;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void isWallNonValid() {
        Point currentPoint = new Point(rowPosition, colPosition);
        FileData fileData = new FileParse().parseFileData(fileDir);
        GameManager gameManager = new GameManagerImpl(fileData);
        Assert.assertEquals("The current point a wall when should not be", expectedResultIsWall, gameManager.isWall(currentPoint));
    }

    @Test
    public void isTreasureNonValid() {
        Point currentPoint = new Point(rowPosition, colPosition);
        FileData fileData = new FileParse().parseFileData(fileDir);
        GameManager gameManager = new GameManagerImpl(fileData);
        Assert.assertEquals("The current point a wall when should not be", expectedResultIsTreasure, gameManager.isTreasure(currentPoint));
    }
}