package disruption.E6BotIntegration.Commands;

import disruption.E6BotIntegration.E6Wrapper.handleE9E6;
import disruption.EventListeners.utility.Logging;
import net.dv8tion.jda.api.entities.Message;
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
                Logging.printToLog("NSFW Media command has been triggered, preparing to send...");
                //Make sure the channel is actually the right channel to send it in
                if (event.getChannel().asTextChannel() == Objects.requireNonNull(event.getGuild()).getTextChannelsByName("nsfw-bot", true).get(0)) {
                    //Start the rest of the code in a new thread to not block the bot's main thread.
                    Thread e6search = new Thread(() -> {
                        TextChannel channel = event.getChannel().asTextChannel();
                        String nsfwtype = Objects.requireNonNull(event.getOption("nsfwtype")).getAsString();
                        //Check if the channel is actually NSFW
                        if (channel.isNSFW()) {
                            Logging.printToLog("channel is identified as NSFW");
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
                                    Logging.printToLog("Message has been sent and Reactions have been added");
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
                    Logging.printToLog("E6search thread has started");
                }
            }
            //Same as above
            case ("media") -> {
                Logging.printToLog("Media command has been triggered, preparing to send...");
                if (event.getChannel().asTextChannel() == Objects.requireNonNull(event.getGuild()).getTextChannelsByName("fluffymedia", true).get(0)) {
                    Thread e9search = new Thread(() -> {
                        TextChannel channel = event.getChannel().asTextChannel();
                        String type = Objects.requireNonNull(event.getOption("type")).getAsString();
                        System.out.println(type);
                        if (type.equals("custom")) {
                            String tags = Objects.requireNonNull(event.getOption("tags")).getAsString();
                            try {
                                Message msg = channel.sendMessage(handleE9E6.handleE9(type, tags)).complete();
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
                    Logging.printToLog("E9search thread has been started");
                }
            }
        }
    }
}




