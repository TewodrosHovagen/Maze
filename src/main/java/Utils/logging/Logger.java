package Utils.logging;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Logger {
    private static String logPath= "./log.txt";

    public Logger(){
        writeToLogFile("",false);
    }

    public static void debug(String logRecord){
        writeToLogFile(String.format("%s DEBUG %s%n", new Date(), logRecord));
    }

    public static void error(String logRecord){
        writeToLogFile(String.format("%s ERROR %s%n", new Date(), logRecord));
    }

    public static void info(String logRecord){
        writeToLogFile(String.format("%s INFO %s%n", new Date(), logRecord));
    }

    private static void writeToLogFile(String logRecord) {
        writeLog(logRecord,true);
    }

    private static void writeToLogFile(String logRecord, boolean append) {
        writeLog(logRecord, append);
    }

    private static void writeLog(String logRecord, boolean append) {
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(logPath, append))) {
                writer.write(logRecord);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
