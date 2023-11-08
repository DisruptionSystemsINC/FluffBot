package main.EventListeners.SlashCommands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.List;

public class BulkDelete extends ListenerAdapter{
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        Thread t = new Thread(() -> {
        String command = event.getName();
        if (command.equals("bulk-delete")) {
            int amountToDelete = event.getOption("delamount").getAsInt();
            if (event.getJDA().getRoles().toString().toLowerCase().contains("staff")) {
                if (amountToDelete > 30) {
                    event.reply("WARNUNG: Das CHORUS System kann bei Größeren Mengen an Posts das löschen nicht Garantieren! Die Löschung kann mehrere Minuten dauern.").setEphemeral(true).complete();
                } else {
                    event.reply("Löschen von " + amountToDelete + " Nachricht(en) wird gestartet... Dies kann etwas Zeit in Anspruch nehmen").setEphemeral(true).complete();
                }
                MessageHistory msg = event.getChannel().getHistory();
                List<Message> Messages = msg.retrievePast(amountToDelete).complete();
                for (int a = 0; a < Messages.size(); a++) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    event.getChannel().deleteMessageById(Messages.get(a).getId()).complete();
                }
            } else {
                event.reply("Du musst Staff sein um diesen Command zu nutzen.").setEphemeral(true).complete();
            }
        }
    });
        t.start();
    }
}