package maze.FileDataParse;

import exceptions.WrongFileFormatException;
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
            fileData = parseFirstLines(br,new FileData());

            // read the first line from the text file
            String fileReader;
            String[][] mazeWorld = new String[fileData.getRows()][fileData.getColumns()];

            // loop until all lines are read
            for (int row =0; row < fileData.getRows(); row++){
                fileReader = br.readLine();
                if (fileReader != null){
                    if (fileReader.length() >= fileData.getColumns()){
                        for(int column=0; column < fileData.getColumns(); column++){
                            mazeWorld[row][column] = fileReader.charAt(column)+"";
                        }
                    }else{
                        int column=0;
                        while (column < fileReader.length()){
                            mazeWorld[row][column] = fileReader.charAt(column)+"";
                            column++;
                        }
                        while (column < fileData.getColumns()){
                            mazeWorld[row][column] = " ";
                            column++;
                        }
                    }

                }else{
                    for(int column=0; column < fileData.getColumns(); column++){
                        mazeWorld[row][column] = " ";
                    }
                }
            }

            fileData.setMazeWorld(mazeWorld);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (WrongFileFormatException e) {
                e.printStackTrace();
            }
        return fileData;
    }

    private boolean isValidChar(char charItem) {
        return (charItem == WALL) || (charItem == SPACE) || (charItem == TREASURE) || (charItem == PLAYER);
    }

    /**
     * The method parse the first lines of the maze data.
     * @param br
     * @param fileData
     * @return
     * @throws WrongFileFormatException
     * @throws IOException
     */

    private FileData parseFirstLines (BufferedReader br, FileData fileData) throws WrongFileFormatException, IOException {
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

