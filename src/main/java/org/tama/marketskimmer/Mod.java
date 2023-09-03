package org.tama.marketskimmer;

public class Mod {
    protected int maxRank;
    protected String urlName;
    protected int endoCost;

    public Mod(int maxRank, String urlName, String rarity) throws Exception {
        this.maxRank = maxRank;
        this.urlName = urlName;
        switch (rarity){
            case "Common":
                endoCost = 10230;
                break;
            case "Uncommon":
                endoCost = 20460;
                break;
            case "Rare":
                endoCost = 30690;
                break;
            case "Legendary":
                endoCost = 40920;
                break;
            default:
                throw new Exception(rarity);
        }
    }
}
