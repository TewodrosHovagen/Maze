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

    @Test(expected = WrongFileFormatException.class)
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
        Assert.assertEquals(fileData.getMazeName(),"Nice simple maze");
        Assert.assertEquals(fileData.getMaxSteps(),10);
        Assert.assertEquals(fileData.getRows(),4);
        Assert.assertEquals(fileData.getColumns(),10);

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
        Assert.assertEquals(fileData.getMaxSteps(),10);
        Assert.assertEquals(fileData.getRows(),4);
        Assert.assertEquals(fileData.getColumns(),10);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesIncorrectSpacesTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectSpacesInParamters.txt");
        Assert.assertNull(fileData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesIncorrectNumberTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectParamterNumber.txt");
        Assert.assertNull(fileData);
    }
    @Test()
    public void fileToParseFirstLinesStrangeNumberTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "strangeParameterNumber.txt");
        Assert.assertNotNull(fileData);
        Assert.assertEquals(fileData.getMaxSteps(),10);

    }
    @Test
    public void fileToParseMazeBodyCorrectTest(){
        String[][] mazeWorld = new String[][]{
                {"#", "#", "#", "#", "#", " ", " ", " ", " ", " "},
                {"#", " ", "@", " ", " ", " ", " ", " ", "#", " "},
                {"#", " ", " ", " ", "#", " ", " ", "$", " ", "#"},
                {" ", " ", " ", " ", "#", "#", "#", "#", "#", " "}
        };
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "correctInputFileExample.txt");
        Assert.assertNotNull(fileData);
        Assert.assertEquals(fileData.getMazeWorld(), mazeWorld);

    }
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
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyIncorrectCharTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectCharInMazeBody.txt");
        Assert.assertNull(fileData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyIncorrectCharTABTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectCharTABInMazeBody.txt");
        Assert.assertNull(fileData);
    }
    @Test
    public void fileToParseMazeBodyLessRowsThanDefinedWillFilledBySpacesTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyLessRowsThanDefined.txt");
        Assert.assertNotNull(fileData);
        Assert.assertEquals(fileData.getMazeWorld().length, 4);
    }
    @Test
    public void fileToParseMazeBodyLessColumnsThanDefinedWillFilledBySpacesTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyLessColumnsThanDefined.txt");
        Assert.assertNotNull(fileData);
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(fileData.getMazeWorld()[0].length, 10);
        }

    }
    @Test
    public void fileToParseMazeBodyExtraRowsThanDefinedWillBeIgnoredTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyExtraRowsThanDefined.txt");
        Assert.assertNotNull(fileData);
        Assert.assertEquals(fileData.getMazeWorld().length, 4);

    }
    @Test
    public void fileToParseMazeBodyExtraColumnsThanDefinedWillBeIgnoredTest(){
        FileData fileData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyExtraColumnsThanDefined.txt");
        Assert.assertNotNull(fileData);
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(fileData.getMazeWorld()[0].length, 10);
        }
    }


}
