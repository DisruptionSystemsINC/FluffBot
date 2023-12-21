package main.EventListeners.utility.tickets;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Timer;
import java.util.TimerTask;

public class TicketDeletion {
    public void ScheduleDeletion (Ticket ticket, MessageReceivedEvent event){
        Timer timer = new Timer("timeTicketDeletion");
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                event.getGuild().getTextChannelsByName(ticket.);
            }
        };
    }
}
