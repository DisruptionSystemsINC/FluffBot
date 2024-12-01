package com.disruption.EventListeners.utility;

import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public ConfigReader(){
        createConfigLayout();
    }
    private final Properties prop = new Properties();
    public String getConfigEntryByKey(String key) {
        try {
            prop.load(new FileReader("config.chorus"));
            return prop.getProperty(key);
        } catch (IOException e) {
            FluffBot.getDragonLog().printToLog(LogLevel.CRITICAL, "Config could not be loaded. Is it present?");
        }
        return null;
    }

    public void createConfigLayout(){
        prop.setProperty("db", "data/database/db.chorus");
        prop.setProperty("logFile", "data/log/chorus.log");
        if (prop.getProperty("Token") == null){
            prop.setProperty("Token", "[YOUR TOKEN HERE]");
        }
    }


/*
  public String getConfigEntryByKey(String key) {
        StringBuilder buffer = new StringBuilder();
        Scanner scanner = new Scanner("config.chorus");
        while (scanner.hasNextLine()) {
            buffer.append(scanner.nextLine());
        }
        try {
            JsonNode node = new JsonMapper().readTree(buffer.toString());
        } catch (JsonProcessingException e) {
            Logging.printToLog("ERROR: Configuration file could not be parsed.");
        }
        throw new NullPointerException("No such configuration key: " + key);
    }
*/
}
