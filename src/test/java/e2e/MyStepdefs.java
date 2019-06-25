package e2e;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import maze.application.Validation;
import maze.fileDataParse.FileParse;
import maze.gameManager.GameManagerImpl;
import maze.gameManager.MazeData;
import utils.reports.SingleGameOutputFile;

import java.io.FileNotFoundException;

public class MyStepdefs {

    GameManagerImpl game;
    private FileParse fileParse;
    private boolean runThePlayer;
    private boolean createAnOutputFile;
    private String outputFilePath;
    private boolean isInputFileExist;
    private MazeData mazeData;
    String mazeFilePath;
    SingleGameOutputFile outputFile;

    @When("validating inputs (.*)")
    public void validatingInputsArguments(String[] args) {
        argumentsValidationBeforeStartApplication(args);
    }

    @And("parse file")
    public void parseFile() {
        parseMazeFileData();
    }

    @And("create Single Game Output File")
    public void createSingleGameOutputFile() throws FileNotFoundException {
         outputFile = new SingleGameOutputFile(outputFilePath);
    }

    @And("create new game manager object and set the output file")
    public void createNewGameManagerObjectAndSetTheOutputFile() {
        game = new GameManagerImpl(mazeData);
        game.setOutputFile(outputFile);
    }

    @Then("run the game")
    public void runTheGame() {
        game.runGame();
    }

    @Then("game ended maze solved within {int} steps.")
    public void gameEndedMazeSolvedWithinSteps(int arg0) {

    }

    private void argumentsValidationBeforeStartApplication(String[] args) {
        int isArgMissing;
        String outputFilePath;
        fileParse = new FileParse();

        isArgMissing = Validation.checkIfMissingArgs(args);
        if (isArgMissing == -1) {
            System.out.println(String.format("Missing maze file argument in command line"));
            System.out.println(String.format("Missing output file argument in command line"));
            runThePlayer = false;
            return;
        } else if (isArgMissing == 0) {
            System.out.println(String.format("Missing output file argument in command line"));
            runThePlayer = false;
            mazeFilePath = args[0];
            outputFilePath = "";
        } else {
            mazeFilePath = args[0];
            outputFilePath = args[1];
        }

        if (isArgMissing == 1) {
            if (Validation.checkExistenceOfFilePath(outputFilePath) && !Validation.checkExistenceOfFile(outputFilePath)) {
                createAnOutputFile = true;
                this.outputFilePath = outputFilePath;
            } else {
                System.out.println(String.format("Command line argument for output file: %s points to a bad path or to a file that already exists", outputFilePath));
                runThePlayer = false;
            }
        }
    }

    public void parseMazeFileData() {
        if (Validation.checkExistenceOfFilePath(mazeFilePath) && Validation.checkExistenceOfFile(mazeFilePath)) {
            isInputFileExist = true;
            mazeData = fileParse.parseFileData(mazeFilePath);
        } else {
            System.out.println(String.format("Command line argument for maze: %s doesn't lead to a maze file", mazeFilePath));
            runThePlayer = false;
        }
    }
}
