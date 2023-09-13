package org.tama.marketskimmer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.Date;

public class Market {
    private static final int MARKET_DATA_DURATION_DAYS = 7;

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
        return new double[]{sumPrice / volume, volume};

    }
}
