package main.E6BotIntegration.DataProcessing;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.E6BotIntegration.Blacklist.Blacklist;
import main.E6BotIntegration.E6Wrapper.handleE9E6;
import main.EventListeners.utility.Logging;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Processing {

    public String ProcessorNSFW(String HTTPContent) throws IOException {

        for (String s : Blacklist.getNSFWBlacklist()) {
            for (String a : getGeneralTags(HTTPContent)) {
                if ((a).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return handleE9E6.handleE9("", "");
                }
            }
            for (String b : getSpeciesTags(HTTPContent)) {
                if ((b).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return handleE9E6.handleE9("", "");
                }
            }
            for (String c : getArtistTags(HTTPContent)) {
                if ((c).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return handleE9E6.handleE9("", "");
                }
            }
        }
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(HTTPContent);
                String url = jsonNode.path("posts").get(0).path("file").path("url").asText();
                String artist = jsonNode.path("posts").get(0).path("tags").path("artist").get(0).asText();
                if (!Objects.equals(url, "null")) {
                    Logging.printToLog("Sending post: \n" + url + "\n from: \n" + HTTPContent);
                    return "Artist: " + artist + "\n" + url;
                } else {
                    handleE9E6.handleE6("", "");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Logging.printToLog("Warning, There has been an error parsing the url json");
                return "WARNUNG: Invalider JSON Syntax. Bitte überprüfe den status der e621 Server und Informiere den bot Operator (disruption@gandhithedergrawr.com)";

            }
            return  handleE9E6.handleE6("", "");
        }


    public String Processor(String HTTPContent) throws IOException {

        for (String s : Blacklist.getSFWBlacklist()) {
            for (String a : getGeneralTags(HTTPContent)) {
                if ((a).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return handleE9E6.handleE9("", "");
                }
            }
            for (String b : getSpeciesTags(HTTPContent)) {
                if ((b).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return handleE9E6.handleE9("", "");
                }
            }
            for (String c : getArtistTags(HTTPContent)) {
                if ((c).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return handleE9E6.handleE9("", "");
                }
            }
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(HTTPContent);
            String url = jsonNode.path("posts").get(0).path("file").path("url").asText();
            String artist = jsonNode.path("posts").get(0).path("tags").path("artist").get(0).asText();
            if (!Objects.equals(url, "null") || !Objects.equals(url,null)) {
                Logging.printToLog("Sending post: \n" + url + "\n from: \n" + HTTPContent);
                return "Artist: " + artist + "\n" + url;
            } else {
                Logging.printToLog("Return URL was null, Retrying");
                handleE9E6.handleE9("", "");
            }
        } catch (IOException e) {
            Logging.printToLog("Warning: There has been an error parsing field \"url\"");
            return "Fehler: Generischer Parserfehler";
        }
        Logging.printToLog("Critical Error 001: Invalid data from endpoint. \nData:\n" + HTTPContent);
        return handleE9E6.handleE9("", "");
    }

    public List<String> getGeneralTags(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode generalTagsNode = rootNode
                    .path("posts")
                    .path(0)
                    .path("tags")
                    .path("general");

            List<String> generalTags = objectMapper.convertValue(generalTagsNode, List.class);

            return generalTags;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<String> getArtistTags(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode generalTagsNode = rootNode
                    .path("posts")
                    .path(0)
                    .path("tags")
                    .path("artist");

            List<String> generalTags = objectMapper.convertValue(generalTagsNode, List.class);

            return generalTags;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<String> getSpeciesTags (String json){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode generalTagsNode = rootNode
                    .path("posts")
                    .path(0)
                    .path("tags")
                    .path("species");

            List<String> generalTags = objectMapper.convertValue(generalTagsNode, List.class);

            return generalTags;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}




