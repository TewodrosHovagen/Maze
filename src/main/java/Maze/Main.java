package Maze;

import Maze.FileDataParse.FileData;
import Maze.FileDataParse.FileParse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        FileParse fileParse = new FileParse();
        FileData fileData = fileParse.parseFileData(args);
        System.out.println(fileData);
    }
}
