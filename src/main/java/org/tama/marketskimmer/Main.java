package org.tama.marketskimmer;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
  //TODO add javadoc comments to everything because BORED
  public static void main(String[] args) throws Exception {
    //grabMods();
    grabArcanes();
  }

  public static void grabMods() throws Exception {
    List<Mod> viableMods = WarframeCommunityApi.getR10Mods();
    viableMods.sort((o1, o2) -> Double.compare(o2.endoPlatRatio, o1.endoPlatRatio));
    System.out.println(viableMods);
    FileWriter writer = new FileWriter("maxedModProfit.csv");
    writer.write("Mod, Plat per 10k Endo, Plat per Mod, Volume\n");
    for (Mod mod : viableMods) {
      writer.write(
          mod.urlName + "," + mod.endoPlatRatio + "," + mod.platPerMod + "," + mod.volume + "\n");
    }
    writer.close();
  }

  public static void grabArcanes() throws IOException {
    String[] imLazy = {"Molt Augmented", "Cascadia Flare", "Molt Reconstruct", "Eternal Onslaught",
        "Eternal Logistics", "Emergence Dissipate", "Emergence Savior", "Emergence Renewed",
        "Eternal Eradicate", "Fractalized Reset", "Cascadia Overcharge", "Cascadia Accuracy",
        "Cascadia Empowered", "Molt Vigor", "Molt Efficiency"};
    List<Arcane> arcaneList = new ArrayList<>();
    for (String arcane : imLazy) {
      String arcaneUrl = arcane.toLowerCase().replace(" ", "_");
      arcaneList.add(new Arcane(arcaneUrl));
    }
    arcaneList.sort(Comparator.comparingDouble(o -> o.averagePriceMaxed));
    FileWriter writer = new FileWriter("ArcaneCalc.csv");
    writer.write("Arcane, Average Plat, Volume\n");
    for (Arcane arcane : arcaneList) {
      writer.write(arcane.urlName + "," + arcane.averagePriceMaxed + "," + arcane.volume+"\n");
    }
    writer.close();
  }
}
