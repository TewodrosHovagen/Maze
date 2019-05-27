package Utils.logging;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

public class OutputLog {
    private static final Logger log = Logger.getLogger(OutputLog.class.getName());
    private static String logPath= "./output.txt";
   // private static boolean printToScreen = false;

    public OutputLog(String path){
        logPath = path;
        File file = new File(logPath);
        if (file.delete()) {
            log.info("The previous file was deleted");
        } else {
            log.info("File was not deleted");
        }
    }
//    public OutputLog(boolean printToScreen){
//        this.printToScreen = true;
//    }

//    public OutputLog(String logPath, boolean printToScreen){
//        this.logPath = logPath;
//        writeToLogFile("",printToScreen);
//    }
//
//    public static void deleteOutputLog() {
//
//    }

    public static void writeToOutput(String logRecord){
        try {
            writeLog(String.format("%s%n", logRecord));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void writeToOutput(String logRecord, boolean append) {
//        writeLog(logRecord, append);
//    }

    private static void writeLog(String logRecord) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(logPath, true))) {
            writer.write(logRecord);
        } catch (IOException e) {
            throw e;
        }
    }



}
