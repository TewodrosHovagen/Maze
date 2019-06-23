package maze.application;

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
    private boolean runThePlayer = true;

    public boolean isRunThePlayer() {
        return runThePlayer;
    }

    private String mazesFolder = "";
    private String playersPackage;
    private int numThread;


    public static void main(String[] args) throws InterruptedException {
        Match match = new Match();
        match.startApplication(args);
        closeLogger();
    }


    public void startApplication(String[] args) throws InterruptedException {
        boolean res = argumentsValidationBeforeStartApplication(args);
        if (res) {
            addValidMazeFilesToList();
        }
        if(runThePlayer){
            List<Class<?>> playersClasses = getClassesInPackage(playersPackage);
            log.info("playersClasses: " + playersClasses);
            log.info("playersClassesSize: " + playersClasses.size());

            players = getPlayersInstanceFromClasses(playersClasses);
            log.info("players size: " + players.size());
//TODO: add suppoort in one thread no pool
            sendTasksToPoolExecution();

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
            runThePlayer = false;
            return false;
        }
        else if(isArgMissing == 0){
            System.out.println(String.format("Missing players_package argument in command line" ));
            runThePlayer = false;
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

    private final List<Class<?>> getClassesInPackage(String packageName) {
        String path = packageName.replace(".", File.separator);
        List<Class<?>> classes = new ArrayList<>();
        String[] classPathEntries = System.getProperty("java.class.path").split(
                System.getProperty("path.separator")
        );

        String name;
        log.info("classPathEntries length: "+ classPathEntries.length);
        for (String classpathEntry : classPathEntries) {
            try {
                log.info("classpathEntry: "+ classpathEntry);
                File base = new File(classpathEntry + File.separatorChar + path);
                log.info("base.listFiles(): "+ base.listFiles());
                for (File file : base.listFiles()) {
                    name = file.getName();
                    if (name.endsWith(".class")
                            && name.startsWith("Player")
                            && !name.equals("Player.class")
                            && !name.equals("PlayerInterface.class")
                            && !name.contains("$")) {
                        log.info("class name before substring: "+ name);
                        name = name.substring(0, name.length() - 6);
                        log.info("class name: "+ name);
                        classes.add(Class.forName(packageName + "." + name));
                    }
                }
            } catch (Exception ex) {
                // Silence is gold
            }

        }
        return classes;
    }

    private List<Class<?>> getPlayersInstanceFromClasses(List<Class<?>> classes){
        List<Class<?>> playerClasses = new ArrayList<>();
        for (Class<?> c: classes){
            Object o = null;
            Player p = null;
            try {
                o = c.getDeclaredConstructor().newInstance();
                p = (Player)o;
                playerClasses.add(c);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println(String.format("Class %s does not passed casting to Player properly ", c.getName()));
//                e.printStackTrace();
            }
        }
        return playerClasses;
    }

    private void addValidMazeFilesToList(){
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
    private void sendTasksToPoolExecution() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(numThread);
        for(MazeData mazeData: mazeFiles){
            List<GameManager> tasks = new ArrayList<>();
            for(Class<?> classPlayer: players){
                Player player = null;
                try {
                    player = (Player)classPlayer.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("Player instansiation has failed");
//                e.printStackTrace();
            }
                GameManagerTask task = new GameManagerTask(mazeData, player);
                tasks.add(task);
                pool.execute(task);
            }
            gameResultMap.put(mazeData, tasks);
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static void closeLogger() {
        try {
            log.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
