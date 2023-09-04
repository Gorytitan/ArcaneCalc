package org.tama.marketskimmer;

public class Mod {
    protected String urlName;
    protected int endoCost;
    protected int maxRank;

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
    }

    @Override
    public String toString() {
        return "Mod{" +
                "urlName='" + urlName + '\'' +
                ", endoCost=" + endoCost +
                ", maxRank=" + maxRank +
                '}';
    }
}
