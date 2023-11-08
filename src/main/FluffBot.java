package main;

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

public class FluffBot {
    private final ShardManager shardmanager;

    public FluffBot() throws LoginException {
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("MTE0ODMxOTA0NDg5NDcyODMyNA.GAOCL0.ido7kuoQaur6N8XcHcGbunwSY67aFtGjoBYGXE");
        builder.setActivity(Activity.watching("Furries beim chatten zu"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MEMBERS);
        builder.enableCache(CacheFlag.MEMBER_OVERRIDES);
        shardmanager = builder.addEventListeners(
                new Initialized(),
                new Ticket(),
                new RegisterSlashCommands(),
                new BulkDelete()).build();

    }

    public ShardManager getShardmanager() {
        return shardmanager;
    }

    public static void main(String[] args){
        try {
            FluffBot bot = new FluffBot();
        } catch (LoginException e) {
            System.out.println("ERROR: Invalid or incomplete Bot Token");
        }
    }
}




