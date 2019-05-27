package maze.application;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class Stepdefs {

    @When("start app with {string} path and {string} path")
    public void start_app_with_paths(String input_file, String output_file){
        String[] args = {input_file, output_file};
        Application.startApplication(args);
    }

    @Then("createAnOutputFile flag is true")
    public void checkCreateAnOutputFileFlag(){
        Assert.assertTrue(Application.isCreateAnOutputFile());
    }
}
