package maze.application;

import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerImpl;
import Utils.logging.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {


    public static void main(String[] args) {
        startApplication(args);
    }

    public static void startApplication(String[] args){
        //  for testing, instead of parameters from outline
        String[] arguments = {"C:\\Users\\sb5844\\Projects\\biq\\Maze\\maze.txt","./output.txt"};
        boolean runThePlayer = true;


        FileParse fileParse = new FileParse();
        FileData dataFile;

        String mazeFilePath = arguments[0];
        String outputFilePath = arguments[1];


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