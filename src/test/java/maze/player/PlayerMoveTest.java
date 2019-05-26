package maze.player;

import Utils.Enums.DirectionsEnum;
import Utils.Enums.WalkingDirectionsEnum;
import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static Utils.Enums.DirectionsEnum.*;
import static Utils.Enums.DirectionsEnum.EAST;
import static Utils.Enums.WalkingDirectionsEnum.RIGHT;
import static Utils.Enums.WalkingDirectionsEnum.STRAIGHT;

@RunWith(Parameterized.class)
public class PlayerMoveTest {


    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {STRAIGHT, NORTH, EAST, true},
                {RIGHT, EAST, NORTH, true},
                {STRAIGHT, NORTH, EAST, false}
        };
    }

    public PlayerMoveTest(WalkingDirectionsEnum lastStep, DirectionsEnum mainDirection, DirectionsEnum expected, boolean isHitWall) {
        this.lastStep = lastStep;
        this.mainDirection = mainDirection;
        this.expected = expected;
        this.hitWall = isHitWall;
    }


    private Player player;
    private WalkingDirectionsEnum lastStep;
    private DirectionsEnum mainDirection;
    private DirectionsEnum expected;
    private boolean hitWall;

    @Before
    public void initializeMazeAndPlayer() {
        player = new PlayerDummy();
        String mazeFilePath = "C:\\Git\\mazeFile.txt";
        FileParse fileParse = new FileParse();
        FileData fileData = fileParse.parseFileData(mazeFilePath);
//        GameManager gameManager = new GameManagerImpl(fileData);
    }


    @Test
    public void moveToNorthHappyTest() {
        player.setLastStep(lastStep);
        player.setMainDirection(mainDirection);
        player.setHitWall(hitWall);
        DirectionsEnum test = player.move();
        Assert.assertEquals(expected, test);


    }

    @Test
    public void moveToNorthAfterHitWallHappyTest() {
        player.setLastStep(lastStep);
        player.setMainDirection(mainDirection);
        player.setHitWall(hitWall);
        DirectionsEnum test = player.move();
        Assert.assertEquals(expected, test);


    }
}
