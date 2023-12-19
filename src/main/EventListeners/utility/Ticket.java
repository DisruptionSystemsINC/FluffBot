package main.EventListeners.utility;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public class Ticket {
    public void createTicket(Member member, String DisplayName, SlashCommandInteractionEvent event, String additionalMention) throws IOException {

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
            event.reply(additionalMention  + "Ein Ticket wurde für dich erstellt. Bitte schreibe dein anliegen in den channel " + chanref + "!").setEphemeral(true).queue();
            Logging.printToLog("A Ticket has been created!");
            channel.sendMessage("Wilkommen. Ein Moderator wird sich um deine Anfrage in kürze kümmern.\n\n Derweil, Schildere bitte dein(e) Anliegen \n\nSolltest du aus Versehen ein Ticket geöffnet haben, kannst du es schließen, indem du diese Nachricht rechtsklickst, und dann -> Apps  -> Schließen auswählst.").complete();
        }


    }
}
