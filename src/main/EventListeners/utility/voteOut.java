package main.EventListeners.utility;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class voteOut extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {;
        if (!event.getUser().isBot()) {
            String MessageID = event.getMessageId();
            TextChannel channel = event.getChannel().asTextChannel();
            Message msg = channel.retrieveMessageById(MessageID).complete();
            if (event.getGuildChannel().getName().equals("nsfw-bot") || event.getGuildChannel().getName().equals("fluffymedia") && msg.getAuthor().isBot()){
                int cnt = 0;
                for (MessageReaction react : msg.getReactions()){
                    cnt++;
                }
                System.out.println(cnt);
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
