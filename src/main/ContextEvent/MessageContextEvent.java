package main.ContextEvent;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageContextEvent extends ListenerAdapter {
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        if (event.getName().equals("schließen")) {
            event.reply("Ticket wird geschlossen...").setEphemeral(true).complete();
            TextChannel channel = event.getChannel().asTextChannel();
            if(channel.getParentCategory().toString().toLowerCase().contains("support-tickets") || channel.getParentCategory().toString().toLowerCase().contains("nsfw-access-request") || channel.getParentCategory().toString().toLowerCase().contains("minecraft-support") || channel.getParentCategory().toString().toLowerCase().contains("fluffbot-support")){
                PermissionOverride permissionoverride =
                        channel.upsertPermissionOverride(event.getInteraction().getMember()).complete();
                permissionoverride.getManager().deny(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY).complete();
                channel.sendMessage("Dieses Ticket wurde Archiviert.").complete();
                Guild guild = event.getGuild();
                guild.modifyTextChannelPositions(event.getChannel().asTextChannel().getParentCategory()).selectPosition(0).setCategory(event.getGuild().getCategoriesByName("Archiv", true).get(0)).queue();
            }
            else{
                event.reply("Du kannst das hier nicht tun.").setEphemeral(true).complete();
            }
    }
}
}
