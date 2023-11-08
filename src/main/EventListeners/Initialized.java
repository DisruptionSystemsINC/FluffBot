package eventListeners;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Initialized extends ListenerAdapter {
    public void onReady(ReadyEvent e){
        System.out.println("CHORUS Subroutine \"FluffBot\" Initialisert.");
        System.out.println("Wilkommen, bei DisruptionOS");
    }
}
