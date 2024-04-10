package disruption.EventListeners.Voice.Lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import disruption.EventListeners.Voice.Lavaplayer.events.MessageTypes;
import disruption.EventListeners.Voice.Lavaplayer.events.PlayerQueueHandler;
import disruption.EventListeners.Voice.Lavaplayer.events.TrackHandler;
import disruption.FluffBot;
import net.dv8tion.jda.api.entities.Message;

;

public class Dragonplayer {
    public AudioPlayerManager getManager(){
        AudioPlayerManager man = new DefaultAudioPlayerManager();
        man.setTrackStuckThreshold(1000);
        man.source(YoutubeAudioSourceManager.class);
        return man;
    }

    public void schedule(AudioPlayer player, AudioPlayerManager man, String indent, TrackHandler handler){
        player.addListener(handler);
        handler.loadTracks(indent, man);
        player.startTrack(PlayerQueueHandler.getNextTrack(), true);
    }

    public static void stopBot(){
        FluffBot.getShardmanager().getGuildsByName("Fluffköpfe", true).get(0).getAudioManager().closeAudioConnection();
    }

    public static void switchPlayPause(){

    }

    public static void switchPausePlay(){

    }

    public static void resume(){

    }

    public static void sendMessageToUser(MessageTypes message){
        switch (message){
            case FAILURE -> {
                System.out.println("LOAD FAILURE");
            }
            case NOMATCH -> {
                System.out.println("COULD NOT FIND MEDIA");
            }
        }
    }

    public void editSongMessage(Message msg, String song){
        msg.editMessage("Now playing: " + song).complete();
    }
}
