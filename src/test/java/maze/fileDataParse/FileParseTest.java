package maze.fileDataParse;
import Utils.exceptions.WrongFileFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;

public class FileParseTest {
    private final String pathTofileDataParseResources = "./src/test/resources/fileDataParse/";
    private FileParse fileParse;

    @Before
    public void setup(){
        this.fileParse = new FileParse();
    }

    @Test
    public void emptyFileParseLocationTest(){

    }
    @Test
    public void correctFileToParseLocationTest(){

    }
    //TODO: null pointer
    @Test(expected = IOException.class)
    public void fileToParseCouldnotBeOpenedTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "nonOpenedFile");
        Assert.assertNull(fileData);
    }
    @Test
    public void fileToParseFirstLinesCorrectTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "correctInputFileExample.txt");
        Assert.assertNotNull(fileData);
    }

    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesMissingParameterTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "missingParameter.txt");
        Assert.assertNull(fileData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesIncorrectSyntaxTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectParamterSyntax.txt");
        Assert.assertNull(fileData);
    }
    @Test
    public void fileToParseFirstLinesCorrectSpacesTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "correctSpacesInParamters.txt");
        Assert.assertNotNull(fileData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesIncorrectSpacesTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectSpacesInParamters.txt");
        Assert.assertNull(fileData);
    }
    @Test
    public void fileToParseMazeBodyCorrectTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "correctInputFileExample.txt");
        Assert.assertNotNull(fileData);
    }
    //TODO: need to throw all error
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyMissingAllBodyTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "missingAllMazeBody.txt");
        Assert.assertNull(fileData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyMissingPlayerTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "missingPlayerSign.txt");
        Assert.assertNull(fileData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyMissingTreasureTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "missingTreasureSign.txt");
        Assert.assertNull(fileData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyTwoPlayersSignsTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "twoPlayerSigns.txt");
        Assert.assertNull(fileData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyTwoTreasureSignsTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "twoTreasureSigns.txt");
        Assert.assertNull(fileData);
    }
    //TODO: add support in TAB chars etc...
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyIncorrectCharTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectCharInMazeBody.txt");
        Assert.assertNull(fileData);
    }
    @Test
    public void fileToParseMazeBodyLessRowsThanDefinedWillFilledBySpacesTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyLessRowsThanDefined.txt");
        Assert.assertNotNull(fileData);
    }
    @Test
    public void fileToParseMazeBodyLessColumnsThanDefinedWillFilledBySpacesTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyLessColumnsThanDefined.txt");
        Assert.assertNotNull(fileData);
    }
    @Test
    public void fileToParseMazeBodyExtraRowsThanDefinedWillBeIgnoredTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyExtraRowsThanDefined.txt");
        Assert.assertNotNull(fileData);
    }
    @Test
    public void fileToParseMazeBodyExtraColumnsThanDefinedWillBeIgnoredTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyExtraColumnsThanDefined.txt");
        Assert.assertNotNull(fileData);
    }


}
