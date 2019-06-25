package maze.application.matchManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)

public class MatchNotValidParameterizedTest {
    private String mazes_folder;
    private String players_package;
    private boolean loadPlayers;
    private boolean validationArgsResult;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {"", "",false, false},
                        {"./src/test/resources/mazeExamples", "",false,true}

                }
        );
    }
    public MatchNotValidParameterizedTest(String mazes_folder, String players_package,boolean loadPlayers, boolean validationArgsResult ){
        this.mazes_folder = mazes_folder;
        this.players_package = players_package;
        this.loadPlayers = loadPlayers;
        this.validationArgsResult = validationArgsResult;
    }


    @Test
    public void verifyMatchArgsValidation(){
        //given
        String[] args;
        if(mazes_folder.equals("") && players_package.equals("")){
             args = new String[]{};
        }else if (!mazes_folder.equals("") && players_package.equals("")){
             args = new String[]{mazes_folder};
        }else{
            args = new String[]{mazes_folder, players_package};
        }

        //when
        Match match = new Match();
        match.startApplication(args);

        //then
        Assert.assertEquals(match.isLoadPlayers(),loadPlayers);
        Assert.assertEquals(match.isValidationArgsResult(),validationArgsResult);
    }

}
