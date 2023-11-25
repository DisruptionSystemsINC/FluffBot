package main.EventListeners.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.E6BotIntegration.E6Wrapper.handleE9E6;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.DayOfWeek;

public class DailyPost extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Thread thread = new Thread( () -> {
            while (true){
                try {
                    Thread.sleep((long) 8.64E7);
                    TextChannel channel = event.getGuild().getTextChannelsByName("nsfw-bot", true).get(0);
                    channel.sendMessage(handleE9E6.handleE6("", "")).complete();
                } catch (InterruptedException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}