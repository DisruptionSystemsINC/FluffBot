package disruption.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import disruption.EventListeners.Voice.Lavaplayer.Dragonplayer;

public class TrackHandler extends AudioEventAdapter {
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {

        if (endReason.mayStartNext && PlayerQueueHandler.getNextTrack() != null){
            player.startTrack(PlayerQueueHandler.getNextTrack(), true);
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

    public void loadTracks(String indent, AudioPlayerManager man){
        man.loadItem(indent, new Dragonhandler());
    }
}
