package com.disruption.EventListeners.Voice;

import com.disruption.EventListeners.utility.Logging;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

public class TempChannel extends ListenerAdapter {
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        try {
            Category cat = Objects.requireNonNull(event.getChannelLeft()).getParentCategory();
            assert cat != null;
            if (cat.getName().equalsIgnoreCase("temporäre voicechannels")) {
                String name = event.getChannelLeft().getName();
                VoiceChannel channel = event.getChannelLeft().asVoiceChannel();
                List<Member> members = channel.getMembers();
                if (members.isEmpty()) {
                    Logging.printToLog("The Voicechannel "+ name + " Has been deleted. Reason: Channel was Empty");
                    channel.delete().complete();
                }
            }
        }
        catch (NullPointerException ignored){}
    }


}