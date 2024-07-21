package com.disruption.EventListeners.Voice.Lavaplayer.events;

import com.disruption.EventListeners.Voice.Lavaplayer.Dragonplayer;
import com.disruption.EventListeners.utility.Logging;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.ExecutionException;

public class TrackHandler extends AudioEventAdapter {
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
            if (endReason.mayStartNext) {
                if (!PlayerQueueHandler.getQueue().isEmpty()) {
                    player.startTrack(PlayerQueueHandler.getNextTrack(), true);
                }
                else {
                    Dragonplayer.stopBot();
                }
            } else if (AudioTrackEndReason.REPLACED.equals(endReason)){
                return;
            } else {
                Dragonplayer.editSongMessage(CommandHandler.actionMessage, "Nothing");
                Dragonplayer.stopBot();
            }
        }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs, StackTraceElement[] stackTrace) {
        Logging.printToLog("TRACK IS STUCK");
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        Logging.printToLog("TRACK IS STUCK");
    }

    public void loadTracks(String indent, AudioPlayerManager man) {
        try {
            man.loadItem(indent, new Dragonhandler()).get();
        }
        catch (InterruptedException | ExecutionException e){
            Logging.printToLog("Loading tracks has been interrupted " + e.getMessage());
        }

    }
}
