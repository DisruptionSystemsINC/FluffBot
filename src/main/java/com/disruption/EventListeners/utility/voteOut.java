package com.disruption.EventListeners.utility;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class voteOut extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (!event.getUser().isBot()) {
            Message msg = event.getChannel().asTextChannel().retrieveMessageById(event.getMessageId()).complete();
            Emoji emote = event.getEmoji();
            if (event.getGuildChannel().getName().equals("nsfw-bot") && msg.getAuthor().isBot()|| event.getGuildChannel().getName().equals("fluffymedia") && msg.getAuthor().isBot()){
               int cnt = msg.getReaction(emote).getCount();

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
