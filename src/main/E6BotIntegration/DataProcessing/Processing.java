package main.E6BotIntegration.DataProcessing;


import com.fasterxml.jackson.core.JsonProcessingException;
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

        if (containsBlacklistedTag(HTTPContent, false)) {
            return handleE9E6.handleE6("", "");
        } else {
            try {
                String url = getURL(HTTPContent);
                String artist = getArtist(HTTPContent);
                Logging.printToLog("Sending post: \n" + url + "\n from: \n" + HTTPContent);
                return "Artist: " + artist + "\n" + url;

            } catch (IOException e) {
                Logging.printToLog("Warning, There has been an error parsing the url json");
                return "WARNUNG: Invalider JSON Syntax. Bitte überprüfe den status der e621 Server und Informiere den bot Operator (disruption@gandhithedergrawr.com)";

            }
        }
    }


    public String Processor(String HTTPContent) throws IOException {

        if (containsBlacklistedTag(HTTPContent, true)) {
            return handleE9E6.handleE9("", "");
        } else {
            try {
                String url = getURL(HTTPContent);
                String artist = getArtist(HTTPContent);
                Logging.printToLog("Sending post: \n" + url + "\n from: \n" + HTTPContent);
                return "Artist: " + artist + "\n" + url;

            } catch (IOException e) {
                Logging.printToLog("Warning: There has been an error parsing field \"url\"");
                return "Fehler: Generischer Parserfehler";
            }
        }
    }

    public String getArtist(String content) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);
            String artist = jsonNode.path("posts").get(0).path("tags").path("artist").get(0).asText();
            if (!Objects.equals(artist, "null")) {
                return artist;
            }
            else return "Unknown Artist";
        } catch (IOException e) {
            Logging.printToLog("Warning, There has been an error parsing the url json");
            return "WARNUNG: Invalider JSON Syntax. Bitte überprüfe den status der e621 Server und Informiere den bot Operator (disruption@gandhithedergrawr.com)";

        }
    }

    public String getURL(String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(content);
        return jsonNode.path("posts").get(0).path("file").path("url").asText();
    }

    public boolean containsBlacklistedTag(String content, boolean sfw) throws IOException {
        List<String> blacklist;
        if (sfw) {
            blacklist = Blacklist.getSFWBlacklist();
        } else {
            blacklist = Blacklist.getNSFWBlacklist();
        }
        for (String s : blacklist) {
            for (String a : getGeneralTags(content)) {
                if ((a).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return true;
                }
            }
            for (String b : getSpeciesTags(content)) {
                if ((b).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return true;
                }
            }
            for (String c : getArtistTags(content)) {
                if ((c).toLowerCase().equals(s)) {
                    System.out.println("Blacklist Triggered");
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getGeneralTags(String json) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode generalTagsNode = rootNode
                    .path("posts")
                    .path(0)
                    .path("tags")
                    .path("general");

            return objectMapper.convertValue(generalTagsNode, List.class);
        } catch (Exception e) {
            Logging.printToLog("Warning: Exception thrown in \"Processing.getGeneralTags\"");
        }
        return Collections.emptyList();
    }

    public List<String> getArtistTags(String json) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode generalTagsNode = rootNode
                    .path("posts")
                    .path(0)
                    .path("tags")
                    .path("artist");

            return objectMapper.convertValue(generalTagsNode, List.class);
        } catch (Exception e) {
            Logging.printToLog("Warning: Exception thrown in \"Processing.getArtistTags\"");
        }
        return Collections.emptyList();
    }

    public List<String> getSpeciesTags (String json) throws IOException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode generalTagsNode = rootNode
                    .path("posts")
                    .path(0)
                    .path("tags")
                    .path("species");

            return objectMapper.convertValue(generalTagsNode, List.class);
        } catch (Exception e) {
            Logging.printToLog("Warning: Exception thrown in \"Processing.getSpeciesTags\"");
        }
        return Collections.emptyList();
    }
}




