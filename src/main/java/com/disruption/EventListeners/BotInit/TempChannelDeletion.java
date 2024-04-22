package com.disruption.EventListeners.BotInit;

import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TempChannelDeletion extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        ScheduledFuture<?> serv = Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {

            List<VoiceChannel> channels = event.getGuild().getCategoriesByName("temporäre voicechannels", true).get(0).getVoiceChannels();
            for(VoiceChannel chan : channels) {
                if (chan.getMembers().isEmpty()) {
                    chan.delete().complete();
                }
            }
            }, 0, 1, TimeUnit.HOURS);
    }
}
