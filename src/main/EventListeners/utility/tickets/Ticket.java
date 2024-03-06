package main.EventListeners.utility.tickets;

import main.EventListeners.utility.Logging;
import main.FluffBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.pagination.MessagePaginationAction;

import javax.lang.model.element.NestingKind;
import java.io.*;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Ticket{
    public static int createTicket(Member member, String DisplayName, StringSelectInteractionEvent event, String additionalMention, String type) throws IOException {
        int TicketID = CountTickets.getTicketCount();
        //Check if the tickets category even exists and create it if it doesn't
        if (!event.getGuild().getCategoriesByName(DisplayName + "s", true).toString().contains(DisplayName)) {
            event.getGuild().createCategory(DisplayName + "s").complete();
            //Acknowledge the interaction and let the user know they have to rerun the command
            event.reply("Dies scheint eine Erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
        } else {
            Category id = event.getGuild().getCategoriesByName(DisplayName + "s", true).get(0);
            //Create the Ticket channel with the ticket ID read from the Ticket counter class
            TextChannel channel = id.createTextChannel(DisplayName + "-" + TicketID).complete();
            String chanref = channel.getAsMention();
            //Add the permissions for the ticket creator to view the ticket
            PermissionOverride permissionoverride =
                    channel.upsertPermissionOverride(member).complete();
            permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).complete();
            try {
                CountTickets.incrementCounter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Log the ticket creation and Acknowledge the interaction
            event.getHook().sendMessage("Ein Ticket wurde für dich erstellt. Bitte schreibe dein anliegen in den channel " + chanref + "!").setEphemeral(true).queue();
            Logging.printToLog("A Ticket has been created with ID " + (CountTickets.getTicketCount()));

            //Get a certain type of ticket. This could technically be solved with an ENUM, this might be in a Future version
            switch (type){
                case ("nsfw") -> {
                    channel.sendMessage("Information: Durch die Nutzung des Tickets Stimmst du der Speicherung eventueller Personenbezogenen Daten zu. \nNachrichten und Medien Die in diesen Kanal \"Gepostet\" werden, sind AUSSCHLIEßLICH durch das Staff Team einsehbar.  \nEine Löschung dieser Daten kann direkt in diesem Ticket Beantragt werden. \nSolltest du nicht zustimmen, kannst du mit der Schaltfläche unten Das Ticket einfach wieder schließen. Dadurch werden keine Daten erfasst.").complete();
                    channel.sendMessage(member.getAsMention() + "\nZum Verifizieren deines Alters (18+) brauchen wir ein Ausweisdokument mit deinem Geburtsdatum und deinem Discord Tag auf einem Bild. \nWir Bitten darum Daten wie Namen und andere unwichtige informationen bis auf das Geburtsdatum auf dem Personalausweis aus Datenschutzgründen zu schwärzen. Sende das am besten so bald wie möglich in diesen Channel hier rein.\n" + additionalMention +
                            " wird sich dann so schnell wie Möglich darum kümmen.").complete();

                }
                case ("fluffbot") ->
                    channel.sendMessage("Wilkommen! " + member.getAsMention() + "\n" + additionalMention + " wird sich um dein Problem in Kürze kümmern \n\nDerweil, Schildere bitte dein problem mit dem FluffBot oder Äußere deinen Blacklist tagvorschlag mit einer kleinen begründung warum du denkst das dieser tag auf der blacklist stehen soll").complete();


                case ("minecraft") ->
                    channel.sendMessage("Wilkommen!" + member.getAsMention() + "\n" + additionalMention + " wird sich um dein Problem in kürze kümmern\n\nDerweil, schildere bitte dein problem").complete();


                case ("critic") ->
                    channel.sendMessage("Wilkommen! " + member.getAsMention() + "\n" + "Ein Moderator wird sich in Kürze melden.\n\nWährenddessen, Äußere doch bitte schon mal deine kritik!").complete();


                default ->
                    channel.sendMessage("Wilkommen!" + member.getAsMention() + "\nEin Moderator wird sich in kürze um dich kümmern, Bitte beschreibe dein anliegen/Problem").complete();

            }
            //Register the buttons. This will add the "Schließen" button as it's seperate message to the channel
            Button button = Button.danger("close", "Schließen");
            channel.sendMessage("Du kannst das Ticket hier Schließen").addActionRow(button).complete();
        }
        return TicketID;
    }
        public static void close(ButtonInteractionEvent event, TextChannel channel) throws IOException, ExecutionException, InterruptedException {
            String TicketID = channel.getName().split("-")[channel.getName().split("-").length - 1];
            Logging.printToLog("Ticket with ID " + TicketID + " Is being closed");
            String TicketFolderPath = FluffBot.getTicketDir() + channel.getName() + "/";

            File ticketSpecificDir = new File(TicketFolderPath);
            ticketSpecificDir.mkdir();

            File mediaFolder = new File(TicketFolderPath + "media/");
            mediaFolder.mkdir();

            File ticket = new File(TicketFolderPath + "Ticket.chorus");
            ticket.createNewFile();

            PrintWriter TicketWriter = new PrintWriter(new BufferedWriter(new FileWriter(ticket, true)));

            //These will be written to the media folder of the ticket
            List<Message.Attachment> attachments = new ArrayList<>();

            //Iterate through all the messages and write them to file
            List<Message> messages = getAllMessages(channel);
                for (Message message : messages) {
                    String author = "<" + message.getAuthor().getName() + ">";

                    TicketWriter.append(author).append("\n").append(message.getContentDisplay()).append("\n");

                    for (Message.Attachment att2 : message.getAttachments()) {
                        attachments.add(att2);
                    }
                }
                //Iterate through the attachments and download them
                for (Message.Attachment attachment : attachments) {
                    File att = new File(mediaFolder + "/" + attachment.getFileName());
                    att.createNewFile();
                    attachment.getProxy().downloadToFile(att);
                }

                channel.delete().complete();
                TicketWriter.close();
        }

        //Just gets all messages in a channel
    public static List<Message> getAllMessages(TextChannel channel) {
        List<Message> allMessages = new ArrayList<>();
        Iterable<Message> messages = channel.getIterableHistory();

        for (Message message : messages) {
            allMessages.add(message);
        }

        return allMessages;
    }
}





