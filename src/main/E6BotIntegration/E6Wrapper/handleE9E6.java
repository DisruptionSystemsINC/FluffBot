package main.E6BotIntegration.E6Wrapper;


import static main.E6BotIntegration.E6Wrapper.HTTPRequestHandler.e9GET;

public class handleE9E6 {
    public static void handleE6(String type, String customType){
    }
    public static void handleE9(String type, String customType){
        switch(type){
            case ("hug") ->{
                e9GET("hug");
            }
            case("cuddles") ->{
                e9GET("cuddling");
            }
            case ("smooch") ->{
                e9GET("kissing");
            }
            case ("sleep") ->{
                e9GET("sleeping");
            }
            default -> {
                e9GET(customType);
            }
        }

    }
}
