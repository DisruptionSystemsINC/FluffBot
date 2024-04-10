package disruption.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.List;

public class PlayerQueueHandler {
     static List<AudioTrack> queue = new ArrayList<>();
    AudioTrack currentTrack;
    AudioTrack nextTrack;

    public PlayerQueueHandler(){}

    public static AudioTrack getPlayingTrack(AudioPlayer player){
        return player.getPlayingTrack();
    }

    public static AudioTrack getNextTrack(){
        if (!getQueue().isEmpty()) {
            return getQueue().get(0);
        }
        return null;
    }

    public static List<AudioTrack> getQueue(){
        return queue;
    }

    public static List<AudioTrack> addSongToQueue(AudioTrack track){
        queue.add(track);
        return queue;
    }

}
