package maze.fileDataParse;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileParseTest {
    private FileParse fileParse;

    @BeforeEach
    public void setup(){
        this.fileParse = new FileParse();
    }
    @Test
    public void emptyFileParseLocationTest(){

    }
    @Test
    public void correctFileParseLocationTest(){

    }

//    @ParameterizedTest
//    @CsvSource({
//            {
//                {"#", "#", "#", "#", "#", " ", " ", " ", " ", " "},
//                {"#", " ", "@", " ", "#", " ", " ", " ", " ", "#"},
//                {"#", " ", " ", " ", "#", " ", " ", "$", " ", "#"},
//                {" ", " ", " ", " ", "#", "#", "#", "#", "#", " "}
//            },
//            {
//                {"#", "#", "#", "#", "#", " ", " ", " ", " ", " "},
//                {"#", " ", "@", " ", "#", " ", " ", " ", " ", "#"},
//                {"#", " ", " ", " ", "#", " ", " ", "$", " ", "#"},
//                {" ", " ", " ", " ", "#", "#", "#", "#", "#", " "}
//            }})

    @Test
    public void parseFileDataFileCouldnotBeOpenedTest(){

    }
    @Test   
    public void parseFileDataFirstLinesCorrectTest(){

    }

    @Test
    public void parseFileDataFirstLinesMissingParameterTest(){

    }
    @Test
    public void parseFileDataFirstLinesIncorrectSyntaxTest(){
//Like param:val
    }
    @Test
    public void parseFileDataFirstLinesCorrectSpacesTest(){

    }
    @Test
    public void parseFileDataFirstLinesIncorrectSpacesTest(){

    }
    @Test
    public void parseFileDataMazeBodyCorrectTest(){

    }
    @Test
    public void parseFileDataMazeBodyMissingAllBodyTest(){

    }
    @Test
    public void parseFileDataMazeBodyMissingStarterTest(){

    }
    @Test
    public void parseFileDataMazeBodyMissingTreasureTest(){

    }
    @Test
    public void parseFileDataMazeBodyTwoStarterSignesTest(){

    }
    @Test
    public void parseFileDataMazeBodyTwoTreasureSignesTest(){

    }
    @Test
    public void parseFileDataMazeBodyIncorrectCharTest(){

    }
    @Test
    public void parseFileDataMazeBodyLessLinesThanDefinedWillFilledBySpacesTest(){

    }
    @Test
    public void parseFileDataMazeBodyLessColumnsThanDefinedWillFilledBySpacesTest(){

    }
    @Test
    public void parseFileDataMazeBodyExtraLinesThanDefinedWillBeIgnoredTest(){

    }
    @Test
    public void parseFileDataMazeBodyExtraColumnsThanDefinedWillBeIgnoredTest(){

    }


}
