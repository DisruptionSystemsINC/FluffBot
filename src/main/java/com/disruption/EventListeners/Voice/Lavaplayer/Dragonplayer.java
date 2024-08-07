package com.disruption.EventListeners.Voice.Lavaplayer;

import com.disruption.EventListeners.Voice.Lavaplayer.events.CommandHandler;
import com.disruption.EventListeners.Voice.Lavaplayer.events.MessageTypes;
import com.disruption.EventListeners.Voice.Lavaplayer.events.PlayerQueueHandler;
import com.disruption.FluffBot;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.concurrent.TimeUnit;

public class Dragonplayer {

    private static AudioPlayer player;
    public boolean result;
    public AudioPlayerManager getManager(){
        AudioPlayerManager manager = new DefaultAudioPlayerManager();
        manager.setTrackStuckThreshold(1000);
        manager.setFrameBufferDuration(200);
        manager.setPlayerCleanupThreshold(1000);
        YoutubeAudioSourceManager ytman = new YoutubeAudioSourceManager(true);
        manager.source(ytman.getClass());
        return manager;
    }


    public void schedule(AudioPlayer player){
        player.setVolume(50);
        if (player.getPlayingTrack() == null) {
            player.startTrack(PlayerQueueHandler.getNextTrack(), true);
        }
        this.player = player;
    }

    public static void stopBot(){
        if (player.getPlayingTrack() != null) {
            player.stopTrack();
            PlayerQueueHandler.getQueue().clear();
            CommandHandler.actionMessage = null;
        }
        FluffBot.getShardmanager().getGuildsByName("Fluffköpfe", true).get(0).getAudioManager().closeAudioConnection();
    }

    public static void resume(){
        player.setPaused(false);
    }

    public static void pause(){
        player.setPaused(true);
    }

    public static void skip(Message msg){
        player.startTrack(PlayerQueueHandler.getNextTrack(), false);
        if (player.getPlayingTrack() != null) {
            editSongMessage(msg, player.getPlayingTrack().getInfo().title);
        } else {
            msg.delete().queue();
        }
    }

    public void sendMessageToUser(MessageTypes message){
        ShardManager man = FluffBot.getShardmanager();
        VoiceChannel chan = man.getGuildsByName("Fluffköpfe", true).get(0).getSelfMember().getVoiceState().getChannel().asVoiceChannel();
        switch (message){
            case FAILURE -> {
                chan.sendMessage("Warnung: Der Inhalt konnte aufgrund eines Technischen fehlers nicht geladen werden.").complete().delete().completeAfter(10, TimeUnit.SECONDS);
            }
            case NOMATCH -> {
                chan.sendMessage("Der Inhalt konnte nicht gefunden werden.").complete().delete().completeAfter(10, TimeUnit.SECONDS);
            }
        }
    }

    public static void editSongMessage(Message msg, String song){
        msg.editMessage("Now playing: " + song).complete();
    }
}
