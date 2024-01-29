package main.EventListeners.SlashCommands;

import main.EventListeners.utility.Logging;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemporaryVoice extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("voice")) {
            List<Member> members = new ArrayList<>();
            //Get all the options
            String name = Objects.requireNonNull(event.getOption("name")).getAsString();
            boolean isNSFW = Objects.requireNonNull(event.getOption("nsfw")).getAsBoolean();
            String mentions = Objects.requireNonNull(event.getOption("users")).getAsString();
            int TimeNeeded = Objects.requireNonNull(event.getOption("time")).getAsInt();

            //Create the Voice channel and add it to the "Voicechannels" category
            VoiceChannel channel = Objects.requireNonNull(event.getGuild()).getCategoriesByName("Temporäre Voicechannels", true).get(0).createVoiceChannel(name).setNSFW(isNSFW).complete();

            //Log the Channel creation
            try {
                Logging.printToLog("A Temporary Voicechannel was requested");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Get all the User Objects from the User Snowflakes
            for (UserSnowflake snf : getusers(mentions)){
                Member currentMember = event.getGuild().getMember(snf);
                members.add(currentMember);
            }

            //Add the permission to view the channel to each member
            for (Member mem : members){
                channel.upsertPermissionOverride(mem).grant(Permission.VIEW_CHANNEL).complete();
            }

            //Also add the permission for the user that launched the command
            channel.upsertPermissionOverride(event.getMember()).grant(Permission.VIEW_CHANNEL).complete();

            //Acknowledge the Interaction and send an ephemeral message
            event.reply(channel.getName() + " wurde für dich Erstellt").setEphemeral(true).complete();

            //Calculate the time in Milliseconds until the channel is closed
            int timeNeededMS = TimeNeeded * 3600000;

            //Create a new thread that will delete the Channel after a specified time
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(timeNeededMS);
                    channel.delete().complete();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            t.start();
        }
    }

    public List<UserSnowflake> getusers(String users) {
        List<UserSnowflake> UserList = new ArrayList<>();
        String[] UserArray = users.split(" ");

        for (String usr : UserArray) {
            String userID = usr.replace("@", "").replace("<", "").replace(">", "");
            UserList.add(User.fromId(userID));
        }

        return UserList;
    }
}