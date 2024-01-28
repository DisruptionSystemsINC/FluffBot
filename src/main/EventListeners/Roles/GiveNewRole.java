package main.EventListeners.Roles;

import main.EventListeners.utility.Logging;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class GiveNewRole extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        Role role = event.getGuild().getRolesByName("Neuling", true).get(0);
        event.getGuild().addRoleToMember(member, role).complete();
        try {
            Logging.printToLog("Member " + member.getAsMention() + " Has received the Neuling role");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
