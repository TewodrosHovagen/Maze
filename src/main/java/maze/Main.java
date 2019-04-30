package maze;

import maze.gameManager.GameManager;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        String[][] mazeWorld = new String[][] {
                {"#","#","#","#","#"," "," "," "," "," "},
                {"#"," ","@"," ","#"," "," "," "," ","#"},
                {"#"," "," "," ","#"," "," ","$"," ","#"},
                {" "," "," "," ","#","#","#","#","#"," "}
        };
        GameManager game = new GameManager(mazeWorld);
        game.startGame();
    }
}
