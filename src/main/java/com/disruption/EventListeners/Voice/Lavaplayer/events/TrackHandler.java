package com.disruption.EventListeners.Voice.Lavaplayer.events;

import com.disruption.EventListeners.Voice.Lavaplayer.Dragonplayer;
import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;
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
        FluffBot.getDragonLog().printToLog(LogLevel.WARNING, "TRACK IS STUCK");
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        FluffBot.getDragonLog().printToLog(LogLevel.WARNING, "TRACK IS STUCK");
    }

    public void loadTracks(String indent, AudioPlayerManager man) {
        try {
            man.loadItem(indent, new Dragonhandler()).get();
        }
        catch (InterruptedException | ExecutionException e){
            FluffBot.getDragonLog().printToLog(LogLevel.WARNING,"Loading tracks has been interrupted " + e.getMessage());
        }

    }
}
