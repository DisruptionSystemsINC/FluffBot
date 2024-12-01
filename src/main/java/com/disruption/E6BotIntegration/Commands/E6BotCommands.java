package com.disruption.E6BotIntegration.Commands;

import com.disruption.E6BotIntegration.E6Wrapper.handleE9E6;
import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.Objects;



public class E6BotCommands extends ListenerAdapter {

    //Commands used to trigger E6bot interactions
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        switch (command) {
            case ("silly-media") -> {
                FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION,"NSFW Media command has been triggered, preparing to send...");
                //Make sure the channel is actually the right channel to send it in
                if (event.getChannel().asTextChannel() == Objects.requireNonNull(event.getGuild()).getTextChannelsByName("nsfw-bot", true).get(0)) {
                    //Start the rest of the code in a new thread to not block the bot's main thread.
                    Thread e6search = new Thread(() -> {
                        TextChannel channel = event.getChannel().asTextChannel();
                        String nsfwtype = Objects.requireNonNull(event.getOption("nsfwtype")).getAsString();
                        //Check if the channel is actually NSFW
                        if (channel.isNSFW()) {
                            FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION,"channel is identified as NSFW");
                            //If someone uses the custom flag, make sure to forward the correct data
                            if (nsfwtype.equals("custom")) {
                                String nsfwtags = (event.getOption("nsfwtags")).getAsString();
                                event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                                try {
                                    channel.sendMessage(handleE9E6.handleE9(nsfwtype, nsfwtags)).complete();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                    event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                                try {
                                    String nsfwtags = "";
                                    channel.sendMessage(handleE9E6.handleE6(nsfwtype, nsfwtags)).complete();
                                    FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION, "Message has been sent");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            } else{
                                event.reply("Dieser Command funktioniert nur in einem NSFW channel.").setEphemeral(true).queue();
                            }
                    });
                    //Start the thread
                    e6search.start();
                    FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION,"E6search thread has started");
                }
            }
            //Same as above
            case ("media") -> {
                FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION,"Media command has been triggered, preparing to send...");
                if (event.getChannel().asTextChannel() == Objects.requireNonNull(event.getGuild()).getTextChannelsByName("fluffymedia", true).get(0)) {
                    Thread e9search = new Thread(() -> {
                        TextChannel channel = event.getChannel().asTextChannel();
                        String type = Objects.requireNonNull(event.getOption("type")).getAsString();
                        System.out.println(type);
                        if (type.equals("custom")) {
                            String tags = Objects.requireNonNull(event.getOption("tags")).getAsString();
                            try {
                                channel.sendMessage(handleE9E6.handleE9(type, tags)).complete();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                        } else {
                            String tags = "";
                            event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                            try {
                                event.getChannel().sendMessage(handleE9E6.handleE9(type, tags)).complete();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    e9search.start();
                    FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION,"E9search thread has been started");
                }
            }
        }
    }
}




