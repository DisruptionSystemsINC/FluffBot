package main.EventListeners.SlashCommands;



import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.io.File;

public class Ticket extends ListenerAdapter {
    int i = 0;
    int f = 0;
    int d = 0;
    int a = 0;
    File buffer = new File("TicketBuffer.log");
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("ticket")) {
            OptionMapping ticketOptions = event.getOption("ticket-type");
            String ticketString = ticketOptions.getAsString();
            Member member = event.getMember();
            switch (ticketString.toLowerCase()){
                case "support" -> {
                    if (!event.getGuild().getCategoriesByName("support-tickets", true).toString().contains("support-tickets")) {
                        event.getGuild().createCategory("support-tickets").queue();
                        event.reply("Dies scheint eine erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
                    } else {
                        Category id = event.getGuild().getCategoriesByName("support-tickets", true).get(0);
                        id.createTextChannel("support-ticket-" + i).complete();
                        String name = "support-ticket-" + i;
                        TextChannel ticket = event.getGuild().getTextChannelsByName(name, false).get(0);
                        PermissionOverride permissionoverride =
                                ticket.upsertPermissionOverride(member).complete();
                        permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).queue();
                        i++;
                        event.reply("Ein support Ticket wurde für dich erstellt. Bitte schreibe dein Anliegen in den Channel " + name + "!").setEphemeral(true).queue();
                    }
                }
                case "nsfw-freischaltung" ->{
                    if (!event.getGuild().getCategoriesByName("nsfw-access-requests", true).toString().contains("nsfw-access-requests")) {
                        event.getGuild().createCategory("nsfw-access-requests").queue();
                        event.reply("Dies scheint eine Erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
                    } else {
                        Category id = event.getGuild().getCategoriesByName("nsfw-access-requests", true).get(0);
                        id.createTextChannel("nsfw-access-request-ticket-" + f).complete();
                        String name = "nsfw-access-request-ticket-" + f;
                        TextChannel ticket = event.getGuild().getTextChannelsByName(name, false).get(0);
                        PermissionOverride permissionoverride =
                                ticket.upsertPermissionOverride(member).complete();
                        permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).queue();
                        i++;
                        event.reply("Ein NSFW Access Request Ticket wurde für dich erstellt. Bitte schreibe dein Anliegen in den Channel " + name + "!").setEphemeral(true).queue();
                    }
                }

                case "vorschlag" ->{
                    if (!event.getGuild().getCategoriesByName("servervorschläge", true).toString().contains("servervorschläge")) {
                        event.getGuild().createCategory("servervorschläge").queue();
                        event.reply("Dies scheint eine Erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
                    } else {
                        Category id = event.getGuild().getCategoriesByName("servervorschläge", true).get(0);
                        id.createTextChannel("servervorschlag-" + d).complete();
                        String name = "servervorschlag-" + d;
                        TextChannel ticket = event.getGuild().getTextChannelsByName(name, false).get(0);
                        PermissionOverride permissionoverride =
                                ticket.upsertPermissionOverride(member).complete();
                        permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).queue();
                        i++;
                        event.reply("Ein Vorschlags Ticket wurde für dich erstellt. Bitte schreibe dein Anliegen in den Channel " + name + "!").setEphemeral(true).queue();
                    }
                }

                case "minecraft-server-support" ->{
                    if (!event.getGuild().getCategoriesByName("minecraft-support", true).toString().contains("minecraft-support")) {
                        event.getGuild().createCategory("minecraft-support").queue();
                        event.reply("Dies scheint eine Erstinstallation zu sein. Bitte führe den Befehl nochmal aus um dein Ticket zu öffnen.").setEphemeral(true).queue();
                    } else {
                        Category id = event.getGuild().getCategoriesByName("minecraft-support", true).get(0);
                        id.createTextChannel("minecraft-support-request-" + a).complete();
                        String name = "minecraft-support-request-" + a;
                        TextChannel ticket = event.getGuild().getTextChannelsByName(name, false).get(0);
                        PermissionOverride permissionoverride =
                                ticket.upsertPermissionOverride(member).complete();
                        permissionoverride.getManager().grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES).queue();
                        i++;
                        event.reply("Ein Minecraft Support Ticket wurde für dich erstellt. Bitte schreibe dein Anliegen in den Channel " + name + "!").setEphemeral(true).queue();
                    }
                }

                default -> {
                    event.reply("Dies war leider keine valide Option. Bitte benutze eine dieser Optionen: 1. vorschlag, 2. nsfw-freischaltung 3. support").setEphemeral(true).queue();
                }
            }
        }
    }

}
