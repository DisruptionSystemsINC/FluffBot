package main.ContextEvent;

import main.EventListeners.utility.tickets.Ticket;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageContextEvent extends ListenerAdapter {
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        if (event.getName().equals("schließen")) {
            TextChannel channel = event.getChannel().asTextChannel();
            if(channel.getParentCategory().toString().toLowerCase().contains("support-tickets") || channel.getParentCategory().toString().toLowerCase().contains("minecraft-server-support-tickets") || channel.getParentCategory().toString().toLowerCase().contains("fluffbot-support-tickets") || channel.getParentCategory().toString().toLowerCase().contains("nsfw-freischaltungs-tickets")){
                Ticket.close(event);
            }
            else{
                event.reply("Du kannst das hier nicht tun.").setEphemeral(true).complete();
            }
    }
}
}
