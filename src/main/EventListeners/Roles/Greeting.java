package main.EventListeners.Roles;

import main.EventListeners.utility.Logging;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class Greeting extends ListenerAdapter {
    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        Role role = event.getRoles().get(0);
            if (role.getName().equals("Verifiziert")) {
                Member member = event.getMember();
                PrivateChannel chnl = member.getUser().openPrivateChannel().complete();
                chnl.sendMessage("***Hey, Ich bin Fluffbot, der All-Purpose bot für den FluffKöpfe Server!***\n\n" +
                        "Glückwunsch zu deiner Verifizierung!\n\n" +
                        "Hier sind meine grundlegenden Funktionen:\n\n" +
                        "1. Das Ticket tool\n" +
                        "Mit dem /Ticket Kommando kannst du ganz einfach unseren Server Support erreichen!\n\n" +
                        "2. Ein Furry Artwork sucher!\n" +
                        "Mit dem /media (Für SFW artwork im SFW Art Bot Channel) und dem\n" +
                        "/silly-media (Für NSFW Artwork in dem NSFW Bot Channel) Kommando,\n" +
                        "Kannst du Ganz einfach ein Artwork laden!\n\n" +
                        "Die nutzung der NSFW Funktion erfordert eine Einmalige manuelle Freischaltung die über das Ticket tool beantragt werden kann.\n\n" +
                        "Eventuell werden weitere funktionen hinzukommen, Diese werden im Server vorgestellt.\n\n" +
                        "Ich wünsche dir viel Spaß auf unserem Server!\n\n\n" +
                        "***-Das FluffKöpfe Team und FluffBot***").complete();
                try {
                    Logging.printToLog(member + " Has been verified");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    }
}
