package main.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.Collections;
import java.util.List;

public class PlayerQueueHandler {
     static List<AudioTrack> queue;
    AudioTrack currentTrack;
    AudioTrack nextTrack;

    public PlayerQueueHandler(){}

    public AudioTrack getPlayingTrack(AudioPlayer player){
        return player.getPlayingTrack();
    }

    public AudioTrack getNextTrack(){
        if (!getQueue().isEmpty()) {
            return getQueue().get(0);
        }
        return null;
    }

    public List<AudioTrack> getQueue(){
        return queue;
    }

    public List<AudioTrack> addSongToQueue(AudioTrack track){
        queue.add(track);
        return queue;
    }

}
