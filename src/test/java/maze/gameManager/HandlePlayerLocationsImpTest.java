package maze.gameManager;

import Utils.Enums;
import maze.fileDataParse.FileData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class HandlePlayerLocationsImpTest {
    private int rowPosition, colPosition, rowExpectedPosition, colExpectedPosition,rows,columns;
    private Enums.MainDirectionsEnum direction;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {0, 0, 3, 0, 4, 10, Enums.MainDirectionsEnum.UP},
                        {1, 1, 0, 1, 4, 10, Enums.MainDirectionsEnum.UP},
                        {3, 9, 3, 0, 4, 10, Enums.MainDirectionsEnum.RIGHT},
                        {3, 8, 3, 9, 4, 10, Enums.MainDirectionsEnum.RIGHT},
                        {1, 0, 1, 9, 4, 10, Enums.MainDirectionsEnum.LEFT},
                        {2, 7, 2, 6, 4, 10, Enums.MainDirectionsEnum.LEFT},
                        {3, 9, 0, 9, 4, 10, Enums.MainDirectionsEnum.DOWN},
                        {2, 6, 3, 6, 4, 10, Enums.MainDirectionsEnum.DOWN},
                        {0, 0, 0, 0, 1, 1, Enums.MainDirectionsEnum.UP},
                        {0, 0, 0, 0, 1, 1, Enums.MainDirectionsEnum.DOWN},
                        {0, 0, 0, 0, 1, 1, Enums.MainDirectionsEnum.LEFT},
                        {0, 0, 0, 0, 1, 1, Enums.MainDirectionsEnum.RIGHT},
                        {1, 2, 1, 2, 4, 10, Enums.MainDirectionsEnum.BOOKMARK}
                }
        );
    }

    public HandlePlayerLocationsImpTest(int rowPosition, int colPosition, int rowExpectedPosition, int colExpectedPosition,int rows, int columns, Enums.MainDirectionsEnum direction) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.rowExpectedPosition = rowExpectedPosition;
        this.colExpectedPosition = colExpectedPosition;
        this.rows = rows;
        this.columns = columns;
        this.direction = direction;
    }

    @Test
    public void moveValidTest() {
        //Arrange
        GameManagerImpl gameManager = new GameManagerImpl();
        Point playerPoint = new Point(colPosition, rowPosition);
        Point playerExpectedPoint = new Point(colExpectedPosition, rowExpectedPosition);
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

    @Test
    public void getBackMoveTest() {
        //Arrange
        GameManagerImpl gameManager = new GameManagerImpl();
        Point playerPoint = new Point(colExpectedPosition, rowExpectedPosition);
        Point playerExpectedPoint = new Point(colPosition, rowPosition);
        FileData fileData = new FileData();
        fileData.setRows(rows);
        fileData.setColumns(columns);
        gameManager.setData(fileData);
        gameManager.setPlayerLocation(playerPoint);

        //Act
        Point playerActualPoint = gameManager.getBackMove(direction);

        //Assert
        Assert.assertEquals("The player is not in the correct location for back direction of: " +direction ,playerExpectedPoint,playerActualPoint);
    }
}
