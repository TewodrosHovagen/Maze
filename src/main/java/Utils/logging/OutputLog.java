package Utils.logging;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

public class OutputLog {
    private static String logPath;
   // private static boolean printToScreen = false;

    public OutputLog(String path){
        logPath = path;
        File file = new File(logPath);
        writeToOutput("",false);

//        if (file.delete()) {
//            log.info("The previous file was deleted");
//        } else {
//            log.info("File was not deleted");
//        }
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
        writeToOutput(String.format("%s%n", logRecord),true);
    }

    private static void writeToOutput(String logRecord, boolean append) {
    try{
        writeLog(logRecord, append);
    } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private static void writeLog(String logRecord, boolean append) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(logPath, append))) {
            writer.write(logRecord);
        } catch (IOException e) {
            throw e;
        }
    }



}
