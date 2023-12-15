package main.EventListeners.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.E6BotIntegration.E6Wrapper.handleE9E6;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
                    Thread.sleep((long) 8.64E7);
                } catch (InterruptedException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}
