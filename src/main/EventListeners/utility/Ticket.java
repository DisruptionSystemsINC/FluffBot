package main.EventListeners.utility;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public class Ticket {
    public Ticket createTicket(Member member, String DisplayName, SlashCommandInteractionEvent event, String additionalMention, String type) throws IOException {
            Ticket ticket = new Ticket(){Member member; };
        if (!event.getGuild().getCategoriesByName(DisplayName + "s", true).toString().contains(DisplayName)) {
            event.getGuild().createCategory(DisplayName + "s").complete();
            event.reply("Dies scheint eine Erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
        } else {
            String TicketID;
            try {
                TicketID = CountTickets.getTicketCount();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Category id = event.getGuild().getCategoriesByName(DisplayName + "s", true).get(0);
            TextChannel channel = id.createTextChannel(DisplayName + "-ticket-" + TicketID).complete();
            String chanref = channel.getAsMention();
            PermissionOverride permissionoverride =
                    channel.upsertPermissionOverride(member).complete();
            permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).queue();
            try {
                CountTickets.incrementCounter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            event.reply("Ein Ticket wurde für dich erstellt. Bitte schreibe dein anliegen in den channel " + chanref + "!").setEphemeral(true).queue();
            Logging.printToLog("A Ticket has been created with ID " + CountTickets.getTicketCount());

            switch (type){
                case ("nsfw") -> {
                    channel.sendMessage(member.getAsMention() + "\nZum Verifizieren deines Alters (18+) brauchen wir ein Ausweisdokument mit deinem Geburtsdatum und deinem Discord Tag auf einem Bild. Bei deinem Ausweisdokument kannst du gerne alles, bis auf das Geburtsdatum schwärzen. Sende das am besten so bald wie möglich in diesen Channel hier rein.\n" + additionalMention +
                            " wird sich dann so schnell wie Möglich darum kümmen").complete();
                }

                case ("fluffbot") -> {
                    channel.sendMessage("Wilkommen! " + member.getAsMention() + "\n" + additionalMention + " wird sich um dein Problem in Kürze kümmern \n\nDerweil, Schildere bitte dein problem mit dem FluffBot oder Äußere deinen Blacklist tagvorschlag mit einer kleinen begründung warum du denkst das dieser tag auf der blacklist stehen soll").complete();
                }

                case ("minecraft") -> {
                    channel.sendMessage("Wilkommen!" + member.getAsMention() + "\n" + additionalMention + " wird sich um dein Problem in kürze kümmern\n\nDerweil, schildere bitte dein problem").complete();
                }

                case ("critic") -> {
                    channel.sendMessage("Wilkommen! " + member.getAsMention() + "\n" + "Ein Moderator wird sich in Kürze melden.\n\nWährenddessen, Äußere doch bitte schon mal deine kritik!").complete();
                }

                default -> {
                    channel.sendMessage("Wilkommen!" + member.getAsMention() + "\nEin Moderator wird sich in kürze um dich kümmern, Bitte beschreibe dein anliegen/Problem").complete();
                }
            }
        }
        return this;
    }

}




