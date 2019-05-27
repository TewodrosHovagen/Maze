package maze.gameManager;

import Utils.Enums;
import maze.fileDataParse.FileData;
import maze.fileDataParse.FileParse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class HandlePlayerLocationsImpTest {
    private int xPosition, yPosition, xExpectedPosition, yExpectedPosition,rows,columns;
    private Enums.DirectionsEnum direction;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {0, 0, 0, 3, 4, 10,Enums.DirectionsEnum.UP},
                        {0, 1, 0, 0, 4, 10,Enums.DirectionsEnum.UP},
                        {10, 3, 0, 3, 4, 10,Enums.DirectionsEnum.RIGHT},
                        {8, 3, 9, 3, 4, 10,Enums.DirectionsEnum.RIGHT},
                        {0, 4, 9, 4, 4, 10,Enums.DirectionsEnum.LEFT},
                        {2, 7, 1, 7, 4, 10,Enums.DirectionsEnum.LEFT},
                        {3, 9, 3, 0, 4, 10,Enums.DirectionsEnum.DOWN},
                        {6, 2, 6, 3, 4, 10,Enums.DirectionsEnum.DOWN},
                        {0, 0, 0, 0, 1, 1,Enums.DirectionsEnum.UP},
                        {0, 0, 0, 0, 1, 1,Enums.DirectionsEnum.DOWN},
                        {0, 0, 0, 0, 1, 1,Enums.DirectionsEnum.LEFT},
                        {0, 0, 0, 0, 1, 1,Enums.DirectionsEnum.RIGHT},
                        {1, 2, 1, 2, 3, 10,Enums.DirectionsEnum.BOOKMARK}
                }
        );
    }

    public HandlePlayerLocationsImpTest(int xPosition, int yPosition, int xExpectedPosition, int yExpectedPosition,int rows, int columns, Enums.DirectionsEnum direction) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xExpectedPosition = xExpectedPosition;
        this.yExpectedPosition = yExpectedPosition;
        this.rows = rows;
        this.columns = columns;
        this.direction = direction;
    }

    @Test
    public void addBookmarkValidTest() {
        //Arrange
        GameManagerImpl gameManager = new GameManagerImpl();
        Point playerPoint = new Point(xPosition, yPosition);
        Point playerExpectedPoint = new Point(xExpectedPosition, yExpectedPosition);
        FileData fileData = new FileData();
        fileData.setRows(rows);
        fileData.setColumns(columns);
        gameManager.setData(fileData);
        gameManager.setPlayerLocation(playerPoint);

        //Act
        Point playerActualPoint = gameManager.move(direction);

        //Assert
        Assert.assertEquals("The player is not in the correct location for direction: " +direction ,playerExpectedPoint,playerActualPoint);
    }
}
