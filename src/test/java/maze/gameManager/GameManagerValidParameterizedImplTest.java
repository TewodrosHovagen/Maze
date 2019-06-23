package maze.gameManager;

import maze.fileDataParse.FileParse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class GameManagerValidParameterizedImplTest {


    private int rowPosition, colPosition;
    private String fileDir = "./src/test/resources/mazeFileTest.txt";
    private boolean expectedResultIsWall, expectedResultIsTreasure;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {0, 0,true,false},
                        {0, 1,true,false},
                        {2, 7,false,true},
                        {2, 6,false,false},
                        {1, 2,false,false}
                }
        );
    }

    public GameManagerValidParameterizedImplTest(int rowPosition, int colPosition, boolean expectedResultIsWall, boolean expectedResultIsTreasure) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.expectedResultIsTreasure=expectedResultIsTreasure;
        this.expectedResultIsWall = expectedResultIsWall;
    }

    @Test
    public void isWallValid() {
        //Arrange
        Point currentPoint = new Point(colPosition, rowPosition);
        MazeData mazeData = new FileParse().parseFileData(fileDir);

        //Act
        GameManager gameManagerInterface = new GameManagerImpl(mazeData);

        //Assert
        Assert.assertEquals("The current point a wall when should not be",expectedResultIsWall, gameManagerInterface.isWall(currentPoint));
    }

    @Test
    public void isTreasureValid() {
        //Arrange
        Point currentPoint = new Point(colPosition, rowPosition);
        MazeData mazeData = new FileParse().parseFileData(fileDir);

        //Act
        GameManager gameManagerInterface = new GameManagerImpl(mazeData);

        //Assert
        Assert.assertEquals("The current point treasure as expected",expectedResultIsTreasure, gameManagerInterface.isTreasure(currentPoint));
    }

    @Test
    public void addBookmarkValidTest() {
        //Arrange
        GameManagerImpl gameManager = new GameManagerImpl();
        Point currentPoint = new Point(colPosition, rowPosition);
        Point expectedPoint = new Point(colPosition, rowPosition);
        int sequenceValue= 1;
        int expectedSequenceValue= 1;
        Map<Point,Integer> bookmarkManagerExpectedMap = new HashMap<>();
        bookmarkManagerExpectedMap.put(expectedPoint,expectedSequenceValue);

        //Act
        gameManager.addBookmark(currentPoint,sequenceValue);
        Map<Point,Integer> bookmarkManagerMap = gameManager.bookmarkSequence;

        //Assert
        Assert.assertEquals("The adding bookmark action did not work",bookmarkManagerExpectedMap.size(),bookmarkManagerMap.size());
        Assert.assertEquals("The keys does not match", bookmarkManagerMap.entrySet().iterator().next().getKey(), bookmarkManagerExpectedMap.entrySet().iterator().next().getKey());
        Assert.assertEquals("The values does not match", bookmarkManagerMap.entrySet().iterator().next().getValue(), bookmarkManagerExpectedMap.entrySet().iterator().next().getValue());
    }
}