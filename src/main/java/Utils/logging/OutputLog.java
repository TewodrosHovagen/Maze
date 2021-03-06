package Utils.logging;


import java.io.*;
import java.util.logging.Logger;

public class OutputLog implements AutoCloseable{
    private String logPath;
    private FileOutputStream fileOutput;
    private OutputStreamWriter writer;

    public OutputLog(String path) throws FileNotFoundException {
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
