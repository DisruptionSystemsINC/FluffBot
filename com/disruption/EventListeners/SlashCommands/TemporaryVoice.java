package disruption.EventListeners.SlashCommands;

import disruption.EventListeners.utility.Logging;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemporaryVoice extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("voice")) {
            if (Objects.requireNonNull(event.getMember()).getRoles().contains(Objects.requireNonNull(event.getGuild()).getRolesByName("Verifiziert", true).get(0))) {
                //Acknowledge the interaction and defer the Reply
                event.deferReply().setEphemeral(true).complete();

                List<Member> members = new ArrayList<>();
                //Get all the options
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                boolean isNSFW = Objects.requireNonNull(event.getOption("nsfw")).getAsBoolean();
                String mentions = Objects.requireNonNull(event.getOption("users")).getAsString();

                //Create the Voice channel and add it to the "Voicechannels" category
                VoiceChannel channel = Objects.requireNonNull(event.getGuild()).getCategoriesByName("Temporäre Voicechannels", true).get(0).createVoiceChannel(name).setNSFW(isNSFW).complete();

                Logging.printToLog("A Temporary Voicechannel was requested");

                //Get all the User Objects from the User Snowflakes
                for (UserSnowflake snf : getusers(mentions)) {
                    Member currentMember = event.getGuild().getMember(snf);
                    members.add(currentMember);
                }

                //Add the permission to view the channel to each member
                for (Member mem : members) {
                    channel.upsertPermissionOverride(mem).grant(Permission.VIEW_CHANNEL).grant(Permission.VOICE_CONNECT).grant(Permission.VOICE_SPEAK).grant(Permission.VOICE_STREAM).complete();
                }
                //Restrict users without NSFW role from viewing the channel, even if they are invited
                if (isNSFW) {
                    channel.upsertPermissionOverride(event.getGuild().getRolesByName("Verifiziert", true).get(0)).deny(Permission.VIEW_CHANNEL).complete();
                    channel.upsertPermissionOverride(event.getGuild().getRolesByName("NSFW", true).get(0)).grant(Permission.VIEW_CHANNEL).complete();
                }

                //Also add the permission for the user that launched the command
                channel.upsertPermissionOverride(event.getMember()).grant(Permission.VIEW_CHANNEL).complete();

                //Send an ephemeral message
                event.getHook().sendMessage(channel.getAsMention() + " wurde für dich Erstellt").setEphemeral(true).complete();
            } else {
                event.reply("Du muss Verifiziert sein um das zu tun!").setEphemeral(true).complete();
            }
        }
    }
    //Parse out the UserID's and put them into a list
    public List<UserSnowflake> getusers(String users) {
        List<UserSnowflake> UserList = new ArrayList<>();
        String[] UserArray = users.replace(" ", "").split(">");

        for (String usr : UserArray) {
            String userID = usr.replace("@", "").replace("<", "");
            UserList.add(User.fromId(userID));
        }

        return UserList;
    }
}