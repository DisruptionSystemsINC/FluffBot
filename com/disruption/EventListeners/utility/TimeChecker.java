package disruption.EventListeners.utility;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TimeChecker extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("timecheck") && event.getMember().getRoles().contains(event.getGuild().getRolesByName("staff", true).get(0))) {
            Message ogMesg = event.reply("Nutzer werden geladen... Dies kann mehrere Minuten dauern. Bitte warten...").complete().retrieveOriginal().complete();
            Thread t = new Thread(() -> {
            try {
                List<Member> members = event.getGuild().getMembers();
                List<TextChannel> channels = event.getGuild().getTextChannels();
                List<String> timeouters = new ArrayList<>();
                List<Message> msgs = new ArrayList<>();
                for (TextChannel chan : channels) {
                    ogMesg.editMessage(("Nutzer werden geladen... Dies kann mehrere Minuten dauern. Bitte warten... Status: Vorbereitung: Lade nachrichten: Aktueller Channel:  " + chan.getName())).complete();
                    System.out.println("Aktueller Channel:  " + chan.getName());
                    msgs.addAll(getAllMessages(chan));
                    Comparator<Message> comparator = Comparator.comparing(Message::getTimeCreated);
                    msgs.sort(comparator);
                    Collections.reverse(msgs);
                }
                for (Member mem : members) {
                    System.out.println("Aktueller nutzer:  " + mem.getEffectiveName());
                    ogMesg.editMessage("Nutzer werden geladen... Dies kann mehrere Minuten dauern. Bitte warten... Status: Berechne Daten...: Lade nachrichten: Aktueller Nutzer:  " + mem.getEffectiveName()).complete();
                    List<LocalDateTime> times = new ArrayList<>();
                                for (Message msg : msgs) {
                                    System.out.println("Aktuelle Nachricht:  " + msg.getId());
                                    if (msg.getAuthor().equals(mem.getUser())) {
                                        LocalDateTime time = msg.getTimeCreated().truncatedTo(ChronoUnit.DAYS).toLocalDateTime();
                                        if (time.isBefore(LocalDateTime.now().minusYears(1))) {
                                            times.add(time);
                                        }
                                        break;
                                    }
                                }
                        if(!times.isEmpty()){
                            Collections.sort(times);
                            Collections.reverse(times);
                            timeouters.add("[" + times.get(0) + "]: " + mem.getAsMention() + "\n");
                        }
                }
                ogMesg.editMessage("Ich habe diese Nutzer gefunden die schon seit einem Jahr nichts mehr geschrieben haben:\n\n" + timeouters).complete();
            }
            catch(Exception e){
                ogMesg.editMessage("WARNUNG: Abbruch. Der Bot hat es nicht geschafft alle Nachrichten zu laden.").complete();
                System.out.println(e.getStackTrace());
                System.out.println(e.getCause());
            }
        });
            t.start();
    }
}
    public static List<Message> getAllMessages(TextChannel channel) throws InterruptedException {
        int i = 0;
        LinkedList<Message> allMessages = new LinkedList<>();
        String lastMessageId = null;
        while (true) {
            i += 100;
            System.out.println("Nachricht " + i + " in channel " + channel.getName() + " Geladen");
            List<Message> messages;
            if (lastMessageId == null) {
                messages = channel.getHistory().retrievePast(100).complete();
            } else {
                Thread.sleep(500);
                messages = channel.getHistoryBefore(lastMessageId, 100).complete().getRetrievedHistory();
            }
            if (messages.isEmpty()) {
                break;
            }
            allMessages.addAll(messages);
            lastMessageId = messages.get(messages.size() - 1).getId();
        }
        System.out.println("Size of messages in channel:  " + allMessages.size());
        return allMessages;
    }
}
