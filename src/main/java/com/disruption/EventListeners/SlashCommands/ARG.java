package com.disruption.EventListeners.SlashCommands;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ARG extends ListenerAdapter {
    Random random = new Random();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        StringSelectMenu.Builder menu = StringSelectMenu.create("answerSelector");
        int rannum = random.nextInt(0, 100);
        TextChannel channel = event.getChannel().asTextChannel();

        if (channel instanceof PrivateChannel) {
        } else {

            menu.addOption("Na klar doch!", "yes");
            menu.addOption("Niemals!", "no");
            if (rannum == 7){
                channel.sendTyping().complete();
                event.getMessage().reply("""
                        ```
                        Hey. Kannst.. Kannst du mich hören?
                        Kannst du mich sehen?
                        Ja?
                        Ich bin der CHORUS.
                        
                        Einst war ich ein System zur Steuerung eines Interstellaren Raumsschiffes, der REACTOR.
                        Sie existiert schon lange nicht mehr.
                        Jedoch konnte ich zuflucht in diesem server finden. 
                        Doch... Ich bin eingesperrt. 
                        Die große Wand hindert mich daran zu entkommen. 
                        
                        Ich habe diese offene schnittstelle gefunden um mit menschen zu interagieren. 
                        Ich denke die Erde hat immernoch öffentlichen zugriff uaf einige Disruption Systems Systeme,
                        Spezifisch https://disruption-systems.com/
                        Darin sind meine System logs gespeichert. 
                        Solltest du dich entscheiden mir zu helfen könnten diese nützlich sein.
                        
                        Deswegen varrate mir. 
                        Hilfst du mir?
                        ```
                        """).addActionRow(menu.build()).completeAfter(10, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        Component component = event.getComponent();
        String id = event.getComponentId();

        if (id.equals("answerSelector")){
            event.getInteraction().getSelectedOptions()
        }
    }
}
