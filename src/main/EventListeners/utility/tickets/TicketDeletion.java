package main.EventListeners.utility.tickets;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TicketDeletion extends ListenerAdapter{
    public static void scheduleDeletion (SlashCommandInteractionEvent event, int id){
        Thread t = new Thread(() -> {
            try {
                Thread.sleep((long) 1.728E8);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (Ticket.getTicketByID(event, id) != null) {
                Ticket.close(event, Ticket.getTicketByID(event, id));
            }
            });
        t.start();
        }
    }
