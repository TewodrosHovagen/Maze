package maze.fileDataParse;
import java.awt.*;
import java.util.Arrays;
import java.util.Set;

public class FileData {

//    private static final OutputLog log = OutputLog.getLogger(FileData.class.getName());
    private String mazeName;
    private int maxSteps;
    private int columns;
    private int rows;
    private Set<Point> wallsSet;
    private String[][] mazeWorld;
    private Point playerLocation;
    private Point treasureLocation;

    public String getMazeName() { return mazeName;   }

    public void setMazeName(String mazeName) {  this.mazeName = mazeName;  }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Set<Point> getWallsSet() {
        return wallsSet;
    }

    public void setWallsSet(Set<Point> wallsSet) {
        this.wallsSet = wallsSet;
    }

    public Point getPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(Point playerLocation) {
        this.playerLocation = playerLocation;
    }

    public Point getTreasureLocation() {
        return treasureLocation;
    }

    public void setTreasureLocation(Point treasureLocation) {
        this.treasureLocation = treasureLocation;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "mazeName='" + mazeName + '\'' +
                ", maxSteps=" + maxSteps +
                ", columns=" + columns +
                ", rows=" + rows +
                ", wallsSet=" + wallsSet +
                ", playerLocation=" + playerLocation +
                ", treasureLocation=" + treasureLocation +
                '}';
    }

    public String[][] getMazeWorld() {
        return mazeWorld;
    }

    public void setMazeWorld(String[][] mazeWorld) {  this.mazeWorld = mazeWorld;  }

    public void printMazeWorld(){
        for (String[] row : mazeWorld) {
            System.out.println(Arrays.toString(row));
//            OutputLog.writeToOutput(Arrays.toString(row));
        }
    }
}
