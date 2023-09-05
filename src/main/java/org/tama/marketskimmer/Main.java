package org.tama.marketskimmer;


import java.io.FileWriter;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
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