package com.disruption.E6BotIntegration.Blacklist;

import com.disruption.FluffBot;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Blacklist {

    //Can probably be simplified into a single function with a boolean switch.
    //Stuff for Fluffbot 6
        public static List<String> getSFWBlacklist() throws IOException {
            //Create a new file object that should contain the blacklist
            File blacklistfile = new File(FluffBot.getBlacklistDir()+"sfwblacklist.json");
            //Make sure the file actually exists before
            if (!blacklistfile.exists()){
                blacklistfile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(blacklistfile));

            //Read the Line of Json. This should never be more than one line
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

        //Same as above, but for NSFW
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
