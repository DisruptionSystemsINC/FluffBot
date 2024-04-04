package main.EventListeners.SlashCommands;

import main.EventListeners.utility.Logging;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static main.EventListeners.Moderation.Moderation.guildLogChannel;

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
                Logging.printToLog("Der Nutzer " + member.getEffectiveName() + " Wurde von " + event.getMember().getEffectiveName() + " Verifiziert ");
                guildLogChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Nutzer Verifiziert!").addField(event.getMember().getEffectiveName() + " hat " + member.getEffectiveName() + " Verfiziert!", reason, false).build()).complete();
            }
        }
    }
}
