package main.EventListeners.utility;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

import static main.FluffBot.isOnboarding;

public class OnboardingSetup extends ListenerAdapter {
    private static boolean isAlready;
    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        if (isOnboarding & !isAlready) {
            System.out.println("Trigger");
            List<Role> roles = event.getGuild().getRoles();
            List<Member> members = event.getGuild().getMembers();
            String[] names = new String[]{"Weiß", "Schwarz", "Grün", "Blau", "Rot", "Lila", "Orange", "Gelb", "Pink", "Wolf", "Fuchs", "Drache", "Katze", "Protogen", "Hund", "Tiger", "Hirsch", "Hai", "Panda", "Fledermaus", "Kuh", "Mensch", "16-17", "14-15", "18-20", "21+", "Männlich", "Weiblich", "Divers", "Heterosexuell", "Homosexuell", "Bisexuell", "Pansexuell", "Asexuell", "Single/suchend", "Single/nicht suchend", "Vergeben", "Vergeben/offene Beziehung", "DMs geöffnet", "DMs geschlossen", "News", "Minecraft"};
            for (Member member : members) {
                for (Role role : roles) {
                    for (String name : names)
                        if (name.equals(role.getName())) {
                            System.out.println(member);
                            System.out.println(role);
                            event.getGuild().removeRoleFromMember(member, role).complete();
                        }
                }
            }
        }
        isAlready = true;
    }
}
