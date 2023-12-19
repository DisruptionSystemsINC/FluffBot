package main;

import main.ContextEvent.MessageContextEvent;
import main.E6BotIntegration.Commands.E6BotCommands;
import main.EventListeners.BotInit.Initialized;
import main.EventListeners.Roles.GiveNewRole;
import main.EventListeners.Roles.Greeting;
import main.EventListeners.SlashCommands.BulkDelete;
import main.EventListeners.SlashCommands.Ticket;
import main.EventListeners.utility.DailyPost;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class FluffBot {
    private static String Token;
    public static boolean isOnboarding;
    ShardManager shardmanager;

    public FluffBot() throws LoginException, IOException {

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(Token);
        builder.setActivity(Activity.watching("Furries beim chatten zu"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT);
        builder.enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOJI, CacheFlag.ROLE_TAGS, CacheFlag.ONLINE_STATUS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        shardmanager = builder.addEventListeners(
                new Initialized(),
                new Ticket(),
                new RegisterSlashCommands(),
                new BulkDelete(),
                new MessageContextEvent(),
                new E6BotCommands(),
                new Greeting(),
                new OnboardingSetup(),
                new GiveNewRole(),
                new DailyPost(),
                new voteOut()).build();

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
            Logging.printToLog("Launching test version...");
        }
        else {
            BufferedReader reader = new BufferedReader(new FileReader(token));
            Token = reader.readLine();
        }
        try {
            FluffBot bot = new FluffBot();
            if (Arrays.stream(args).toList().toString().contains("--setOnboarding")){
                System.out.println("WARNING: ONBOARDING OVERRIDE ACTIVE. ARE YOU SURE THAT IS WHAT YOU WANT TO DO? y/n");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.equals("y")) {
                    Logging.printToLog("Onboarding override active");
                    isOnboarding = true;
                }
                else {Logging.printToLog("ANSWER WAS NOT y, ABORTING"); System.exit(0);}
            }
        } catch (LoginException e) {
            System.out.println("ERROR: Invalid or incomplete Bot Token");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




