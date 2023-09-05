package org.tama.marketskimmer;

public class Mod {
    protected String urlName;
    protected int maxRank;
    protected double endoPlatRatio;
    protected double endoCost;

    public Mod(String urlName, String rarity, int maxRank) throws Exception {
        this.urlName = urlName;
        this.maxRank = maxRank;
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
        endoPlatRatio = Market.getDifference(Market.getOrders(urlName)) / endoCost * 10000;
    }

    @Override
    public String toString() {
        return urlName + ", " + endoPlatRatio + "\n";
    }
}
