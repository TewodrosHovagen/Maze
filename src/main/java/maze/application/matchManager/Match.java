package maze.application.matchManager;

import maze.application.Validation;
import utils.logging.Logger;
import utils.reports.MultipleGameOutputResult;
import maze.fileDataParse.FileParse;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerTask;
import maze.gameManager.MazeData;
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
    private static final Logger log = Logger.getInstance();
    private  Map<MazeData,List<GameManager>> gameResultMap = new HashMap<>();
    private  List<MazeData> mazeFiles = new ArrayList<>();
    private  List<Class<?>> players;
    private String mazesFolder = "";
    private String playersPackage;
    private int numThread;
    private ExecutorService pool;
    private Thread thread ;
    private boolean loadPlayers = true;
    private boolean validationArgsResult;
    private boolean isThreadsStrategyPool = false;
    private boolean isThreadsStrategySingleThread = false;

    protected boolean isLoadPlayers() {
        return loadPlayers;
    }

    protected boolean isValidationArgsResult() {
        return validationArgsResult;
    }

    protected List<MazeData> getMazeFiles() {
        return mazeFiles;
    }

    protected List<Class<?>> getPlayers() {
        return players;
    }

    protected boolean isThreadsStrategyPool() {
        return isThreadsStrategyPool;
    }

    protected boolean isThreadsStrategySingleThread() {
        return isThreadsStrategySingleThread;
    }

    public static void main(String[] args) throws InterruptedException {
        Match match = new Match();
        match.startApplication(args);
        closeLogger();
    }


    public void startApplication(String[] args)  {
        this.validationArgsResult = argumentsValidationBeforeStartApplication(args);
        if (validationArgsResult) {
            loadValidMazeFiles();
        }
        if(loadPlayers){
            players = getPlayersClassesInPackage(playersPackage);
            log.info("playersClasses: " + players);
            log.info("players size: " + players.size());

            sendTasksToExecution();
            MultipleGameOutputResult.printConsoleOutputResult(gameResultMap);
        }
    }

    private boolean argumentsValidationBeforeStartApplication(String[] args){
        int isArgMissing;
        //        -mazes_folder <mazes_folder> -players <players_package> -threads <num_threads>
        isArgMissing = Validation.checkIfMissingArgs(args);
        if (isArgMissing == -1) {
            System.out.println(String.format("Missing mazes_folder argument in command line" ));
            System.out.println(String.format("Missing players_package argument in command line" ));
            loadPlayers = false;
            return false;
        }
        else if(isArgMissing == 0){
            System.out.println(String.format("Missing players_package argument in command line" ));
            loadPlayers = false;
            this.mazesFolder = args[0];
            log.info("mazesFolder: "+ mazesFolder);
            return true;
        }else{
            this.mazesFolder = args[0];
            log.info("mazesFolder: "+ mazesFolder);
            this.playersPackage = args[1];
            log.info("playersPackage: "+ playersPackage);
            this.numThread = args.length > 2 ? Integer.valueOf(args[2]) : 1;
            log.info("numThread: "+ numThread);
            return true;
        }

    }

    private final List<Class<?>> getPlayersClassesInPackage(String packageName) {
        log.info("packageName: "+packageName);
        String path = packageName.replace(".", File.separator);
        log.info("path: "+path);

        List<Class<?>> classes = new ArrayList<>();
        String[] classPathEntries = System.getProperty("java.class.path").split(
                System.getProperty("path.separator")
        );

        String name;
        log.info("classPathEntries length: "+ classPathEntries.length);
        log.info("classPathEntries: "+ classPathEntries.toString());

        for (String classpathEntry : classPathEntries) {
            if (!classpathEntry.endsWith(".jar")){
                try {
                    log.info("classpathEntry: " + classpathEntry);
                    File base = new File(classpathEntry + File.separatorChar + path);
                    log.info("base.listFiles(): " + base.listFiles());
                    for (File file : base.listFiles()) {
                        name = file.getName();
                        if (name.endsWith(".class")
                                && name.startsWith("Player")
                                && !name.equals("Player.class")
                                && !name.equals("PlayerInterface.class")
                                && !name.contains("$")) {
                            log.info("class name before substring: " + name);
                            name = name.substring(0, name.length() - 6);
                            log.info("class name: " + name);
                            Class<?> c = Class.forName(packageName + "." + name);
                            try {
                                Object o = c.getDeclaredConstructor().newInstance();
                                Player p = (Player) o;
                                classes.add(c);
                            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                                System.out.println(String.format("Class %s does not passed casting to Player properly ", c.getName()));
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return classes;
    }

    private void loadValidMazeFiles(){
        FileParse fileParse;
        MazeData mazeData;

        if(Validation.checkExistenceOfFilePath(mazesFolder)){

            Path absPath = Paths.get(mazesFolder).toAbsolutePath();
            log.info("absPath: "+ absPath);
            File file = new File(absPath.toString());
            String[] listFiles = file.list();
            log.info("listFiles size: "+ listFiles.length);

            for(String fileName : listFiles){
                log.info("fileName: "+fileName);
                fileParse = new FileParse();
                mazeData = fileParse.parseFileData(absPath+ "/" + fileName);
                if (mazeData.isValidFile()){
                    mazeFiles.add(mazeData);
                }
            }

        }
    }
    private void sendTasksToExecution() {
        threadsStrategy();
    }
    private void threadsStrategy() {
        if(numThread > 1){
            isThreadsStrategyPool = true;
            executeByPool();
        }
        else{
            isThreadsStrategySingleThread = true;
            executeBySingleThread();
        }
    }
    private void executeByPool()  {
        this.pool = Executors.newFixedThreadPool(numThread);
        for(MazeData mazeData: mazeFiles){
            List<GameManager> tasks = new ArrayList<>();
            for(Class<?> classPlayer: players){
                GameManagerTask task = taskCreation(tasks, classPlayer, mazeData);
                this.pool.execute(task);
            }
            gameResultMap.put(mazeData, tasks);
        }
        pool.shutdown();
        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void executeBySingleThread(){
        this.thread = new Thread();
        for(MazeData mazeData: mazeFiles){
            List<GameManager> tasks = new ArrayList<>();
            for(Class<?> classPlayer: players){
                GameManagerTask task = taskCreation(tasks, classPlayer, mazeData);
                while(this.thread.isAlive()) {//wait for thread will not be alive}
                }
                this.thread = new Thread(task);
                this.thread.start();
            }
            gameResultMap.put(mazeData, tasks);
        }
    }
    private GameManagerTask taskCreation(List<GameManager> tasks, Class<?> classPlayer, MazeData mazeData){
        Player player = null;
        try {
            player = (Player)classPlayer.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("Player instantiation has failed");
        }
        GameManagerTask task = new GameManagerTask(mazeData, player);
        tasks.add(task);
        return task;
    }

    private static void closeLogger() {
        try {
            log.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
