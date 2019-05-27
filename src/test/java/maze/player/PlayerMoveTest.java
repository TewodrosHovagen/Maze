package maze.player;

import Utils.Enums.DirectionsEnum;
import Utils.Enums.WalkingDirectionsEnum;
import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static Utils.Enums.DirectionsEnum.EAST;
import static Utils.Enums.DirectionsEnum.NORTH;
import static Utils.Enums.WalkingDirectionsEnum.RIGHT;
import static Utils.Enums.WalkingDirectionsEnum.STRAIGHT;

@RunWith(Parameterized.class)
public class PlayerMoveTest {


    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {STRAIGHT, NORTH, EAST, true},
                {RIGHT, EAST, NORTH, true},
                {STRAIGHT, NORTH, NORTH, false}
        };
    }


    private MazePlayer player;
    private WalkingDirectionsEnum lastStep;
    private DirectionsEnum mainDirection;
    private DirectionsEnum expected;
    private boolean hitWall;

    public PlayerMoveTest(WalkingDirectionsEnum lastStep, DirectionsEnum mainDirection, DirectionsEnum expected, boolean isHitWall) {
//        this.player=new MazePlayer();
        this.lastStep = lastStep;
        this.mainDirection = mainDirection;
        this.expected = expected;
        this.hitWall = isHitWall;
    }

    @Before
    public void initializeMazeAndPlayer() {
        player=new MazePlayer();
        String mazeFilePath = "C:\\Git\\mazeFile.txt";
        FileParse fileParse = new FileParse();
        FileData fileData = fileParse.parseFileData(mazeFilePath);
//        GameManager gameManager = new GameManagerImpl(fileData);
    }


    @Test
    public void moveToNorthHappyTest() {
        player.setHitWall(true);
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
