package main.EventListeners.BotInit;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class Initialized extends ListenerAdapter {
    public static boolean isInit;
    public void onReady(ReadyEvent e){
        System.out.println("CHORUS Subroutine \"FluffBot\" Initialisert.");
        System.out.println("Wilkommen, bei DisruptionOS");
        isInit = true;
    }
}
