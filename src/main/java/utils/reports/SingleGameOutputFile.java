package utils.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SingleGameOutputFile implements AutoCloseable{
    private String logPath;
    private FileOutputStream fileOutput;
    private OutputStreamWriter writer;

    public SingleGameOutputFile(String path) throws FileNotFoundException {
        logPath = path;
        fileOutput = new FileOutputStream(logPath);
        writer = new OutputStreamWriter(fileOutput);
    }

    public void writeToOutput(String logRecord){
        try {
            writer.write(String.format("%s%n", logRecord));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        writer.close();
        fileOutput.close();
    }
}
