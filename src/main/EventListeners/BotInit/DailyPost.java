package main.EventListeners.BotInit;

import main.E6BotIntegration.E6Wrapper.handleE9E6;
import main.EventListeners.utility.Logging;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class DailyPost extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Thread thread = new Thread( () -> {
            while (true){
                try {
                    TextChannel channel = event.getGuild().getTextChannelsByName("nsfw-bot", true).get(0);
                    TextChannel sfwchannel = event.getGuild().getTextChannelsByName("fluffymedia", true).get(0);
                    channel.sendMessage(handleE9E6.handleE6("", "")).complete();
                    sfwchannel.sendMessage(handleE9E6.handleE9("", "")).complete();
                    Logging.printToLog("Daily Post has been triggered");
                    Thread.sleep((long) 8.64E7);
                } catch (InterruptedException | IOException e) {
                    try {
                        Logging.printToLog("Der dailypost wurde interrupted");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}
