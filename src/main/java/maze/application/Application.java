package maze.application;

import Utils.logging.Logger;
import Utils.logging.OutputLog;
import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {

    private boolean createAnOutputFile = false;
    private boolean isInputFileExist = false;
    private boolean runThePlayer = true;

    public static void main(String[] args) {
        Application app = new Application();
        app.startApplication(args);
    }

    public boolean isCreateAnOutputFile() {
        return createAnOutputFile;
    }
    public boolean isInputFileExist() {
        return isInputFileExist;
    }
    public boolean isRunThePlayer() {
        return runThePlayer;
    }

    public void startApplication(String[] args){
        Logger log = new Logger();

        FileParse fileParse = new FileParse();
        FileData dataFile = null;
        //TODO: Shoshi to remove when tested perfect
//        String[] args = new String[2];
//        args[0] = "./src/main/resources/maze_file.txt";
//        args[1] = "./output.txt";

        if (args.length != 2){
            System.out.println(String.format("Missing maze file or output file argument in command line" ));
            runThePlayer = false;
            return;
        }

        String mazeFilePath = args[0];
        String outputFilePath = args[1];

        if(checkExistenceOfFilePath(outputFilePath) && !checkExistenceOfFile(outputFilePath)){
            createAnOutputFile = true;
        }
        else{
            System.out.println(String.format("Command line argument for output file: %s points to a bad path or to a file that already exists", outputFilePath ));
            runThePlayer = false;
        }

        if(checkExistenceOfFilePath(mazeFilePath) && checkExistenceOfFile(mazeFilePath)){
            isInputFileExist = true;
            dataFile = fileParse.parseFileData(mazeFilePath);
        }
        else{
            System.out.println(String.format("Command line argument for maze: %s doesn't lead to a maze file", mazeFilePath ));
            runThePlayer = false;
        }

        if(runThePlayer && createAnOutputFile) {
            Logger.info("START THE GAME!!!");
            OutputLog outputFile = new OutputLog(outputFilePath);
            GameManager game = new GameManagerImpl(dataFile);
            game.startGame();
        }
        else
            Logger.info("GAME WILL NOT START!!!");
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
