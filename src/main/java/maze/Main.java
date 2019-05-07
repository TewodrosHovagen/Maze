package maze;

import maze.FileDataParse.FileData;
import maze.FileDataParse.FileParse;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerVers1;

public class Main {


    public static void main(String[] args) {
        String[][] mazeWorld = new String[][]{
                {"#", "#", "#", "#", "#", " ", " ", " ", " ", " "},
                {"#", " ", "@", " ", "#", " ", " ", " ", " ", "#"},
                {"#", " ", " ", " ", "#", " ", " ", "$", " ", "#"},
                {" ", " ", " ", " ", "#", "#", "#", "#", "#", " "}
        };
//        game.startGame();

        FileParse fileParse = new FileParse();
        FileData dataFile = fileParse.parseFileData(args);
        System.out.println(dataFile);
        dataFile.printMazeWorld();

        GameManager game = new GameManagerVers1(dataFile);
//        game.startGame();
    }
}
