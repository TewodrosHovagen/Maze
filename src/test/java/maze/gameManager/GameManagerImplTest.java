package maze.gameManager;

import utils.directionEnum.Enums;
import utils.reports.SingleGameOutputFile;
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
        GameManagerImpl gameManager = new GameManagerImpl(new MazeData());
        Point currentPoint = new Point(2,3);
        Point expectedPoint = new Point(5,3);
        int sequenceValue= 1;
        int expectedSequenceValue= 1;
        Map<Point,Integer> bookmarkManagerExpectedMap = new HashMap<>();
        bookmarkManagerExpectedMap.put(expectedPoint,expectedSequenceValue);

        //Act
        gameManager.addBookmark(currentPoint,sequenceValue);
        Map<Point,Integer> bookmarkManagerMap = gameManager.getBookmarkSequence();

        //Assert
        Assert.assertEquals("The adding bookmark action did not work",bookmarkManagerExpectedMap.size(),bookmarkManagerMap.size());
        Assert.assertNotEquals("The keys does not match", bookmarkManagerMap.entrySet().iterator().next().getKey(), bookmarkManagerExpectedMap.entrySet().iterator().next().getKey());
        Assert.assertEquals("The values does not match", bookmarkManagerMap.entrySet().iterator().next().getValue(), bookmarkManagerExpectedMap.entrySet().iterator().next().getValue());
    }

    @Test
    public void addBookmarkNonValidSequenceTest() {
        //Arrange
        GameManagerImpl gameManager = new GameManagerImpl(new MazeData());
        Point currentPoint = new Point(2,3);
        Point expectedPoint = new Point(2,3);
        int sequenceValue= 1;
        int expectedSequenceValue= 2;
        Map<Point,Integer> bookmarkManagerExpectedMap = new HashMap<>();
        bookmarkManagerExpectedMap.put(expectedPoint,expectedSequenceValue);

        //Act
        gameManager.addBookmark(currentPoint,sequenceValue);
        Map<Point,Integer> bookmarkManagerMap = gameManager.getBookmarkSequence();

        //Assert
        Assert.assertEquals("The adding bookmark action did not work",bookmarkManagerExpectedMap.size(),bookmarkManagerMap.size());
        Assert.assertEquals("The keys does not match", bookmarkManagerMap.entrySet().iterator().next().getKey(), bookmarkManagerExpectedMap.entrySet().iterator().next().getKey());
        Assert.assertNotEquals("The values does not match", bookmarkManagerMap.entrySet().iterator().next().getValue(), bookmarkManagerExpectedMap.entrySet().iterator().next().getValue());
    }

    @Test
    public void startGameWinTest() throws Exception {
        //Arrange
        MazeData mazeData = new FileParse().parseFileData(fileDir);
        Enums.MainDirectionsEnum direction = Enums.MainDirectionsEnum.RIGHT;
        String directionExpectedSTR = "R";
        String gameExpectedStatusSTR = "!";
        try (SingleGameOutputFile outputFile = new SingleGameOutputFile(fileOutputDir)) {
            GameManagerImpl gameManager = new GameManagerImpl(mazeData);
            gameManager.setPlayer(player);
            gameManager.setOutputFile(outputFile);
            Mockito.when(player.move()).thenReturn(direction);

            //Act
            gameManager.runGame();
        }
        //Assert
        try (FileInputStream fileInputStream = new FileInputStream(fileOutputDir);
             InputStreamReader reader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            Assert.assertEquals("The test did not move to " + direction, directionExpectedSTR, bufferedReader.readLine());
            Assert.assertEquals("Game state is not correct", gameExpectedStatusSTR, bufferedReader.readLine());
        }
    }

    @Test
    public void startGameWallTest() throws Exception {
        //Arrange
        MazeData mazeData = new FileParse().parseFileData(fileDir);
        Enums.MainDirectionsEnum direction = Enums.MainDirectionsEnum.UP;
        String directionExpectedSTR = "U";
        String gameExpectedStatusSTR = "X";
        try(SingleGameOutputFile outputFile = new SingleGameOutputFile(fileOutputDir)) {
            GameManagerImpl gameManager = new GameManagerImpl(mazeData);
            gameManager.setPlayer(player);
            gameManager.setOutputFile(outputFile);
            Mockito.when(player.move()).thenReturn(direction);

            //Act
            gameManager.runGame();
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
        MazeData mazeData = new FileParse().parseFileData(fileDir);
        Enums.MainDirectionsEnum direction = Enums.MainDirectionsEnum.LEFT;
        String directionExpectedSTR = "L";
        String gameExpectedStatusSTR = "X";
        try(SingleGameOutputFile outputFile = new SingleGameOutputFile(fileOutputDir)) {
            GameManagerImpl gameManager = new GameManagerImpl(mazeData);
            gameManager.setPlayer(player);
            gameManager.setOutputFile(outputFile);
            Mockito.when(player.move()).thenReturn(direction);

            //Act
            gameManager.runGame();
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
        MazeData mazeData = new FileParse().parseFileData(fileDir);
        Enums.MainDirectionsEnum direction = Enums.MainDirectionsEnum.BOOKMARK;
        String directionExpectedSTR = "B";
        String gameExpectedStatusSTR = "X";
        try(SingleGameOutputFile outputFile = new SingleGameOutputFile(fileOutputDir)) {
            GameManagerImpl gameManager = new GameManagerImpl(mazeData);
            gameManager.setPlayer(player);
            gameManager.setOutputFile(outputFile);
            Mockito.when(player.move()).thenReturn(direction);

            //Act
            gameManager.runGame();
        }
        //Assert
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileOutputDir));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            Assert.assertEquals("The test did not move to " + direction,directionExpectedSTR,bufferedReader.readLine());
            Assert.assertEquals("Game state is not correct",gameExpectedStatusSTR,bufferedReader.readLine());
        }
    }
}
