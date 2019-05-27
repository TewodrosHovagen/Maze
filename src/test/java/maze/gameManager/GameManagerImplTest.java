package maze.gameManager;

import maze.fileDataParse.FileData;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameManagerImplTest {

    @Test
    void addBookmarkNonValidPointTest() {
        //Arrange
        GameManagerImpl gameManager = new GameManagerImpl(new FileData());
        Point currentPoint = new Point(2,3);
        Point expectedPoint = new Point(5,3);
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
                Assert.assertNotEquals("The keys does not match", itemActual.getKey(), itemExpected.getKey());
                Assert.assertEquals("The values does not match", itemActual.getValue(), itemExpected.getValue());
            }
        }
    }

    @Test
    void addBookmarkNonValidSequenceTest() {
        //Arrange
        GameManagerImpl gameManager = new GameManagerImpl(new FileData());
        Point currentPoint = new Point(2,3);
        Point expectedPoint = new Point(2,3);
        int sequenceValue= 1;
        int expectedSequenceValue= 2;
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
                Assert.assertNotEquals("The values does not match", itemActual.getValue(), itemExpected.getValue());
            }
        }
    }
}
