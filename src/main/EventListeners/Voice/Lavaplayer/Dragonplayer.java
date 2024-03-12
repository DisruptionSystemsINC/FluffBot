package main.EventListeners.Voice.Lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import main.EventListeners.Voice.Lavaplayer.events.AudioStoppedEvent;

public class Dragonplayer {
    private AudioPlayer player = this.getNewPlayer();
    public AudioPlayer getNewPlayer(){
        AudioPlayerManager man = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(man);
        player = man.createPlayer();
        return player;
    }

    public void schedule(){
        AudioStoppedEvent trackHandler = new AudioStoppedEvent(player);
        player.addListener(trackHandler);
    }

    public static void stopBot(){

    }

    public static void switchPlayPause(){

    }

    public static void switchPausePlay(){

    }
}
