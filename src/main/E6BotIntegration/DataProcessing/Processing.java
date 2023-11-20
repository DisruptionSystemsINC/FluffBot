package main.E6BotIntegration.DataProcessing;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.E6BotIntegration.E6Wrapper.handleE9E6;
import java.io.IOException;
import java.util.Objects;

import static main.E6BotIntegration.Blacklist.Blacklist.blacklist;
import static main.E6BotIntegration.Blacklist.Blacklist.check;

public class Processing {

    public String ProcessorNSFW(String HTTPContent) {

        if (check(HTTPContent)) {
            handleE9E6.handleE6("", "");
            return "Blacklist Getriggert, Lade Zufälligen Post...";
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(HTTPContent);
                String url = jsonNode.path("posts").get(0).path("file").path("url").asText();
                if (!Objects.equals(url, "null")) {
                    return url;
                } else {
                    handleE9E6.handleE6("", "");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Warning, There has been an error parsing the url json");
                return "WARNUNG: Invalider JSON Syntax. Bitte überprüfe den status der e621 Server und Informiere den bot Operator (disruption@gandhithedergrawr.com)";

            }
            return "Fehler bei Verarbeitung: Timeout";
        }
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




