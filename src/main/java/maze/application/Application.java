package maze.application;

import Utils.logging.Logger;
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

    public static void startApplication(String[] args){
        //  for testing, instead of parameters from outline
        boolean runThePlayer = true;


        FileParse fileParse = new FileParse();
        FileData dataFile;
        //TODO: need to get file from main arguments
        String mazeFilePath = args[0];
        String outputFilePath = args[1];

        if(checkExistenceOfFilePath(outputFilePath)){
            Logger log = new Logger(outputFilePath);
        }
        else{
            System.out.println("ERROR--> Output file location is not exist");
            Logger log = new Logger(true);
            runThePlayer = false;
        }

        if(checkExistenceOfFilePath(mazeFilePath) && checkExistenceOfFile(mazeFilePath)){
            dataFile = fileParse.parseFileData(mazeFilePath);
        }
        else{
            System.out.println(String.format("ERROR--> Command line argument for maze: %s doesn't lead to a maze file", mazeFilePath ));
            dataFile = fileParse.parseFileData("");
            runThePlayer = false;
        }

        System.out.println(dataFile);
        dataFile.printMazeWorld();

        if(runThePlayer) {
            //            System.out.println("START THE GAME!!!");
            GameManager game = new GameManagerImpl(dataFile);
        }
        else
            System.out.println("GAME WILL NOT START!!!");

//        game.startGame();
//        gameManager starts the game in ctor
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