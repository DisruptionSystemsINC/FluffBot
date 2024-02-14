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
        OptionData amountToDelete = new OptionData(OptionType.INTEGER, "delamount", "Die Menge der Messages die du löschen willst", true);
        //Register all the selectable NSFW types
        OptionData nsfwtype = new OptionData(OptionType.STRING, "nsfwtype", "Der Typ des Artworks", true)
                .addChoice("Custom", "custom")
                .addChoice("Gay Anal", "gay+anal")
                .addChoice("Gay Threesome", "gay+threesome")
                .addChoice("Lesbian", "lesbian")
                .addChoice("Solo Female", "female+solo")
                .addChoice("Male Solo", "male+solo")
                .addChoice("Straight", "straight")
                .addChoice("Lesbian Threesome", "lesbian+threesome")
                .addChoice("Gay Fellatio", "gay+oral")
                .addChoice("Straight Fellatio", "straight+oral")
                .addChoice("Lesbian Fellatio", "lesbian+fellatio");

        //Register all the selectable SFW types
        OptionData nsfwtags = new OptionData(OptionType.STRING, "nsfwtags", "Gebe deine e621 Tags hier ein wenn du \"Custom\" gewählt hast. Der syntax der tags ist tag1+tag2+tag3");
        OptionData type = new OptionData(OptionType.STRING, "type", "Der Typ des Artworks", true)
                .addChoice("Custom", "custom")
                .addChoice("Hug", "hug")
                .addChoice("Cuddles", "cuddling")
                .addChoice("Kiss","kissing")
                .addChoice("Sleeping","sleeping");

        //Register the different optionData
        OptionData tags = new OptionData(OptionType.STRING, "tags", "Gebe deine e926 Tags hier ein wenn du \"Custom\" gewählt hast. Der syntax der tags ist: tag1+tag2+tag3");
        OptionData vcName = new OptionData(OptionType.STRING, "name", "Gebe hier den namen deines VoiceChannels ein", true);
        OptionData vcUsers = new OptionData(OptionType.STRING, "users", "Die Nutzer denen du Zugriff gewähren möchtest als @Mentions hinzufügen", true);
        OptionData isNSFW = new OptionData(OptionType.BOOLEAN, "nsfw", "Ist der Kanal für NSFW content gedacht?", true);
        OptionData verifyMember = new OptionData(OptionType.USER, "vermember", "Der Member den du verifizieren möchtest", true);
        OptionData verifyReason = new OptionData(OptionType.STRING, "verreason", "Der Verifizierungsgrund", true);

        //Register the Interactions
        Commanddata.add(Commands.slash("media", "Hol dir ein Artwork von E926(SFW)").addOptions(type, tags));
        Commanddata.add(Commands.slash("silly-media", "Hol dir ein Artwork von E621(NSFW)").addOptions(nsfwtype, nsfwtags).setNSFW(true));
        Commanddata.add(Commands.slash("bulk-delete", "Staff-only: Lösche eine menge Messages auf einmal").addOptions(amountToDelete));
        Commanddata.add(Commands.slash("voice", "Erstelle einen Temporören Voicechannel für dich und deine Freunde!").addOptions(vcName, vcUsers, isNSFW));
        Commanddata.add(Commands.slash("ticketsetup", "Staff only: Erstelle die Ticket Nachricht"));
        Commanddata.add(Commands.slash("verify", "Owner Only: Verifiziert einen Nutzer").addOptions(verifyMember, verifyReason));
        event.getGuild().updateCommands().addCommands(Commanddata).queue();
    }
}