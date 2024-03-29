package main.EventListeners.utility;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class voteOut extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (!event.getUser().isBot()) {
            Message msg = event.getChannel().asTextChannel().retrieveMessageById(event.getMessageId()).complete();
            if (event.getGuildChannel().getName().equals("nsfw-bot") && msg.getAuthor().isBot()|| event.getGuildChannel().getName().equals("fluffymedia") && msg.getAuthor().isBot()){
                int cnt = 0;
                for ( MessageReaction react : msg.getReactions()){
                    cnt++;
                }
                if (cnt >= 3){
                    msg.delete().complete();
                    try {
                        Logging.printToLog("Message has been deleted. Reason: Voted to delete. Message content:\n" + msg.getContentDisplay());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
