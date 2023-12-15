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
        String command = event.getName();
        if (command.equals("ticket")) {
            OptionMapping ticketOptions = event.getOption("ticket-type");
            String ticketString = ticketOptions.getAsString();
            Member member = event.getMember();

            switch (ticketString.toLowerCase()) {

                case "support" -> {
                    Thread t = new Thread(() -> {
                        main.EventListeners.utility.Ticket ticket = new main.EventListeners.utility.Ticket();
                        ticket.createTicket(member, "support-ticket", event, "");
                    });
                    t.start();
                }
                case "nsfw-freischaltung" -> {
                    Thread t = new Thread(() -> {
                        main.EventListeners.utility.Ticket ticket = new main.EventListeners.utility.Ticket();
                        ticket.createTicket(member, "nsfw-freischaltungs-ticket", event, "||<@266637315831496704>||");
                    });
                    t.start();
                }
                case "minecraft-server-support" -> {
                    Thread t = new Thread(() -> {
                        main.EventListeners.utility.Ticket ticket = new main.EventListeners.utility.Ticket();
                        ticket.createTicket(member, "minecraft-server-support-ticket", event, "||<@447387517143089162>||");
                    });
                    t.start();
                }

                case "fluffbot-support" -> {
                    Thread t = new Thread(() -> {
                        main.EventListeners.utility.Ticket ticket = new main.EventListeners.utility.Ticket();
                        ticket.createTicket(member, "fluffbot-support-ticket", event, "||<@447387517143089162>||");
                    });
                    t.start();
                }

                case "server-critics" -> {
                    Thread t = new Thread(() -> {
                        main.EventListeners.utility.Ticket ticket = new main.EventListeners.utility.Ticket();
                        ticket.createTicket(member, "server-kritik-ticket", event, "");
                    });
                    t.start();
                }




/*
                /Testing statement
                case "tt" -> {
                    Thread t = new Thread(()-> {
                        main.EventListeners.utility.Ticket ticket = new main.EventListeners.utility.Ticket();
                        ticket.createTicket(member, "DisplayName", event, "");
                    });
                    t.start();
                }
         */

                //Legacy Code. Not needed but generally good practice to still include a default to fall back on instead of just throwing a NullpointerException
                default -> {
                    event.reply("Dies war leider keine valide Option.").setEphemeral(true).queue();
                }
            }
        }
    }
}