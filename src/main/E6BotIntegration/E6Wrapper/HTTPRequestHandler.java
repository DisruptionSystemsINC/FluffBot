package main.E6BotIntegration.E6Wrapper;
import main.E6BotIntegration.Commands.E6BotCommands;
import main.E6BotIntegration.DataProcessing.Processing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequestHandler {

    public static HttpURLConnection connection;

    public static StringBuffer responseContent;
    public static BufferedReader reader;

    public static void e6GET(String type) {
            String e6URL;
        try {
            responseContent = new StringBuffer();
            String line;
            String StandardTags = "tags=order%3Arandom+gay+score%3A>500";
            if (E6BotCommands.tags == null) {
                e6URL = ("https://e621.net/posts.json?" + StandardTags + ";limit=1");
                System.out.println(e6URL);
            } else {
                e6URL = ("https://e621.net/posts.json?tags=" + E6BotCommands.tags + "+order:random;limit=1");
                System.out.println(e6URL);
            }
            URL url = new URL(e6URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status != 200)
                System.out.println("HTTP-ERROR" + status);

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

                connection.disconnect();

            } else {

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
                connection.disconnect();
                Processing.Processor();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }

    public static void e9GET(String type) {
        String e6URL;
        try {
            responseContent = new StringBuffer();
            String line;
            String StandardTags = "tags=order:random+rating:safe";
            if (E6BotCommands.tags == null) {
                e6URL = ("https://e621.net/posts.json?" + StandardTags + ";limit=1");
                System.out.println(e6URL);
            } else {
                e6URL = ("https://e621.net/posts.json?tags=" + E6BotCommands.tags + "+order:random;limit=1");
                System.out.println(e6URL);
            }
            URL url = new URL(e6URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status != 200)
                System.out.println("HTTP-ERROR" + status);

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

                connection.disconnect();

            } else {

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
                connection.disconnect();
                Processing.Processor();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }
}