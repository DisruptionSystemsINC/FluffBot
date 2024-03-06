package main.EventListeners.utility.tickets;

import main.FluffBot;

import java.io.*;

public class CountTickets {

    //Open the file that contains the ticket buffer. Not the most beautiful way to do this but it works
    private static File Storage = new File(FluffBot.getLoggingDir()+"TicketBuffer.log");

    //Increment the number in the ticket by one
    public static void incrementCounter() throws IOException {
        int buffer =  getTicketCount() + 1;
        try {
            PrintWriter writer = new PrintWriter(Storage);
            writer.print(buffer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Read the ticket ID from the file
    public static int getTicketCount() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Storage));
        int amount = Integer.parseInt(reader.readLine());
        reader.close();
        return  amount;
    }
}
