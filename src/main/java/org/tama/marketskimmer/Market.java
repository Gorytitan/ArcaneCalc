package org.tama.marketskimmer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Market {
    public static ArrayList<Order> getOrders(String mod) throws Exception {
        //https://api.warframe.market/v1
        URL url = new URL("https://api.warframe.market/v1/items/" + mod + "/orders");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        //Check if connect is made
        int responseCode = conn.getResponseCode();

        //200 ok
        if (responseCode != 200)
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        StringBuilder informationString = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            informationString.append(scanner.nextLine());
        }
        scanner.close();
        conn.disconnect();

        //turn the string into a json
        JSONParser parse = new JSONParser();
        JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(informationString));
        JSONArray arr = (JSONArray) ((JSONObject) dataObject.get("payload")).get("orders");
        ArrayList<Order> orders = new ArrayList<>();
        for (Object objectOrder :
                arr) {
            JSONObject jsonOrder = (JSONObject) objectOrder;
            int p = ((Long) jsonOrder.get("platinum")).intValue();
            int mr = ((Long) jsonOrder.get("mod_rank")).intValue();
            boolean wtb = jsonOrder.get("order_type").toString().equals("buy");
            boolean online = !((JSONObject) jsonOrder.get("user")).get("status").toString().equals("offline");
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            DateTime lastUpdated = formatter.parseDateTime(jsonOrder.get("last_update").toString());
            Order order = new Order(p, mr, wtb, online, lastUpdated);
            orders.add(order);
        }
        orders.sort(null);
        System.out.println(orders);

        return orders;
    }
    public static ArrayList<Order>[] splitOrders(ArrayList<Order> arr) {
        return null;
    }
}
