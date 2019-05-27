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
                        {0, 0, 0, 3, 4, 10,Enums.DirectionsEnum.NORTH},
                        {0, 1, 0, 0, 4, 10,Enums.DirectionsEnum.NORTH},
                        {3, 9, 3, 0, 4, 10,Enums.DirectionsEnum.EAST},
                        {3, 8, 3, 9, 4, 10,Enums.DirectionsEnum.EAST},
                        {0, 4, 9, 4, 4, 10,Enums.DirectionsEnum.WEST},
                        {2, 7, 3, 7, 4, 10,Enums.DirectionsEnum.WEST},
                        {3, 6, 0, 6, 4, 10,Enums.DirectionsEnum.SOUTH},
                        {2, 6, 1, 6, 4, 10,Enums.DirectionsEnum.SOUTH},
                        {0, 0, 0, 0, 1, 1,Enums.DirectionsEnum.NORTH},
                        {0, 0, 0, 0, 1, 1,Enums.DirectionsEnum.SOUTH},
                        {0, 0, 0, 0, 1, 1,Enums.DirectionsEnum.WEST},
                        {0, 0, 0, 0, 1, 1,Enums.DirectionsEnum.EAST},
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
