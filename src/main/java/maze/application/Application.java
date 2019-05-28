package maze.application;

import Utils.logging.Logger;
import Utils.logging.OutputLog;
import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerImpl;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {

    private boolean createAnOutputFile = false;
    private boolean isInputFileExist = false;
    private boolean runThePlayer = true;
    private String outputFilePath;
    private FileData dataFile;

    public boolean isCreateAnOutputFile() {
        return createAnOutputFile;
    }
    public boolean isInputFileExist() {
        return isInputFileExist;
    }
    public boolean isRunThePlayer() {
        return runThePlayer;
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.startApplication(args);
    }

    public void startApplication(String[] args){
        Logger log = new Logger();

//        FileData dataFile = null;
        //TODO: Shoshi to remove when tested perfect
//        String[] args = new String[2];
//        args[0] = "./src/main/resources/maze_file.txt";
//        args[1] = "./output.txt";

        argumentsValidationBeforeStartApplication(args);

        if(runThePlayer && createAnOutputFile) {
            Logger.info("START THE GAME!!!");
            try(OutputLog outputFile = new OutputLog(outputFilePath)){
                GameManager game = new GameManagerImpl(dataFile);
                game.startGame(outputFile);
            }  catch (Exception e) {
                System.out.println("Problem while writing to output file: " + e);
            }
        }
        else
            Logger.info("GAME WILL NOT START!!!");
    }

    private void argumentsValidationBeforeStartApplication(String[] args) {
        int isArgMissing;
        String mazeFilePath ;
        String outputFilePath ;
        FileParse fileParse = new FileParse();

        isArgMissing = checkIfMissingArgs(args);
        if (isArgMissing == -1) {
            return;
        }
        else if(isArgMissing == 0){
            mazeFilePath = args[0];
            outputFilePath = "";
        }else{
            mazeFilePath = args[0];
            outputFilePath = args[1];
        }

        if (isArgMissing == 1 ){
            if(checkExistenceOfFilePath(outputFilePath) && !checkExistenceOfFile(outputFilePath)){
                createAnOutputFile = true;
                this.outputFilePath = outputFilePath;
            }
            else{
                System.out.println(String.format("Command line argument for output file: %s points to a bad path or to a file that already exists", outputFilePath ));
                runThePlayer = false;
            }
        }


        if(checkExistenceOfFilePath(mazeFilePath) && checkExistenceOfFile(mazeFilePath)){
            isInputFileExist = true;
            dataFile = fileParse.parseFileData(mazeFilePath);
        }
        else{
            System.out.println(String.format("Command line argument for maze: %s doesn't lead to a maze file", mazeFilePath ));
            runThePlayer = false;
        }
    }

    private int checkIfMissingArgs(String[] args) {
        if (args.length == 0){
            System.out.println(String.format("Missing maze file argument in command line" ));
            System.out.println(String.format("Missing output file argument in command line" ));
            runThePlayer = false;
            return -1;
        } else if (args.length == 1){
            System.out.println(String.format("Missing output file argument in command line" ));
            runThePlayer = false;
            return 0;
        } return 1;
    }

    private static boolean checkExistenceOfFilePath(String pathWithFileName){
        Path path = Paths.get(pathWithFileName);
        Path parentPath = path.getParent().toAbsolutePath();
        return Files.exists(parentPath);

    }
    private static boolean checkExistenceOfFile(String pathWithFileName){
        Path path = Paths.get(pathWithFileName);
        Path absPath = path.toAbsolutePath();
        return Files.exists(absPath);
    }
}
