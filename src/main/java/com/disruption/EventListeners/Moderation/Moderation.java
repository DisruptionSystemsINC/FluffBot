package com.disruption.EventListeners.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;


public class Moderation extends ListenerAdapter {

    public static TextChannel guildLogChannel;

    @Override
    public void onGuildReady(GuildReadyEvent event){
        guildLogChannel = event.getGuild().getTextChannelsByName("administrative-logs", true).get(0);
    }
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        User user = event.getUser();
        AuditLogEntry auditLogEntry = event.getGuild().retrieveAuditLogs().complete().get(0);
        if (auditLogEntry.getType() == ActionType.BAN){
            guildLogChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Der Nutzer (" + user.getName() + ") hat den Server verlassen").addField(auditLogEntry.getType().name(), "Der Nutzer " + event.getMember().getAsMention() + LeaveType.BANNED + "\nGrund: " + auditLogEntry.getReason() +  " (" + auditLogEntry.getUser().getName() + ")", false).build()).complete();
        } else if (auditLogEntry.getType() == ActionType.KICK) {
            guildLogChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Der Nutzer (" + user.getName() + ") hat den Server verlassen").addField(auditLogEntry.getType().name(), "Der Nutzer " + event.getMember().getAsMention() + " " + LeaveType.KICKED + "\nGrund: " + auditLogEntry.getReason() +  " (" + auditLogEntry.getUser().getName() + ")", false).build()).complete();
        }
    }

    @Override
    public void onGuildMemberUpdate(GuildMemberUpdateEvent event) {
        if (event.getMember().isTimedOut()){
            AuditLogEntry auditLogEntry = event.getGuild().retrieveAuditLogs().complete().get(0);
            User user = event.getUser();
            guildLogChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Der Nutzer (" + user.getName() + ") wurde getimeoutet").addField("TIMEOUT", "Der Nutzer " + event.getMember().getAsMention() + " wurde bis " + event.getMember().getTimeOutEnd().toLocalDate() + ", " + event.getMember().getTimeOutEnd().toLocalTime().truncatedTo(ChronoUnit.MINUTES) + LeaveType.TIMEOUTED + "\nGrund: " + auditLogEntry.getReason() +  " (" + auditLogEntry.getUser().getName() + ")", false).build()).complete();

        }
    }

    @Override
    public void onGuildInviteCreate(GuildInviteCreateEvent event) {
        User user = event.getInvite().getInviter();
        Member member = event.getGuild().getMember(user);
        if (member.getTimeJoined().isAfter(OffsetDateTime.now().minusSeconds(30))){
            guildLogChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Ein Nutzer (" + user.getAsMention() + ") hat zu schnell einen Invite erstellt.")
                    .addField("Potentiellen Betrüger in den Timeout Versetzt", "Der Nutzer " + member.getAsMention() + " wurde in einen Automatischen timeout für eine Stunde Versetzt", false)
                    .build()).complete();
            member.timeoutFor(1, TimeUnit.HOURS).reason("Automatischer timeout (Auffällige aktivität)").complete();
            user.openPrivateChannel().complete().sendMessage("Dein Account wurde von C.H.O.R.U.S als potentiell Gefährlich markiert. Du wurdest für eine Stunde in den Timeout versetzt. \nDies ist eine Vorsichtsmaßnahme um Betrüger und Trolls im zaum zu halten. \n\nWir bitten um verständnis").complete();
        }
    }
}




