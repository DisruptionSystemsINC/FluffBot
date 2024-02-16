package main.EventListeners.utility;

import main.FluffBot;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logging {
    public static void printToLog(String line) throws IOException {
        System.out.println(line);
        File logfile = new File(FluffBot.getLoggingDir()+"fluffbot.log");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logfile, true)));

        if (!logfile.exists()) {
            logfile.createNewFile();
        }

        pw.append("[" + LocalDate.now() + "]    [" + LocalTime.now() + "]" +"\n\n" + line + "\n\n\n\n");
        pw.close();
    }
}
