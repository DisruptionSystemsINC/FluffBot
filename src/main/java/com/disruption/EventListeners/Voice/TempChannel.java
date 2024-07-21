package com.disruption.EventListeners.Voice;

import com.disruption.EventListeners.Voice.Lavaplayer.Dragonplayer;
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
        if (event.getChannelLeft().getParentCategory() != null) {
            Category cat = event.getChannelLeft().getParentCategory();
            if (cat.getName().equalsIgnoreCase("temporäre voicechannels")) {
                String name = event.getChannelLeft().getName();
                VoiceChannel channel = event.getChannelLeft().asVoiceChannel();
                List<Member> members = channel.getMembers();
                if (members.isEmpty() || (members.contains(event.getGuild().getSelfMember())) && members.size() < 2) {
                    channel.delete().complete();
                    Dragonplayer.stopBot();
                    Logging.printToLog("The Voicechannel " + name + " Has been deleted. Reason: Channel was Empty");
                }
            }
        }
    }
}