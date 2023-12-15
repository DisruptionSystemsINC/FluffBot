package main.EventListeners.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Logging {
    public void printToLog(String line) throws IOException {
        PrintWriter pw = new PrintWriter("fluffbot.log");
        File logfile = new File("fluffbot.log");
        if (logfile.exists()){
            logfile.delete();
        }
        logfile.createNewFile();
        pw.println(line);
    }
}
