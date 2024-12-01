package com.disruption.EventListeners.BotInit;

import com.disruption.E6BotIntegration.E6Wrapper.handleE9E6;
import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DailyPost extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        ScheduledFuture<?> sched = Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
            TextChannel channel = event.getGuild().getTextChannelsByName("nsfw-bot", true).get(0);
            TextChannel sfwchannel = event.getGuild().getTextChannelsByName("fluffymedia", true).get(0);
            try {
                channel.sendMessage(handleE9E6.handleE6("", "")).complete();
                sfwchannel.sendMessage(handleE9E6.handleE9("", "")).complete();
                FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION, "Daily Post has been triggered");
            } catch(IOException exception){
                FluffBot.getDragonLog().printToLog(LogLevel.ERROR, "Der Dailypost hatte eine unerwartete störung");
                exception.printStackTrace();
            }
        }, 0, 12, TimeUnit.HOURS);
    }
}