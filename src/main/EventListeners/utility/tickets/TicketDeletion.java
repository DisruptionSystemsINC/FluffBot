package main.EventListeners.utility.tickets;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class TicketDeletion extends ListenerAdapter{
    public void scheduleDeletion (int ticketid, MessageReceivedEvent event){
        TextChannel ticket = Ticket.getTicketByID(event, ticketid);
        Timer timer = new Timer("timeTicketDeletion");
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

            }
        };
    }
}
