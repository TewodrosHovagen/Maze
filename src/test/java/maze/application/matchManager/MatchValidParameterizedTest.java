package maze.application.matchManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)

public class MatchValidParameterizedTest {
    private String mazes_folder;
    private String players_package;
    private String num_threads;
    private boolean validationArgsResult;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {"C:\\Users\\sb5844\\Projects\\biq\\Maze\\src\\test\\resources\\mazeExamples", "maze.player", "1",true},
                        {"./src/test/resources/mazeExamples", "maze.player","1",true},
                        {"./src/test/resources/mazeExamples", "maze.player","2",true},
                        {"./src/test/resources/mazeExamples", "maze.player", "" ,true}
                }
        );
    }
    public MatchValidParameterizedTest(String mazes_folder,String players_package, String num_threads, boolean validationArgsResult ){
        this.mazes_folder = mazes_folder;
        this.players_package = players_package;
        this.num_threads = num_threads;
        this.validationArgsResult = validationArgsResult;
    }


    @Test
    public void verifyMatchArgsValidation(){
        //given
        String[] args;
        if(this.num_threads.equals("")){
             args = new String[]{mazes_folder, players_package};
        }else {
             args = new String[]{mazes_folder, players_package, num_threads};
        }

        //when
        Match match = new Match();
        match.startApplication(args);

        //then
        Assert.assertEquals(match.isValidationArgsResult(),validationArgsResult);
    }

}
