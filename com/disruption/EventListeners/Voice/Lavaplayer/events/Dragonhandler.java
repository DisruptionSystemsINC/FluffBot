package disruption.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import disruption.EventListeners.Voice.Lavaplayer.Dragonplayer;
import disruption.EventListeners.utility.Logging;


public class Dragonhandler implements AudioLoadResultHandler{
    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        Logging.printToLog("Track has been successfully loaded: " + audioTrack.getInfo().title);
        PlayerQueueHandler.addSongToQueue(audioTrack);
        }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        Logging.printToLog("Playlist has been successfully loaded: " + audioPlaylist.getName());
        for (AudioTrack track : audioPlaylist.getTracks()){
            PlayerQueueHandler.addSongToQueue(track);
        }
    }

    @Override
    public void noMatches() {
        Dragonplayer.sendMessageToUser(MessageTypes.NOMATCH);
    }

    @Override
    public void loadFailed(FriendlyException e) {
        Dragonplayer.sendMessageToUser(MessageTypes.FAILURE);
    }
}
