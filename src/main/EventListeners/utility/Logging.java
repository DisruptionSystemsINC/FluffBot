package main.EventListeners.utility;

import main.FluffBot;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logging {
    public static void printToLog(String line) throws IOException {
         //Print the line into the terminal too
        System.out.println(line);
        //Open the log file
        File logfile = new File(FluffBot.getLoggingDir()+"fluffbot.log");
        //Register a new Printwriter and open the log in append mode
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logfile, true)));
        //Make sure there actually is a file to write to and create one if there isn't
        if (!logfile.exists()) {
            logfile.createNewFile();
        }
        //Add the local date and time as well as a few newlines for overview
        pw.append("[" + LocalDate.now() + "]    [" + LocalTime.now() + "]" +"\n\n" + line + "\n\n\n\n");
        pw.close();
    }
}
