package disruption.EventListeners.buttonContextInteractionEvent;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.io.IOException;
import java.util.List;

public class TicketButtons extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ticketsetup")) {
            StringSelectMenu selmenu = StringSelectMenu.create("seltype").addOption("Generisches Support Ticket", "support").addOption("NSFW-Freischaltungs Ticket", "nsfw").addOption("Fluffbot Support Ticket", "fluffbot").addOption("Minecraft Server Support ticket", "minecraft").addOption("Geschlossene Server Kritik", "critic").build();
            TextChannel channel = event.getChannel().asTextChannel();
            List<Role> roles = event.getMember().getRoles();
            for (Role role : roles) {
                if (role.getName().equals("Staff")) {
                    event.reply("Ticket Nachricht wurde erstellt").setEphemeral(true).complete();
                    channel.sendMessage("Wilkommen bei dem Fluffköpfe Ticket System!\nNutze die Schaltflächen unten um ein Ticket zu erstellen!\n\nPowered by Disruption Systems C.H.O.R.U.S\nBei Problemen Bitte an GandhiTheDergRawr wenden.\n\n").addActionRow(selmenu).complete();
                    return;
                }
            }
            event.reply("Du bist nicht berechtigt dies zu tun").setEphemeral(true).complete();
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        TextChannel chann = event.getChannel().asTextChannel();

        if (chann.getName().equals("fluff-tickets")) {

            Member member = event.getMember();
            String interaction = event.getInteraction().getSelectedOptions().get(0).getValue();
            event.getInteraction().editComponents(event.getMessage().getComponents()).complete();

            switch (interaction) {

                case "support" -> {
                        try {
                            disruption.EventListeners.utility.tickets.Ticket.createTicket(member, "support-ticket", event, "", "");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }
                case "nsfw" -> {
                        try {
                            disruption.EventListeners.utility.tickets.Ticket.createTicket(member, "nsfw-freischaltungs-ticket", event, "||<@266637315831496704>||", "nsfw");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }
                case "minecraft" -> {
                        try {
                            disruption.EventListeners.utility.tickets.Ticket.createTicket(member, "minecraft-server-support-ticket", event, "||<@447387517143089162>||", "minecraft");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }

                case "fluffbot" -> {
                        try {
                            disruption.EventListeners.utility.tickets.Ticket.createTicket(member, "fluffbot-support-ticket", event, "||<@447387517143089162>||", "fluffbot");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }

                case "critic" -> {
                        try {
                            disruption.EventListeners.utility.tickets.Ticket.createTicket(member, "server-kritik-ticket", event, "", "critic");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        }
    }
}
