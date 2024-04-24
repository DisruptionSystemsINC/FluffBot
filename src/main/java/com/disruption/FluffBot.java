package com.disruption;

import com.disruption.E6BotIntegration.Commands.E6BotCommands;
import com.disruption.E6BotIntegration.ContentMenuInteractionEvents.TimeoutMenu;
import com.disruption.EventListeners.BotInit.DailyPost;
import com.disruption.EventListeners.BotInit.Initialized;
import com.disruption.EventListeners.BotInit.TempChannelDeletion;
import com.disruption.EventListeners.Moderation.Moderation;
import com.disruption.EventListeners.Roles.GiveNewRole;
import com.disruption.EventListeners.Roles.Greeting;
import com.disruption.EventListeners.SlashCommands.BulkDelete;
import com.disruption.EventListeners.SlashCommands.TemporaryVoice;
import com.disruption.EventListeners.SlashCommands.Verify;
import com.disruption.EventListeners.Voice.Lavaplayer.events.CommandHandler;
import com.disruption.EventListeners.Voice.TempChannel;
import com.disruption.EventListeners.buttonContextInteractionEvent.TicketButtons;
import com.disruption.EventListeners.buttonContextInteractionEvent.TicketCloseButton;
import com.disruption.EventListeners.utility.Logging;
import com.disruption.EventListeners.utility.TimeChecker;
import com.disruption.EventListeners.utility.VerifiedChecker;
import com.disruption.EventListeners.utility.voteOut;
import com.disruption.SlashCommands.RegisterSlashCommands;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

;


public class FluffBot {
    private static String Token;
    static ShardManager shardmanager;

    //Define the config root folder here

    public static String getConfigRoot(){
        return "config/";
    }
    //Define the logging dir here
    public static String getLoggingDir(){
        return getConfigRoot() + "logs/";
    }
    //Define the Ticket Dir here
    public static String getTicketDir(){
        return getConfigRoot() + "tickets/";
    }
    //Define the blacklist dir here
    public static String getBlacklistDir(){
        return getConfigRoot() + "blacklist/";
    }

    //Build the bot and register the EventListeners
    public FluffBot() throws LoginException, IOException {

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(Token);
        builder.setActivity(Activity.watching("Furries beim chatten zu"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES);
        builder.enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOJI, CacheFlag.ROLE_TAGS, CacheFlag.ONLINE_STATUS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        shardmanager = builder.addEventListeners(
                new Initialized(),
                new RegisterSlashCommands(),
                new BulkDelete(),
                new TicketCloseButton(),
                new E6BotCommands(),
                new Greeting(),
                new GiveNewRole(),
                new DailyPost(),
                new voteOut(),
                new TemporaryVoice(),
                new TempChannel(),
                new TempChannelDeletion(),
                new Moderation(),
                new CommandHandler(),
                new TimeChecker(),
                new TimeoutMenu(),
                new VerifiedChecker(),
                new Verify(),
                new TicketButtons()).build();

    }

    public static ShardManager getShardmanager() {
        return shardmanager;
    }

    public static void main(String[] args) throws IOException {
        //Create the config directory shoud it not exist
        createBaseFolderStructure();
        //Get both the path of the test token and the Normal token
        File token = new File("token.chorus");
        File testtoken = new File("testtoken.chorus");
        System.out.println(Arrays.stream(args).toList());
        //Use the testtoken when  --test is used to launch
        if (Arrays.stream(args).toList().toString().contains("--test")) {
            BufferedReader reader = new BufferedReader(new FileReader(testtoken));
            Token = reader.readLine();
            Logging.printToLog("Launching test version...");
        } else {
            //Read in the token
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

    //Create the Config folder.
    public static void createBaseFolderStructure(){
        String[] configFolders = new String[]{getConfigRoot(), getLoggingDir(), getBlacklistDir(), getTicketDir()};
        for (String folderPath : configFolders) {
            File folder = new File(folderPath);
            if (!folder.exists()){
                folder.mkdir();
            }
        }
    }
}




