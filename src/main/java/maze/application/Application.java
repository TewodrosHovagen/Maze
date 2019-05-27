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


    public static void main(String[] args) {
        startApplication(args);
    }

    public static void startApplication(String[] arguments){
        Logger log = new Logger();
        boolean runThePlayer = true;
        boolean createAnOutputFile = false;

        FileParse fileParse = new FileParse();
        FileData dataFile = null;
        String[] args = new String[2];
        args[0] = "./src/main/resources/maze_file.txt";
        args[1] = "./output.txt";

        if (args.length != 2){
            System.out.println(String.format("Missing maze file or output file argument in command line" ));

        }

        //TODO: need to get file from main arguments
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
        }
        else
            Logger.info("GAME WILL NOT START!!!");


    }

    private static boolean checkExistenceOfFilePath(String pathWithFileName){
        Path path = Paths.get(pathWithFileName);
        Path parentPath = path.getParent().toAbsolutePath();
        if (Files.exists(parentPath)) {
            return true;
        }
        return false;

    }
    private static boolean checkExistenceOfFile(String pathWithFileName){
        Path path = Paths.get(pathWithFileName);
        Path absPath = path.toAbsolutePath();
        if (Files.exists(absPath)) {
            return true;
        }
        return false;

    }
}
//        String[][] mazeWorld = new String[][]{
//                {"#", "#", "#", "#", "#", " ", " ", " ", " ", " "},
//                {"#", " ", "@", " ", "#", " ", " ", " ", " ", "#"},
//                {"#", " ", " ", " ", "#", " ", " ", "$", " ", "#"},
//                {" ", " ", " ", " ", "#", "#", "#", "#", "#", " "}
//        };