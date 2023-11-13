package main.SlashCommands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
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
        OptionData ticketOptions = new OptionData(OptionType.STRING, "ticket-type", "Die Art des Tickets", true).addChoice("Support", "support").addChoice("Nsfw-Freischaltung", "nsfw-freischaltung").addChoice("Minecraft Server Support", "minecraft-server-support");
        OptionData amountToDelete = new OptionData(OptionType.INTEGER, "delamount", "Die Menge der Messages die du löschen willst", true);
        OptionData nsfwtype = new OptionData(OptionType.STRING, "nsfwtype", "Der Typ des Artworks", true).addChoices().addChoice("Custom", "custom");
        OptionData nsfwtags = new OptionData(OptionType.STRING, "nsfwtags", "Gebe deine e621 Tags hier ein wenn du \"Custom\" gewählt hast. Der syntax der tags ist tag1+tag2+tag3");
        OptionData type = new OptionData(OptionType.STRING, "type", "Der Typ des Artworks", true).addChoices().addChoice("Custom", "custom").addChoice("Hug", "hug").addChoice("Cuddles","cuddling").addChoice("Kiss","kissing").addChoice("Sleeping","sleeping");
        OptionData tags = new OptionData(OptionType.STRING, "tags", "Gebe deine e926 Tags hier ein wenn du \"Custom\" gewählt hast. Der syntax der tags ist: tag1+tag2+tag3");


        Commanddata.add(Commands.slash("media", "Hol dir ein Artwork von E926(SFW)").addOptions(type, tags));
        Commanddata.add(Commands.slash("silly-media", "Hol dir ein Artwork von E621(NSFW)").addOptions(nsfwtype, nsfwtags).setNSFW(true));
        Commanddata.add(Commands.slash("ticket", "Mach ein neues Ticket").addOptions(ticketOptions));
        Commanddata.add(Commands.slash("bulk-delete", "Lösche eine menge Messages auf einmal").addOptions(amountToDelete));
        Commanddata.add(Commands.message("schließen"));
        event.getGuild().updateCommands().addCommands(Commanddata).queue();
    }
}
