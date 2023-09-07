package org.tama.marketskimmer;


import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Main {
    /*TODO
    *   1. make modstats, which holds the difference between each thingy. Uses JSONArray as parameter
    *   2. redo all apis with apicaller just to make speenah happy DONE
    *   3. modstats needs to add all the averages * volume and then dived by total volume afterwards,
    *       probably iterate using enhanced for loop
    *   4. delete unnecessary code once done
    * */
    public static void main(String[] args) throws Exception {
        Market.getStats("primed_fulmination");
        //run(null);
    }

    public static void run(String[] args) throws Exception {
        List<Mod> viableMods = WarframeCommunityApi.getR10Mods();
        viableMods.sort((o1, o2) -> Double.compare(o2.endoPlatRatio, o1.endoPlatRatio));
        System.out.println(viableMods);
        FileWriter writer = new FileWriter("csv.csv");
        for (Mod mod : viableMods) {
            writer.write(mod.urlName + ", " + mod.endoPlatRatio + "\n");
        }
        writer.close();
    }
}