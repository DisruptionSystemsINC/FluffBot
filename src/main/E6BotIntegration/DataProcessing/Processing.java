package main.E6BotIntegration.DataProcessing;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.E6BotIntegration.E6Wrapper.handleE9E6;
import java.io.IOException;
import java.util.Objects;

import static main.E6BotIntegration.Blacklist.Blacklist.blacklist;

public class Processing {

    public String ProcessorNSFW(String HTTPContent) {

        for (int i = 0; i < blacklist.length; i++) {
            if ((HTTPContent).contains(blacklist[i])) {
                System.out.println("Blacklist Triggered");
                handleE9E6.handleE6("", "");
            }
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(HTTPContent);
            String url = jsonNode.path("posts").get(0).path("file").path("url").asText();
            System.out.println(url);
            if (!Objects.equals(url, "null")) {
                return url;
            } else {
                handleE9E6.handleE6("", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Warning, There has been an error parsing the url json");
            return null;
        }
        return "Fehler bei Verarbeitung: Timeout";
    }

    public String Processor(String HTTPContent) {

        for (int i = 0; i < blacklist.length; i++) {
            if ((HTTPContent).contains(blacklist[i])) {
                System.out.println("Blacklist Triggered");
                handleE9E6.handleE9("", "");
            }
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(HTTPContent);
            String url = jsonNode.path("posts").get(0).path("file").path("url").asText();
            System.out.println(url);
            if (!Objects.equals(url, "null")) {
                return url;
            } else {
                handleE9E6.handleE9("", "");
            }
        } catch (IOException e) {
            System.out.println("Warning, There has been an error parsing the url json");
            return null;
        }
        return "Fehler bei Verarbeitung: Timeout";
    }
}




