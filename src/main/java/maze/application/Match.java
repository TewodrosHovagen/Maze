package maze.application;

import Utils.logging.OutputResult;
import Utils.packageclasscode.Try;
import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerTask;
import maze.player.Player;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Match {
//    private Map<String, List<String>> gamesResults;
    private static Map<FileData,List<GameManager>> gameResultMap = new HashMap<>();
    private static List<FileData> mazeFiles = new ArrayList<>();
    private static List<Player> players;

    public static void main(String[] args) throws InterruptedException {
//        -mazes_folder <mazes_folder> -players <players_package> -threads <num_threads>
        String mazesFolder = args[0];
        String playersPackage = args[1];
        int numThread = args.length > 2 ? Integer.valueOf(args[2]) : 1;
        System.out.println("mazesFolder: "+ mazesFolder);
        System.out.println("playersPackage: "+ playersPackage);
        System.out.println("numThread: "+ numThread);

        FileParse fileParse;
        FileData dataFile;

        List<Class<?>> playersClasses = Try.getClassesInPackage(playersPackage);
        System.out.println("playersClasses: " + playersClasses);
        System.out.println("playersClassesSize: " + playersClasses.size());

        players = getPlayersInstanceFromClasses(playersClasses);
        System.out.println("players size: " + players.size());

        ExecutorService pool = Executors.newFixedThreadPool(numThread);

        if(Validation.checkExistenceOfFilePath(mazesFolder)){

            Path absPath = Paths.get(mazesFolder).toAbsolutePath();
            System.out.println("absPath: "+ absPath);
            File file = new File(absPath.toString());
            String[] listFiles = file.list();
            System.out.println("listFiles size: "+ listFiles.length);

            for(String fileName : listFiles){
                System.out.println("fileName: "+fileName);
                fileParse = new FileParse();
                dataFile = fileParse.parseFileData(absPath+ "/" + fileName);
                if (dataFile != null){
                    mazeFiles.add(dataFile);
                }
            }

        }
        for(FileData mazeFile: mazeFiles){
            List<GameManager> tasks = new ArrayList<>();
            for(Player player: players){
                GameManagerTask task = new GameManagerTask(mazeFile, player);
                tasks.add(task);
                pool.execute(task);
            }
            gameResultMap.put(mazeFile, tasks);
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        OutputResult.printConsoleOutputResult(gameResultMap);

    }
    private static List<Player> getPlayersInstanceFromClasses(List<Class<?>> classes){
        List<Player> playerInstances = new ArrayList<>();
        for (Class<?> c: classes){
            Object o = null;
            Player p = null;
            try {
                o = c.getDeclaredConstructor().newInstance();
                p = (Player)o;
                playerInstances.add(p);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return playerInstances;
    }

}
