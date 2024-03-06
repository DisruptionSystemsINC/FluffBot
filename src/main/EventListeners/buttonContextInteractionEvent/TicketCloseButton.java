package main.EventListeners.buttonContextInteractionEvent;

import main.EventListeners.utility.tickets.Ticket;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TicketCloseButton extends ListenerAdapter {
    //Listen to the button trigger
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        //Figure out if the button that has been pressed is actually the right button
        if (event.getButton().getLabel().equals("Schließen")) {
            Button button = Button.danger("schließen", "Schließen").asDisabled();
            //Refresh the button to disable it
            event.editButton(button).complete();
            //Get the ticket the button was pushed in
            TextChannel channel = event.getChannel().asTextChannel();
                try {
                    Ticket.close(event, channel);
                } catch (IOException | InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
