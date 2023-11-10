package main.E6BotIntegration.E6Wrapper;

public class handleE9E6 {
    public static String handleE6(String type, String customType){
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
            default -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET(customType);
            }
        }
    }
    public static String handleE9(String type, String customType){
        switch(type){
            case ("hug") ->{
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET("hug");
            }
            case("cuddles") ->{
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET("cuddling");
            }
            case ("smooch") ->{
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET("kissing");
            }
            case ("sleep") ->{
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET("sleeping");
            }
            default -> {
                return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET(customType);
            }
        }

    }
}
