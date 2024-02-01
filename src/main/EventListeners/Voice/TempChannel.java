package main.EventListeners.Voice;

import main.EventListeners.utility.Logging;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.List;

public class TempChannel extends ListenerAdapter {
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        try {
            Category cat = event.getChannelLeft().getParentCategory();
        if (cat.getName().toLowerCase().equals("temporäre voicechannels")) {
            String name = event.getChannelLeft().getName();
            try {
                Logging.printToLog("The Voicechannel "+ name + " Has been deleted. Reason: Channel was Empty");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            VoiceChannel channel = event.getChannelLeft().asVoiceChannel();
            List<Member> members = channel.getMembers();
            if (members.isEmpty()) {
                channel.delete().complete();
            }
        }
        }
        catch (NullPointerException ignored){
        }
    }


}