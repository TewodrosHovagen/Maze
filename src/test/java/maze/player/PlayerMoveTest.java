package maze.player;

import Utils.Enums.MainDirectionsEnum;
import Utils.Enums.WalkingDirectionsEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PlayerMoveTest {


    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {WalkingDirectionsEnum.STRAIGHT, MainDirectionsEnum.UP, true, MainDirectionsEnum.RIGHT},
                {WalkingDirectionsEnum.RIGHT, MainDirectionsEnum.RIGHT,true, MainDirectionsEnum.UP},
                {WalkingDirectionsEnum.STRAIGHT, MainDirectionsEnum.UP, false, MainDirectionsEnum.UP}
        };
    }


    private MazePlayer player;
    private WalkingDirectionsEnum lastStep;
    private MainDirectionsEnum mainDirection;
    private MainDirectionsEnum expected;
    private boolean hitWall;

    public PlayerMoveTest(WalkingDirectionsEnum lastStep, MainDirectionsEnum mainDirection, boolean isHitWall, MainDirectionsEnum expected) {
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
        MainDirectionsEnum test = player.move();
        Assert.assertEquals(expected, test);


    }

    @Test
    public void moveToNorthAfterHitWallHappyTest() {
        player.setLastStep(lastStep);
        player.setMainDirection(mainDirection);
        player.setHitWall(hitWall);
        MainDirectionsEnum test = player.move();
        Assert.assertEquals(expected, test);


    }
}
