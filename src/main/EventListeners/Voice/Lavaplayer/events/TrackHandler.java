package main.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import main.EventListeners.Voice.Lavaplayer.Dragonplayer;

public class TrackHandler extends AudioEventAdapter {
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        PlayerQueueHandler queueHandler = new PlayerQueueHandler();
        if (endReason.mayStartNext && queueHandler.getNextTrack() != null){
            player.startTrack(queueHandler.getNextTrack(), true);
        }
        else {
            Dragonplayer.stopBot();
        }
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        Dragonplayer.switchPlayPause();
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        Dragonplayer.switchPausePlay();
    }


}
