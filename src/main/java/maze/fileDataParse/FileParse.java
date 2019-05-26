package maze.fileDataParse;

import Utils.GenericMultipleException;
import Utils.NumberParseException;
import Utils.WrongFileFormatException;
import Utils.logging.Logger;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileParse {

    private static final int VALUE_LOCATION =1;
    private static final int ITEM_NAME_LOCATION =0;
    private static final String MAX_STEPS_FORMAT ="MaxSteps";
    private static final String COLUMNS_FORMAT ="Cols";
    private static final String ROWS_FORMAT ="Rows";
    private static final char PLAYER ='@';
    private static final char WALL = '#';
    private static final char TREASURE ='$';
    private static final char SPACE =' ';
    private boolean foundPlayer = false;
    private boolean foundTreasure = false;

    private List<RuntimeException> headerFileExceptions = new ArrayList<>();
    private List<RuntimeException> bodyFileExceptions = new ArrayList<>();



    /**
     * The method generate parse the file.
     * @param fileLocation
     * @return
     */
    public FileData parseFileData (String fileLocation) {
            Logger.info("Reading the maze.txt file");
            FileData fileData;
//            String fileLocation;

            //TODO: throw input output exception
            if (fileLocation.equals(""))
                fileLocation ="maze.txt";

            try(// create a Buffered Reader object instance with a FileReader
                BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {

                // create File Data to store the maze data
                fileData = parseFirstLines(br);

                // read the first line from the text file
                String fileReader;
                String[][] mazeWorld = new String[fileData.getRows()][fileData.getColumns()];

                // loop until all lines are read
                for (int row =0; row < fileData.getRows(); row++){
                    fileReader = br.readLine();
                    if (fileReader != null){
                        //If the file length is too long so need to work with the expected size
                        if (fileReader.length() >= fileData.getColumns()){
                            //log.info("The file length " + fileReader.length() + " is more than declared in file data therefore using only the file data " + fileData.getColumns() + " as max column");
                            for(int column=0; column < fileData.getColumns(); column++){
                                addCharacterToBoard(fileReader.charAt(column),row,column,fileData,mazeWorld);
                            }
                        }else{
                            //log.info("The file length " + fileReader.length() + " is less than declared in file data therefore need to complete to " + fileData.getColumns());
                            int column=0;
                            while (column < fileReader.length()){
                                addCharacterToBoard(fileReader.charAt(column),row,column,fileData,mazeWorld);
                                column++;
                            }
                            while (column < fileData.getColumns()){
                                mazeWorld[row][column] = " ";
                                column++;
                            }
                        }
                    }else{
                        //There are no more rows to read from the file
                        for(int column=0; column < fileData.getColumns(); column++){
                            mazeWorld[row][column] = " ";
                        }
                    }
                }

                if (!foundPlayer || !foundTreasure){
                    if (!foundPlayer){
                        bodyFileExceptions.add(new WrongFileFormatException("Missing @ in maze"));
                    }else{
                        bodyFileExceptions.add(new WrongFileFormatException("Missing $ in maze"));
                    }
                }else{
                    fileData.setMazeWorld(mazeWorld);
                }
            } catch (IOException e) {
                Logger.info("There was a problem with the file " + e);
                System.out.println(String.format("Command line argument for maze: %s leads to a file that cannot be opened", fileLocation ));
                return null;
            }
        if(!bodyFileExceptions.isEmpty()) {
            System.out.println("Bad maze in maze file:");
            throw new GenericMultipleException(bodyFileExceptions);
        }
        return fileData;
    }


    private void addCharacterToBoard (char charItem, int row, int column, FileData fileData,String [][] mazeWorld ) {
        if(isValidChar(charItem)) {
            if(isPlayer(charItem)){
                if (!foundPlayer) {
                    fileData.setPlayerLocation(new Point(row,column));
                    foundPlayer = true;
                    Logger.info("Found a player in position" + new Point(row,column));
                }else {
                    bodyFileExceptions.add(new WrongFileFormatException("More than one @ in maze"));
                }
            }
            if(isTreasure(charItem)){
                if (!foundTreasure) {
                    fileData.setTreasureLocation(new Point(row,column));
                    foundTreasure = true;
                    Logger.info("Found a treasure in position" + new Point(row,column));
                }else {
                    bodyFileExceptions.add(new WrongFileFormatException("More than one $ in maze"));
                }
            }
            mazeWorld[row][column] = charItem + "";
        }else{
            bodyFileExceptions.add(new WrongFileFormatException(
                    String.format("Wrong character in maze: %s in row %s, col %s", charItem, row, column)));

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
     * The method parse the first lines of the maze data.
     * which includes:
     *  Maze Name , Max Step , Number of rows, Number of columns.
     *  Also validate if the format is as expected, if Throw WrongFileFormatException.
     * @param br - BufferReader to read from the file
     * @return - New File date with the first 4 items
     * @throws WrongFileFormatException
     * @throws IOException
     */

    private FileData parseFirstLines (BufferedReader br) throws IOException {
        FileData fileData = new FileData();
        try {
            Logger.info("Paring the first 4 lines");
            String[] pairValues;
            //Get the maze Name
            String fileReader = br.readLine();
            fileData.setMazeName(fileReader);
            Logger.info("Maze name is " + fileReader);

            //Get the Max steps
            fileReader = br.readLine();
            pairValues = getStringValue(fileReader);
            if (!pairValues[ITEM_NAME_LOCATION].equals(MAX_STEPS_FORMAT))
                headerFileExceptions.add(new WrongFileFormatException(String.format("expected in line 2 - %s = <num> \ngot: %s", MAX_STEPS_FORMAT, fileReader)));

            try {
                int rowValue = Integer.parseInt(pairValues[VALUE_LOCATION]);
                fileData.setMaxSteps(rowValue);
                Logger.info("Maze Max Step for user in the game is is " + rowValue);
            } catch (NumberParseException e) {
                headerFileExceptions.add(new NumberParseException(String.format("expected in line 2 - %s = <num> \ngot: %s", MAX_STEPS_FORMAT, fileReader)));
            }

            //Get the Totals Rows
            fileReader = br.readLine();
            pairValues = getStringValue(fileReader);
            if (!pairValues[ITEM_NAME_LOCATION].equals(ROWS_FORMAT))
                headerFileExceptions.add(new WrongFileFormatException(String.format("expected in line 3 - %s = <num> \ngot: %s", ROWS_FORMAT, fileReader)));

            try {
                int totalRows = Integer.parseInt(pairValues[VALUE_LOCATION]);
                fileData.setRows(totalRows);
                Logger.info("The total number of rows is " + totalRows);
            } catch (NumberParseException e) {
                headerFileExceptions.add(new NumberParseException(String.format("expected in line 3 - %s = <num> \ngot: %s", ROWS_FORMAT, fileReader)));
            }

            //Get the Totals Columns
            fileReader = br.readLine();
            pairValues = getStringValue(fileReader);
            if (!pairValues[ITEM_NAME_LOCATION].equals(COLUMNS_FORMAT))
                headerFileExceptions.add(new WrongFileFormatException(String.format("expected in line 4 - %s = <num> \ngot: %s", COLUMNS_FORMAT, fileReader)));

            try {
                int totalColumns = Integer.parseInt(pairValues[VALUE_LOCATION]);
                fileData.setColumns(totalColumns);
                Logger.info("The total number of columns is " + totalColumns);

            } catch (NumberParseException e) {
                headerFileExceptions.add(new NumberParseException(String.format("expected in line 4 - %s = <num> \ngot: %s", COLUMNS_FORMAT, fileReader)));
            }
        } catch(ArrayIndexOutOfBoundsException e)   {
            headerFileExceptions.add(new ArrayIndexOutOfBoundsException("Missing one of the parameters"));

        }
        if (!headerFileExceptions.isEmpty()){
            System.out.println("Bad maze file header:");
            throw new GenericMultipleException(headerFileExceptions);

        }
        return fileData;
    }

    private String[] getStringValue(String strToParse){
        Logger.info("Parsing the line " + strToParse);
        String[] strValues = strToParse.split("=");
        strValues[0] = strValues[0].trim();
        strValues[1] = strValues[1].trim();
        return strValues;
    }



}

