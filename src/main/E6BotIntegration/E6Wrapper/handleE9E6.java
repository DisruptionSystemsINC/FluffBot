package main.E6BotIntegration.E6Wrapper;

import java.io.IOException;

public class handleE9E6 {
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
