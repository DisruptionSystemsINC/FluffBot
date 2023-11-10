package main.E6BotIntegration.E6Wrapper;

public class handleE9E6 {
    public static String handleE6(String type, String customType){

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
