package com.disruption.EventListeners.Voice.Lavaplayer.events;

import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;


public class Dragonhandler implements AudioLoadResultHandler{
    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION,"Track has been successfully loaded: " + audioTrack.getInfo().title);
        PlayerQueueHandler.addSongToQueue(audioTrack);
        }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION,"Playlist has been successfully loaded: " + audioPlaylist.getName());
        if (audioPlaylist.isSearchResult()){
            PlayerQueueHandler.addSongToQueue(audioPlaylist.getTracks().get(0));
        }
        else {
            for (AudioTrack track : audioPlaylist.getTracks()) {
                PlayerQueueHandler.addSongToQueue(track);
            }
        }
    }

    @Override
    public void noMatches() {
        CommandHandler.dergonplayer.sendMessageToUser(MessageTypes.NOMATCH);
    }

    @Override
    public void loadFailed(FriendlyException e) {
        CommandHandler.dergonplayer.sendMessageToUser(MessageTypes.FAILURE);
    }
}
