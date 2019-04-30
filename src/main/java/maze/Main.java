package maze;

import maze.FileDataParse.FileData;
import maze.FileDataParse.FileParse;
import maze.gameManager.GameManager;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {
//        String[][] mazeWorld = new String[][] {
//                {"#","#","#","#","#"," "," "," "," "," "},
//                {"#"," ","@"," ","#"," "," "," "," ","#"},
//                {"#"," "," "," ","#"," "," ","$"," ","#"},
//                {" "," "," "," ","#","#","#","#","#"," "}
//        };
//        GameManager game = new GameManager(mazeWorld);
//        game.startGame();

        FileParse fileParse = new FileParse();
        FileData dataFile = fileParse.parseFileData(args);
        System.out.println(dataFile);
        dataFile.printMazeWorld();
    }
}
