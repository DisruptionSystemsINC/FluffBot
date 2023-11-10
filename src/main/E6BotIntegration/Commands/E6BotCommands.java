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
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        String type = event.getOption("type").getAsString();
        if (command.equals("media")) {
            Boolean isNSFW = event.getOption("isnsfw").getAsBoolean();
            if(isNSFW) {
                Thread e6search = new Thread(() -> {
                    TextChannel channel = event.getChannel().asTextChannel();
                    String tags = event.getOption("tags").getAsString();
                    event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                        if (channel.isNSFW()) {
                            channel.sendMessage(handleE9E6.handleE6(type, tags)).complete();
                        } else {
                            event.reply("Dieser Command funktioniert nur in einem NSFW channel.").setEphemeral(true).queue();
                        }
                });
                e6search.start();
            }
            else {
                Thread e9search = new Thread(() -> {
                    TextChannel channel = event.getChannel().asTextChannel();
                    String tags = event.getOption("tags").toString();
                    event.reply("Das Bild braucht etwas zum laden, Bitte habe etwas Geduld").setEphemeral(true).queue();
                            channel.sendMessage(handleE9E6.handleE9(type, tags)).complete();
                });
                e9search.start();
            }
        }
    }


        @Override
        public void onGuildReady (GuildReadyEvent ready){

            List<CommandData> CommandData = new ArrayList<>();
            OptionData nsfwtype = new OptionData(OptionType.STRING, "nsfwtype", "Der Typ des Artworks", true).addChoices().addChoice("Custom", "custom");
            OptionData nsfwtags = new OptionData(OptionType.STRING, "nsfwtags", "Gebe deine e621 Tags hier ein wenn du \"Custom\" gewählt hast");
            OptionData type = new OptionData(OptionType.STRING, "type", "Der Typ des Artworks", true).addChoices().addChoice("Custom", "custom").addChoice("Hug", "hug").addChoice("Cuddles","cuddling").addChoice("Kiss","kissing").addChoice("Sleeping","sleeping");
            OptionData tags = new OptionData(OptionType.STRING, "tags", "Gebe deine e926 Tags hier ein wenn du \"Custom\" gewählt hast");
            CommandData.add(Commands.slash("media", "Hol dir ein Artwork von E926(SFW)").addOptions(type, tags));
            CommandData.add(Commands.slash("silly-media", "Hol dir ein Artwork von E621(NSFW)").addOptions(nsfwtype, nsfwtags));
            ready.getGuild().updateCommands().addCommands(CommandData).queue();
        }
    }



