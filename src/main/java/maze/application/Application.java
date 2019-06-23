package maze.application;

import utils.logging.Logger;
import utils.reports.SingleGameOutputFile;
import maze.gameManager.MazeData;
import maze.fileDataParse.FileParse;
import maze.gameManager.GameManagerImpl;

public class Application {

    private static final Logger log = Logger.getInstance();
    private boolean createAnOutputFile = false;
    private boolean isInputFileExist = false;
    private boolean runThePlayer = true;
    private String outputFilePath;
    private MazeData mazeData;

    public boolean isCreateAnOutputFile() {
        return createAnOutputFile;
    }
    public boolean isInputFileExist() {
        return isInputFileExist;
    }
    public boolean isRunThePlayer() {
        return runThePlayer;
    }

    public static void main(String[] args) {
        Application app = new Application();
//        System.out.println("removing file output ...");
//        FileUtil.file("./output.txt").delete();
        app.startApplication(args);
        closeLogger();
    }


    public void startApplication(String[] args) {
        argumentsValidationBeforeStartApplication(args);

        if (runThePlayer && createAnOutputFile) {
            log.info("START THE GAME!!!");
            try (SingleGameOutputFile outputFile = new SingleGameOutputFile(outputFilePath)) {
                GameManagerImpl game = new GameManagerImpl(mazeData);
                game.setOutputFile(outputFile);
                game.runGame();
            } catch (Exception e) {
                System.out.println("Problem while writing to output file: " + e);
            }
        } else
            log.info("GAME WILL NOT START!!!");

    }

    private void argumentsValidationBeforeStartApplication(String[] args) {
        int isArgMissing;
        String mazeFilePath ;
        String outputFilePath ;
        FileParse fileParse = new FileParse();

        isArgMissing = Validation.checkIfMissingArgs(args);
        if (isArgMissing == -1) {
            System.out.println(String.format("Missing maze file argument in command line" ));
            System.out.println(String.format("Missing output file argument in command line" ));
            runThePlayer = false;
            return;
        }
        else if(isArgMissing == 0){
            System.out.println(String.format("Missing output file argument in command line" ));
            runThePlayer = false;
            mazeFilePath = args[0];
            outputFilePath = "";
        }else{
            mazeFilePath = args[0];
            outputFilePath = args[1];
        }

        if (isArgMissing == 1 ){
            if(Validation.checkExistenceOfFilePath(outputFilePath) && !Validation.checkExistenceOfFile(outputFilePath)){
                createAnOutputFile = true;
                this.outputFilePath = outputFilePath;
            }
            else{
                System.out.println(String.format("Command line argument for output file: %s points to a bad path or to a file that already exists", outputFilePath ));
                runThePlayer = false;
            }
        }


        if(Validation.checkExistenceOfFilePath(mazeFilePath) && Validation.checkExistenceOfFile(mazeFilePath)){
            isInputFileExist = true;
            mazeData = fileParse.parseFileData(mazeFilePath);
        }
        else{
            System.out.println(String.format("Command line argument for maze: %s doesn't lead to a maze file", mazeFilePath ));
            runThePlayer = false;
        }
    }



    private static void closeLogger() {
        try {
            log.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
