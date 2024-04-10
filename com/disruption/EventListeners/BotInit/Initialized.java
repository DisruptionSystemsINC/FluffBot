package disruption.EventListeners.BotInit;

import disruption.EventListeners.utility.Logging;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Initialized extends ListenerAdapter {
    public static boolean isInit;

    public void onReady(ReadyEvent e) {
        Logging.printToLog("Bot has been initialized using CHORUS Subsystem");
        isInit = true;
    }
}
