package main.E6BotIntegration.Blacklist;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.FluffBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Blacklist {
        public static List<String> getSFWBlacklist() throws IOException {

            File blacklistfile = new File(FluffBot.getBlacklistDir()+"sfwblacklist.json");
            if (!blacklistfile.exists()){
                blacklistfile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(blacklistfile));
            String BlacklistJson = reader.readLine();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(BlacklistJson);
            List<String> blacklist = new ArrayList<>();
            for (int i = 0; i < jsonNode.get("tags").size(); i++){
                String tag = jsonNode.get("tags").get(i).asText();
                blacklist.add(tag);
            }
            return blacklist;
        }

        public static List<String> getNSFWBlacklist() throws IOException {


            File blacklistfile = new File(FluffBot.getBlacklistDir()+"blacklist.json");
            if (!blacklistfile.exists()){
                blacklistfile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(blacklistfile));
            String BlacklistJson = reader.readLine();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(BlacklistJson);
            List<String> blacklist = new ArrayList<>();
            for (int i = 0; i < jsonNode.get("tags").size(); i++){
                String tag = jsonNode.get("tags").get(i).asText();
                blacklist.add(tag);
            }
            return blacklist;
        }
}
