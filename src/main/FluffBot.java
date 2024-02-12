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
        File token = new File("token.chorus");
        File testtoken = new File("testtoken.chorus");
        System.out.println(Arrays.stream(args).toList());
        if (Arrays.stream(args).toList().toString().contains("--test")) {
            BufferedReader reader = new BufferedReader(new FileReader(testtoken));
            Token = reader.readLine();
            Logging.printToLog("Launching test version...");
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(token));
            Token = reader.readLine();
        }
        try {
            FluffBot bot = new FluffBot();
            if (Arrays.stream(args).toList().toString().contains("--setOnboarding")) {
                System.out.println("WARNING: ONBOARDING OVERRIDE ACTIVE. THIS WILL DELETE ALL SELECTION ROLES. ARE YOU SURE THAT IS WHAT YOU WANT TO DO? y/n");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.equals("y")) {
                    Logging.printToLog("Onboarding override active");
                    isOnboarding = true;
                } else {
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
}




