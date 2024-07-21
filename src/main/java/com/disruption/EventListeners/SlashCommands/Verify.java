package com.disruption.EventListeners.SlashCommands;

import com.disruption.EventListeners.utility.Logging;
import com.disruption.EventListeners.utility.VerifiedChecker;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static com.disruption.EventListeners.Moderation.Moderation.guildLogChannel;

public class Verify extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("verify")) {
            if (event.getMember().getRoles().contains(event.getGuild().getRolesByName("Owner", true).get(0))) {
                User user = event.getOption("vermember").getAsUser();
                String reason = event.getOption("verreason").getAsString();
                Member member = event.getGuild().getMember(user);
                Role verifiedrole = event.getGuild().getRolesByName("Verifiziert", true).get(0);
                event.getGuild().addRoleToMember(member, verifiedrole).complete();
                event.getGuild().removeRoleFromMember(member, event.getGuild().getRolesByName("neuling", true).get(0)).complete();
                Logging.printToLog("Der Nutzer " + member.getEffectiveName() + " Wurde von " + event.getMember().getEffectiveName() + " Verifiziert ");
                event.reply("Verifizierung erfolgreich").queue();
                guildLogChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Nutzer Verifiziert!").addField(event.getMember().getEffectiveName() + " hat " + member.getEffectiveName() + " Verfiziert!", reason, false).build()).complete();
                try {
                    new VerifiedChecker().removeFromVerifyList(event.getGuild().getTextChannelsByName("to-verify", true).get(0).retrieveMessageById(event.getGuild().getTextChannelsByName("to-verify", true).get(0).getLatestMessageId()).complete(), member);
                } catch (IndexOutOfBoundsException e){
                    Logging.printToLog("Warning: Missing Bot Message. This should not happen");
                };
            }
            event.reply("Du musst Owner sein um das zu tun.").setEphemeral(true).queue();
        }
    }
}
