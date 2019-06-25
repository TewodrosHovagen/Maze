package maze.application.matchManager;

import maze.gameManager.MazeData;
import maze.player.PlayerInterface;
import maze.player.PlayerMaze;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatchTest {
    private Match match;
    private String mazes_folder = "./src/test/resources/mazeExamples";
    private String players_package = "maze.player";

    @Before
    public void setup(){
        this.match = new Match();
    }

    @Test
    public void verifyPlayersClassesWereLoaded(){
        String[] args = {mazes_folder, players_package};
        match.startApplication(args);
        Assert.assertEquals(match.getPlayers().contains(PlayerMaze.class), true);
    }
    @Test
    public void verifyNonPlayersClassesWereNotLoaded(){
        String[] args = {mazes_folder, players_package};
        match.startApplication(args);
        Assert.assertEquals(match.getPlayers().contains(PlayerInterface.class), false);

    }
    @Test
    public void verifyValidMazeFilesLoadedForExec(){
        String[] args = {mazes_folder, players_package};
        match.startApplication(args);
        boolean res = false;
        for (MazeData maze: match.getMazeFiles()){
            if(maze.getMazeName().equals("Nice simple maze")){
                res =true;
            break;
            }
        }
        Assert.assertEquals(res, true);
    }
    @Test
    public void verifyNonValidMazeFilesWereNotLoadedForExec(){
        String[] args = {mazes_folder, players_package};
        match.startApplication(args);
        boolean res = false;
        for (MazeData maze: match.getMazeFiles()){
            if(maze.getMazeName().equals("Missing Treasure Sign")){
                res =true;
                break;
            }
        }
        Assert.assertEquals(res, false);
    }
    @Test
    public void verifySingleThreadExecWhenArgIs1(){
        String[] args = {mazes_folder, players_package, "1"};
        match.startApplication(args);
        Assert.assertEquals(match.isThreadsStrategySingleThread(), true);
    }
    @Test
    public void verifyThreadPoolExecWhenArgGreaterThan1(){
        String[] args = {mazes_folder, players_package, "2"};
        match.startApplication(args);
        Assert.assertEquals(match.isThreadsStrategyPool(), true);
    }
    @Test
    public void verifySingleThreadExecWhenArgWasNotProvided(){
        String[] args = {mazes_folder, players_package};
        match.startApplication(args);
        Assert.assertEquals(match.isThreadsStrategySingleThread(), true);
    }

}
