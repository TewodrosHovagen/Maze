package maze.application;

import maze.gameManager.GameManager;

import java.util.Map;

public class Match {
    private Map<String, Map<String, Integer>> gamesResults;

    public static void main(String[] args) {
//        -mazes_folder <mazes_folder> -players <players_package> -threads <num_threads>
        String mazesFolder = args[0];
        String playersPackage = args[1];
        int numThread = args.length > 2 ? Integer.valueOf(args[2]) : 1;
//        GameManager



    }

}
