package maze.gameManager;

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
    private String fileDir = "./src/test/resources/mazeFileTest.txt";
    private boolean expectedResultIsWall, expectedResultIsTreasure;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {Integer.MAX_VALUE, Integer.MAX_VALUE},
                        {Integer.MIN_VALUE, Integer.MIN_VALUE},
                        {-1, -1}
                }
        );
    }

    public GameManagerNonValidParameterizedImplTest(int rowPosition, int colPosition) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.expectedResultIsTreasure = false;
        this.expectedResultIsWall = false;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void isWallNonValid() {
        //Arrange
        Point currentPoint = new Point(rowPosition, colPosition);
        MazeData mazeData = new FileParse().parseFileData(fileDir);

        //Act
        GameManager gameManagerInterface = new GameManagerImpl(mazeData);

        //Assert
        Assert.assertEquals("The current point should not be a wall", expectedResultIsWall, gameManagerInterface.isWall(currentPoint));
    }

    @Test
    public void isTreasureNonValid() {
        //Arrange
        Point currentPoint = new Point(rowPosition, colPosition);
        MazeData mazeData = new FileParse().parseFileData(fileDir);

        //Act
        GameManager gameManagerInterface = new GameManagerImpl(mazeData);

        //Assert
        Assert.assertEquals("The current point should not be a treasure", expectedResultIsTreasure, gameManagerInterface.isTreasure(currentPoint));
    }
}