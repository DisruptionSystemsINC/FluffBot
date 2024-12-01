package com.disruption.EventListeners.BotInit;

import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Initialized extends ListenerAdapter {
    public static boolean isInit;

    public void onReady(ReadyEvent e) {
        FluffBot.getDragonLog().printToLog(LogLevel.INFORMATION, "Bot has been initialized using CHORUS Subsystem");
        isInit = true;
    }
}
