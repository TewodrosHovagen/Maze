package Utils.logging;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Logger {
    private static String logPath= "./output.txt";
    private static boolean printToScreen = false;

    public Logger(String name){
        this.logPath = name;
        writeToLogFile("",false);

    }
    public Logger(boolean printToScreen){
        this.printToScreen = true;
    }

    public static void debug(String logRecord){
        writeToLogFile(String.format("%s DEBUG %s", new Date(), logRecord));
    }

    public static void error(String logRecord){
        writeToLogFile(String.format("%s ERROR %s", new Date(), logRecord));
    }

    public static void info(String logRecord){
        writeToLogFile(String.format("%s INFO %s", new Date(), logRecord));
    }

    private static void writeToLogFile(String logRecord) {
        writeLog(logRecord,true);
    }

    private static void writeToLogFile(String logRecord, boolean append) {
        writeLog(logRecord, append);
    }

    private static void writeLog(String logRecord, boolean append) {
        if (!printToScreen){
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(logPath, append))) {
                writer.write(logRecord + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println(logRecord);
        }

    }

}
