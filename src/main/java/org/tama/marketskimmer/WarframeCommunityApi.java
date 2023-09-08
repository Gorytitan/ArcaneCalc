package org.tama.marketskimmer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WarframeCommunityApi {
    public static List<Mod> getR10Mods() throws Exception {
        JSONArray mods = ApiCaller.getJsonArray("https://raw.githubusercontent.com/WFCD/warframe-items/master/data/json/Mods.json");
        System.out.println("Finished getting mod list");
        List<Mod> fusionLimitTenMods = new ArrayList<>();
        for (Object objectMod :
                mods) {
            JSONObject jsonMod = (JSONObject) objectMod;
            if (    jsonMod.get("fusionLimit") != null
                    && ((Long) jsonMod.get("fusionLimit")).intValue() == 10
                    && !(jsonMod.get("uniqueName").toString().toLowerCase().contains("expert")
                    && !jsonMod.get("name").toString().toLowerCase().contains("prime"))) {
                String urlName = jsonMod.get("name").toString().toLowerCase().replace(" ", "_");
                urlName = urlName.replace("-", "_");
                String rarity = jsonMod.get("rarity").toString();
                fusionLimitTenMods.add(new Mod(urlName, rarity));
            }
        }
        return fusionLimitTenMods;
    }
}
