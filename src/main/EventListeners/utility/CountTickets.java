package main.EventListeners.utility;

import java.io.*;

public class CountTickets {

    private static File Storage = new File("TicketBuffer.log");
    public static void incrementCounter() throws IOException {
        int buffer =  Integer.parseInt(getTicketCount()) + 1;
        try {
            PrintWriter writer = new PrintWriter(Storage);
            writer.print(buffer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTicketCount() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Storage));
        String amount = reader.readLine();
        reader.close();
        return  amount;
    }
}
