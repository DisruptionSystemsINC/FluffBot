package main.EventListeners.SlashCommands;


import main.EventListeners.utility.CountTickets;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.io.File;
import java.io.IOException;

public class Ticket extends ListenerAdapter {
    File buffer = new File("TicketBuffer.log");
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String user = event.getInteraction().getMember().getAsMention();
        String command = event.getName();
        if (command.equals("ticket")) {
            OptionMapping ticketOptions = event.getOption("ticket-type");
            String ticketString = ticketOptions.getAsString();
            Member member = event.getMember();
            switch (ticketString.toLowerCase()){
                case "support" -> {
                    if (!event.getGuild().getCategoriesByName("support-tickets", true).toString().contains("support-tickets")) {
                        event.getGuild().createCategory("support-tickets");
                        event.reply("Dies scheint eine erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
                    } else {
                        String TicketID;
                        try {
                            TicketID = CountTickets.getTicketCount();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Category id = event.getGuild().getCategoriesByName("support-tickets", true).get(0);
                        TextChannel channel = id.createTextChannel("support-ticket-" + TicketID).complete();
                        String name = "support-ticket-" + TicketID;
                        TextChannel ticket = event.getGuild().getTextChannelsByName(name, false).get(0);
                        PermissionOverride permissionoverride =
                                ticket.upsertPermissionOverride(member).complete();
                        permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).queue();
                        try {
                            CountTickets.incrementCounter();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        event.reply("Ein support Ticket wurde für dich erstellt. Bitte schreibe dein Anliegen in den Channel " + name + "!").setEphemeral(true).queue();
                        channel.sendMessage("Herzlich willkommen beim Support " + user + "!\nEin Mitglied des Staff's wird sich bald möglichst darum kümmern. Derweil beschreibe bitte deine Problematik.\n\nSolltest du aus Versehen ein Ticket geöffnet haben, kannst du es schließen, indem du diese Nachricht rechtsklickst, und dann -> Apps  -> Schließen auswählst.").complete();
                    }
                }
                case "nsfw-freischaltung" ->{
                    if (!event.getGuild().getCategoriesByName("nsfw-access-requests", true).toString().contains("nsfw-access-requests")) {
                        event.getGuild().createCategory("nsfw-access-requests").complete();
                        event.reply("Dies scheint eine Erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
                    } else {
                        String TicketID;
                        try {
                            TicketID = CountTickets.getTicketCount();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Category id = event.getGuild().getCategoriesByName("nsfw-access-requests", true).get(0);
                        TextChannel channel = id.createTextChannel("nsfw-access-request-ticket-" + TicketID).complete();
                        String name = "nsfw-access-request-ticket-" + TicketID;
                        TextChannel ticket = event.getGuild().getTextChannelsByName(name, false).get(0);
                        PermissionOverride permissionoverride =
                                ticket.upsertPermissionOverride(member).complete();
                        permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).queue();
                        try {
                            CountTickets.incrementCounter();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        event.reply("Ein NSFW Access Request Ticket wurde für dich erstellt. Bitte schreibe dein Anliegen in den Channel " + name + "!").setEphemeral(true).queue();
                        channel.sendMessage("||" + user + "|| Zum Verifizieren deines Alters (18+) brauchen wir ein Ausweisdokument mit deinem Geburtsdatum und deinem Discord Tag auf einem Bild. Bei deinem Ausweisdokument kannst du gerne alles, bis auf das Geburtsdatum schwärzen. Sende das am besten so bald wie möglich in diesen Channel hier rein.\n" +
                                "<@266637315831496704> wird sich dann so schnell wie Möglich darum kümmen").complete();

                    }
                }
                case "minecraft-server-support" ->{
                    if (!event.getGuild().getCategoriesByName("minecraft-support", true).toString().contains("minecraft-support")) {
                        event.getGuild().createCategory("minecraft-support").complete();
                        event.reply("Dies scheint eine Erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
                    } else {
                        String TicketID;
                        try {
                            TicketID = CountTickets.getTicketCount();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Category id = event.getGuild().getCategoriesByName("minecraft-support", true).get(0);
                        id.createTextChannel("minecraft-support-ticket-" + TicketID).complete();
                        String name = "minecraft-support-ticket-" + TicketID;
                        TextChannel ticket = event.getGuild().getTextChannelsByName(name, false).get(0);
                        PermissionOverride permissionoverride =
                                ticket.upsertPermissionOverride(member).complete();
                        permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).queue();
                        try {
                            CountTickets.incrementCounter();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        event.reply("Ein Minecraft Support Ticket wurde für dich erstellt. Bitte schreibe dein Anliegen in den Channel " + name + "!").setEphemeral(true).queue();
                        ticket.sendMessage("Wilkommen bei unserem Minecraft Server Support!\n<@447387517143089162> wird sich um dein problem in Kürze kümmern.\n\nSolltest du aus Versehen ein Ticket geöffnet haben, kannst du es schließen, indem du diese Nachricht rechtsklickst, und dann -> Apps  -> Schließen auswählst.").complete();
                    }
                }
                    //Legacy Code. Not needed but generally good practice to still include a default to fall back on instead of just throwing a NullpointerException
                default -> {
                    event.reply("Dies war leider keine valide Option. Bitte benutze eine dieser Optionen: 1. vorschlag, 2. nsfw-freischaltung 3. support").setEphemeral(true).queue();
                }
            }
        }
    }

}
