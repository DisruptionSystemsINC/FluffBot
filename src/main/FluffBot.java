package main;

import main.E6BotIntegration.Commands.E6BotCommands;
import main.EventListeners.BotInit.DailyPost;
import main.EventListeners.BotInit.Initialized;
import main.EventListeners.BotInit.TempChannelDeletion;
import main.EventListeners.Moderation.Moderation;
import main.EventListeners.Roles.GiveNewRole;
import main.EventListeners.Roles.Greeting;
import main.EventListeners.SlashCommands.BulkDelete;
import main.EventListeners.SlashCommands.TemporaryVoice;
import main.EventListeners.Voice.Lavaplayer.events.ButtonHandler;
import main.EventListeners.Voice.TempChannel;
import main.EventListeners.buttonContextInteractionEvent.TicketButtons;
import main.EventListeners.buttonContextInteractionEvent.TicketCloseButton;
import main.EventListeners.utility.Logging;
import main.EventListeners.utility.TimeChecker;
import main.EventListeners.utility.voteOut;
import main.SlashCommands.RegisterSlashCommands;
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


public class FluffBot {
    private static String Token;
    public static boolean isOnboarding;
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
                new ButtonHandler(),
                new TimeChecker(),
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




