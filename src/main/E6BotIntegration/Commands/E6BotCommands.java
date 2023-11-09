package main.E6BotIntegration.Commands;

import main.E6BotIntegration.DataProcessing.Processing;
import main.E6BotIntegration.E6Wrapper.handleE9E6;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.ArrayList;
import java.util.List;

public class E6BotCommands extends ListenerAdapter {
    public static String tags;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        String type = event.getOption("type").getAsString();
        if (command.equals("media")) {
            Boolean isNSFW = event.getOption("isnsfw").getAsBoolean();
            if(isNSFW) {
                Thread e6search = new Thread(() -> {
                    TextChannel channel = event.getChannel().asTextChannel();
                    OptionMapping tag = event.getOption("tags");
                    event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                    tags = tag.getAsString();
                    handleE9E6.handleE6(type, tags);
                    if (Processing.ClientURL == null) {
                        System.out.println("The e621 API returned an unexpected value.");
                        event.reply("Fehler: Die e621 API hat einen unerwarteten Wert. Ist die website down?").setEphemeral(true).queue();
                    } else {
                        if (channel.isNSFW()) {
                            channel.sendMessage(Processing.Processor()).complete();
                        } else {
                            event.reply("Dieser Command funktioniert nur in einem NSFW channel.").setEphemeral(true).queue();
                        }
                    }
                });
                e6search.start();
            }
            else {
                Thread e9search = new Thread(() -> {
                    TextChannel channel = event.getChannel().asTextChannel();
                    OptionMapping tag = event.getOption("tags");
                    event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                    tags = tag.getAsString();
                    handleE9E6.handleE9(type, tags);
                    if (Processing.ClientURL == null) {
                        System.out.println("The e621 API returned an unexpected value.");
                        event.reply("Fehler: Die e926 API hat einen unerwarteten Wert. Ist die Website down?").setEphemeral(true).queue();
                    }
                    else {
                            channel.sendMessage(Processing.Processor()).complete();
                    }
                });
                e9search.start();
            }               }
    }


        @Override
        public void onGuildReady (GuildReadyEvent ready){

            List<CommandData> CommandData = new ArrayList<>();
            OptionData type = new OptionData(OptionType.STRING, "type", "Der Typ des Artworks", true).addChoices();
            OptionData isnsfw = new OptionData(OptionType.BOOLEAN, "isnsfw", "NSFW?", true);
            OptionData tag = new OptionData(OptionType.STRING, "tags", "Gebe deine e621/e926 Tags hier ein wenn du \"Custom\" gewählt hast");
            CommandData.add(Commands.slash("media", "Hol dir ein Artwork von E926(SFW) oder E621(NSFW)!").addOptions(isnsfw, type, tag));
            ready.getGuild().updateCommands().addCommands(CommandData).queue();
        }
    }



