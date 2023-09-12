package org.tama.marketskimmer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.Date;

public class Market {
    private static final int MARKET_DATA_DURATION_DAYS = 7;

    private static JSONArray getModStatsJson(String modUrlName) throws IOException {
        StringBuilder jsonStringBuilder = ApiCaller.getJSON("https://api.warframe.market/v1/items/" + modUrlName + "/statistics");
        //turn the string into a json
        JSONObject wrapper;
        try {
            JSONParser parse = new JSONParser();
            wrapper = (JSONObject) parse.parse(String.valueOf(jsonStringBuilder));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        JSONObject payload = (JSONObject) wrapper.get("payload");
        JSONObject statisticsClosed = (JSONObject) payload.get("statistics_closed");
        return (JSONArray) statisticsClosed.get("90days");
    }

    public static double[] getModStats(String modUrlName) throws IOException {
        JSONArray stats = getModStatsJson(modUrlName);
        if (stats == null) return new double[]{0, 0};
        double sumMax = 0;
        double sumUnranked = 0;
        double volMax = 0;
        double volUnranked = 0;
        for (Object stat : stats) {
            JSONObject statJson = (JSONObject) stat;
            Date dateDaysFromToday = DateManagement.subtractDaysFromToday(MARKET_DATA_DURATION_DAYS);
            Date dateFromJson = DateManagement.extractDateFromJson(((JSONObject) stat).get("datetime").toString());
            if (dateDaysFromToday.after(dateFromJson)) continue;
            if (Double.parseDouble(statJson.get("mod_rank").toString()) == 0) {
                sumUnranked += Double.parseDouble(statJson.get("wa_price").toString()) * Double.parseDouble(statJson.get("volume").toString());
                volUnranked += Double.parseDouble(statJson.get("volume").toString());
            } else {
                sumMax += Double.parseDouble(statJson.get("wa_price").toString()) * Double.parseDouble(statJson.get("volume").toString());
                volMax += Double.parseDouble(statJson.get("volume").toString());
            }
        }
        return new double[]{sumMax / Math.max(volMax, 1) - sumUnranked / Math.max(volUnranked, 1), volMax};
    }

    public static double[] getArcaneStats(String urlName) throws IOException {
        StringBuilder jsonStringBuilder = ApiCaller.getJSON("https://api.warframe.market/v1/items/" + urlName + "/statistics");
        //turn the string into a json
        JSONObject wrapper;
        try {
            JSONParser parse = new JSONParser();
            wrapper = (JSONObject) parse.parse(String.valueOf(jsonStringBuilder));
        } catch (Exception e) {
            System.out.println(e);
            return new double[]{0, 0};
        }
        JSONObject payload = (JSONObject) wrapper.get("payload");
        JSONObject statisticsClosed = (JSONObject) payload.get("statistics_closed");
        JSONArray statisticsLastNinetyDays = (JSONArray) statisticsClosed.get("90days");
        double sumPrice = 0;
        int volume = 0;
        for (Object stat : statisticsLastNinetyDays) {
            JSONObject statJson = (JSONObject) stat;
            Date dateDaysFromToday = DateManagement.subtractDaysFromToday(MARKET_DATA_DURATION_DAYS);
            Date dateFromJson = DateManagement.extractDateFromJson(((JSONObject) stat).get("datetime").toString());
            if (dateDaysFromToday.after(dateFromJson)) continue;
            if (Double.parseDouble(statJson.get("mod_rank").toString()) == 5) {
                sumPrice += Double.parseDouble(statJson.get("wa_price").toString()) * Integer.parseInt(statJson.get("volume").toString());
                volume += Integer.parseInt(statJson.get("volume").toString());
            }
        }
        return new double[] {sumPrice / volume, volume};

    }
}
