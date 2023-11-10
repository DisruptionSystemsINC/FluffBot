package main.E6BotIntegration.DataProcessing;

import main.E6BotIntegration.E6Wrapper.HTTPRequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import static main.E6BotIntegration.Blacklist.Blacklist.blacklist;

public class Processing {

    public String Processor(String HTTPContent) {

        String sIn = (HTTPContent);
        for (int i = 0; i < blacklist.length; i++) {
            if (sIn.contains(blacklist[i])) {
                System.out.println("Blacklisted tag, retrying...");
            } else {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(HTTPContent);
                    String url = objectMapper.convertValue(jsonNode.get("url"), objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
                    return url;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Warning, There has been an error parsing the url json");
                    return null;
                }
                /*
        String sIn = (HTTPRequestHandler.responseContent.toString());
        for (int i = 0; i < blacklist.length; i++) {
            if (sIn.contains(blacklist[i])) {
                System.out.println("Blacklisted tag, retrying...");
            } else {
                String[] aWords = sIn.split(",");
                for (int x = 8; x < 9; x++) {
                    String tIn = (aWords[x]);
                    String[] bWords = tIn.split(":");
                    for (int y = 0; y < bWords.length; y++) {
                        String input = (bWords[y]);
                        String output = (input.replaceAll("}", " "));
                        String in = ("https:" + output);
                        String out = (in.replace("\"", " "));
                        if (out.equals("https:null")) {
                            System.out.println("API returned NULL, retrying");
                            HTTPRequestHandler.e6GET();
                        } else {
                            ClientURL = out;
                            System.out.println(ClientURL);
                        }
                    }
                }
            }
        }
        */
            }
        }
        return "";
    }
}




