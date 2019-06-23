package maze.fileDataParse;

import utils.exceptions.WrongFileFormatMultipleException;
import utils.exceptions.WrongFileFormatException;
import utils.logging.Logger;
import maze.gameManager.MazeData;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParse {

    private final Logger log = Logger.getInstance();
    private static final int VALUE_LOCATION =1;
    private static final int ITEM_NAME_LOCATION =0;
    private static final String MAX_STEPS_FORMAT ="MaxSteps";
    private static final String COLUMNS_FORMAT ="Cols";
    private static final String ROWS_FORMAT ="Rows";
    public static final char PLAYER ='@';
    public static final char WALL = '#';
    public static final char TREASURE ='$';
    public static final char SPACE =' ';
    private boolean foundPlayer = false;
    private boolean foundTreasure = false;

    private List<RuntimeException> headerFileExceptions = new ArrayList<>();
    private List<RuntimeException> bodyFileExceptions = new ArrayList<>();

    protected List<RuntimeException> getHeaderFileExceptions() {
        return headerFileExceptions;
    }

    protected List<RuntimeException> getBodyFileExceptions() {
        return bodyFileExceptions;
    }

    /**
     * The method parse the file from a given location and generate a MazeData object.
     * @param fileLocation - location for the maze mazeData
     * @return - MazeData object with all the maze mazeData
     */
    public MazeData parseFileData (String fileLocation){
            log.info("Reading the maze.txt file in location "+fileLocation);
            MazeData mazeData;

            try(// create a Buffered Reader object instance with a FileReader
                FileReader fr = new FileReader(fileLocation);
                BufferedReader br = new BufferedReader(fr)) {

                // create File Data to store the maze mazeData
                mazeData = parseFirstLines(br);

                // read the first line from the text file
                String fileReader;
                String[][] mazeWorld = new String[mazeData.getRows()][mazeData.getColumns()];

                // loop until all lines are read
                for (int row = 0; row < mazeData.getRows(); row++){
                    fileReader = br.readLine();
                    if (fileReader != null){
                        //If the file length is too long so need to work with the expected size
                        if (fileReader.length() >= mazeData.getColumns()){
                            //log.writeToOutput("The file length " + fileReader.length() + " is more than declared in file mazeData therefore using only the file mazeData " + mazeData.getColumns() + " as max column");
                            for(int column = 0; column < mazeData.getColumns(); column++){
                                addCharacterToBoard(fileReader.charAt(column),row,column, mazeData,mazeWorld);
                            }
                        }else{
                            //log.writeToOutput("The file length " + fileReader.length() + " is less than declared in file mazeData therefore need to complete to " + mazeData.getColumns());
                            int column=0;
                            while (column < fileReader.length()){
                                addCharacterToBoard(fileReader.charAt(column),row,column, mazeData,mazeWorld);
                                column++;
                            }
                            while (column < mazeData.getColumns()){
                                mazeWorld[row][column] = " ";
                                column++;
                            }
                        }
                    }else{
                        //There are no more rows to read from the file
                        for(int column = 0; column < mazeData.getColumns(); column++){
                            mazeWorld[row][column] = " ";
                        }
                    }
                }

                if (!foundPlayer || !foundTreasure){
                    if (!foundTreasure && ! foundPlayer) {
                        bodyFileExceptions.add(new WrongFileFormatException("Missing @ in maze"));
                        bodyFileExceptions.add(new WrongFileFormatException("Missing $ in maze"));
                    }
                    else {
                        if (!foundPlayer) {
                            bodyFileExceptions.add(new WrongFileFormatException("Missing @ in maze"));
                        } else {
                            bodyFileExceptions.add(new WrongFileFormatException("Missing $ in maze"));
                        }
                    }
                }else{
                    mazeData.setMazeWorld(mazeWorld);
                }
            } catch (IOException e) {
                log.info("There was a problem with the file " + e);
                System.out.println(String.format("Command line argument for maze: %s leads to a file that cannot be opened", fileLocation ));
                throw new WrongFileFormatException(e.getMessage());
            }
        if(!bodyFileExceptions.isEmpty()) {
            System.out.println("Bad maze in maze file:");
            Exception e =  new WrongFileFormatMultipleException(bodyFileExceptions);
            mazeData.setValidFile(false);

        }
        return mazeData;
    }

    /**
     * The method add character to the board
     * @param charItem
     * @param row
     * @param column
     * @param mazeData
     * @param mazeWorld
     */
    private void addCharacterToBoard (char charItem, int row, int column, MazeData mazeData, String [][] mazeWorld ) {
        if(isValidChar(charItem)) {
            if(isPlayer(charItem)){
                if (!foundPlayer) {
                    mazeData.setPlayerLocation(new Point(column,row));
                    foundPlayer = true;
                    log.info(String.format("Found a player in position: (%s,%s)",column,row));
                }else {
                    bodyFileExceptions.add(new WrongFileFormatException("More than one @ in maze"));
                }
            }
            if(isTreasure(charItem)){
                if (!foundTreasure) {
                    mazeData.setTreasureLocation(new Point(column,row));
                    foundTreasure = true;
                    log.info(String.format("Found a treasure in position: (%s,%s)",column,row));
                }else {
                    bodyFileExceptions.add(new WrongFileFormatException("More than one $ in maze"));
                }
            }
            mazeWorld[row][column] = charItem + "";
        }else{
            int ascii = (int) charItem;
            if (ascii == 9) {
                bodyFileExceptions.add(new WrongFileFormatException(
                        String.format("Wrong character in maze: TAB in row %s, col %s", row, column)));
            }
            else if (ascii != 10) {
                bodyFileExceptions.add(new WrongFileFormatException(
                        String.format("Wrong character in maze: %s in row %s, col %s", charItem, row, column)));
            }
        }
    }


    /**
     *  Validate if the char is the treasure char: '$'
     * @param charItem - char item to check
     * @return - true if the treasure char false otherwise.
     */
    private boolean isTreasure(char charItem) {
        return charItem == TREASURE;
    }


    /**
     *  Validate if the char is the player char: '@'
     * @param charItem - char item to check
     * @return - true if the player char false otherwise.
     */
    private boolean isPlayer(char charItem) {
        return charItem == PLAYER;
    }

    /**
     *  Validate if the char is : '@' , ' ' , '$' , '#'
     * @param charItem - char item to check
     * @return - true if one of the above char false otherwise.
     */
    private boolean isValidChar(char charItem) {
        return (charItem == WALL) || (charItem == SPACE) || (charItem == TREASURE) || (charItem == PLAYER);
    }

    /**
     * The method parse the first lines of the maze mazeData.
     * which includes:
     *  Maze Name , Max Step , Number of rows, Number of columns.
     *  Also validate if the format is as expected, if Throw WrongFileFormatException.
     * @param br - BufferReader to read from the file
     * @return - New File date with the first 4 items
     * @throws WrongFileFormatException
     * @throws IOException
     */
    private MazeData parseFirstLines (BufferedReader br) throws IOException {
        MazeData mazeData = new MazeData();
        try {
            log.info("Paring the first 4 lines");
            String[] pairValues;
            //Get the maze Name
            String fileReader = br.readLine();
            mazeData.setMazeName(fileReader);
            log.info("Maze name is " + fileReader);

            //Get the Max steps
            fileReader = br.readLine();
            pairValues = getTextValuesOnFormat(fileReader,MAX_STEPS_FORMAT,String.format("expected in line 2 - %s = <num> \ngot: %s", MAX_STEPS_FORMAT, fileReader));
            int rowValue = getTextIntValue(pairValues[VALUE_LOCATION],String.format("expected in line 2 - %s = <num> \ngot: %s", MAX_STEPS_FORMAT, fileReader));
            mazeData.setMaxSteps(rowValue);
            log.info("Maze Max Step for user in the game is is " + rowValue);


            //Get the Totals Rows
            fileReader = br.readLine();
            pairValues = getTextValuesOnFormat(fileReader, ROWS_FORMAT, String.format("expected in line 3 - %s = <num> \ngot: %s", ROWS_FORMAT, fileReader));
            int totalRows = getTextIntValue(pairValues[VALUE_LOCATION],String.format("expected in line 3 - %s = <num> \ngot: %s", ROWS_FORMAT, fileReader));
            mazeData.setRows(totalRows);
            log.info("The total number of rows is " + totalRows);

            //Get the Totals Columns
            fileReader = br.readLine();
            pairValues = getTextValuesOnFormat(fileReader,COLUMNS_FORMAT,String.format("expected in line 4 - %s = <num> \ngot: %s", COLUMNS_FORMAT, fileReader));
            int totalColumns = getTextIntValue(pairValues[VALUE_LOCATION],String.format("expected in line 4 - %s = <num> \ngot: %s", COLUMNS_FORMAT, fileReader));
            mazeData.setColumns(totalColumns);
            log.info("The total number of columns is " + totalColumns);

        } catch(ArrayIndexOutOfBoundsException e)   {
            headerFileExceptions.add(new ArrayIndexOutOfBoundsException("Missing one of the parameters"));
        }
        if (!headerFileExceptions.isEmpty()){
            System.out.println("Bad maze file header:");
            Exception e =  new WrongFileFormatMultipleException(headerFileExceptions);
            mazeData.setValidFile(false);
        }
        return mazeData;
    }

    private String[] getStringValue(String strToParse) throws IOException{
        log.info("Parsing the line " + strToParse);
        String[] strValues;
        try{
            strValues = strToParse.split("=");
            if(strValues.length == 2) {
                strValues[0] = strValues[0].trim();
                strValues[1] = strValues[1].trim();
            }
            return strValues;
        }catch(Exception e){
            throw new IOException();
        }
    }

    private String[] getTextValuesOnFormat(String text,String format, String errorMessage) throws IOException {
        String[] pairValues = getStringValue(text);
        if (!pairValues[ITEM_NAME_LOCATION].equals(format))
            headerFileExceptions.add(new WrongFileFormatException(errorMessage));
        return pairValues;
    }

    private int getTextIntValue(String textValue, String errorMessage){
        try{
            return Integer.parseInt(textValue);
        }catch (NumberFormatException e){
            headerFileExceptions.add(new WrongFileFormatException(errorMessage));
        }
        return 0;
    }
}

