package com.disruption.EventListeners.utility;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerifiedChecker extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (!event.getGuild().getTextChannels().toString().contains("to-verify")) {
            event.getGuild().getCategoriesByName("team stuff :D", true).get(0).createTextChannel("to-verify").complete();
        }
            Message msg = event.getGuild().getTextChannelsByName("to-verify", true).get(0).retrieveMessageById(event.getGuild().getTextChannelsByName("to-verify", true).get(0).getLatestMessageId()).complete();

        if (msg == null){
            event.getGuild().getTextChannelsByName("to-verify", true).get(0).sendMessage("Diese Nutzer Warten auf Verifizierung:").complete();
        }
        if (msg.getAuthor() != event.getGuild().getSelfMember()){
            msg = msg.getChannel().sendMessage("Diese Nutzer Warten auf Verifizierung:").complete();
        }
        addToVerifyList(msg, event.getMember());
    }

    public void addToVerifyList(Message msg, Member mem){
        String ogMesg = msg.getContentDisplay();
        String newMesg = ogMesg + "\n" + mem.getAsMention() + " Wartet auf Verifizierung.";
        msg.editMessage(newMesg).complete();
    }

    public void removeFromVerifyList(Message msg, Member mem){
        String ogMesg = msg.getContentDisplay();
        List<String> mentions = new ArrayList<>(Arrays.stream(ogMesg.split("\n")).toList());
        mentions.removeIf(m -> m.contains(mem.getAsMention()));
    }
}
