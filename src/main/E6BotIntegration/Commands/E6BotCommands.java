package main.E6BotIntegration.Commands;

import main.E6BotIntegration.E6Wrapper.handleE9E6;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class E6BotCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        switch (command) {
            case ("silly-media") -> {
                Thread e6search = new Thread(() -> {
                    TextChannel channel = event.getChannel().asTextChannel();
                    String nsfwtype = event.getOption("nsfwtype").getAsString();
                    if (channel.isNSFW()) {
                        String nsfwtags = event.getOption("nsfwtags").getAsString();
                        event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                        channel.sendMessage(handleE9E6.handleE6(nsfwtype, nsfwtags)).complete();
                    } else {
                        event.reply("Dieser Command funktioniert nur in einem NSFW channel.").setEphemeral(true).queue();
                    }
                });
                e6search.start();
            }
            case ("media") -> {
                Thread e9search = new Thread(() -> {
                    TextChannel channel = event.getChannel().asTextChannel();
                    String type = event.getOption("type").getAsString();
                    System.out.println(type);
                    if (type.equals("custom")) {
                        String tags = event.getOption("tags").toString();
                        channel.sendMessage(handleE9E6.handleE9(type, tags)).complete();
                        event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                    } else {
                        String tags = "";
                        event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                        event.getChannel().sendMessage(handleE9E6.handleE9(type, tags)).complete();
                    }
                });
                e9search.start();
            }
        }
    }
}




