package org.example;


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

public class Main {
    public static class Order implements Comparable<Order>{
        protected int platinum;
        protected int modrank;
        protected boolean wtb;
        protected boolean online;
        protected DateTime lastUpdated;

        public Order(int p, int mr, boolean wtb, boolean online, DateTime lastUpdated) {
            platinum = p;
            modrank = mr;
            this.wtb = wtb;
            this.online = online;
            this.lastUpdated = lastUpdated;
        }

        @Override
        public String toString() {
            return "\nplatinum: " + platinum
                    + "\nrank: " + modrank
                    + "\nwtb: " + wtb
                    + "\nonline: " + online
                    + "\nlastUpdate: " + lastUpdated;
        }

        @Override
        public int compareTo(Order o) {
            return this.platinum - o.platinum;
        }
    }

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

    public static void main(String[] args) throws Exception {
        ArrayList<Order> orders = getOrders("primed_fulmination");
        /*//https://api.warframe.market/v1
        try {
            URL url = new URL("https://api.warframe.market/v1/items/primed_fulmination/orders");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            //200 ok
            if (responseCode != 200)
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            else {
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

                //sorting by price for easier access
                Collections.sort(arr, new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        return ((Long) o1.get("platinum")).intValue() - ((Long) o2.get("platinum")).intValue();
                    }
                });
                //delete old posts (might not be needed if i just do online ones
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                DateTime now = new DateTime();
                DateTime old = now.minusDays(30);
                for (int i = arr.size() - 1; i >= 0; i--) {
                    DateTime dt = formatter.parseDateTime(((JSONObject) arr.get(i)).get("last_update").toString());
                    if (dt.isBefore(old)) {
                        arr.remove(i);
                    }
                }

                //seperate into 2 arrays, one for unranked one for maxed
                JSONArray unranked = new JSONArray();
                JSONArray maxed = new JSONArray();
                for (Object order :
                        arr) {
                    JSONObject orderjson = (JSONObject) order;
                    switch (((Long) orderjson.get("mod_rank")).intValue()) {
                        case 0:
                            if (orderjson.get("order_type").toString().equals("buy")
                                    && ((((JSONObject) orderjson.get("user")).get("status").toString().equals("online"))
                                    || ((JSONObject) orderjson.get("user")).get("status").toString().equals("ingame"))) {
                                unranked.add(orderjson);
                            }
                            break;
                        case 10:
                            if (orderjson.get("order_type").toString().equals("sell")
                                    && ((((JSONObject) orderjson.get("user")).get("status").toString().equals("online"))
                                    || ((JSONObject) orderjson.get("user")).get("status").toString().equals("ingame"))) {
                                maxed.add(orderjson);
                            }
                            break;
                    }
                }
                System.out.println(maxed);
                *//*TODO  i seperated them into wts maxed and wtb unranked, take average of the 3 cheapest each and find the difference. Make a class for a mod with this code inside of it then
                  TODO another method to pull information from a spread sheet to do this for each mod. Make a compare method for the mod class and then just collections.sort lol
                  TODO then output that in a new spreadsheet?
                *//*
            }

        } catch (Exception e) {
            System.out.print(e);
        }*/

    }
}