package main.E6BotIntegration.E6Wrapper;

import java.io.IOException;

public class handleE9E6 {
    //Handle the E621 tags and E926 tags seperately for ease of use. This honestly could just be one function but would require a Considerable Refactor to change
    public static String handleE6(String type, String customType) throws IOException {
        if (type.equals("custom")){
            return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET(customType);
        }
        return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e6GET(type);
    }
    public static String handleE9(String type, String customTags) throws IOException {
        if (type.equals("custom")){
            return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET(customTags);
        }
        return new main.E6BotIntegration.E6Wrapper.HTTPRequestHandler().e9GET(type);
    }
}
