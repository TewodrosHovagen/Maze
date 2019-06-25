package e2e2;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import maze.application.matchManager.Match;
import org.junit.Assert;
import utils.reports.MultipleGameOutputResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Stepdefs {

    private List<String> outputFilesList = new ArrayList<>();
    private Match match;

    @Given("init the match")
    public void init_app(){
        match = new Match();
    }

    @When("start app with correct {string} and {string}")
    public void args(String mazes_folder, String players_package){
        String[] args = {mazes_folder, players_package};
        match.startApplication(args);
    }

    @Then("results after execution are correct")
    public void checkResultAfterExecution(){
        Map<String, Map<String,Integer>> mapMazesWithPlayersResActual = MultipleGameOutputResult.saveOutputResultToMap(match.getGameResultMap());

        Assert.assertEquals(mapMazesWithPlayersResActual.get("Nice simple maze").get("PlayerMaze").intValue(),
                9);
        Assert.assertEquals(mapMazesWithPlayersResActual.get("A maze ing !!").get("PlayerMaze").intValue(),
                32);
    }

}
