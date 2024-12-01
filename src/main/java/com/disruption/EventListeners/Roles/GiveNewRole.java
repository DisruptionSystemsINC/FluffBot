package com.disruption.EventListeners.Roles;

import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GiveNewRole extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        Role role = event.getGuild().getRolesByName("Neuling", true).get(0);
        event.getGuild().addRoleToMember(member, role).complete();
        FluffBot.getDragonLog().printToLog(LogLevel.WARNING, "Member " + member.getAsMention() + " Has received the Neuling role");
    }
}
