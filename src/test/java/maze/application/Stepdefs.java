package maze.application;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Stepdefs {

    private List<String> outputFilesList = new ArrayList<>();
    private Application app;

    @Given("init the application")
    public void init_app(){
        app = new Application();
    }

    @When("start app with (incorrect/correct ){string} path and (incorrect/correct ){string} path")
    public void start_app_with_paths(String input_file, String output_file){
        outputFilesList.add(output_file);
        String[] args = {input_file, output_file};
        app.startApplication(args);
    }
    @When("start app with one missing path {string}")
    public void start_app_with_missing_paths(String file_path){
        String[] args = {file_path};
        app.startApplication(args);
    }
    @When("start app with two missing paths")
    public void start_app_with_missing_paths(){
        String[] args = new String[0];
        app.startApplication(args);
    }

    @Then("{word} flag is {word}")
    public void checkAppFlagsState(String flag, String condition){
        switch (flag){
            case "createAnOutputFile":
                if(condition.equals("true"))
                    Assert.assertTrue(app.isCreateAnOutputFile());
                else
                    Assert.assertFalse(app.isCreateAnOutputFile());
                break;
            case "isInputFileExist":
                if(condition.equals("true"))
                    Assert.assertTrue(app.isInputFileExist());
                else
                    Assert.assertFalse(app.isInputFileExist());
                break;
            case "isRunThePlayer":
                if(condition.equals("true"))
                    Assert.assertTrue(app.isRunThePlayer());
                else
                    Assert.assertFalse(app.isRunThePlayer());
                break;
        }
    }

    @After
    public void removeOutputFiles() throws IOException {
        if(!outputFilesList.isEmpty()){
            for(String file : outputFilesList){
                Path path = Paths.get(file);
                Path absPath = path.toAbsolutePath();
                if (Files.exists(absPath)) {
                    Files.delete(absPath);
                }
            }

        }
    }
}
