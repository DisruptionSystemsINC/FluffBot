package disruption.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import disruption.EventListeners.Voice.Lavaplayer.Dragonplayer;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.concurrent.TimeUnit;

public class ButtonHandler extends ListenerAdapter {

    private static AudioPlayerManager audioPlayerManager;
    private static AudioPlayer player;
    private static Message actionMessage;

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        VoiceChannel channel = event.getChannel().asVoiceChannel();
        if (channel.getParentCategory().getName().equalsIgnoreCase("temporäre voicechannels") && channel.equals(event.getMember().getVoiceState().getChannel())) {
            String label = event.getButton().getId();
            switch (label) {
                case ("stop"): {
                    Dragonplayer.stopBot();
                    event.deferEdit().complete();
                    actionMessage.delete().complete();
                    System.out.println("Stopped");
                    break;
                }
                case ("play"): {
                    Dragonplayer.resume();
                    event.deferEdit().complete();
                    System.out.println("Started");
                    break;
                }
                case ("pause"): {
                    Dragonplayer.pause();
                    event.deferEdit().complete();
                    System.out.println("paused");
                    break;
                }
                case ("skip"): {
                    Dragonplayer.skip(actionMessage);
                    System.out.println("skipped");
                    event.deferEdit().complete();
                    break;
                }
            }
        }
    }

    public Message postAudioSelector(SlashCommandInteractionEvent event) {
        Button stop = Button.danger("stop", "■");
        Button play = Button.primary("play", "►");
        Button pause = Button.primary("pause", "║");
        Button skip = Button.secondary("skip", "»");
        Message msg = event.reply("Now Playing: Nothing").addActionRow(stop, play, pause, skip).complete().retrieveOriginal().complete();
        return msg;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("queue")) {
            if (event.getMember().getVoiceState().inAudioChannel()) {
                VoiceChannel channel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
                if (!event.getGuild().getSelfMember().getVoiceState().inAudioChannel() || channel == event.getGuild().getSelfMember().getVoiceState().getChannel()) {
                    Dragonplayer dergonplayer = new Dragonplayer();
                    if (actionMessage == null) {
                        actionMessage = postAudioSelector(event);
                    } else {
                        event.reply("Song/Playlist wurde der Warteschlange hinzugefügt.").complete().deleteOriginal().completeAfter(10, TimeUnit.SECONDS);
                    }
                    AudioManager manager = channel.getGuild().getAudioManager();
                    TrackHandler handler = new TrackHandler();
                    String song = event.getOption("song").getAsString();
                    if (!song.startsWith("http")) {
                        song = "ytmsearch: " + song;
                    }
                    if (audioPlayerManager == null) {
                        audioPlayerManager = dergonplayer.getManager();
                    }
                    if (player == null) {
                        player = audioPlayerManager.createPlayer();
                        player.addListener(handler);
                    }
                    manager.openAudioConnection(channel);
                    manager.setAutoReconnect(true);
                    AudioSourceManagers.registerRemoteSources(audioPlayerManager);
                    manager.setSendingHandler(new DragonSender(player));
                    handler.loadTracks(song, audioPlayerManager);
                    dergonplayer.schedule(player);
                    dergonplayer.editSongMessage(actionMessage, player.getPlayingTrack().getInfo().title);
                } else {
                    event.reply("Der Bot ist schon in einem anderen VC").setEphemeral(true).queue();
                }
            } else {
                event.reply("Du musst in einem VC sein um das zu tun.").setEphemeral(true).queue();
            }
        }
    }
}