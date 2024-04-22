package com.disruption.E6BotIntegration.E6Wrapper;

import com.disruption.E6BotIntegration.DataProcessing.Processing;
import com.disruption.EventListeners.utility.Logging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequestHandler {


    public String getPost(String type, boolean NSFW) throws IOException {
        HttpURLConnection connection;
        BufferedReader reader;
            String URL;
        StringBuffer responseContent = new StringBuffer();
        try {
            String line;
            if (NSFW){
                URL = ("https://e621.net/posts.json?tags=rating:explicit+" + type + "+order:random+score:>100;limit=1");
            }
            else {
                URL = ("https://e621.net/posts.json?tags=rating:safe+" + type + "+order:random+score:>100;limit=1");
            }
            URL url = new URL(URL);
            System.out.println(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status != 200)
                Logging.printToLog("ENCOUNTERED HTTP-ERROR " + status);

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
        return new Processing().Processor(responseContent.toString(), NSFW);
    }
}