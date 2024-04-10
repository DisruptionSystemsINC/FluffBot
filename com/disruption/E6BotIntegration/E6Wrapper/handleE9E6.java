package disruption.E6BotIntegration.E6Wrapper;

import java.io.IOException;

public class handleE9E6 {
    //Handle the E621 tags and E926 tags seperately for ease of use. This honestly could just be one function but would require a Considerable Refactor to change
    public static String handleE6(String type, String customType) throws IOException {
        if (type.equals("custom")){
            return new disruption.E6BotIntegration.E6Wrapper.HTTPRequestHandler().getPost(customType, true);
        }
        return new disruption.E6BotIntegration.E6Wrapper.HTTPRequestHandler().getPost(type, true);
    }
    public static String handleE9(String type, String customTags) throws IOException {
        if (type.equals("custom")){
            return new disruption.E6BotIntegration.E6Wrapper.HTTPRequestHandler().getPost(customTags, false);
        }
        return new disruption.E6BotIntegration.E6Wrapper.HTTPRequestHandler().getPost(type, false);
    }
}
