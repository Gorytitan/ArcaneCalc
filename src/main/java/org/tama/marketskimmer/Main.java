package org.tama.marketskimmer;


import java.io.FileWriter;
import java.util.List;

public class Main {
    //TODO do it by last week, instead of past 48 hours
    public static void main(String[] args) throws Exception {
        run();
    }

    public static void run() throws Exception {
        List<Mod> viableMods = WarframeCommunityApi.getR10Mods();
        viableMods.sort((o1, o2) -> Double.compare(o2.endoPlatRatio, o1.endoPlatRatio));
        System.out.println(viableMods);
        FileWriter writer = new FileWriter("maxedModProfit.csv");
        writer.write("Mod, Plat per 10k Endo, Plat per Mod, Volume\n");
        for (Mod mod : viableMods) {
            writer.write(mod.urlName + ", " + mod.endoPlatRatio + "," + mod.platPerMod + "," + mod.volume + "\n");
        }
        writer.close();
    }
}