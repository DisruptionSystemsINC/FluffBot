package main.E6BotIntegration.E6Wrapper;
import main.E6BotIntegration.Commands.E6BotCommands;
import main.E6BotIntegration.DataProcessing.Processing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequestHandler {


    public String e6GET(String type) {
        HttpURLConnection connection;
        BufferedReader reader;
            String e6URL;
        StringBuffer responseContent = new StringBuffer();
        try {
            String line;
            e6URL = ("https://e621.net/posts.json?tags=" + type + "+order:random;limit=1");
            System.out.println(e6URL);
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
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return new Processing().Processor(responseContent.toString());
    }

    public String e9GET(String type) {
        HttpURLConnection connection;
        BufferedReader reader;
        StringBuffer responseContent;
        String e6URL;
        try {
            responseContent = new StringBuffer();
            String line;
            e6URL = ("https://e621.net/posts.json?tags=" + type + "+rating:safe+order:random;limit=1");
            System.out.println(e6URL);
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
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return new Processing().Processor(responseContent.toString());
    }
}