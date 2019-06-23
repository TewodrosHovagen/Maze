package maze.fileDataParse;
import Utils.exceptions.WrongFileFormatException;
import maze.gameManager.MazeData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileParseTest {
    private final String pathTofileDataParseResources = "./src/test/resources/fileDataParse/";
    private FileParse fileParse;

    @Before
    public void setup(){
        this.fileParse = new FileParse();
    }

    @Test(expected = WrongFileFormatException.class)
    public void fileToParseCouldnotBeOpenedTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "nonOpenedFile");
        Assert.assertNull(mazeData);
    }
    @Test
    public void fileToParseFirstLinesCorrectTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "correctInputFileExample.txt");
        Assert.assertNotNull(mazeData);
        Assert.assertEquals(mazeData.getMazeName(),"Nice simple maze");
        Assert.assertEquals(mazeData.getMaxSteps(),10);
        Assert.assertEquals(mazeData.getRows(),4);
        Assert.assertEquals(mazeData.getColumns(),10);

    }

    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesMissingParameterTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "missingParameter.txt");
        Assert.assertNull(mazeData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesIncorrectSyntaxTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectParamterSyntax.txt");
        Assert.assertNull(mazeData);
    }
    @Test
    public void fileToParseFirstLinesCorrectSpacesTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "correctSpacesInParamters.txt");
        Assert.assertNotNull(mazeData);
        Assert.assertEquals(mazeData.getMaxSteps(),10);
        Assert.assertEquals(mazeData.getRows(),4);
        Assert.assertEquals(mazeData.getColumns(),10);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesIncorrectSpacesTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectSpacesInParamters.txt");
        Assert.assertNull(mazeData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseFirstLinesIncorrectNumberTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectParamterNumber.txt");
        Assert.assertNull(mazeData);
    }
    @Test()
    public void fileToParseFirstLinesStrangeNumberTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "strangeParameterNumber.txt");
        Assert.assertNotNull(mazeData);
        Assert.assertEquals(mazeData.getMaxSteps(),10);

    }
    @Test
    public void fileToParseMazeBodyCorrectTest(){
        String[][] mazeWorld = new String[][]{
                {"#", "#", "#", "#", "#", " ", " ", " ", " ", " "},
                {"#", " ", "@", " ", " ", " ", " ", " ", "#", " "},
                {"#", " ", " ", " ", "#", " ", " ", "$", " ", "#"},
                {" ", " ", " ", " ", "#", "#", "#", "#", "#", " "}
        };
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "correctInputFileExample.txt");
        Assert.assertNotNull(mazeData);
        Assert.assertEquals(mazeData.getMazeWorld(), mazeWorld);

    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyMissingAllBodyTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "missingAllMazeBody.txt");
        Assert.assertNull(mazeData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyMissingPlayerTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "missingPlayerSign.txt");
        Assert.assertNull(mazeData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyMissingTreasureTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "missingTreasureSign.txt");
        Assert.assertNull(mazeData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyTwoPlayersSignsTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "twoPlayerSigns.txt");
        Assert.assertNull(mazeData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyTwoTreasureSignsTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "twoTreasureSigns.txt");
        Assert.assertNull(mazeData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyIncorrectCharTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectCharInMazeBody.txt");
        Assert.assertNull(mazeData);
    }
    @Test(expected = WrongFileFormatException.class)
    public void fileToParseMazeBodyIncorrectCharTABTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "incorrectCharTABInMazeBody.txt");
        Assert.assertNull(mazeData);
    }
    @Test
    public void fileToParseMazeBodyLessRowsThanDefinedWillFilledBySpacesTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyLessRowsThanDefined.txt");
        Assert.assertNotNull(mazeData);
        Assert.assertEquals(mazeData.getMazeWorld().length, 4);
    }
    @Test
    public void fileToParseMazeBodyLessColumnsThanDefinedWillFilledBySpacesTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyLessColumnsThanDefined.txt");
        Assert.assertNotNull(mazeData);
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(mazeData.getMazeWorld()[0].length, 10);
        }

    }
    @Test
    public void fileToParseMazeBodyExtraRowsThanDefinedWillBeIgnoredTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyExtraRowsThanDefined.txt");
        Assert.assertNotNull(mazeData);
        Assert.assertEquals(mazeData.getMazeWorld().length, 4);

    }
    @Test
    public void fileToParseMazeBodyExtraColumnsThanDefinedWillBeIgnoredTest(){
        MazeData mazeData = fileParse.parseFileData(pathTofileDataParseResources
                + "mazeBodyExtraColumnsThanDefined.txt");
        Assert.assertNotNull(mazeData);
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(mazeData.getMazeWorld()[0].length, 10);
        }
    }


}
