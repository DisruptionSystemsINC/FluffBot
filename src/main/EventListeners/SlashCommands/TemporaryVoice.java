package main.EventListeners.SlashCommands;

import main.EventListeners.utility.Logging;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.Objects;

public class TemporaryVoice extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("voice")){
            try {
                Logging.printToLog("A Temporary Voicechannel was requested");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String name = Objects.requireNonNull(event.getOption("name")).getAsString();
            Member member = Objects.requireNonNull(event.getOption("users")).getAsMember();
            event.reply("Ein Voice channel mit dem namen \"" + name + "\" wird für dich erstellt").complete();
        }
    }
}
