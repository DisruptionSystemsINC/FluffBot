package main;

import main.ContextEvent.MessageContextEvent;
import main.E6BotIntegration.Commands.E6BotCommands;
import main.EventListeners.Initialized;
import main.EventListeners.SlashCommands.BulkDelete;
import main.EventListeners.SlashCommands.Ticket;
import main.SlashCommands.RegisterSlashCommands;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Arrays;

public class FluffBot {
    private static String Token;
    private final ShardManager shardmanager;

    public FluffBot() throws LoginException, IOException {

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(Token);
        builder.setActivity(Activity.watching("Furries beim chatten zu"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGE_REACTIONS);
        builder.enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOJI);
        shardmanager = builder.addEventListeners(
                new Initialized(),
                new Ticket(),
                new RegisterSlashCommands(),
                new BulkDelete(),
                new MessageContextEvent(),
                new E6BotCommands()).build();

    }

    public ShardManager getShardmanager() {
        return shardmanager;
    }

    public static void main(String[] args) throws IOException {
        File token = new File("token.chorus");
        File testtoken = new File("testtoken.chorus");
        System.out.println(Arrays.stream(args).toList());
        if (Arrays.stream(args).toList().toString().contains("--test")){
            BufferedReader reader = new BufferedReader(new FileReader(testtoken));
            Token = reader.readLine();
            System.out.println("Launching Test version...");
        }
        else {
            BufferedReader reader = new BufferedReader(new FileReader(token));
            Token = reader.readLine();
        }
        try {
            FluffBot bot = new FluffBot();
        } catch (LoginException e) {
            System.out.println("ERROR: Invalid or incomplete Bot Token");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




