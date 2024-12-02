package com.disruption.EventListeners.utility;

import com.disruption.FluffBot;
import com.disruptionsystems.logging.LogLevel;

import java.io.FileReader;
import java.io.FileWriter;
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
        try {
            prop.load(new FileReader("config.chorus"));
        } catch (IOException e) {
            FluffBot.getDragonLog().printToLog(LogLevel.ERROR, "Config could not be loaded. Is it present?");
        }
        if (prop.getProperty("db") == null) {
            prop.setProperty("db", "jdbc:sqlite:data/database/db.chorus");
        }
        if (prop.getProperty("logLocation") == null) {
            prop.setProperty("logLocation", "data/log/chorus.log");
        }
        if (prop.getProperty("Token") == null){
            prop.setProperty("Token", "[YOUR TOKEN HERE]");
        }
        if (prop.getProperty("TestToken") == null){
            prop.setProperty("TestToken", "[YOUR TEST TOKEN HERE]");
        }
        try {
            prop.store(new FileWriter("config.chorus"), null);
        } catch (IOException e) {
            FluffBot.getDragonLog().printToLog(LogLevel.ERROR, "Config could not be stored. Is it present?");
        }
    }
}
