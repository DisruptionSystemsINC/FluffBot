package main.E6BotIntegration.E6Wrapper;

import main.E6BotIntegration.DataProcessing.Processing;
import main.EventListeners.utility.Logging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequestHandler {


    public String e6GET(String type) throws IOException {
        HttpURLConnection connection;
        BufferedReader reader;
            String e6URL;
        StringBuffer responseContent = new StringBuffer();
        try {
            String line;
            e6URL = ("https://e621.net/posts.json?tags=" + type + "+rating:explicit+order:random+score:>100;limit=1");
            System.out.println(e6URL);
            URL url = new URL(e6URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status != 200)
                Logging.printToLog("ENCOUNTERED HTTP-ERROR " + status + " in E9");

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

        return new Processing().ProcessorNSFW(responseContent.toString());
    }

    public String e9GET(String type) throws IOException {
        HttpURLConnection connection;
        BufferedReader reader;
        StringBuffer responseContent;
        String e6URL;
        try {
            responseContent = new StringBuffer();
            String line;
            e6URL = ("https://e621.net/posts.json?tags=" + type + "+rating:safe+order:random+score:>100;limit=1");
            System.out.println(e6URL);
            URL url = new URL(e6URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status != 200)
                Logging.printToLog("ENCOUNTERED HTTP-ERROR " + status + " In E6");

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