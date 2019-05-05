package maze.FileDataParse;

import exceptions.WrongFileFormatException;

import java.awt.*;
import java.io.*;

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

    /**
     * The method generate parse the file.
     * @param args
     * @return
     */
    public FileData parseFileData (String [] args ) {

            FileData fileData=null;
            try(// create a Buffered Reader object instance with a FileReader
                BufferedReader br = new BufferedReader(new FileReader("maze.txt"))) {

                // create File Data to store the maze data
                fileData = parseFirstLines(br);

                // read the first line from the text file
                String fileReader;
                String[][] mazeWorld = new String[fileData.getRows()][fileData.getColumns()];

                // loop until all lines are read
                for (int row =0; row < fileData.getRows(); row++){
                    fileReader = br.readLine();
                    if (fileReader != null){
                        //The file length is too long so need to work with the expected size
                        if (fileReader.length() >= fileData.getColumns()){
                            for(int column=0; column < fileData.getColumns(); column++){
                                char charItem = fileReader.charAt(column);
                                addCharacterToBoard(fileReader.charAt(column),row,column,fileData,mazeWorld);
                            }
                        }else{
                            //There are less lines in the file need to complete spaces
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
                    if (!foundTreasure && ! foundPlayer){
                        throw new WrongFileFormatException("There is no treasure and player on the board");
                    }else{
                        if (!foundPlayer){
                            throw new WrongFileFormatException("There is no player on the board");
                        }else{
                            throw new WrongFileFormatException("There is no treasure on the board");
                        }
                    }
                }else{
                    fileData.setMazeWorld(mazeWorld);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (WrongFileFormatException e) {
                e.printStackTrace();
                return null;
            }
        return fileData;
    }


    private void addCharacterToBoard (char charItem, int row, int column, FileData fileData,String [][] mazeWorld ) throws  WrongFileFormatException{
        if(isValidChar(charItem)) {
            if(isPlayer(charItem)){
                if (!foundPlayer) {
                    fileData.setPlayerLocation(new Point(row,column));
                    foundPlayer = true;
                }else {
                    throw new WrongFileFormatException("The is more than one Players in the board");
                }
            }
            if(isTreasure(charItem)){
                if (!foundTreasure) {
                    fileData.setTreasureLocation(new Point(row,column));
                    foundTreasure = true;
                }else {
                    throw new WrongFileFormatException("The is more than one Treasure in the board");
                }
            }
            mazeWorld[row][column] = charItem + "";
        }else{
            throw  new WrongFileFormatException("The character "  + charItem + " is not valid one");
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

    private FileData parseFirstLines (BufferedReader br) throws WrongFileFormatException, IOException {
        FileData fileData = new FileData();
        try {
            String[] pairValues;
            //Get the maze Name
            String fileReader = br.readLine();
            fileData.setMazeName(fileReader);

            //Get the Max steps
            fileReader = br.readLine();
            pairValues = getStringValue(fileReader);
            int rowValue = Integer.parseInt(pairValues[VALUE_LOCATION]);
            fileData.setMaxSteps(rowValue);
            if (!pairValues[ITEM_NAME_LOCATION].equals(MAX_STEPS_FORMAT))
                throw new WrongFileFormatException("Wrong item name the text is " + pairValues[ITEM_NAME_LOCATION] + " while should be " + MAX_STEPS_FORMAT);

            //Get the Totals Rows
            fileReader = br.readLine();
            pairValues = getStringValue(fileReader);
            int totalRows = Integer.parseInt(pairValues[VALUE_LOCATION]);
            fileData.setRows(totalRows);
            if (!pairValues[ITEM_NAME_LOCATION].equals(ROWS_FORMAT))
                throw new WrongFileFormatException("Wrong item name the text is " + pairValues[ITEM_NAME_LOCATION] + " while should be " + ROWS_FORMAT);

            //Get the Totals Columns
            fileReader = br.readLine();
            pairValues = getStringValue(fileReader);
            int totalColumns = Integer.parseInt(pairValues[VALUE_LOCATION]);;
            fileData.setColumns(totalColumns);
            if (!pairValues[ITEM_NAME_LOCATION].equals(COLUMNS_FORMAT))
                throw new WrongFileFormatException("Wrong item name the text is " + pairValues[ITEM_NAME_LOCATION] + " while should be " + COLUMNS_FORMAT);

        }catch (NumberFormatException e){
            throw new WrongFileFormatException();
        }
        return fileData;
    }

    private String[] getStringValue(String strToParse){
        strToParse = strToParse.replaceAll(" ","");
        String[] strValues = strToParse.split("=");
        return strValues;
    }



}

