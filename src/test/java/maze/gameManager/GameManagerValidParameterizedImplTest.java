package maze.gameManager;

import Utils.logging.OutputLog;
import maze.fileDataParse.FileData;
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


    private int xPosition,yPosition;
    private String fileDir = "C:\\Maze\\src\\test\\resources\\mazeFileTest.txt";
    private boolean expectedResultIsWall, expectedResultIsTreasure;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
//                        {0, 0,true,false},
//                        {1, 0,true,false},
                        {2, 7,false,true},
                        {2, 6,false,false},
                        {1, 2,false,false}
                }
        );
    }

    public GameManagerValidParameterizedImplTest(int xPosition, int yPosition, boolean expectedResultIsWall, boolean expectedResultIsTreasure) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.expectedResultIsTreasure=expectedResultIsTreasure;
        this.expectedResultIsWall = expectedResultIsWall;
    }

    @Test
    public void isWallValid() {
        //Arrange
        Point currentPoint = new Point(xPosition,yPosition);
        FileData fileData = new FileParse().parseFileData(fileDir);

        //Act
        GameManager gameManager = new GameManagerImpl(fileData);

        //Assert
        Assert.assertEquals("The current point a wall when should not be",expectedResultIsWall,gameManager.isWall(currentPoint));
    }

    @Test
    public void isTreasureValid() {
        //Arrange
        Point currentPoint = new Point(xPosition,yPosition);
        FileData fileData = new FileParse().parseFileData(fileDir);

        //Act
        GameManager gameManager = new GameManagerImpl(fileData);

        //Assert
        Assert.assertEquals("The current point a wall when should not be",expectedResultIsTreasure,gameManager.isTreasure(currentPoint));
    }

    @Test
    public void addBookmarkValidTest() {
        //Arrange
        GameManagerImpl gameManager = new GameManagerImpl();
        Point currentPoint = new Point(xPosition,yPosition);
        Point expectedPoint = new Point(xPosition,yPosition);
        int sequenceValue= 1;
        int expectedSequenceValue= 1;
        Map<Point,Integer> bookmarkManagerExpectedMap = new HashMap<>();
        bookmarkManagerExpectedMap.put(expectedPoint,expectedSequenceValue);

        //Act
        gameManager.addBookmark(currentPoint,sequenceValue);

        //Assert
        Map<Point,Integer> bookmarkManagerMap = gameManager.bookmarkSequence;
        Assert.assertEquals("The adding bookmark action did not work",bookmarkManagerExpectedMap.size(),bookmarkManagerMap.size());
        for(Map.Entry<Point,Integer> itemExpected : bookmarkManagerExpectedMap.entrySet()){
            for(Map.Entry<Point,Integer> itemActual : bookmarkManagerMap.entrySet()){
                Assert.assertEquals("The keys does not match", itemActual.getKey(), itemExpected.getKey());
                Assert.assertEquals("The values does not match", itemActual.getValue(), itemExpected.getValue());
            }
        }
    }
}