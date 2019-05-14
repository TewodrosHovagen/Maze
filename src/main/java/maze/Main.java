package maze;

import maze.FileDataParse.FileData;
import maze.FileDataParse.FileParse;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerVers1;
import maze.logging.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {


    public static void main(String[] args) {
        String[] arguments = {"C:\\Users\\sb5844\\Projects\\biq\\Maze\\maze.txt","./output.txt"};
        boolean runThePlayer = true;
//        String[][] mazeWorld = new String[][]{
//                {"#", "#", "#", "#", "#", " ", " ", " ", " ", " "},
//                {"#", " ", "@", " ", "#", " ", " ", " ", " ", "#"},
//                {"#", " ", " ", " ", "#", " ", " ", "$", " ", "#"},
//                {" ", " ", " ", " ", "#", "#", "#", "#", "#", " "}
//        };

        FileParse fileParse = new FileParse();
        FileData dataFile;

        String mazeFilePath = arguments[0];
        String outputFilePath = arguments[1];


        if(checkExistenceOfFilePath(outputFilePath)){
            Logger log = new Logger(outputFilePath);
        }
        else{
            System.out.println("ERROR--> output file location is not exist");
            Logger log = new Logger(true);
            runThePlayer = false;
        }

        if(checkExistenceOfFilePath(mazeFilePath) && checkExistenceOfFile(mazeFilePath)){
            dataFile = fileParse.parseFileData(mazeFilePath);
        }
        else{
            dataFile = fileParse.parseFileData("");
            runThePlayer = false;
        }

        System.out.println(dataFile);
        dataFile.printMazeWorld();

        if(runThePlayer) {
            //            System.out.println("START THE GAME!!!");
            GameManager game = new GameManagerVers1(dataFile);
        }
        else
            System.out.println("GAME WILL NOT START!!!");

//        game.startGame();
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
