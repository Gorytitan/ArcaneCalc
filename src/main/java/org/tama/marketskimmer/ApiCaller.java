package org.tama.marketskimmer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiCaller {
    /**
     * Connnects to an URL to pull the String implementation of a JSON File. This is then parsed by the method that calls it
     *
     * @param apiUrl the URL string of the API you're pulling the JSON from
     * @return StringBuilder of the JSON url provided
     * @throws IOException Bad url or network issues can cause this
     */
    public static StringBuilder getJSON(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();

        //200 ok
        if (responseCode != 200) throw new RuntimeException("HttpResponseCode: " + responseCode);
        StringBuilder informationString = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            informationString.append(scanner.nextLine());
        }
        scanner.close();
        connection.disconnect();

        return informationString;
    }
}

