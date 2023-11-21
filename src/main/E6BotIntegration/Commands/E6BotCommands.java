package main.E6BotIntegration.Commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.E6BotIntegration.E6Wrapper.handleE9E6;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;


public class E6BotCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        switch (command) {
            case ("silly-media") -> {
                if (event.getChannel().asTextChannel() == Objects.requireNonNull(event.getGuild()).getTextChannelsByName("nsfw-bot", true).get(0)) {
                    Thread e6search = new Thread(() -> {
                        TextChannel channel = event.getChannel().asTextChannel();
                        String nsfwtype = Objects.requireNonNull(event.getOption("nsfwtype")).getAsString();
                        if (channel.isNSFW()) {
                            String nsfwtags = Objects.requireNonNull(event.getOption("nsfwtags")).getAsString();
                            event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                            try {
                                channel.sendMessage(handleE9E6.handleE6(nsfwtype, nsfwtags)).complete();
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            event.reply("Dieser Command funktioniert nur in einem NSFW channel.").setEphemeral(true).queue();
                        }
                    });
                    e6search.start();
                }
            }
            case ("media") -> {
                if (event.getChannel().asTextChannel() == Objects.requireNonNull(event.getGuild()).getTextChannelsByName("fluffymedia", true).get(0)) {
                    Thread e9search = new Thread(() -> {
                        TextChannel channel = event.getChannel().asTextChannel();
                        String type = Objects.requireNonNull(event.getOption("type")).getAsString();
                        System.out.println(type);
                        if (type.equals("custom")) {
                            String tags = Objects.requireNonNull(event.getOption("tags")).toString();
                            try {
                                channel.sendMessage(handleE9E6.handleE9(type, tags)).complete();
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                        } else {
                            String tags = "";
                            event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                            try {
                                event.getChannel().sendMessage(handleE9E6.handleE9(type, tags)).complete();
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    e9search.start();
                }
            }
        }
    }
}




