package org.tama.marketskimmer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class Market {
    public static ArrayList<Order> getOrders(String mod) throws Exception {
        //https://api.warframe.market/v1
        JSONObject dataObject = getOrderJsons(mod);
        if (dataObject == null) {
            return null;
        }
        JSONArray arr = (JSONArray) ((JSONObject) dataObject.get("payload")).get("orders");
        ArrayList<Order> orders = new ArrayList<>();
        for (Object objectOrder : arr) {
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
        //System.out.println(orders);

        return orders;
    }

    private static JSONObject getOrderJsons(String mod) throws Exception {
        StringBuilder jsonStringBuilder = ApiCaller.getJSON("https://api.warframe.market/v1/items/" + mod + "/orders");
        //turn the string into a json
        try {
            JSONParser parse = new JSONParser();
            return (JSONObject) parse.parse(String.valueOf(jsonStringBuilder));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static int getCurrentDifference(ArrayList<Order> arr) {
        if (arr == null) return -1;
        ArrayList<Order> wtbOrders = new ArrayList<>();
        ArrayList<Order> wtsOrders = new ArrayList<>();
        for (Order order : arr) {
            if (order.wtb && order.online && order.modrank == 0) wtbOrders.add(order);
            else if (!order.wtb && order.online && order.modrank == 10) wtsOrders.add(order);
        }
        wtbOrders.sort(null);
        wtsOrders.sort(null);
        if (wtbOrders.isEmpty() || wtsOrders.isEmpty()) {
            return -1;
        }
        return wtsOrders.get(0).platinum - wtbOrders.get(wtbOrders.size() - 1).platinum;
    }

    public static JSONArray getStats(String modUrlName) throws Exception {
        JSONObject wrapper = ApiCaller.getJsonObject("https://api.warframe.market/v1/items/" + modUrlName + "/statistics");
        JSONObject payload = (JSONObject) wrapper.get("payload");
        JSONObject statisticsClosed = (JSONObject) payload.get("statistics_closed");
        JSONArray pastTwoDayStats = (JSONArray) statisticsClosed.get("48hours");
        return pastTwoDayStats;
    }
    public static double[] getAverageDifference(String modUrlName) throws Exception{
        JSONArray stats = getStats(modUrlName);
        int sumMax = 0;
        int sumUnranked = 0;
        double volMax = 0;
        double volUnranked = 0;
        for (Object stat :
                stats) {
            JSONObject statJson = (JSONObject) stat;
            if (((Long) statJson.get("mod_rank")).intValue() == 0){
                sumUnranked += ((Long) statJson.get("wa_price")).intValue() * ((Long) statJson.get("volume")).intValue();
                volUnranked += ((Long) statJson.get("volume")).intValue();
            }
            else {
                sumMax += ((Long) statJson.get("wa_price")).intValue() * ((Long) statJson.get("volume")).intValue();
                volMax += ((Long) statJson.get("volume")).intValue();
            }
        }
        return new double[] {sumUnranked / volUnranked, sumMax / volMax};
    }
}
