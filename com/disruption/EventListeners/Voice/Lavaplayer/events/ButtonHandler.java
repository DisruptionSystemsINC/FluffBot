package disruption.EventListeners.Voice.Lavaplayer.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import disruption.EventListeners.Voice.Lavaplayer.Dragonplayer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.managers.AudioManager;

public class ButtonHandler extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        VoiceChannel channel = event.getChannel().asVoiceChannel();
        if (channel.getParentCategory().getName().equalsIgnoreCase("temporäre voicechannels") && channel.equals(event.getMember().getVoiceState().getChannel())) {
            Message msg = event.getMessage();
            String label = event.getButton().getId();
            switch (label){
                case ("stop"):{
                    Dragonplayer.stopBot();
                    event.reply("Dragonplayer gestoppt").setEphemeral(true).complete();
                    msg.delete().complete();
                }
                case ("play"):{
                    Dragonplayer.resume();
                }
            }
        }
    }

    public void postAudioSelector(SlashCommandInteractionEvent event, VoiceChannel channel) {
        Button stop = Button.danger("stop", "■");
        Button play = Button.primary("play", "►");
        Button pause = Button.primary("pause", "║");
        Button skip = Button.secondary("skip", "»");
        channel.sendMessage("Now Playing: Nothing").addActionRow(stop, play, pause, skip).complete();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("sing")) {
            String song = event.getOption("song").getAsString();
            event.deferReply().setEphemeral(true).complete();
            Member member = event.getInteraction().getMember();
            if (member.getVoiceState().inAudioChannel()) {
                Dragonplayer dergonplayer = new Dragonplayer();
                VoiceChannel channel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
                AudioManager manager = channel.getGuild().getAudioManager();
                AudioPlayerManager playerManager = dergonplayer.getManager();
                AudioPlayer player = playerManager.createPlayer();
                AudioSourceManagers.registerRemoteSources(playerManager);
                TrackHandler handler = new TrackHandler();
                dergonplayer.schedule(player, playerManager, song, handler);
                postAudioSelector(event, channel);
                manager.setSendingHandler(new DragonSender(player));
                manager.openAudioConnection(channel);

            } else {
                event.getHook().sendMessage("Du musst in einem VC sein um das zu tun").complete();
            }
        }
    }
}