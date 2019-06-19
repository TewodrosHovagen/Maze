package maze.application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Validation {
    public static boolean checkExistenceOfFilePath(String pathWithFileName){
        Path path = Paths.get(pathWithFileName);
        Path parentPath = path.getParent().toAbsolutePath();
        return Files.exists(parentPath);

    }
    public static boolean checkExistenceOfFile(String pathWithFileName){
        Path path = Paths.get(pathWithFileName);
        Path absPath = path.toAbsolutePath();
        return Files.exists(absPath);
    }
}
