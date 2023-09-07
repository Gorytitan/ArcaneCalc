package org.tama.marketskimmer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiCaller {
    public static StringBuilder getJSON(String apiUrl) throws Exception{
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
    public static JSONObject getJsonObject(String apiurl) throws Exception{
        JSONParser parser = new JSONParser();
        StringBuilder json = getJSON(apiurl);
        return (JSONObject) parser.parse(String.valueOf(json));
    }
    public static JSONArray getJsonArray(String apiurl) throws Exception{
        JSONParser parser = new JSONParser();
        StringBuilder json = getJSON(apiurl);
        return (JSONArray) parser.parse(String.valueOf(json));
    }
}
