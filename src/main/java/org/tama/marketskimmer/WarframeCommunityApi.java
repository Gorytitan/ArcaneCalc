package org.tama.marketskimmer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WarframeCommunityApi {
    public static List<Mod> getR10Mods() throws Exception {
        JSONArray mods = getMods();
        List<Mod> fusionLimitTenMods = new ArrayList<>();
        for (Object objectMod :
                mods) {
            JSONObject jsonMod = (JSONObject) objectMod;
            //there can be an issue if theres a legit "expert" mod that isn't primed
            //Same issue with it vise versa
            //TODO check all expert mods and primed mods to make sure this won't break
            if (    jsonMod.get("fusionLimit") != null
                    && ((Long) jsonMod.get("fusionLimit")).intValue() == 10
                    && !(jsonMod.get("uniqueName").toString().toLowerCase().contains("expert")
                    && !jsonMod.get("name").toString().toLowerCase().contains("prime"))) {
                String urlName = jsonMod.get("name").toString().toLowerCase().replace(" ", "_");
                urlName = urlName.replace("-", "_");
                String rarity = jsonMod.get("rarity").toString();
                int maxRank = ((Long) jsonMod.get("fusionLimit")).intValue();
                fusionLimitTenMods.add(new Mod(urlName, rarity, maxRank));
            }
        }
        return fusionLimitTenMods;
    }

    private static JSONArray getMods() throws IOException, ParseException {
        URL modUrl = new URL("https://raw.githubusercontent.com/WFCD/warframe-items/master/data/json/Mods.json");
        HttpsURLConnection connection = (HttpsURLConnection) modUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int requestCode = connection.getResponseCode();

        if (requestCode != 200)
            throw new RuntimeException("HTTP Response Code: " + requestCode);
        StringBuilder informationString = new StringBuilder();
        Scanner scanner = new Scanner(modUrl.openStream());

        while (scanner.hasNext()) {
            informationString.append(scanner.nextLine());
        }
        scanner.close();
        connection.disconnect();
        JSONParser parse = new JSONParser();
        return (JSONArray) parse.parse(String.valueOf(informationString));
    }
}
