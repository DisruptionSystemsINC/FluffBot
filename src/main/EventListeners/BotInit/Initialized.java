package main.EventListeners.BotInit;

import main.EventListeners.utility.Logging;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class Initialized extends ListenerAdapter {
    public static boolean isInit;
    public void onReady(ReadyEvent e){
        try {
            Logging.printToLog("Bot has been initialized using CHORUS Subsystem");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        isInit = true;
    }
}
