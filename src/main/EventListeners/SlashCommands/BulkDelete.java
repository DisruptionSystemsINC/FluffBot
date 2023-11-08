package main.EventListeners.SlashCommands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

public class BulkDelete extends ListenerAdapter{
    public void onSlashCommandInteraction( SlashCommandInteractionEvent event) {
        Thread t = new Thread(() -> {
        String command = event.getName();
        if (command.equals("bulk-delete")) {
            int amountToDelete = Objects.requireNonNull(event.getOption("delamount")).getAsInt();
            if(amountToDelete <= 100) {
                System.out.println(event.getInteraction().getMember().getRoles());
                if (event.getInteraction().getMember().getRoles().toString().toLowerCase().contains("staff")) {
                    event.reply("Löschen von " + amountToDelete + " Nachricht(en) wird gestartet... Dies kann etwas Zeit in Anspruch nehmen").setEphemeral(true).complete();
                    MessageHistory msg = event.getChannel().getHistory();
                    List<Message> Messages = msg.retrievePast(amountToDelete).complete();
                    event.getChannel().asTextChannel().deleteMessages(Messages).complete();
                } else {
                    event.reply("Du musst Staff sein um diesen Command zu nutzen.").setEphemeral(true).complete();
                }
            }
            else {
                event.reply("FEHLER: Das Chorus System kann aufgrund einer Discord API limitation ***Maximal*** 100 Nachrichten auf einmal löschen. Um mehr zu löschen führe den Befehl nochmal aus.").setEphemeral(true).complete();
            }
        }
    });
        t.start();
    }
}