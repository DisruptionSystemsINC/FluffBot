package main.EventListeners.utility;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerifiedChecker extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

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
