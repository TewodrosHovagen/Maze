package utils.logging;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Logger implements AutoCloseable {
    private static Logger logger_instance = null;
    private final String logPath= "./log.txt";
    private FileOutputStream fileOutput;
    private OutputStreamWriter writer;

    private Logger()  {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(logPath);
             OutputStreamWriter streamWriter = new OutputStreamWriter(fileOutputStream) ;
            fileOutput = fileOutputStream;
            writer = streamWriter;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }
    public static Logger getInstance()
    {
        if (logger_instance == null)
            logger_instance = new Logger();

        return logger_instance;
    }

    public void debug(String logRecord){
        writeLog(String.format("%s DEBUG %s%n", new Date(), logRecord));
    }
    public void error(String logRecord){
        writeLog(String.format("%s ERROR %s%n", new Date(), logRecord));
    }
    public void info(String logRecord){
        writeLog(String.format("%s INFO %s%n", new Date(), logRecord));
    }



    private  void writeLog(String logRecord) {
        try {
            writer.write(logRecord);
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
