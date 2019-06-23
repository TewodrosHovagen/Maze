package Utils.logging;

import maze.gameManager.MazeData;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerTask;
import maze.player.PlayerMaze;
import maze.player.PlayerRandom;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputResult {

    private static int MAZE_NAME_MAX_LENGTH = 40;

    public static void printConsoleOutputResult(Map<MazeData,List<GameManager>> gameResultMap) {

        int maxMazesNameLength = getMazeNameMaxLength(gameResultMap);


        System.out.println("******** MAZE RESULT ********");
        String padded = String.format("%-"+maxMazesNameLength+"s", " ");
        System.out.print(padded+ "\t");
        Map.Entry<MazeData, List<GameManager>> gameFirstItem = gameResultMap.entrySet().iterator().next();
        for (GameManager gameManager : gameFirstItem.getValue()) {
            System.out.print("\t" + gameManager.getPlayer().getClass().getSimpleName());
        }

        System.out.println();

        for(Map.Entry<MazeData,List<GameManager>> gameItem: gameResultMap.entrySet()){
            String mazeName = getMazeNameAccordingToLength(gameItem.getKey().getMazeName(),maxMazesNameLength);
            System.out.print(mazeName+"\t");
            for(GameManager gameManager : gameItem.getValue()){
                System.out.print("\t\t"+((GameManagerTask)gameManager).getMaxStepsResults()+"\t\t");
            }
            System.out.println();
        }
    }

    private static String getMazeNameAccordingToLength(String mazeName,int maxMazesNameLength){
        String returnMazeName = mazeName;
        if(mazeName.length()<maxMazesNameLength){
            int mazeReturnLength = maxMazesNameLength - mazeName.length();
            String padded = String.format("%-"+mazeReturnLength+"s", " ");
            returnMazeName+=padded;
        }
        return returnMazeName;
    }



    private static int getMazeNameMaxLength(Map<MazeData,List<GameManager>> gameResultMap){
        int maxLength =0;
        for(Map.Entry<MazeData,List<GameManager>> gameItem: gameResultMap.entrySet()){
            maxLength = Math.max(gameItem.getKey().getMazeName().length(),maxLength);
        }
        return maxLength;
    }

//    private static String getMazeNameAccordingToLength(String mazeName){
//        if (mazeName.length()>MAZE_NAME_MAX_LENGTH-1) {
//            mazeName= mazeName.substring(0,MAZE_NAME_MAX_LENGTH-1)+"...";
//        }
//        if (mazeName.length()>MAZE_NAME_MAX_LENGTH-2) {
//            mazeName= mazeName.substring(0,MAZE_NAME_MAX_LENGTH-2)+"...";
//        }
//        if (mazeName.length()>MAZE_NAME_MAX_LENGTH) {
//            mazeName= mazeName.substring(0,MAZE_NAME_MAX_LENGTH-3)+"...";
//        }
//
//        return mazeName;
//    }

    public static void main(String[] args) {
        Map<MazeData,List<GameManager>> gameResultMap = new HashMap<>();
        MazeData mazeData1 = new MazeData();
        mazeData1.setMazeName("Maze 1 with the longest name you could ever think of");
        MazeData mazeData2 = new MazeData();
        mazeData2.setMazeName("Maze 1 with the longest name you could ever think of very very long name");
        GameManager gameManager1 = new GameManagerTask(mazeData1, new PlayerRandom());
        GameManager gameManager2 = new GameManagerTask(mazeData2, new PlayerMaze());
        List<GameManager> gameManagerList = new ArrayList<>();
        gameManagerList.add(gameManager1);
        gameManagerList.add(gameManager2);
        gameResultMap.put(mazeData1,gameManagerList);
        gameResultMap.put(mazeData2,gameManagerList);

        printConsoleOutputResult(gameResultMap);
    }
}
