package org.tama.marketskimmer;

public class Mod {
    protected String urlName;
    protected double endoCost;
    protected double endoPlatRatio;
    protected double platPerMod;
    protected int volume;
    public Mod(String urlName, String rarity) throws Exception {
        this.urlName = urlName;
        System.out.println(urlName);
        switch (rarity) {
            case "Common" -> endoCost = 10230;
            case "Uncommon" -> endoCost = 20460;
            case "Rare" -> endoCost = 30690;
            case "Legendary" -> endoCost = 40920;
            default -> throw new Exception(rarity);
        }
        updateRatio();
    }

    public void updateRatio() throws Exception {
        double[] stats = Market.getStats(urlName);
        endoPlatRatio = stats[0] / endoCost * 10000;
        platPerMod = stats[0];
        volume = (int) stats[1];

    }

    @Override
    public String toString() {
        return urlName + ", " + endoPlatRatio + "\n";
    }
}
