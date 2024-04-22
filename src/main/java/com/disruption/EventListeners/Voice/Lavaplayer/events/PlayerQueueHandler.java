package com.disruption.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.List;

public class PlayerQueueHandler {
    private static final List<AudioTrack> queue = new ArrayList<>();

    public PlayerQueueHandler(){}

    public static AudioTrack getPlayingTrack(AudioPlayer player){
        return player.getPlayingTrack();
    }

    public static AudioTrack getNextTrack(){
        if (!getQueue().isEmpty()) {
            AudioTrack track = getQueue().get(0);
            getQueue().remove(0);
            return track;
        }
        return null;
    }

    public static List<AudioTrack> getQueue(){
        return queue;
    }

    public static void addSongToQueue(AudioTrack track){
        queue.add(track);
    }

}
