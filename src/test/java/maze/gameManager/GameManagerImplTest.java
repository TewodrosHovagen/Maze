package maze.gameManager;

import Utils.Enums;
import Utils.logging.OutputLog;
import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import maze.player.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class GameManagerImplTest {

    @Mock
    Player player;
    private String fileDir ="./src/test/resources/simpleMazeFileTest.txt";
    private String fileOutputDir ="./output.txt";


    @Test
    public void addBookmarkNonValidPointTest() {
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
    public void addBookmarkNonValidSequenceTest() {
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

    @Test
    public void startGameWinTest() throws Exception {
        //Arrange
        FileData fileData = new FileParse().parseFileData(fileDir);
        Enums.DirectionsEnum direction = Enums.DirectionsEnum.RIGHT;
        String directionExpectedSTR = "R";
        String gameExpectedStatusSTR = "!";
        try (OutputLog outputFile = new OutputLog(fileOutputDir)) {
            GameManagerImpl gameManager = new GameManagerImpl(fileData);
            gameManager.setPlayer(player);
            gameManager.setOutputFile(outputFile);
            Mockito.when(player.move()).thenReturn(direction);

            //Act
            gameManager.startGame();
        }
        //Assert
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileOutputDir));
            BufferedReader bufferedReader = new BufferedReader(reader)) {
            Assert.assertEquals("The test did not move to " + direction,directionExpectedSTR,bufferedReader.readLine());
            Assert.assertEquals("Game state is not correct",gameExpectedStatusSTR,bufferedReader.readLine());
        }
    }

    @Test
    public void startGameWallTest() throws Exception {
        //Arrange
        FileData fileData = new FileParse().parseFileData(fileDir);
        Enums.DirectionsEnum direction = Enums.DirectionsEnum.UP;
        String directionExpectedSTR = "U";
        String gameExpectedStatusSTR = "X";
        try(OutputLog outputFile = new OutputLog(fileOutputDir)) {
            GameManagerImpl gameManager = new GameManagerImpl(fileData);
            gameManager.setPlayer(player);
            gameManager.setOutputFile(outputFile);
            Mockito.when(player.move()).thenReturn(direction);

            //Act
            gameManager.startGame();
        }
        //Assert
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileOutputDir));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            Assert.assertEquals("The test did not move to " + direction,directionExpectedSTR,bufferedReader.readLine());
            Assert.assertEquals("Game state is not correct",gameExpectedStatusSTR,bufferedReader.readLine());
        }
    }

    @Test
    public void startGameNoWinTest() throws Exception {
        //Arrange
        FileData fileData = new FileParse().parseFileData(fileDir);
        Enums.DirectionsEnum direction = Enums.DirectionsEnum.LEFT;
        String directionExpectedSTR = "L";
        String gameExpectedStatusSTR = "X";
        try(OutputLog outputFile = new OutputLog(fileOutputDir)) {
            GameManagerImpl gameManager = new GameManagerImpl(fileData);
            gameManager.setPlayer(player);
            gameManager.setOutputFile(outputFile);
            Mockito.when(player.move()).thenReturn(direction);

            //Act
            gameManager.startGame();
        }
        //Assert
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileOutputDir));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            Assert.assertEquals("The test did not move to " + direction,directionExpectedSTR,bufferedReader.readLine());
            Assert.assertEquals("Game state is not correct",gameExpectedStatusSTR,bufferedReader.readLine());
        }
    }

    @Test
    public void startGameNoWinBookmarkTest() throws Exception {
        //Arrange
        FileData fileData = new FileParse().parseFileData(fileDir);
        Enums.DirectionsEnum direction = Enums.DirectionsEnum.BOOKMARK;
        String directionExpectedSTR = "B";
        String gameExpectedStatusSTR = "X";
        try(OutputLog outputFile = new OutputLog(fileOutputDir)) {
            GameManagerImpl gameManager = new GameManagerImpl(fileData);
            gameManager.setPlayer(player);
            gameManager.setOutputFile(outputFile);
            Mockito.when(player.move()).thenReturn(direction);

            //Act
            gameManager.startGame();
        }
        //Assert
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileOutputDir));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            Assert.assertEquals("The test did not move to " + direction,directionExpectedSTR,bufferedReader.readLine());
            Assert.assertEquals("Game state is not correct",gameExpectedStatusSTR,bufferedReader.readLine());
        }
    }
}
