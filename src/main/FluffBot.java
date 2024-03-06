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
import main.EventListeners.Voice.TempChannel;
import main.EventListeners.buttonContextInteractionEvent.TicketButtons;
import main.EventListeners.buttonContextInteractionEvent.TicketCloseButton;
import main.EventListeners.utility.Logging;
import main.EventListeners.utility.OnboardingSetup;
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
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


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
                new OnboardingSetup(),
                new GiveNewRole(),
                new DailyPost(),
                new voteOut(),
                new TemporaryVoice(),
                new TempChannel(),
                new TempChannelDeletion(),
                new Moderation(),
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
            //DANGER!!!! Will erase an array of predefined roles from all users.
            if (Arrays.stream(args).toList().toString().contains("--setOnboarding")) {
                System.out.println("WARNING: ONBOARDING OVERRIDE ACTIVE. THIS WILL DELETE ALL SELECTION ROLES. ARE YOU SURE THAT IS WHAT YOU WANT TO DO? y/n");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.equals("y")) {
                    Logging.printToLog("Onboarding override active");
                    isOnboarding = true;
                } else {
                    //Exit if the answer is not y
                    Logging.printToLog("ANSWER WAS NOT y, ABORTING");
                    System.exit(0);
                }
            }
        } catch (LoginException e) {
            System.out.println("ERROR: Invalid or incomplete Bot Token");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Create the Config folder.
    public static void createBaseFolderStructure(){
        File FolderRoot = new File(getConfigRoot());
        if (!FolderRoot.exists()){
            FolderRoot.mkdir();
        }
        File logs = new File(getLoggingDir());
        if (!logs.exists()){
            logs.mkdir();
        }
        File blacklist = new File(getBlacklistDir());
        if (!blacklist.exists()){
            blacklist.mkdir();
        }
        File tickets = new File(getTicketDir());
        if (!tickets.exists()){
            tickets.mkdir();
        }
    }
}




