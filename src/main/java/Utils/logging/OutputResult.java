package Utils.logging;

import maze.fileDataParse.FileData;
import maze.gameManager.GameManager;
import maze.gameManager.GameManagerTask;
import maze.player.MazePlayer;
import maze.player.PlayerRandom;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputResult {

    public static void printExcelOutputResult(Map<FileData,List<GameManager>> gameResultMap){
//        byte[] report = new XlsxBuilder().
//                startSheet("Dream cars").                            // start with sheet
//                startRow().                                          // then go row by row
//                setRowTitleHeight().                                 // set row style, add borders and so on
//                addTitleTextColumn("Dream cars").                    // add columns
//                startRow().                                          // another row
//                setRowTitleHeight().                                 // row styling
//                setRowThinBottomBorder().
//                addBoldTextLeftAlignedColumn("Dreamed By:").
//                addTextLeftAlignedColumn("John Seaman").
//                startRow().                                          // empty row
//                startRow().                                          // header row
//                setRowTitleHeight().
//                setRowThickTopBorder().
//                setRowThickBottomBorder().
//                addBoldTextCenterAlignedColumn("Type").
//                addBoldTextCenterAlignedColumn("Color").
//                addBoldTextCenterAlignedColumn("Reason").
//                startRow().                                          // rows with records (usually within a loop)
//                addTextLeftAlignedColumn("Ferrari").
//                addTextLeftAlignedColumn("Green").
//                addTextLeftAlignedColumn("It looks nice").
//                startRow().
//                addTextLeftAlignedColumn("Lamborgini").
//                addTextLeftAlignedColumn("Yellow").
//                addTextLeftAlignedColumn("It's fast enough").
//                startRow().
//                addTextLeftAlignedColumn("Bugatti").
//                addTextLeftAlignedColumn("Blue").
//                addTextLeftAlignedColumn("Price is awesome").
//                startRow().
//                setRowThinTopBorder().
//                startRow().
//                startRow().                                          // footer row
//                addTextLeftAlignedColumn("This is just a footer and I use it instead of 'Lorem ipsum dolor...'").
//                setColumnSize(0, "XXXXXXXXXXXXX".length()).          // setting up column sizes at the end of the sheet
//                setAutoSizeColumn(1).
//                setAutoSizeColumn(2).
//                build();
//                // now deal with byte array (e.g. write to the file or database)
//        try {
//            saveMyFile(report);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        XlsxBuilder xlsxBuilderResult = new XlsxBuilder();
        xlsxBuilderResult.startSheet("******** MAZE RESULT ********").startRow().startRow().startRow();
        xlsxBuilderResult.addBoldTextCenterAlignedColumn("");

        Map.Entry<FileData, List<GameManager>> gameFirstItem = gameResultMap.entrySet().iterator().next();
        for (GameManager gameManager : gameFirstItem.getValue()) {
                xlsxBuilderResult.addTextLeftAlignedColumn(gameManager.getPlayer().getClass().getSimpleName());
        }


        xlsxBuilderResult.startRow();

        for(Map.Entry<FileData,List<GameManager>> gameItem: gameResultMap.entrySet()){
            xlsxBuilderResult.addTextLeftAlignedColumn(gameItem.getKey().getMazeName());
            for(GameManager gameManager : gameItem.getValue()){
                GameManagerTask gameManagerTask = (GameManagerTask)gameManager;
                xlsxBuilderResult.addTextLeftAlignedColumn(gameManagerTask.getMaxStepsResults()+"");
            }
            xlsxBuilderResult.startRow();
        }
    }


    public static void printConsoleOutputResult(Map<FileData,List<GameManager>> gameResultMap) {

        System.out.println("******** MAZE RESULT ********");
        System.out.print("\t");
        Map.Entry<FileData, List<GameManager>> gameFirstItem = gameResultMap.entrySet().iterator().next();
        for (GameManager gameManager : gameFirstItem.getValue()) {
            System.out.print("\t" + gameManager.getPlayer().getClass().getSimpleName());
        }

        System.out.println();

        for(Map.Entry<FileData,List<GameManager>> gameItem: gameResultMap.entrySet()){
            System.out.print(gameItem.getKey().getMazeName());
            for(GameManager gameManager : gameItem.getValue()){
                System.out.print("\t\t"+((GameManagerTask)gameManager).getMaxStepsResults()+"\t\t");
            }
            System.out.println();
        }
    }

    private static void saveMyFile(byte[] bytes) throws IOException {
        FileUtils.writeByteArrayToFile(new File("C:\\Users\\sm0679\\Desktop\\output\\result.xlsx"), bytes);
    }

    public static void main(String[] args) {
        Map<FileData,List<GameManager>> gameResultMap = new HashMap<>();
        FileData fileData1 = new FileData();
        fileData1.setMazeName("Maze 1");
        FileData fileData2 = new FileData();
        fileData2.setMazeName("Maze 2");
        GameManager gameManager1 = new GameManagerTask(fileData1, new PlayerRandom());
        GameManager gameManager2 = new GameManagerTask(fileData2, new MazePlayer());
        List<GameManager> gameManagerList = new ArrayList<>();
        gameManagerList.add(gameManager1);
        gameManagerList.add(gameManager2);
        gameResultMap.put(fileData1,gameManagerList);
        gameResultMap.put(fileData2,gameManagerList);

        printConsoleOutputResult(gameResultMap);
    }
}
