package maze.player;

import utils.directionEnum.Enums.MainDirectionsEnum;
import maze.player.directionEnum.playerEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PlayerMoveTest {

/**
 * @param - laststep , mainDirection , isHitWall , expected **/
    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {playerEnum.WalkingDirectionsEnum.STRAIGHT, MainDirectionsEnum.UP, true, MainDirectionsEnum.RIGHT},
                {playerEnum.WalkingDirectionsEnum.RIGHT, MainDirectionsEnum.RIGHT,true, MainDirectionsEnum.UP},
                {playerEnum.WalkingDirectionsEnum.STRAIGHT, MainDirectionsEnum.UP, false, MainDirectionsEnum.UP}
        };
    }


    private PlayerMaze player;
    private playerEnum.WalkingDirectionsEnum lastStep;
    private MainDirectionsEnum mainDirection;
    private MainDirectionsEnum expected;
    private boolean hitWall;

    public PlayerMoveTest(playerEnum.WalkingDirectionsEnum lastStep, MainDirectionsEnum mainDirection, boolean isHitWall, MainDirectionsEnum expected) {
        this.lastStep = lastStep;
        this.mainDirection = mainDirection;
        this.expected = expected;
        this.hitWall = isHitWall;

    }

    @Before
    public void initializeMazeAndPlayer() {
        player = new PlayerMaze();player.setBookmarkNextMove=false;
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
