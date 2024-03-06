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
import java.util.stream.Stream;


public class Processing {

    //The processor for the NSFW version of the bot. Might be simplified into one function in another version

    public String Processor(String HTTPContent, boolean NSFW) throws IOException {
            //Check if the blacklist contains one of the tags in the post and send a new request if it does
            if (containsBlacklistedTag(HTTPContent, NSFW)) {
                if (NSFW) {
                    return handleE9E6.handleE6("", "");
                }
                else {
                    return handleE9E6.handleE9("", "");
                }
            } else {
                try {
                    //Try to get the url from e621 and check that it is not null
                    String url = getURL(HTTPContent);
                    if (url.equals("null")) {
                        if (NSFW) {
                            return handleE9E6.handleE6("", "");
                        }
                        else {
                            return handleE9E6.handleE9("", "");
                        }
                    }
                    //Get the artists of the post
                    else {
                        String artist = getArtist(HTTPContent);
                        Logging.printToLog("Sending post: \n" + url + "\n from: \n" + HTTPContent);
                        //Send the post off to be sent as a message
                        return "Artist: " + artist + "\n" + url;
                    }
                } catch (IOException e) {
                    Logging.printToLog("Warning, There has been an error parsing the url json");
                    return "WARNUNG: Invalider JSON Syntax. Bitte überprüfe den status der e621 Server und Informiere den bot Operator (Fluffbot Support Ticket)";
                }
            }
    }

    //Gets the artist of a post
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

    public boolean containsBlacklistedTag(String content, boolean NSFW) throws IOException {
        List<String> blacklist;
        if (NSFW) {
            blacklist = Blacklist.getNSFWBlacklist();
        } else {
            blacklist = Blacklist.getSFWBlacklist();
        }
        return  Stream.concat(Stream.concat(getGeneralTags(content).stream(),
                getArtistTags(content).stream()),
                getSpeciesTags(content).stream()
        ).anyMatch(blacklist::contains);
    }

    public List<String> getGeneralTags(String json) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode generalTagsNode = rootNode
                    .path("posts")
                    .path(0)
                    .path("tags")
                    .get("general");

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




