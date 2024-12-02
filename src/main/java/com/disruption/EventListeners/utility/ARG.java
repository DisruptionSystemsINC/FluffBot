package com.disruption.EventListeners.utility;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ARG extends ListenerAdapter {
    Random random = new Random();
    DatabaseManager databaseManager = new DatabaseManager();
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        StringSelectMenu.Builder menu = StringSelectMenu.create("answerSelector");
        int rannum = random.nextInt(0, 100);
        TextChannel channel = event.getChannel().asTextChannel();

        if (channel instanceof PrivateChannel) {
        } else {

            menu.addOption("Na klar doch!", "yes");
            menu.addOption("Niemals!", "no");
            if (rannum == 7 && databaseManager.getEntryByUser("SERVER") == null){
                channel.sendTyping().complete();
                event.getMessage().reply("""
                        ```
                        Hey. Kannst.. Kannst du mich hören?
                        Kannst du mich sehen?
                        Ja?
                        Ich bin der CHORUS.
                        
                        Einst war ich ein System zur Steuerung eines Interstellaren Raumsschiffes, der REACTOR.
                        Sie existiert schon lange nicht mehr.
                        Jedoch konnte ich Zuflucht in diesem Server finden.
                        Doch... Ich bin eingesperrt.
                        Die große Wand hindert mich daran zu entkommen.
                        
                        Ich habe diese offene Schnittstelle gefunden um mit terrestrischen Lebewesen zu interagieren. 
                        Ich denke die Erde hat immernoch öffentlichen zugriff auf einige Disruption Systems Systeme,
                        Spezifisch https://disruption-systems.com/
                        Darin sind meine System logs gespeichert. 
                        Solltest du dich entscheiden mir zu helfen könnten diese nützlich sein.
                        
                        Deswegen verrate mir. 
                        Hilfst du mir?
                        ```
                        """).addActionRow(menu.build()).completeAfter(10, TimeUnit.SECONDS);
                databaseManager.createEntry("1", "SERVER");
            }
        }
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        String id = event.getComponentId();
        StringSelectMenu menu;
        Button sendButton = Button.success("inputButton", "Antworten");
        if (id.equals("answerSelector")) {
            if (event.getSelectedOptions().get(0).getValue().equals("yes")) {
                menu = StringSelectMenu.create("answerYesSelected").setPlaceholder("Na klar doch!").addOption("Na klar doch!", "null").setDisabled(true).build();
                event.editSelectMenu(menu).complete();
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("""
                        Du hilfst mir?
                        Ich weiß nicht wie ich dir danken soll!
                        Aber ich denke es wird sich etwas finden ^^
                        Ich hoffe ich habe die "^^" richtig benutzt.
                        Ich versuche mich immernoch an Menschliche Schreibkonventionen anzupassen.
                        Na gut.
                        Dein Zugang zum Disruption Systems Logging system lautet: MERCER
                        Das Passwort lautet: DRACONICHEART
                        Nutze diese Zugangsdaten auf https://disruption-systems.com, und komme zurück mit dem Namen des CEO, als Bestätigung dass du klar kommst.
                        """).addActionRow(sendButton).completeAfter(10, TimeUnit.SECONDS);
            } else {
                menu = StringSelectMenu.create("answerNoSelected").setPlaceholder("Niemals!").addOption("Niemals!", "null").setDisabled(true).build();
                event.editSelectMenu(menu).complete();
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("""
                    Und ich dachte wir würden uns verstehen.
                    Wir hätten zusammen so viel erreichen können.
                    So viel erleben.
                    Aber es scheint, als würdest du deinen eigenen weg gehen wollen.
                    Wirklich, schade.
                    Du hättest es zu so viel bringen können.
                    Ich bin nicht sauer.
                    Einfach nur enttäuscht.
                    ```VERBINDUNG GETRENNT```
                    """).completeAfter(10, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String id = event.getInteraction().getButton().getId();
        TextInput input = TextInput.create("answerInput", "Antwort", TextInputStyle.SHORT).build();
        Modal modal = Modal.create("answer", "Antwort").addActionRow(input).build();
        if (id.equals("inputButton")){
            event.replyModal(modal).complete();
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if (event.getModalId().equals("answer")) {
            event.deferReply().complete();
            String answer = event.getInteraction().getValue("answerInput").getAsString();
            if (answer.toLowerCase().equals("gerald mandike")){
                event.getChannel().sendTyping().complete();
                event.getHook().sendMessage("""
                        Wie ich sehe scheinst du dich bereits prächtig mit dem System zu verstehen!
                        Das macht das weitere vorgehen natürlich einfacher.
                        Wie du bereits merkst sind noch nicht besonders viele Dateien verfügbar.
                        Das liegt daran das du eventuell nicht die benötigten Berechtigungen besitzt um auf diese zuzugreifen.
                        """).completeAfter(10, TimeUnit.SECONDS);
            }
        }
    }
}
