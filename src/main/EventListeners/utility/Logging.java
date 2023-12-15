package main.EventListeners.utility;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logging {
    public static void printToLog(String line) throws IOException {
        System.out.println(line);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fluffbot.log", true)));
        File logfile = new File("fluffbot.log");

        if (!logfile.exists()) {
            logfile.createNewFile();
        }

        pw.append("[" + LocalDate.now() + "]    [" + LocalTime.now() + "]" +"\n\n" + line + "\n\n\n\n");
        pw.close();
    }
}
