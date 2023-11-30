package main.E6BotIntegration.E6Wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;

public class handleE9E6 {
    public static String handleE6(String type, String customType) throws JsonProcessingException {
        switch(type){
            case ("gay-anal") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("gay+anal");
            }
            case ("gay-threesome") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("gay+threesome");
            }
            case("lesbian") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("lesbian");
            }
            case ("female-solo") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("female+solo");
            }
            case ("male-solo") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("male+solo");
            }
            case ("straight") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("straight");
            }
            case ("lesbian-threesome") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("lesbian+threesome");
            }
            case ("gay-oral") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("gay+fellatio");
            }
            case ("lesbian-oral") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("lesbian+fellatio");
            }
            case ("straight-oral") -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET("straight+fellatio");
            }
            default -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET(customType);
            }
        }
    }
    public static String handleE9(String type, String customTags) throws JsonProcessingException {
        switch(type){
            case ("hug") ->{
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET("hug");
            }
            case("cuddling") ->{
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET("cuddling");
            }
            case ("kissing") ->{
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET("kissing");
            }
            case ("sleeping") ->{
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET("sleeping");
            }
            default -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET(customTags);
            }
        }

    }
}
