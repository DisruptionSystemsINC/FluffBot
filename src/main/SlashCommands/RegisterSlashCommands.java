package main.SlashCommands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class RegisterSlashCommands extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> Commanddata = new ArrayList<>();
        OptionData ticketOptions = new OptionData(OptionType.STRING, "ticket-type", "Die Art des Tickets", true).addChoice("Support", "support").addChoice("Nsfw-Freischaltung", "nsfw-freischaltung").addChoice("Vorschlag", "vorschlag").addChoice("Server Support", "minecraft-server-support");
        OptionData amountToDelete = new OptionData(OptionType.INTEGER, "delamount", "Die Menge der Messages die du löschen willst", true);


        Commanddata.add(Commands.slash("ticket", "Mach ein neues Ticket").addOptions(ticketOptions));
        Commanddata.add(Commands.slash("bulk-delete", "Lösche eine menge Messages auf einmal").addOptions(amountToDelete));
        event.getGuild().updateCommands().addCommands(Commanddata).queue();
    }
}
