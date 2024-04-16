package disruption.E6BotIntegration.ContentMenuInteractionEvents;

import disruption.EventListeners.utility.Logging;
import disruption.EventListeners.utility.MemberChecks;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class TimeoutMenu extends ListenerAdapter {
    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        if (event.getName().equals("Timeout (1 Tag)") || event.getName().equals("Timeout (1 Stunde)")) {
            event.deferReply().setEphemeral(true).complete();
            MemberChecks checks = new MemberChecks();
            Member target = event.getTargetMember();
            Member origin = event.getMember();
            Guild guild = event.getGuild();
            String timeoutLength = event.getName();
            TimeUnit timeunit;
            if (checks.isStaff(origin, guild)) {
                if (timeoutLength.equals("Timeout (1 Tag)")) {
                    timeunit = TimeUnit.DAYS;
                } else {
                    timeunit = TimeUnit.HOURS;
                }
                if (origin.equals(target)) {
                    event.getHook().editOriginal("Du kannst dich nicht selbst in den Timeout versetzen.").complete();
                } else if (checks.isStaff(target, guild)) {
                    event.getHook().editOriginal("Du kannst keinen Staff member in den timeout versetzen.").complete();
                } else {
                    Logging.printToLog(target.getEffectiveName() + " Wurde von " + origin.getEffectiveName() + " in den timeout versetzt");
                    target.timeoutFor(1, timeunit).complete();
                    event.getHook().editOriginal("Nutzer wurde in den Timeout versetzt").complete();
                }
            } else {
                event.getHook().editOriginal("Nur Staff kann dies tun.").complete();
            }
        }
    }
}