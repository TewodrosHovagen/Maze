package Maze;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameManager {

    public Map<Point, Character> maze = new HashMap<>();
    public Map<String, String> mazeDetails = new HashMap<>();


    public static void readMazeFile(String mazeFile) throws IOException {
        File path = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\" + mazeFile);
        FileReader fileReader = new FileReader(path);
        String[] strings = new String[3];
        int i;
        int j = 0;
        while ((i = fileReader.read()) != -1)
            strings[j] += String.valueOf((char) i);
        if (i == 13) {
            j++;
        }
    }
}
