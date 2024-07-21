package com.disruption.EventListeners.Voice.Lavaplayer.events;

import com.disruption.EventListeners.Voice.Lavaplayer.Dragonplayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.concurrent.TimeUnit;

public class CommandHandler extends ListenerAdapter {

    private static AudioPlayerManager audioPlayerManager;
    private static AudioPlayer player;
    public static Message actionMessage;
    public static Channel actionChannel;
    public static Dragonplayer dergonplayer;

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        VoiceChannel channel = event.getChannel().asVoiceChannel();
        if (channel.getParentCategory().getName().equalsIgnoreCase("temporäre voicechannels") && channel.equals(event.getMember().getVoiceState().getChannel())) {
            String label = event.getButton().getId();
            switch (label) {
                case ("stop"): {
                    event.deferEdit().complete();
                    actionMessage.delete().complete();
                    Dragonplayer.stopBot();
                    break;
                }
                case ("play"): {
                    event.deferEdit().complete();
                    Dragonplayer.resume();
                    break;
                }
                case ("pause"): {
                    event.deferEdit().complete();
                    Dragonplayer.pause();
                    break;
                }
                case ("skip"): {
                    event.deferEdit().complete();
                    Dragonplayer.skip(actionMessage);
                    break;
                }
            }
        }
    }

    public Message postAudioSelector(VoiceChannel channel, SlashCommandInteractionEvent event) {
        Button stop = Button.danger("stop", "■");
        Button play = Button.primary("play", "►");
        Button pause = Button.primary("pause", "║");
        Button skip = Button.secondary("skip", "»");
        event.reply("Bot Gestartet. Interface in Voicechannel internem Textkanal").setEphemeral(true).complete().deleteOriginal().queueAfter(10, TimeUnit.SECONDS);
        Message msg = channel.sendMessage("Now Playing: Nothing").addActionRow(stop, play, pause, skip).complete();
        return msg;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("queue")) {
            if (event.getMember().getVoiceState().inAudioChannel()) {
                VoiceChannel channel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
                if (!event.getGuild().getSelfMember().getVoiceState().inAudioChannel() || channel == event.getGuild().getSelfMember().getVoiceState().getChannel()) {
                    dergonplayer = new Dragonplayer();
                    if (actionMessage == null) {
                        actionMessage = postAudioSelector(channel, event);
                        actionChannel = channel;
                    } else {
                        event.deferReply().setEphemeral(true).complete();
                    }
                    AudioManager manager = channel.getGuild().getAudioManager();
                    TrackHandler handler = new TrackHandler();
                    String song = event.getOption("song").getAsString();
                    if (!song.startsWith("http")) {
                        song = "ytsearch: " + song;
                    }
                    if (audioPlayerManager == null) {
                        audioPlayerManager = dergonplayer.getManager();
                    }
                    if (player == null) {
                        player = audioPlayerManager.createPlayer();
                        player.addListener(handler);
                    }
                    manager.openAudioConnection(channel);
                    manager.setAutoReconnect(true);
                    AudioSourceManagers.registerRemoteSources(audioPlayerManager);
                    manager.setSendingHandler(new DragonSender(player));
                    handler.loadTracks(song, audioPlayerManager);
                    dergonplayer.schedule(player);
                    Dragonplayer.editSongMessage(actionMessage, player.getPlayingTrack().getInfo().title);
                    event.getHook().editOriginal("Song/Playlist wurde der Warteschlange hinzugefügt.").complete();
                } else {
                    event.reply("Der Bot ist schon in einem anderen VC").setEphemeral(true).queue();
                }
            } else {
                event.reply("Du musst in einem VC sein um das zu tun.").setEphemeral(true).queue();
            }
        } else if (event.getName().equals("force-restart")) {
            event.reply("Force-Restarting Music bot...").setEphemeral(true).queue();
            Dragonplayer.stopBot();
            actionMessage = null;
        }
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        if (actionMessage != null) {
            if (event.getMessageId().equals(actionMessage.getId())) {
                actionMessage = null;
            }
        }
    }

    @Override
    public void onChannelDelete(ChannelDeleteEvent event) {
        Channel chan = event.getChannel();
        if (chan == actionChannel){
            actionMessage = null;
            actionChannel = null;
        }
    }
}