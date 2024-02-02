package main.EventListeners.buttonContextInteractionEvent;

import main.EventListeners.utility.tickets.Ticket;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.io.IOException;
import java.util.Objects;

public class TicketCloseButton extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getButton().getLabel().equals("Schließen")) {
            Button button = Button.danger("schließen", "Schließen").asDisabled();
            event.editButton(button).complete();
            TextChannel channel = event.getChannel().asTextChannel();

            if (Objects.requireNonNull(channel.getParentCategory()).toString().toLowerCase().contains("support-tickets") || channel.getParentCategory().toString().toLowerCase().contains("minecraft-server-support-tickets") || channel.getParentCategory().toString().toLowerCase().contains("fluffbot-support-tickets") || channel.getParentCategory().toString().toLowerCase().contains("nsfw-freischaltungs-tickets") || channel.getParentCategory().toString().toLowerCase().contains("server-kritik-tickets")) {
                try {
                    Ticket.close(event, channel);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                event.reply("Du kannst das hier nicht tun.").setEphemeral(true).complete();
            }
        }
    }
}
