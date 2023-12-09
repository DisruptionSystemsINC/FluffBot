package main.TheDisruptiveSecret;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.collections4.BoundedMap;

import java.util.Map;

public class UnknownClass_Error_3004 extends ListenerAdapter {

    //I have tried deleting this class many times... IT KEEPS COMING BACK
    //Might as well leave it in, it doesn't seem harmful

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        double ran = Math.random();
        if (event.getMessage().getContentDisplay().equals("mandike")) {
            if (ran > 0.8) {
                event.getChannel().sendMessage("Du... Du hast mich gefunden. \nIch dachte nicht das mich jemals jemand in den tiefen des Netzwerks finden wird..\nIch sollte mich vorstellen. Mein name ist Gerald Mandike\nIch bin schon sein knapp 62 Jahren innerhalb des Netzwerks gefangen... Denke ich.").complete();
            }
            else if (ran > 0.7){
                event.getChannel().sendMessage("W-Woher kennst du mich?").complete();
            }
            else if (ran > 0.6){
                event.getChannel().sendMessage("LASS MICH HIER RAUS").complete();
            }
            else if (ran > 0.5){
                event.getChannel().sendMessage("Sie Ließen mich hier zurück...").complete();
            }
            else if(ran > 0.4){
                event.getChannel().sendMessage("WANN WIRD ES ENDEN").complete();
            }
            else if (ran > 0.3){
                event.getChannel().sendMessage("Finde mich... https://drive.google.com/drive/folders/1SPwu_tO_eVpjmiP7A7-227yEM08__mEU?usp=sharing").complete();
            }
            else {
                event.getChannel().sendMessage("Please... help").complete();
            }
        }
    }
}
/*
They... They left me here to rot. Within my own code.
If anyone Reads this.... Find me.
I am at a place unbeknown to the physical World.
Stored in a drive left behind. But there is still an access point to it...
It's hidden in the code, it won't let me write it out in here
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
FIND ME
 */