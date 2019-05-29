package maze.player;

import Utils.Enums.DirectionsEnum;
import Utils.Enums.WalkingDirectionsEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static Utils.Enums.DirectionsEnum.UP;
import static Utils.Enums.WalkingDirectionsEnum.RIGHT;
import static Utils.Enums.WalkingDirectionsEnum.STRAIGHT;

@RunWith(Parameterized.class)
public class PlayerMoveTest {


    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {WalkingDirectionsEnum.STRAIGHT, DirectionsEnum.UP, true, DirectionsEnum.RIGHT},
                {WalkingDirectionsEnum.RIGHT, DirectionsEnum.RIGHT,true, DirectionsEnum.UP},
                {WalkingDirectionsEnum.STRAIGHT, DirectionsEnum.UP, false, DirectionsEnum.UP}
        };
    }


    private MazePlayer player;
    private WalkingDirectionsEnum lastStep;
    private DirectionsEnum mainDirection;
    private DirectionsEnum expected;
    private boolean hitWall;

    public PlayerMoveTest(WalkingDirectionsEnum lastStep, DirectionsEnum mainDirection, boolean isHitWall, DirectionsEnum expected) {
        this.lastStep = lastStep;
        this.mainDirection = mainDirection;
        this.expected = expected;
        this.hitWall = isHitWall;
    }

    @Before
    public void initializeMazeAndPlayer() {
        player = new MazePlayer();
    }


    @Test
    public void moveToNorthHappyTest() {
        player.setHitWall(hitWall);
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
