package main.EventListeners.utility;

import java.io.*;

public class CountTickets {

    private static File Storage = new File("TicketBuffer.log");
    public static void incrementCounter(){
        try {
            BufferedWriter writer = new BufferedWriter(new PrintWriter(Storage));
            writer.write(getTicketCount() + 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getTicketCount() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Storage));
        String amount = reader.readLine();
        reader.close();
        return  Integer.parseInt(amount);
    }
}
