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
        Emoji Denyemo = event.getJDA().getEmojiById("1185232083426218105");
        Emoji Checkemo = event.getJDA().getEmojiById("1149381059159523518");
        if (!event.getUser().isBot()) {
            String MessageID = event.getMessageId();
            TextChannel channel = event.getChannel().asTextChannel();
            Message msg = channel.retrieveMessageById(MessageID).complete();
            Emoji emj = event.getReaction().getEmoji();
            if (emj.getName().equals(Checkemo.getName()) | emj.getName().equals(Denyemo.getName()) && event.getGuildChannel().getName().equals("nsfw-bot") || event.getGuildChannel().getName().equals("fluffymedia") ){
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
